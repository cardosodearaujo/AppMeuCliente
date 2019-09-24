package br.com.newoutsourcing.walletofclients.Repository.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.API.API;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.ClientRequest;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.TasksRequest;
import br.com.newoutsourcing.walletofclients.Tools.NotificationMessages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class SynchronizeService  extends Service {
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
            while(true){
                try{
                    //** Clientes **//
                    List<Client> clientList;

                    //Enviando novos clientes:
                    clientList = TB_CLIENT.SelectForInsert();
                    if (clientList != null && clientList.size() > 0){
                        for (Client client : clientList){
                            PostClient(new ClientRequest().convert(client));
                        }
                    }

                    //Enviado atualizações de clientes
                    clientList = TB_CLIENT.SelectForUpdate();
                    if (clientList != null && clientList.size() > 0){
                        for (Client client: clientList){
                            PutClient(new ClientRequest().convert(client));
                        }
                    }

                    //** Tarefas **//
                    List<Tasks> tasksList;

                    //Enviando novas tarefas:
                    tasksList = TB_TASKS.SelectForInsert();
                    if (tasksList != null && tasksList.size() > 0){
                        for (Tasks tasks : tasksList){
                            PostTasks(new TasksRequest().convert(tasks));
                        }
                    }

                    //Enviando atualizações das tarefas:
                    tasksList = TB_TASKS.SelectForUpdate();
                    if (tasksList != null && tasksList.size() > 0){
                        for (Tasks tasks : tasksList){
                            PostTasks(new TasksRequest().convert(tasks));
                        }
                    }
                }catch (Exception ex){
                    NotificationMessages
                            .onNotificationError("Serviço de sicronização de dados", ex);
                }
            }
        }).start();
    }

    private void PostClient(ClientRequest client){
        API
            .postClient(client)
            .enqueue(new Callback<ClientRequest>() {
                @Override
                public void onResponse(Call<ClientRequest> call, Response<ClientRequest> response) {
                    if (response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<ClientRequest> call, Throwable t) {
                    NotificationMessages
                            .onNotificationError("Salvar clientes", t);
                }
            });
    }

    private void PutClient(ClientRequest client){
        API
            .putClient(client)
            .enqueue(new Callback<ClientRequest>() {
                @Override
                public void onResponse(Call<ClientRequest> call, Response<ClientRequest> response) {
                    if (response.isSuccessful()){

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ClientRequest> call, Throwable t) {
                    NotificationMessages
                            .onNotificationError("Atualizar clientes", t);
                }
            });
    }

    private void PostTasks(TasksRequest tasks){
        API
            .postTasks(tasks)
            .enqueue(new Callback<TasksRequest>() {
                @Override
                public void onResponse(Call<TasksRequest> call, Response<TasksRequest> response) {
                    if (response.isSuccessful()){

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<TasksRequest> call, Throwable t) {
                    NotificationMessages
                            .onNotificationError("Salvar/atualizar tarefas", t);
                }
            });
    }
}
