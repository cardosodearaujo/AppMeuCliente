package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        this.onStartActivity();
    }

    private void onStartActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FunctionsApp.startActivity(SplashActivity.this,ListClientActivity.class,null);
                FunctionsApp.closeActivity(SplashActivity.this);
            }
        },3000);
    }
}
