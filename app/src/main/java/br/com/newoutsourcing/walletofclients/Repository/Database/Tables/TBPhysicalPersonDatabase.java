package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.PhysicalPerson;

public class TBPhysicalPersonDatabase {

    private SQLiteDatabase Database;
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

    }

    public static TBPhysicalPersonDatabase newInstance(Context context){
        return new TBPhysicalPersonDatabase(context);
    }

    public List<PhysicalPerson> SELECT(){
        try{
            List<PhysicalPerson> list = new ArrayList<PhysicalPerson>();
            String[] columns = new String[]{
                    Fields.ID_PHYSICAL_PERSON.name(),
                    Fields.ID_CLIENT.name(),
                    Fields.NAME.name(),
                    Fields.NICKNAME.name(),
                    Fields.CPF.name(),
                    Fields.BIRTH_DATE.name(),
                    Fields.SEX.name()
            };

            Cursor cursor = this.Database.query(this.Table,columns,
                    null,null,null,
                    null, Fields.ID_PHYSICAL_PERSON.name() + " ASC");

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                PhysicalPerson physicalPerson;
                do{
                    physicalPerson = new PhysicalPerson();

                    physicalPerson.setPhysicalPersonId(cursor.getInt(0));
                    physicalPerson.setClientId(cursor.getInt(1));
                    physicalPerson.setName(cursor.getString(2));
                    physicalPerson.setNickname(cursor.getString(3));
                    physicalPerson.setCPF(cursor.getString(4));
                    physicalPerson.setBirthDate(cursor.getString(   5));
                    physicalPerson.setSex(cursor.getString(6));

                    list.add(physicalPerson);
                }while (cursor.moveToNext());
            }
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
