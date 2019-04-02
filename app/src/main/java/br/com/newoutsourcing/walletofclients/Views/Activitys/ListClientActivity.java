package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import java.util.ArrayList;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ClientAdapter;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class ListClientActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar idToolbar;
    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;
    private RecyclerView idRecycleView;
    private Button idBtnClose;

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
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
        //Eventos dos botões:
        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnClose.setOnClickListener(this.onClickClose);
        //Configurando a RecyclerView:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void loadInformationView(){
        //this.idRecycleView.setAdapter(this.getAdapter());
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

    View.OnClickListener onClickClose = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(ListClientActivity.this);
        }
    };

    private ClientAdapter getAdapter(){
        ArrayList<Client> clientList = new ArrayList<Client>();
        return new ClientAdapter(clientList);
    }
}
