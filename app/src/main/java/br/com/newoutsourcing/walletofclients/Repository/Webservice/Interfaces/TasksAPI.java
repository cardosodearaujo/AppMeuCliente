package br.com.newoutsourcing.walletofclients.Repository.Webservice.Interfaces;

import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.TasksRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TasksAPI {
    @POST("task/")
    Call<TasksRequest> post(@Body TasksRequest tasks);
}
