package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.AdditionalInformation;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBAdditionalInformationDatabase extends TableConfigurationDatabase {

    private enum Fields {
        ID_ADDITIONAL_INFORMATION,
        ID_CLIENT,
        CELLPHONE,
        TELEPHONE,
        EMAIL,
        SITE,
        OBSERVATION
    }

    public TBAdditionalInformationDatabase(Context context){
        super(context);
        this.Table = "TB_ADDITIONAL_INFORMATION";
    }

    public static TBAdditionalInformationDatabase newInstance(Context context){
        return new TBAdditionalInformationDatabase(context);
    }

    @Override
    public List<AdditionalInformation> Select(long clientId){
        super.openDatabaseInstance();
        try{
            List<AdditionalInformation> list = new ArrayList<AdditionalInformation>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_CLIENT.name() + " = " + clientId
                        + " Order by " + Fields.ID_CLIENT;
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order by " + Fields.ID_CLIENT;
            }

            this.cursor = this.database.rawQuery(this.SQL,null);

            if (this.cursor.getCount()>0){
                this.cursor.moveToFirst();
                AdditionalInformation additionalInformation;
                do{
                    additionalInformation = new AdditionalInformation();

                    additionalInformation.setAdditionalInformationId(this.cursor.getInt(0));
                    additionalInformation.setClientId(this.cursor.getInt(1));
                    additionalInformation.setCellphone(this.cursor.getString(2));
                    additionalInformation.setTelephone(this.cursor.getString(3));
                    additionalInformation.setEmail(this.cursor.getString(4));
                    additionalInformation.setSite(this.cursor.getString(   5));
                    additionalInformation.setObservation(this.cursor.getString(6));

                    list.add(additionalInformation);
                }while(this.cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long Insert(AdditionalInformation additionalInformation){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(),additionalInformation.getClientId());
            values.put(Fields.CELLPHONE.name(),additionalInformation.getCellphone());
            values.put(Fields.TELEPHONE.name(),additionalInformation.getTelephone());
            values.put(Fields.EMAIL.name(),additionalInformation.getEmail());
            values.put(Fields.SITE.name(),additionalInformation.getSite());
            values.put(Fields.OBSERVATION.name(),additionalInformation.getObservation());

            return this.database.insert(this.Table,null,values);

        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(AdditionalInformation additionalInformation){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(), additionalInformation.getClientId());
            values.put(Fields.CELLPHONE.name(), additionalInformation.getCellphone());
            values.put(Fields.TELEPHONE.name(), additionalInformation.getTelephone());
            values.put(Fields.EMAIL.name(), additionalInformation.getEmail());
            values.put(Fields.SITE.name(), additionalInformation.getSite());
            values.put(Fields.OBSERVATION.name(), additionalInformation.getObservation());

            this.database.update(this.Table, values,
                    Fields.ID_ADDITIONAL_INFORMATION.name() + " = " + additionalInformation.getAdditionalInformationId(),
                    null);

            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(AdditionalInformation additionalInformation){
        super.openDatabaseInstance();
        try {
            if (additionalInformation.getAdditionalInformationId() > 0) {
                this.database.delete(this.Table,
                        Fields.ID_ADDITIONAL_INFORMATION.name() + " = " + additionalInformation.getAdditionalInformationId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    private String getFields(){
        String StringFields = "";

        for(Fields Field: Fields.values()){
            StringFields += Field.name() + ",";
        }

        if (StringFields.length() > 0){
            StringFields = StringFields.substring(0,StringFields.length()-1);
        }else{
            StringFields = "*";
        }

        return StringFields;
    }
}
