package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBClientDatabase extends TableConfigurationDatabase {

    private enum Fields {
        ID_CLIENT,
        IMAGE,
        TYPE
    }

    public TBClientDatabase(Context context){
        super(context);
        super.Table = "TB_CLIENT";
    }

    public static TBClientDatabase newInstance(Context context){
        return new TBClientDatabase(context);
    }

    @Override
    public List<Client> Select(long clientId){
        super.openDatabaseInstance();
        try{
            List<Client> list = new ArrayList<Client>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_CLIENT.name() + " = " + clientId
                        + " Order by " + Fields.ID_CLIENT.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order by " + Fields.ID_CLIENT.name();
            }

            this.Cursor = this.Database.rawQuery(this.SQL,null);

            if (this.Cursor.getCount()>0){
                this.Cursor.moveToFirst();
                Client client;
                do{
                    client = new Client();

                    client.setClientId(this.Cursor.getInt(0));
                    client.setImage(this.Cursor.getString(1));
                    client.setType(this.Cursor.getInt(2));

                    list.add(client);
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

    public long Insert(Client client){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(),client.getClientId());
            values.put(Fields.IMAGE.name(),client.getImage());
            values.put(Fields.TYPE.name(),client.getType());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(Client client){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(), client.getClientId());
            values.put(Fields.IMAGE.name(), client.getImage());
            values.put(Fields.TYPE.name(), client.getType());

            this.Database.update(this.Table, values,
                    Fields.ID_CLIENT.name() + " = " + client.getClientId(),
                    null);

            return true;
        }catch (Exception ex){
            return false;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(Client client){
        super.openDatabaseInstance();
        try {
            if (client.getClientId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_CLIENT.name() + " = " + client.getClientId(),
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
