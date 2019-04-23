package br.com.newoutsourcing.walletofclients.Repository.Database.Configurations;

import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAdditionalInformationDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAddressDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBClientDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBLegalPersonDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBPhysicalPersonDatabase;

public class SessionDatabase {
    public static final String DB_NAME = "DB_WalletOfClients";
    public static final int DB_VERSION = 1;

    public static TBClientDatabase TB_CLIENT;
    public static TBLegalPersonDatabase TB_LEGAL_PERSON;
    public static TBPhysicalPersonDatabase TB_PHYSICAL_PERSON;
    public static TBAddressDatabase TB_ADDRESS;
    public static TBAdditionalInformationDatabase TB_ADDITIONAL_INFORMATION;
}
