package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.LegalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public class TBLegalPersonDatabase {

    private SQLiteDatabase Database;
    private String Table = "TB_LEGAL_PERSON";
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
        this.Database = new ConfigurationDatabase(context).getReadableDatabase();
    }

    public static TBLegalPersonDatabase newInstance(Context context){
        return new TBLegalPersonDatabase(context);
    }

    public List<LegalPerson> SELECT(){
        try{
            List<LegalPerson> list = new ArrayList<LegalPerson>();
            String[] columns = new String[]{
                    Fields.ID_LEGAL_PERSON.name(),
                    Fields.ID_CLIENT.name(),
                    Fields.SOCIAL_NAME.name(),
                    Fields.FANTASY_NAME.name(),
                    Fields.CNPJ.name(),
                    Fields.IE.name(),
                    Fields.IM.name()
            };

            Cursor cursor = this.Database.query(this.Table,columns,
                    null,null,null,
                    null, Fields.ID_LEGAL_PERSON.name() + " ASC");

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                LegalPerson legalPerson;
                do{
                    legalPerson = new LegalPerson();

                    legalPerson.setLegalPersonId(cursor.getInt(0));
                    legalPerson.setClientId(cursor.getInt(1));
                    legalPerson.setSocialName(cursor.getString(2));
                    legalPerson.setFantasyName(cursor.getString(3));
                    legalPerson.setCNPJ(cursor.getString(4));
                    legalPerson.setIE(cursor.getString(   5));
                    legalPerson.setIM(cursor.getString(6));

                    list.add(legalPerson);
                }while (cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }
    }

    public long INSERT(LegalPerson legalPerson){
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
        }
    }

    public Boolean UPDATE(LegalPerson legalPerson){
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
        }
    }

    public Boolean DELETE(LegalPerson legalPerson){
        try {
            if (legalPerson.getLegalPersonId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_LEGAL_PERSON.name() + " = " + legalPerson.getLegalPersonId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
        }
    }
}
