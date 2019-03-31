package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Address;
import br.com.newoutsourcing.walletofclients.Objects.LegalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public class TBAddressDatabase {

    private SQLiteDatabase Database;
    private String Table = "TB_ADDRESS";
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
        this.Database = new ConfigurationDatabase(context).getReadableDatabase();
    }

    public static TBLegalPersonDatabase newInstance(Context context){
        return new TBLegalPersonDatabase(context);
    }

    public List<Address> SELECT(){
        try{
            List<Address> list = new ArrayList<Address>();
            String[] columns = new String[]{
                    Fields.ID_ADDRESS.name(),
                    Fields.ID_CLIENT.name(),
                    Fields.CEP.name(),
                    Fields.STREET.name(),
                    Fields.NUMBER.name(),
                    Fields.NEIGHBORHOOD.name(),
                    Fields.CITY.name(),
                    Fields.STATE.name(),
                    Fields.COUNTRY.name()
            };

            Cursor cursor = this.Database.query(this.Table,columns,
                    null,null,null,
                    null, Fields.ID_ADDRESS.name() + " ASC");

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                Address address;
                do{
                    address = new Address();

                    address.setAddressId(cursor.getInt(0));
                    address.setClientId(cursor.getInt(1));
                    address.setCEP(cursor.getString(2));
                    address.setStreet(cursor.getString(3));
                    address.setNumber(cursor.getInt(4));
                    address.setNeighborhood(cursor.getString(   5));
                    address.setCity(cursor.getString(6));
                    address.setCountry(cursor.getString(7));

                    list.add(address);
                }while (cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }
    }

    public long INSERT(Address address){
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
        }
    }

    public Boolean UPDATE(Address address){
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
        }
    }

    public Boolean DELETE(Address address){
        try {
            if (address.getAddressId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_ADDRESS.name() + " = " + address.getAddressId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
        }
    }
}
