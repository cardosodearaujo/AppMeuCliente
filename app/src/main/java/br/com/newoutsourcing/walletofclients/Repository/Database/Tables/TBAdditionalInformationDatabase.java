package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.Context;

public class TBAdditionalInformationDatabase {

    public TBAdditionalInformationDatabase(Context context){

    }

    public static TBAdditionalInformationDatabase newInstance(Context context){
        return new TBAdditionalInformationDatabase(context);
    }
}
