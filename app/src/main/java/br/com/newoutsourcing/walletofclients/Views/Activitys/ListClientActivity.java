package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import java.util.ArrayList;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ClientAdapter;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.ClientObject;
import br.com.newoutsourcing.walletofclients.R;


public class ListClientActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar idToolbar;
    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;
    private FloatingActionButton idBtnFabError;
    private RecyclerView idRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list_client);
        this.loadConfigurationView();
        this.loadInformationView();
        this.setSupportActionBar(this.idToolbar);
    }

    private void loadConfigurationView(){
        //Inflando views:
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idBtnFam = this.findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = this.findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = this.findViewById(R.id.idBtnFabClientPhysicalPerson);
        this.idBtnFabError = this.findViewById(R.id.idBtnFabError);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);

        //Eventos dos botões:
        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnFabError.setOnClickListener(this.onClickBtnFabError);

        //Configurando a RecyclerView:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void loadInformationView(){
        this.idRecycleView.setAdapter(this.getAdapter());
    }

    private ClientAdapter getAdapter(){
        ArrayList<ClientObject> clientList = new ArrayList<ClientObject>();
        for (int cont = 0; cont <=100; cont++){
            clientList.add(getClient("Pai de familha " + cont, "11111111111","PF"));
        }
        return new ClientAdapter(clientList);
    }

    private ClientObject getClient(String nome, String CPF_CNPJ, String tipo){
        ClientObject client = new ClientObject();
        client.setNome(nome);
        client.setCPF_CNPJ(CPF_CNPJ);
        client.setTipo(tipo);
        return client;
    }

    View.OnClickListener onClickBtnFabClientLegalPerson = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("TipoCadastro","J");
            FunctionsApp.startActivity(ListClientActivity.this,RegisterClientActivity.class,bundle);
        }
    };

    View.OnClickListener onClickBtnFabClientPhysicalPerson = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("TipoCadastro","F");
            FunctionsApp.startActivity(ListClientActivity.this,RegisterClientActivity.class,bundle);
        }
    };

    View.OnClickListener onClickBtnFabError = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.startActivity(ListClientActivity.this,ErrorActivity.class,null );
        }
    };

    @Override
    public void onClick(View v) {
        //Nada por enquanto....
    }
}
