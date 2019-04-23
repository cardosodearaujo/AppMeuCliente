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
        RG,
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
        super.openDatabaseInstance();
        try{
            List<PhysicalPerson> list = new ArrayList<PhysicalPerson>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_CLIENT.name() + " = " + clientId
                        + " Order By " + Fields.ID_PHYSICAL_PERSON.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order By " + Fields.ID_PHYSICAL_PERSON.name();
            }

            this.cursor = this.database.rawQuery(SQL,null);

            if (this.cursor.getCount()>0){
                this.cursor.moveToFirst();
                PhysicalPerson physicalPerson;
                do{
                    physicalPerson = new PhysicalPerson();

                    physicalPerson.setPhysicalPersonId(this.cursor.getInt(0));
                    physicalPerson.setClientId(this.cursor.getInt(1));
                    physicalPerson.setName(this.cursor.getString(2));
                    physicalPerson.setNickname(this.cursor.getString(3));
                    physicalPerson.setCPF(this.cursor.getString(4));
                    physicalPerson.setRG(this.cursor.getString(5));
                    physicalPerson.setBirthDate(this.cursor.getString(   6));
                    physicalPerson.setSex(this.cursor.getString(7));

                    list.add(physicalPerson);
                }while (this.cursor.moveToNext());
            }

            this.cursor.close();

            return list;
        }catch (Exception ex){
            throw ex;
        }finally {
            this.closeDatabaseInstance();
        }
    }

    public long Insert(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(),physicalPerson.getClientId());
            values.put(Fields.NAME.name(),physicalPerson.getName());
            values.put(Fields.NICKNAME.name(),physicalPerson.getNickname());
            values.put(Fields.CPF.name(),physicalPerson.getCPF());
            values.put(Fields.RG.name(),physicalPerson.getRG());
            values.put(Fields.BIRTH_DATE.name(),physicalPerson.getBirthDate());
            values.put(Fields.SEX.name(),physicalPerson.getSex());

            return this.database.insert(this.Table,null,values);

        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(), physicalPerson.getClientId());
            values.put(Fields.NAME.name(), physicalPerson.getName());
            values.put(Fields.NICKNAME.name(), physicalPerson.getNickname());
            values.put(Fields.CPF.name(), physicalPerson.getCPF());
            values.put(Fields.RG.name(), physicalPerson.getRG());
            values.put(Fields.BIRTH_DATE.name(), physicalPerson.getBirthDate());
            values.put(Fields.SEX.name(), physicalPerson.getSex());

            this.database.update(this.Table, values,
                    Fields.ID_PHYSICAL_PERSON.name() + " = " + physicalPerson.getPhysicalPersonId(),
                    null);

            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(PhysicalPerson physicalPerson){
        super.openDatabaseInstance();
        try {
            if (physicalPerson.getPhysicalPersonId() > 0) {
                this.database.delete(this.Table,
                        Fields.ID_PHYSICAL_PERSON.name() + " = " + physicalPerson.getPhysicalPersonId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long CheckCPF(String CPF){
        super.openDatabaseInstance();
        try{
            if (CPF.isEmpty()) return 0;

            this.SQL
                    = " Select " + Fields.ID_CLIENT.name() + " From " + this.Table
                    + " Where " + Fields.CPF.name() + " = '" + CPF + "'"
                    + " Order By " + Fields.ID_PHYSICAL_PERSON.name();

            this.cursor = this.database.rawQuery(SQL,null);

            if (this.cursor.getCount()>0){
                return this.cursor.getInt(0);
            }

            return 0;
        }catch (Exception ex){
            throw ex;
        }finally {
            this.closeDatabaseInstance();
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
