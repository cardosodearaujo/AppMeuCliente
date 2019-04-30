package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
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

public class ListClientActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar idToolbar;
    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;
    private FloatingActionButton idBtnFabClientExport;
    private FloatingActionButton idBtnFabClientConfig;
    private RecyclerView idRecycleView;
    private TextView idTvwSizeClient;
    private View idView;
    private AdView idAdsView;
    private LinearLayout idLLMessageEmpty;

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
        this.idBtnFabClientConfig = this.findViewById(R.id.idBtnFabClientConfig);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        this.idTvwSizeClient = this.findViewById(R.id.idTvwSizeClient);
        this.idAdsView = this.findViewById(R.id.idAdsView);
        this.idLLMessageEmpty = this.findViewById(R.id.idLLMessageEmpty);
    }

    private void onConfiguration(){
        MobileAds.initialize(ListClientActivity.this, "@string/str_app_admob_id");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.idAdsView.loadAd(adRequest);

        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnFabClientExport.setOnClickListener(this.onClickExport);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume(){
        super.onResume();
        this.AtualizarLista();
    }

    public void AtualizarLista(){
        try{
            if (this.idRecycleView != null && TB_CLIENT != null){
                List<Client> clientList = TB_CLIENT.Select();
                if (clientList != null && clientList.size() > 0){
                    this.idTvwSizeClient.setText("Total: " + clientList.size());
                    this.idRecycleView.setAdapter(new ClientAdapter(clientList));
                    this.idTvwSizeClient.setVisibility(View.VISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.INVISIBLE);
                    this.idRecycleView.setVisibility(View.VISIBLE);
                }else{
                    this.idTvwSizeClient.setVisibility(View.INVISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.VISIBLE);
                    this.idRecycleView.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception ex){
            FunctionsApp.showSnackBarLong(this.idView,ex.getMessage());
        }
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

    View.OnClickListener onClickExport = new View.OnClickListener(){
        @Override
        public void onClick(View v){
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
                FunctionsApp.showAlertDialog(v.getContext(),"Erro!",ex.getMessage(),"Fechar");
            }
        }
    };

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
}
