package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.AdditionalInformation;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public class TBAdditionalInformationDatabase {

    private SQLiteDatabase Database;
    private String Table = "TB_ADDITIONAL_INFORMATION";
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
        this.Database = new ConfigurationDatabase(context).getReadableDatabase();
    }

    public static TBAdditionalInformationDatabase newInstance(Context context){
        return new TBAdditionalInformationDatabase(context);
    }

    public List<AdditionalInformation> SELECT(){
        try{
            List<AdditionalInformation> list = new ArrayList<AdditionalInformation>();
            String[] columns = new String[]{
                    Fields.ID_ADDITIONAL_INFORMATION.name(),
                    Fields.ID_CLIENT.name(),
                    Fields.CELLPHONE.name(),
                    Fields.TELEPHONE.name(),
                    Fields.EMAIL.name(),
                    Fields.SITE.name(),
                    Fields.OBSERVATION.name()
            };

            Cursor cursor = this.Database.query(this.Table,columns,
                    null,null,null,
                    null, Fields.ID_ADDITIONAL_INFORMATION.name() + " ASC");

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                AdditionalInformation additionalInformation;
                do{
                    additionalInformation = new AdditionalInformation();

                    additionalInformation.setAdditionalInformationId(cursor.getInt(0));
                    additionalInformation.setClientId(cursor.getInt(1));
                    additionalInformation.setCellphone(cursor.getString(2));
                    additionalInformation.setTelephone(cursor.getString(3));
                    additionalInformation.setEmail(cursor.getString(4));
                    additionalInformation.setSite(cursor.getString(   5));
                    additionalInformation.setObservation(cursor.getString(6));

                    list.add(additionalInformation);
                }while (cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }
    }

    public long INSERT(AdditionalInformation additionalInformation){
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_ADDITIONAL_INFORMATION.name(),additionalInformation.getAdditionalInformationId());
            values.put(Fields.ID_CLIENT.name(),additionalInformation.getClientId());
            values.put(Fields.CELLPHONE.name(),additionalInformation.getCellphone());
            values.put(Fields.TELEPHONE.name(),additionalInformation.getTelephone());
            values.put(Fields.EMAIL.name(),additionalInformation.getEmail());
            values.put(Fields.SITE.name(),additionalInformation.getSite());
            values.put(Fields.OBSERVATION.name(),additionalInformation.getObservation());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }
    }

    public Boolean UPDATE(AdditionalInformation additionalInformation){
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_ADDITIONAL_INFORMATION.name(), additionalInformation.getAdditionalInformationId());
            values.put(Fields.ID_CLIENT.name(), additionalInformation.getClientId());
            values.put(Fields.CELLPHONE.name(), additionalInformation.getCellphone());
            values.put(Fields.TELEPHONE.name(), additionalInformation.getTelephone());
            values.put(Fields.EMAIL.name(), additionalInformation.getEmail());
            values.put(Fields.SITE.name(), additionalInformation.getSite());
            values.put(Fields.OBSERVATION.name(), additionalInformation.getObservation());

            this.Database.update(this.Table, values,
                    Fields.ID_ADDITIONAL_INFORMATION.name() + " = " + additionalInformation.getAdditionalInformationId(),
                    null);

            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public Boolean DELETE(AdditionalInformation additionalInformation){
        try {
            if (additionalInformation.getAdditionalInformationId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_ADDITIONAL_INFORMATION.name() + " = " + additionalInformation.getAdditionalInformationId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
        }
    }
}
