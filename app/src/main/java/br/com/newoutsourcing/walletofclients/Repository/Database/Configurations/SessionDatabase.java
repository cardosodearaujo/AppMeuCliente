package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAdditionalInformationDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAddressDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBClientDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBLegalPersonDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBPhysicalPersonDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBTasksDatabase;

public class SessionDatabase {
    public static final String DB_NAME = "DB_WalletOfClients1";
    public static final int DB_VERSION = 2;

    public static TBClientDatabase TB_CLIENT;
    public static TBLegalPersonDatabase TB_LEGAL_PERSON;
    public static TBPhysicalPersonDatabase TB_PHYSICAL_PERSON;
    public static TBAddressDatabase TB_ADDRESS;
    public static TBAdditionalInformationDatabase TB_ADDITIONAL_INFORMATION;
    public static TBTasksDatabase TB_TASKS;
}
