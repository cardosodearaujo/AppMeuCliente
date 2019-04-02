package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

public class TableConfigurationDatabase<T> {

    protected SQLiteDatabase Database;
    protected Cursor Cursor;
    protected String SQL;
    protected String Table;
    protected Context context;

    public TableConfigurationDatabase(Context context){
        this.context = context;
    }

    protected void openDatabaseInstance(){
        if(this.Database != null || !this.Database.isOpen()){
            this.Database = new ConfigurationDatabase(context).getReadableDatabase();
        }
    }

    protected void closeDatabaseInstance(){
        if (this.Database.isOpen()){
            this.Database.close();
        }
    }

    public List<T> Select(){
        return this.Select(0);
    }

    public List<T> Select(long id){
        this.openDatabaseInstance();
        return null;
    }
}
