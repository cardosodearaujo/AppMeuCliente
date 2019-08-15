package br.com.newoutsourcing.walletofclients.Views.Bases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.ConfigurationDatabase;

public abstract class TableConfigurationBase<obj> {

    protected SQLiteDatabase database;
    protected Cursor cursor;
    protected String SQL;
    public String Table;
    protected Context context;

    public TableConfigurationBase(Context context){
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

    public List<obj> Select(){
        return this.Select(0);
    }

    public abstract List<obj> Select(long id);

    public abstract long Insert(obj obj);

    public abstract Boolean Update(obj obj);

    public abstract Boolean Delete(obj obj);

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

    protected abstract String getFields();
}
