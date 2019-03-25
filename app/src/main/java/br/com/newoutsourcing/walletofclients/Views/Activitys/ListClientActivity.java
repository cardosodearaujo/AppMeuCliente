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

    @Override
    public void onClick(View v) {
        //Nada por enquanto....
    }

    private void loadConfigurationView(){
        //Inflando views:
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idBtnFam = this.findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = this.findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = this.findViewById(R.id.idBtnFabClientPhysicalPerson);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        //Eventos dos botões:
        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        //Configurando a RecyclerView:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void loadInformationView(){
        this.idRecycleView.setAdapter(this.getAdapter());
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

    private ClientAdapter getAdapter(){
        ArrayList<ClientObject> clientList = new ArrayList<ClientObject>();
        for (int cont = 0; cont <100; cont++){
            if (cont % 2 == 0){
                clientList.add(getClient((cont+1) + " - Everaldo Cardoso de Araújo  ", "146.442.937-50","PF"));
            }else {
                clientList.add(getClient((cont+1) + " - New Outsourcing MEI", "30.797.064/0001-81", "PF"));
            }
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
}
