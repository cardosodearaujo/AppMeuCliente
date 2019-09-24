package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.AdditionalInformationTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.AddressTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.ClientTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.LegalPersonTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.PhysicalPersonTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TasksTable;

public class SessionDatabase {
    public static final String DB_NAME = "DB_WalletOfClients1";
    public static final int DB_VERSION = 3;

    public static ClientTable TB_CLIENT;
    public static LegalPersonTable TB_LEGAL_PERSON;
    public static PhysicalPersonTable TB_PHYSICAL_PERSON;
    public static AddressTable TB_ADDRESS;
    public static AdditionalInformationTable TB_ADDITIONAL_INFORMATION;
    public static TasksTable TB_TASKS;
}
