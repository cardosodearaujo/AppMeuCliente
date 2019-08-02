package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Repository.Tasks.ExportAsyncTask;
import br.com.newoutsourcing.walletofclients.Repository.Tasks.ImportAsyncTask;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ClientAdapter;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class ListClientActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private Toolbar idToolbar;
    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;
    private FloatingActionButton idBtnFabClientExport;
    private FloatingActionButton idBtnFabContact;
    private FloatingActionButton idBtnFabNewTaks;
    private FloatingActionButton idBtnFabConsultTasks;
    private RecyclerView idRecycleView;
    private TextView idTvwSizeClient;
    private View idView;
    private AdView idAdsView;
    private LinearLayout idLLMessageEmpty;
    private Button idBtnSearch;
    private AppBarLayout idAppBarLayoutSearch;
    private AppBarLayout idAppBarLayout;
    private Button idBtnSearchExit;
    private EditText idEdtSearch;
    private SwipeRefreshLayout idSwipeContainer;
    private LinearLayout idLLMessageEmptySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list_client);
        this.onInflate();
        this.onConfiguration();
        this.setSupportActionBar(this.idToolbar);
    }

    @Override
    public void onClick(View v) {
    }

    private void onInflate(){
        this.idView = this.findViewById(android.R.id.content);
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idBtnFam = this.findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = this.findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = this.findViewById(R.id.idBtnFabClientPhysicalPerson);
        this.idBtnFabClientExport = this.findViewById(R.id.idBtnFabClientExport);
        this.idBtnFabContact = this.findViewById(R.id.idBtnFabContact);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        this.idTvwSizeClient = this.findViewById(R.id.idTvwSizeClient);
        this.idAdsView = this.findViewById(R.id.idAdsView);
        this.idLLMessageEmpty = this.findViewById(R.id.idLLMessageEmpty);
        this.idBtnSearch = this.findViewById(R.id.idBtnSearch);
        this.idAppBarLayoutSearch = this.findViewById(R.id.idAppBarLayoutSearch);
        this.idAppBarLayout = this.findViewById(R.id.idAppBarLayout);
        this.idBtnSearchExit = this.findViewById(R.id.idBtnSearchExit);
        this.idEdtSearch = this.findViewById(R.id.idEdtSearch);
        this.idSwipeContainer = this.findViewById(R.id.idSwipeContainer);
        this.idLLMessageEmptySearch = this.findViewById(R.id.idLLMessageEmptySearch);
        this.idBtnFabNewTaks = this.findViewById(R.id.idBtnFabNewTaks);
        this.idBtnFabConsultTasks = this.findViewById(R.id.idBtnFabConsultTasks);
    }

    private void onConfiguration(){
        MobileAds.initialize(ListClientActivity.this, "@string/str_app_admob_id");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.idAdsView.loadAd(adRequest);

        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnFabClientExport.setOnClickListener(this.onClickExport);
        this.idBtnFabContact.setOnClickListener(this.onClickBtnFabContact);
        this.idBtnFabNewTaks.setOnClickListener(this.onClickBtnFabNewTaks);
        this.idBtnFabConsultTasks.setOnClickListener(this.onClickBtnFabConsultTasks);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.idBtnSearch.setOnClickListener(this.onClickBtnSearch);
        this.idBtnSearchExit.setOnClickListener(this.onClickBtnSearchExit);
        this.idSwipeContainer.setOnRefreshListener(this);
        this.idSwipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onResume(){
        super.onResume();
        this.AtualizarLista();
        if (this.idAppBarLayoutSearch.getVisibility() == View.VISIBLE) this.SairModoPesquisa();
    }

    public boolean AtualizarLista(){
        try{
            if (this.idRecycleView != null && TB_CLIENT != null){
                List<Client> clientList = TB_CLIENT.Select();
                if (clientList != null && clientList.size() > 0){
                    this.idTvwSizeClient.setText("Total: " + clientList.size());
                    this.idRecycleView.setAdapter(new ClientAdapter(clientList));
                    this.idTvwSizeClient.setVisibility(View.VISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.INVISIBLE);
                    this.idLLMessageEmptySearch.setVisibility(View.INVISIBLE);
                    this.idRecycleView.setVisibility(View.VISIBLE);
                }else{
                    this.idTvwSizeClient.setVisibility(View.INVISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.VISIBLE);
                    this.idLLMessageEmptySearch.setVisibility(View.INVISIBLE);
                    this.idRecycleView.setVisibility(View.INVISIBLE);
                }
            }
            return true;
        }catch (Exception ex){
            FunctionsApp.showSnackBarLong(this.idView,ex.getMessage());
            return false;
        }
    }

    private void SairModoPesquisa(){
        this.idAppBarLayoutSearch.setVisibility(View.INVISIBLE);
        this.idAppBarLayout.setVisibility(View.VISIBLE);
        FunctionsApp.closeKeyboard(ListClientActivity.this ,this.idView);
    }

    private void EntrarEmModoPesquisa(){
        idAppBarLayout.setVisibility(View.INVISIBLE);
        idAppBarLayoutSearch.setVisibility(View.VISIBLE);
        idEdtSearch.requestFocus();
        FunctionsApp.showKeybord(ListClientActivity.this);
    }

    private void BuscarClientes(){
        List<Client> clientList = TB_CLIENT.Select(idEdtSearch.getText().toString());
        if (clientList != null && clientList.size() > 0){
            idRecycleView.setAdapter(new ClientAdapter(clientList));
            idTvwSizeClient.setText("Total: " + clientList.size());
            idTvwSizeClient.setVisibility(View.VISIBLE);
            idLLMessageEmptySearch.setVisibility(View.INVISIBLE);
            idRecycleView.setVisibility(View.VISIBLE);
        }else{
            idRecycleView.setAdapter(new ClientAdapter(clientList));
            idTvwSizeClient.setText("Total: " + clientList.size());
            idTvwSizeClient.setVisibility(View.INVISIBLE);
            idLLMessageEmptySearch.setVisibility(View.VISIBLE);
            idRecycleView.setVisibility(View.INVISIBLE);
            if (idLLMessageEmpty.getVisibility() == View.VISIBLE) idLLMessageEmpty.setVisibility(View.INVISIBLE);
            FunctionsApp.closeKeyboard(ListClientActivity.this,this.idView);
        }
    }

    private void Exportar(){
        try{
            AlertDialog alert;
            ArrayList<String> itens = new ArrayList<String>();

            itens.add("Importar");
            itens.add("Exportar");
            itens.add("Apagar todos os clientes");

            ArrayAdapter adapter = new ArrayAdapter(ListClientActivity.this, R.layout.alert_dialog_question, itens);
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ListClientActivity.this,R.style.Theme_MaterialComponents_Light_Dialog);
            builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int idOption) {
                    Intent intent;
                    switch (idOption) {
                        case 0: //Importar
                            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            intent.setType("text/*");
                            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(Intent.createChooser(intent,"Selecione um arquivo .CSV"),FunctionsApp.IMAGEM_INTERNA);
                            dialog.cancel();
                            break;
                        case 1: //Exportar
                            ExportClients();
                            dialog.cancel();
                            break;
                        case 2: //Deletar tudo:
                            FunctionsApp.showPgDialog(ListClientActivity.this,"Apagando os clientes...");
                            TB_ADDRESS.DeleteAll();
                            TB_ADDITIONAL_INFORMATION.DeleteAll();
                            TB_LEGAL_PERSON.DeleteAll();
                            TB_PHYSICAL_PERSON.DeleteAll();
                            TB_CLIENT.DeleteAll();
                            AtualizarLista();
                            FunctionsApp.closePgDialog();
                            dialog.cancel();
                    }
                }
            });
            alert = builder.create();
            alert.setTitle("Escolha uma opção:");
            alert.show();
        }catch (Exception ex){
            FunctionsApp.showAlertDialog(this.idView.getContext(),"Erro!",ex.getMessage(),"Fechar");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == RESULT_OK) this.ImportClients(data.getData());
        }catch (Exception ex){
            FunctionsApp.showAlertDialog(ListClientActivity.this,"Erro",ex.getMessage(),"Fechar");
        }
    }

    private void ExportClients(){
        try{
            ExportAsyncTask exportAsyncTask = new ExportAsyncTask(ListClientActivity.this){
                @Override
                protected void onPostExecute(Boolean isSuccess) {
                    FunctionsApp.closePgDialog();
                    if (isSuccess){
                        FunctionsApp.showAlertDialog(
                                ListClientActivity.this,
                                "Atenção!",
                                "Clientes exportados com sucesso para a pasta /WalletOfClients/ListOfClients_" + FunctionsApp.getCurrentDate("dd-MM-yyyy") + ".csv",
                                "Fechar");
                    }else{
                        FunctionsApp.showSnackBarLong(idView,"Não foi possivel importar os dados. Tente novamente!");
                    }
                }
            };

            exportAsyncTask.execute();
        }catch (Exception ex){
            throw ex;
        }
    }

    private void ImportClients(Uri uri) {
        try{
            ImportAsyncTask importAsyncTask = new ImportAsyncTask(ListClientActivity.this){
                @Override
                protected void onPostExecute(Boolean isSuccess){
                    FunctionsApp.closePgDialog();
                    if (isSuccess) {
                        AtualizarLista();
                        FunctionsApp.showAlertDialog(ListClientActivity.this,"Atenção!","Clientes importados com sucesso!","Fechar");
                    }else{
                        FunctionsApp.showSnackBarLong(idView,"Não foi possivel importar os dados. Tente novamente!");
                    }
                }
            };
            importAsyncTask.execute(uri);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void onRefresh() {
        if (this.AtualizarLista()) this.idSwipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_ENTER && this.idAppBarLayoutSearch.getVisibility() == View.VISIBLE){
           this.BuscarClientes();
           return true;
       }else{
           return super.onKeyUp(keyCode, event);
       }
    }

    View.OnClickListener onClickBtnSearch = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            EntrarEmModoPesquisa();
        }
    };

    View.OnClickListener onClickBtnSearchExit = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            SairModoPesquisa();
            AtualizarLista();
        }
    };

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

    View.OnClickListener onClickBtnFabNewTaks = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.startActivity(ListClientActivity.this,NewTaskActivity.class,null);
        }
    };

    View.OnClickListener onClickBtnFabConsultTasks = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.startActivity(ListClientActivity.this,SearchTaksActivity.class,null);
        }
    };

    View.OnClickListener onClickBtnFabContact = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FunctionsApp.startActivity(ListClientActivity.this,ContactActivity.class,null);
        }
    };

    View.OnClickListener onClickExport = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Exportar();
        }
    };
}
