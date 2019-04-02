package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Address;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBAddressDatabase extends TableConfigurationDatabase {

    private enum Fields {
        ID_ADDRESS,
        ID_CLIENT,
        CEP,
        STREET,
        NUMBER,
        NEIGHBORHOOD,
        CITY,
        STATE,
        COUNTRY
    }

    public TBAddressDatabase(Context context){
        super(context);
        super.Table = "TB_ADDRESS";
    }

    public static TBAddressDatabase newInstance(Context context){
        return new TBAddressDatabase(context);
    }

    @Override
    public List<Address> Select(long clientId){
        super.openDatabaseInstance();
        try{
            List<Address> list = new ArrayList<Address>();

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

            this.Cursor = this.Database.rawQuery(this.SQL,null);

            if (this.Cursor.getCount()>0){
                this.Cursor.moveToFirst();
                Address address;
                do{
                    address = new Address();

                    address.setAddressId(this.Cursor.getInt(0));
                    address.setClientId(this.Cursor.getInt(1));
                    address.setCEP(this.Cursor.getString(2));
                    address.setStreet(this.Cursor.getString(3));
                    address.setNumber(this.Cursor.getInt(4));
                    address.setNeighborhood(this.Cursor.getString(   5));
                    address.setCity(this.Cursor.getString(6));
                    address.setCountry(this.Cursor.getString(7));

                    list.add(address);
                }while (this.Cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long Insert(Address address){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_ADDRESS.name(),address.getAddressId());
            values.put(Fields.ID_CLIENT.name(),address.getClientId());
            values.put(Fields.CEP.name(),address.getCEP());
            values.put(Fields.STREET.name(),address.getStreet());
            values.put(Fields.NUMBER.name(),address.getNumber());
            values.put(Fields.NEIGHBORHOOD.name(),address.getNeighborhood());
            values.put(Fields.CITY.name(),address.getCity());
            values.put(Fields.STATE.name(),address.getState());
            values.put(Fields.COUNTRY.name(),address.getCountry());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(Address address){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_ADDRESS.name(), address.getAddressId());
            values.put(Fields.ID_CLIENT.name(), address.getClientId());
            values.put(Fields.CEP.name(), address.getCEP());
            values.put(Fields.STREET.name(), address.getStreet());
            values.put(Fields.NUMBER.name(), address.getNumber());
            values.put(Fields.NEIGHBORHOOD.name(), address.getNeighborhood());
            values.put(Fields.CITY.name(), address.getCity());
            values.put(Fields.STATE.name(), address.getState());
            values.put(Fields.COUNTRY.name(), address.getCountry());

            this.Database.update(this.Table, values,
                    Fields.ID_ADDRESS.name() + " = " + address.getAddressId(),
                    null);

            return true;
        }catch (Exception ex){
            return false;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(Address address){
        super.openDatabaseInstance();
        try {
            if (address.getAddressId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_ADDRESS.name() + " = " + address.getAddressId(),
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
