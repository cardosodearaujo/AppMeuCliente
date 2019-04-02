package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.PhysicalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBPhysicalPersonDatabase extends TableConfigurationDatabase {

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
        super(context);
        super.Table = "TB_PHYSICAL_PERSON";
    }

    public static TBPhysicalPersonDatabase newInstance(Context context){
        return new TBPhysicalPersonDatabase(context);
    }

    @Override
    public List<PhysicalPerson> Select(long clientId){
        super.Select(clientId);
        try{
            List<PhysicalPerson> list = new ArrayList<PhysicalPerson>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where ID_CLIENT = " + clientId
                        + " Order By " + Fields.ID_PHYSICAL_PERSON.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order By " + Fields.ID_PHYSICAL_PERSON.name();
            }

            this.Cursor = this.Database.rawQuery(SQL,null);

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
        }finally {
            this.closeDatabaseInstance();
        }
    }

    public long Insert(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
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
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
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
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
        try {
            if (physicalPerson.getPhysicalPersonId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_PHYSICAL_PERSON.name() + " = " + physicalPerson.getPhysicalPersonId(),
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
