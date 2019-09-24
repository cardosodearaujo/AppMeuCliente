package br.com.newoutsourcing.walletofclients.Repository.Webservice.Interfaces;

import br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects.ClientRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ClientAPI {
    @POST("client/")
    Call<ClientRequest> post(@Body ClientRequest client);

    @PUT("client/")
    Call<ClientRequest> put(@Body ClientRequest client);
}
