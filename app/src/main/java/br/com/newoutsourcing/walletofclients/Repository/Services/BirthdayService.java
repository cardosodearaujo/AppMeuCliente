package br.com.newoutsourcing.walletofclients.Repository.Services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.PhysicalPerson;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class BirthdayService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        this.startNotificationListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    public void startNotificationListener() {
        new Thread(() -> {
            boolean notificou = false;
            while(true){
                if (FunctionsTools.getCurrentTime().equals("09:55") && !notificou){
                    List<Client> clients = TB_CLIENT.Select();
                    int cont = 0;
                    for(Client client : clients){
                        if (client.getType() == 1){
                            PhysicalPerson physicalPerson = client.getPhysicalPerson();
                            if (physicalPerson.getBirthDate().substring(0,5).equals(FunctionsTools.getCurrentDate().substring(0,5))){
                                cont ++;
                            }
                        }
                    }
                    if (cont > 0){
                        FunctionsTools.showNotificationPush(BirthdayService.this,"Atenção!!!",
                                "Você tem " + cont + " cliente(s) que faz(em) aniversário hoje. Dê os parabéns!");
                    }
                    notificou = true;
                }
            }
        }).start();
    }
}
