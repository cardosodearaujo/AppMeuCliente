package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.Context;

public class TBAddressDatabase {

    public TBAddressDatabase(Context context){

    }

    public static TBAddressDatabase newInstance(Context context){
        return new TBAddressDatabase(context);
    }
}
