package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

public class TableConfigurationDatabase<T> {

    protected SQLiteDatabase database;
    protected Cursor cursor;
    protected String SQL;
    public String Table;
    protected Context context;

    public TableConfigurationDatabase(Context context){
        this.context = context;
    }

    protected void openDatabaseInstance(){
        if(this.database == null || !this.database.isOpen()){
            this.database = new ConfigurationDatabase(context).getReadableDatabase();
        }
    }

    protected void closeDatabaseInstance(){
        if (this.database != null && this.database.isOpen()){
            this.database.close();
        }
    }

    public List<T> Select(){
        return this.Select(0);
    }

    public List<T> Select(long id){
        return null;
    }

    public Boolean DeleteAll(){
        this.openDatabaseInstance();
        try {
            this.database.delete(this.Table,null,null);
            return  true;
        }catch (Exception ex){
            throw ex;
        }finally {
            this.closeDatabaseInstance();
        }
    }

    public String QuoteParam(String param){
        return param.replace("'","''");
    }
}
