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

            this.Cursor = this.Database.rawQuery(this.SQL,null);

            if (this.Cursor.getCount()>0){
                this.Cursor.moveToFirst();
                LegalPerson legalPerson;
                do{
                    legalPerson = new LegalPerson();

                    legalPerson.setLegalPersonId(this.Cursor.getInt(0));
                    legalPerson.setClientId(this.Cursor.getInt(1));
                    legalPerson.setSocialName(this.Cursor.getString(2));
                    legalPerson.setFantasyName(this.Cursor.getString(3));
                    legalPerson.setCNPJ(this.Cursor.getString(4));
                    legalPerson.setIE(this.Cursor.getString(   5));
                    legalPerson.setIM(this.Cursor.getString(6));

                    list.add(legalPerson);
                }while (this.Cursor.moveToNext());
            }

            this.Cursor.close();

            return list;
        }catch (Exception ex){
            return null;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long Insert(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_LEGAL_PERSON.name(),legalPerson.getLegalPersonId());
            values.put(Fields.ID_CLIENT.name(),legalPerson.getClientId());
            values.put(Fields.SOCIAL_NAME.name(),legalPerson.getSocialName());
            values.put(Fields.FANTASY_NAME.name(),legalPerson.getFantasyName());
            values.put(Fields.CNPJ.name(),legalPerson.getCNPJ());
            values.put(Fields.IE.name(),legalPerson.getIE());
            values.put(Fields.IM.name(),legalPerson.getIM());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_LEGAL_PERSON.name(), legalPerson.getLegalPersonId());
            values.put(Fields.ID_CLIENT.name(), legalPerson.getClientId());
            values.put(Fields.SOCIAL_NAME.name(), legalPerson.getSocialName());
            values.put(Fields.FANTASY_NAME.name(), legalPerson.getFantasyName());
            values.put(Fields.CNPJ.name(), legalPerson.getCNPJ());
            values.put(Fields.IE.name(), legalPerson.getIE());
            values.put(Fields.IM.name(), legalPerson.getIM());

            this.Database.update(this.Table, values,
                    Fields.ID_LEGAL_PERSON.name() + " = " + legalPerson.getLegalPersonId(),
                    null);

            return true;
        }catch (Exception ex){
            return false;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(LegalPerson legalPerson){
        super.openDatabaseInstance();
        try {
            if (legalPerson.getLegalPersonId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_LEGAL_PERSON.name() + " = " + legalPerson.getLegalPersonId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
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
