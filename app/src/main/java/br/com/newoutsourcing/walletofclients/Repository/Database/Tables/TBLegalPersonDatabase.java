package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.LegalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBLegalPersonDatabase extends TableConfigurationDatabase {

    private enum Fields {
        ID_LEGAL_PERSON,
        ID_CLIENT,
        SOCIAL_NAME,
        FANTASY_NAME,
        CNPJ,
        IE,
        IM
    }

    public TBLegalPersonDatabase(Context context){
        super(context);
        super.Table = "TB_LEGAL_PERSON";
    }

    public static TBLegalPersonDatabase newInstance(Context context){
        return new TBLegalPersonDatabase(context);
    }

    @Override
    public List<LegalPerson> Select(long clientId){
        super.openDatabaseInstance();
        try{
            List<LegalPerson> list = new ArrayList<LegalPerson>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_CLIENT + " = " + clientId
                        + " Order by " + Fields.ID_LEGAL_PERSON.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order by " + Fields.ID_LEGAL_PERSON.name();
            }

            this.cursor = this.database.rawQuery(this.SQL,null);

            if (this.cursor.getCount()>0){
                this.cursor.moveToFirst();
                LegalPerson legalPerson;
                do{
                    legalPerson = new LegalPerson();

                    legalPerson.setLegalPersonId(this.cursor.getInt(0));
                    legalPerson.setClientId(this.cursor.getInt(1));
                    legalPerson.setSocialName(this.cursor.getString(2));
                    legalPerson.setFantasyName(this.cursor.getString(3));
                    legalPerson.setCNPJ(this.cursor.getString(4));
                    legalPerson.setIE(this.cursor.getString(   5));
                    legalPerson.setIM(this.cursor.getString(6));

                    list.add(legalPerson);
                }while (this.cursor.moveToNext());
            }

            this.cursor.close();

            return list;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long Insert(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(),legalPerson.getClientId());
            values.put(Fields.SOCIAL_NAME.name(),legalPerson.getSocialName());
            values.put(Fields.FANTASY_NAME.name(),legalPerson.getFantasyName());
            values.put(Fields.CNPJ.name(),legalPerson.getCNPJ());
            values.put(Fields.IE.name(),legalPerson.getIE());
            values.put(Fields.IM.name(),legalPerson.getIM());

            return this.database.insert(this.Table,null,values);

        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(), legalPerson.getClientId());
            values.put(Fields.SOCIAL_NAME.name(), legalPerson.getSocialName());
            values.put(Fields.FANTASY_NAME.name(), legalPerson.getFantasyName());
            values.put(Fields.CNPJ.name(), legalPerson.getCNPJ());
            values.put(Fields.IE.name(), legalPerson.getIE());
            values.put(Fields.IM.name(), legalPerson.getIM());

            this.database.update(this.Table, values,
                    Fields.ID_LEGAL_PERSON.name() + " = " + legalPerson.getLegalPersonId(),
                    null);

            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try {
            if (legalPerson.getLegalPersonId() > 0) {
                this.database.delete(this.Table,
                        Fields.ID_LEGAL_PERSON.name() + " = " + legalPerson.getLegalPersonId(),
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
