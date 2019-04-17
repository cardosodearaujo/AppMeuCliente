package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static br.com.newoutsourcing.walletofclients.App.FunctionsApp.DB_NAME;
import static br.com.newoutsourcing.walletofclients.App.FunctionsApp.DB_VERSION;

public class ConfigurationDatabase extends SQLiteOpenHelper {

    public ConfigurationDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DbEnjoy) {
        try {
            String SQL;

            /**Tabela de Clientes**/
            SQL =  " CREATE TABLE TB_CLIENT( ";
            SQL += "    ID_CLIENT INTEGER PRIMARY KEY AUTOINCREMENT , ";
            SQL += "    IMAGE TEXT NULL, ";
            SQL += "    TYPE INTEGER NOT NULL ";
            SQL += " ); ";
            DbEnjoy.execSQL(SQL);

            /**Pessoa Fisica**/
            SQL =  " CREATE TABLE TB_PHYSICAL_PERSON( ";
            SQL += "    ID_PHYSICAL_PERSON INTEGER PRIMARY KEY AUTOINCREMENT , ";
            SQL += "    ID_CLIENT INTEGER NOT NULL , ";
            SQL += "    NAME TEXT NOT NULL , ";
            SQL += "    NICKNAME TEXT NULL , ";
            SQL += "    CPF TEXT NOT NULL , ";
            SQL += "    RG TEXT NOT NULL ,";
            SQL += "    BIRTH_DATE DATETIME NOT NULL , ";
            SQL += "    SEX TEXT NOT NULL ";
            SQL += " ); ";
            DbEnjoy.execSQL(SQL);

            /**Pessoa Juridica**/
            SQL =  " CREATE TABLE TB_LEGAL_PERSON( ";
            SQL += "    ID_LEGAL_PERSON INTEGER PRIMARY KEY AUTOINCREMENT , ";
            SQL += "    ID_CLIENT INTEGER NOT NULL , ";
            SQL += "    SOCIAL_NAME TEXT NOT NULL , ";
            SQL += "    FANTASY_NAME TEXT NOT NULL , ";
            SQL += "    CNPJ TEXT NOT NULL , ";
            SQL += "    IE TEXT NOT NULL , ";
            SQL += "    IM TEXT NULL ";
            SQL += " ); ";
            DbEnjoy.execSQL(SQL);

            /**Endereço**/
            SQL =  " CREATE TABLE TB_ADDRESS( ";
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
            DbEnjoy.execSQL(SQL);

            /**Informações adicionais**/
            SQL =  " CREATE TABLE TB_ADDITIONAL_INFORMATION( ";
            SQL += "    ID_ADDITIONAL_INFORMATION INTEGER PRIMARY KEY AUTOINCREMENT, ";
            SQL += "    ID_CLIENT INTEGER NOT NULL, ";
            SQL += "    CELLPHONE TEXT NULL, ";
            SQL += "    TELEPHONE TEXT NULL, ";
            SQL += "    EMAIL TEXT NULL, ";
            SQL += "    SITE TEXT NULL, ";
            SQL += "    OBSERVATION TEXT NULL ";
            SQL += " ); ";
            DbEnjoy.execSQL(SQL);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase DbEnjoy, int i, int i1) {
        try {
            String SQL;

            /**Pessoa Fisica**/
            SQL = " DROP TABLE PHYSICAL_PERSON; ";
            DbEnjoy.execSQL(SQL);
            /**Pessoa Juridica**/
            SQL = " DROP TABLE LEGAL_PERSON; ";
            DbEnjoy.execSQL(SQL);
            /**Endereço**/
            SQL = " DROP TABLE ADDRESS; ";
            DbEnjoy.execSQL(SQL);
            /**Informações adicionais**/
            SQL = " DROP TABLE ADDITIONAL_INFORMATION; ";
            DbEnjoy.execSQL(SQL);
            /**Tabela de Clientes**/
            SQL  = " DROP TABLE CLIENT; ";
            DbEnjoy.execSQL(SQL);

        } catch (Exception ex) {
            throw ex;
        }
    }
}
