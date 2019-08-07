package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.DB_NAME;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.DB_VERSION;

public class ConfigurationDatabase extends SQLiteOpenHelper {

    public ConfigurationDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private String SQL;

    private String getScript_TB_CLIENTE(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_CLIENT( ";
        SQL += "    ID_CLIENT INTEGER PRIMARY KEY AUTOINCREMENT , ";
        SQL += "    IMAGE TEXT NULL, ";
        SQL += "    TYPE INTEGER NOT NULL ";
        SQL += " ); ";

        return SQL;
    }

    private String getScript_TB_PHYSICAL_PERSON(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_PHYSICAL_PERSON( ";
        SQL += "    ID_PHYSICAL_PERSON INTEGER PRIMARY KEY AUTOINCREMENT , ";
        SQL += "    ID_CLIENT INTEGER NOT NULL , ";
        SQL += "    NAME TEXT NOT NULL , ";
        SQL += "    NICKNAME TEXT NULL , ";
        SQL += "    CPF TEXT NOT NULL , ";
        SQL += "    RG TEXT NOT NULL ,";
        SQL += "    BIRTH_DATE DATETIME NOT NULL , ";
        SQL += "    SEX TEXT NOT NULL ";
        SQL += " ); ";

        return SQL;
    }

    private String getScript_TB_LEGAL_PERSON(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_LEGAL_PERSON( ";
        SQL += "    ID_LEGAL_PERSON INTEGER PRIMARY KEY AUTOINCREMENT , ";
        SQL += "    ID_CLIENT INTEGER NOT NULL , ";
        SQL += "    SOCIAL_NAME TEXT NOT NULL , ";
        SQL += "    FANTASY_NAME TEXT NOT NULL , ";
        SQL += "    CNPJ TEXT NOT NULL , ";
        SQL += "    IE TEXT NOT NULL , ";
        SQL += "    IM TEXT NULL ";
        SQL += " ); ";

        return SQL;
    }

    private String getScript_TB_ADDRSSS(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_ADDRESS( ";
        SQL += "    ID_ADDRESS INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL += "    ID_CLIENT INTEGER NOT NULL, ";
        SQL += "    CEP TEXT NULL, ";
        SQL += "    STREET TEXT NULL, ";
        SQL += "    NUMBER INTEGER NOT NULL, ";
        SQL += "    NEIGHBORHOOD TEXT NULL, ";
        SQL += "    CITY TEXT NULL, ";
        SQL += "    STATE TEXT NULL, ";
        SQL += "    COUNTRY TEXT NULL ";
        SQL += " ); ";

        return SQL;
    }

    private String getScript_TB_ADDITIONAL_INFORMATION(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_ADDITIONAL_INFORMATION( ";
        SQL += "    ID_ADDITIONAL_INFORMATION INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL += "    ID_CLIENT INTEGER NOT NULL, ";
        SQL += "    CELLPHONE TEXT NULL, ";
        SQL += "    TELEPHONE TEXT NULL, ";
        SQL += "    EMAIL TEXT NULL, ";
        SQL += "    SITE TEXT NULL, ";
        SQL += "    OBSERVATION TEXT NULL ";
        SQL += " ); ";

        return SQL;
    }

    private String getScript_TB_TASKS(){
        SQL =  " CREATE TABLE IF NOT EXISTS TB_TASKS( ";
        SQL += "    ID_TASK INTEGER PRIMARY KEY AUTOINCREMENT, ";
        SQL += "    TITLE TEXT NOT NULL, ";
        SQL += "    ID_CLIENT INTEGER NULL, ";
        SQL += "    ALL_DAY INTEGER NOT NULL, ";
        SQL += "    DATE TEXT NOT NULL, ";
        SQL += "    HOUR TEXT NULL, ";
        SQL += "    OBSERVATION TEXT NULL ";
        SQL += " ); ";

        return SQL;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        try {
            DB.execSQL(getScript_TB_CLIENTE());
            DB.execSQL(getScript_TB_PHYSICAL_PERSON());
            DB.execSQL(getScript_TB_LEGAL_PERSON());
            DB.execSQL(getScript_TB_ADDRSSS());
            DB.execSQL(getScript_TB_ADDITIONAL_INFORMATION());
            DB.execSQL(getScript_TB_TASKS());
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        try {
            switch (newVersion){
                case 1:
                    break;
                case 2:
                    DB.execSQL(getScript_TB_TASKS());
                    break;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
