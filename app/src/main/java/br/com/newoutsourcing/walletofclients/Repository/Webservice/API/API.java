package br.com.newoutsourcing.walletofclients.Repository.Webservice.API;

import br.com.newoutsourcing.walletofclients.Repository.Webservice.Interfaces.ClientAPI;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.Interfaces.TasksAPI;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.ClientRequest;
import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.TasksRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final Retrofit RETROFIT = new Retrofit
            .Builder()
            .baseUrl("https://newmeucrmapi.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static Call<ClientRequest> postClient(ClientRequest client) {
        ClientAPI clientAPI = RETROFIT.create(ClientAPI.class);
        return clientAPI.post(client);
    }

    public static Call<ClientRequest> putClient(ClientRequest client) {
        ClientAPI userAPI = RETROFIT.create(ClientAPI.class);
        return userAPI.put(client);
    }

    public static Call<TasksRequest> postTasks(TasksRequest tasks){
        TasksAPI tasksAPI = RETROFIT.create(TasksAPI.class);
        return tasksAPI.post(tasks);
    }
}
