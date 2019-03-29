package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.Context;

public class TBLegalPersonDatabase {

    public TBLegalPersonDatabase(Context context){

    }

    public static TBLegalPersonDatabase newInstance(Context context){
        return new TBLegalPersonDatabase(context);
    }
}
