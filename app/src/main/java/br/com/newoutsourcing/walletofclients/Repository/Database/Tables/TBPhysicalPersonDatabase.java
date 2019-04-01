package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.PhysicalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public class TBPhysicalPersonDatabase {

    private SQLiteDatabase Database;
    private Cursor Cursor;
    private String SQL;
    private String Table = "TB_PHYSICAL_PERSON";
    private enum Fields {
        ID_PHYSICAL_PERSON,
        ID_CLIENT,
        NAME,
        NICKNAME,
        CPF,
        BIRTH_DATE,
        SEX
    }

    public TBPhysicalPersonDatabase(Context context){
        this.Database = new ConfigurationDatabase(context).getReadableDatabase();
    }

    public static TBPhysicalPersonDatabase newInstance(Context context){
        return new TBPhysicalPersonDatabase(context);
    }

    public List<PhysicalPerson> SELECT(){
        return SELECT(0);
    }

    public List<PhysicalPerson> SELECT(long clientId){
        try{
            List<PhysicalPerson> list = new ArrayList<PhysicalPerson>();
            if (clientId > 0){
                SQL = " Select * From " + this.Table
                    + " Where ID_CLIENT = " + clientId
                    + " Order By " + Fields.ID_PHYSICAL_PERSON.name();

                this.Cursor = this.Database.rawQuery(SQL,null);
            }else{
                SQL = " Select * From " + this.Table
                    + " Order By " + Fields.ID_PHYSICAL_PERSON.name();

                this.Cursor = this.Database.rawQuery(SQL,null);
            }

            if (this.Cursor.getCount()>0){
                this.Cursor.moveToFirst();
                PhysicalPerson physicalPerson;
                do{
                    physicalPerson = new PhysicalPerson();

                    physicalPerson.setPhysicalPersonId(this.Cursor.getInt(0));
                    physicalPerson.setClientId(this.Cursor.getInt(1));
                    physicalPerson.setName(this.Cursor.getString(2));
                    physicalPerson.setNickname(this.Cursor.getString(3));
                    physicalPerson.setCPF(this.Cursor.getString(4));
                    physicalPerson.setBirthDate(this.Cursor.getString(   5));
                    physicalPerson.setSex(this.Cursor.getString(6));

                    list.add(physicalPerson);
                }while (this.Cursor.moveToNext());
            }

            this.Cursor.close();

            return list;
        }catch (Exception ex){
            return null;
        }
    }

    public long INSERT(PhysicalPerson physicalPerson){
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_PHYSICAL_PERSON.name(),physicalPerson.getPhysicalPersonId());
            values.put(Fields.ID_CLIENT.name(),physicalPerson.getName());
            values.put(Fields.NAME.name(),physicalPerson.getName());
            values.put(Fields.NICKNAME.name(),physicalPerson.getNickname());
            values.put(Fields.CPF.name(),physicalPerson.getCPF());
            values.put(Fields.BIRTH_DATE.name(),physicalPerson.getBirthDate());
            values.put(Fields.SEX.name(),physicalPerson.getSex());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }
    }

    public Boolean UPDATE(PhysicalPerson physicalPerson){
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_PHYSICAL_PERSON.name(), physicalPerson.getPhysicalPersonId());
            values.put(Fields.ID_CLIENT.name(), physicalPerson.getClientId());
            values.put(Fields.NAME.name(), physicalPerson.getName());
            values.put(Fields.NICKNAME.name(), physicalPerson.getNickname());
            values.put(Fields.CPF.name(), physicalPerson.getCPF());
            values.put(Fields.BIRTH_DATE.name(), physicalPerson.getBirthDate());
            values.put(Fields.SEX.name(), physicalPerson.getSex());

            this.Database.update(this.Table, values,
                    Fields.ID_PHYSICAL_PERSON.name() + " = " + physicalPerson.getPhysicalPersonId(),
                    null);

            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public Boolean DELETE(PhysicalPerson physicalPerson){
        try {
            if (physicalPerson.getPhysicalPersonId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_PHYSICAL_PERSON.name() + " = " + physicalPerson.getPhysicalPersonId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
        }
    }
}
