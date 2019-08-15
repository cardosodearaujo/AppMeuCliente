package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Handler;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.AdditionalInformationTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.AddressTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.ClientTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.LegalPersonTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.PhysicalPersonTable;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TasksTable;
import br.com.newoutsourcing.walletofclients.Tools.NofiticationMessages;
import br.com.newoutsourcing.walletofclients.Views.Bases.ActivityBase;

public class SplashActivity extends ActivityBase {

    public SplashActivity() {
        super(R.layout.activity_splash);
    }

    @Override
    protected void onConfiguration() {
        NofiticationMessages.onNotificationUse();
        this.onLoadDatabaseSession();
        this.onStartActivity();
    }

    private void onLoadDatabaseSession(){
        try{
            SessionDatabase.TB_CLIENT = ClientTable.newInstance(SplashActivity.this);
            SessionDatabase.TB_PHYSICAL_PERSON = PhysicalPersonTable.newInstance(SplashActivity.this);
            SessionDatabase.TB_LEGAL_PERSON = LegalPersonTable.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDITIONAL_INFORMATION = AdditionalInformationTable.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDRESS = AddressTable.newInstance(SplashActivity.this);
            SessionDatabase.TB_TASKS = TasksTable.newInstance(SplashActivity.this);
        }catch (Exception ex){
            FunctionsTools.showAlertDialog(SplashActivity.this,"Erro","Ocorreu um erro ao inicializar a aplicação. Tente novamente!","Fechar");
            FunctionsTools.closeActivity(SplashActivity.this);
        }
    }

    private void onStartActivity(){
        new Handler().postDelayed(() -> {
            FunctionsTools.startActivity(SplashActivity.this,ListClientActivity.class,null);
            FunctionsTools.closeActivity(SplashActivity.this);
        },1000);
    }


}
