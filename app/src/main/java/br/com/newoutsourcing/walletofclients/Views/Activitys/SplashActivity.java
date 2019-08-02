package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAdditionalInformationDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAddressDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBClientDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBLegalPersonDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBPhysicalPersonDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        this.onLoadDatabaseSession();
        this.onStartActivity();
    }

    private void onLoadDatabaseSession(){
        try{
            SessionDatabase.TB_CLIENT = TBClientDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_PHYSICAL_PERSON = TBPhysicalPersonDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_LEGAL_PERSON = TBLegalPersonDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDITIONAL_INFORMATION = TBAdditionalInformationDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDRESS = TBAddressDatabase.newInstance(SplashActivity.this);
        }catch (Exception ex){
            FunctionsApp.showAlertDialog(SplashActivity.this,"Erro","Ocorreu um erro ao inicializar a aplicação. Tente novamente!","Fechar");
            FunctionsApp.closeActivity(SplashActivity.this);
        }
    }

    private void onStartActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FunctionsApp.startActivity(SplashActivity.this,ListClientActivity.class,null);
                FunctionsApp.closeActivity(SplashActivity.this);
            }
        },1000);
    }
}
