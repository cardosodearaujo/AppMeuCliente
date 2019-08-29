package br.com.newoutsourcing.walletofclients.Repository.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class TasksService extends Service {

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
                if (FunctionsTools.getCurrentTime().equals("08:00") && !notificou){
                    List<Tasks> tarefas = TB_TASKS.Select(FunctionsTools.getCurrentDate());
                    if (tarefas != null && tarefas.size() > 0){
                        FunctionsTools.showNotificationPush(TasksService.this,
                                "Bom dia!","Você tem " + tarefas.size() + " tarefa(s) no dia de hoje. Aproveite o dia!");
                    }
                    notificou = true;
                }
            }
        }).start();
    }
}
