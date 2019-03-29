package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public class TBClientDatabase {

    private SQLiteDatabase Database;
    private String Table = "TB_CLIENT";
    private enum Fields {
        ID_CLIENT,
        IMAGE,
        TYPE
    }

    public TBClientDatabase(Context context){
        this.Database = new ConfigurationDatabase(context).getReadableDatabase();
    }

    public static TBClientDatabase newInstance(Context context){
        return new TBClientDatabase(context);
    }

    public List<Client> SELECT(){
        try{
            List<Client> list = new ArrayList<Client>();
            String[] columns = new String[]{
                    Fields.ID_CLIENT.name(),
                    Fields.IMAGE.name(),
                    Fields.TYPE.name()
            };

            Cursor cursor = this.Database.query(this.Table,columns,
                    null,null,null,
                    null,Fields.ID_CLIENT.name() + " ASC");

            if (cursor.getCount()>0){
                cursor.moveToFirst();
                Client client;
                do{
                    client = new Client();

                    client.setClientId(cursor.getInt(0));
                    client.setImage(cursor.getString(1));
                    client.setType(cursor.getInt(2));

                    list.add(client);
                }while (cursor.moveToNext());
            }
            return list;
        }catch (Exception ex){
            return null;
        }
    }

    public long INSERT(Client client){
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.ID_CLIENT.name(),client.getClientId());
            values.put(Fields.IMAGE.name(),client.getImage());
            values.put(Fields.TYPE.name(),client.getType());

            return this.Database.insert(this.Table,null,values);

        }catch (Exception ex){
            return 0;
        }
    }

    public Boolean UPDATE(Client client){
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
        }
    }

    public Boolean DELETE(Client client){
        try {
            if (client.getClientId() > 0) {
                this.Database.delete(this.Table,
                        Fields.ID_CLIENT.name() + " = " + client.getClientId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            return false;
        }
    }
}
