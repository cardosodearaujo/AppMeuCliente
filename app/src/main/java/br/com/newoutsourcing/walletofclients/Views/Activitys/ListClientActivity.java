package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import butterknife.BindView;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class ListClientActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idBtnFam) FloatingActionMenu idBtnFam;
    protected @BindView(R.id.idBtnFabClientLegalPerson) FloatingActionButton idBtnFabClientLegalPerson;
    protected @BindView(R.id.idBtnFabClientPhysicalPerson) FloatingActionButton idBtnFabClientPhysicalPerson;
    protected @BindView(R.id.idBtnFabClientExport) FloatingActionButton idBtnFabClientExport;
    protected @BindView(R.id.idBtnFabContact) FloatingActionButton idBtnFabContact;
    protected @BindView(R.id.idBtnFabNewTaks) FloatingActionButton idBtnFabNewTaks;
    protected @BindView(R.id.idBtnFabConsultTasks) FloatingActionButton idBtnFabConsultTasks;
    protected @BindView(R.id.idRecycleView) RecyclerView idRecycleView;
    protected @BindView(R.id.idTvwSizeClient) TextView idTvwSizeClient;
    protected @BindView(R.id.idAdsView) AdView idAdsView;
    protected @BindView(R.id.idLLMessageEmpty) LinearLayout idLLMessageEmpty;
    protected @BindView(R.id.idBtnSearch) Button idBtnSearch;
    protected @BindView(R.id.idAppBarLayoutSearch) AppBarLayout idAppBarLayoutSearch;
    protected @BindView(R.id.idAppBarLayout) AppBarLayout idAppBarLayout;
    protected @BindView(R.id.idBtnSearchExit) Button idBtnSearchExit;
    protected @BindView(R.id.idEdtSearch) EditText idEdtSearch;
    protected @BindView(R.id.idSwipeContainer) SwipeRefreshLayout idSwipeContainer;
    protected @BindView(R.id.idLLMessageEmptySearch) LinearLayout idLLMessageEmptySearch;

    public ListClientActivity() {
        super(R.layout.activity_list_client);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onConfiguration(){
        this.setSupportActionBar(this.idToolbar);
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
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
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
            FunctionsTools.showSnackBarLong(this.View,ex.getMessage());
            return false;
        }
    }

    private void SairModoPesquisa(){
        this.idAppBarLayoutSearch.setVisibility(View.INVISIBLE);
        this.idAppBarLayout.setVisibility(View.VISIBLE);
        FunctionsTools.closeKeyboard(ListClientActivity.this ,this.View);
    }

    private void EntrarEmModoPesquisa(){
        idAppBarLayout.setVisibility(View.INVISIBLE);
        idAppBarLayoutSearch.setVisibility(View.VISIBLE);
        idEdtSearch.requestFocus();
        FunctionsTools.showKeybord(ListClientActivity.this);
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
            FunctionsTools.closeKeyboard(ListClientActivity.this,this.View);
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
            builder.setSingleChoiceItems(adapter, 0, (dialog, idOption) -> {
                Intent intent;
                switch (idOption) {
                    case 0: //Importar
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("text/*");
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intent,"Selecione um arquivo .CSV"), FunctionsTools.IMAGEM_INTERNA);
                        dialog.cancel();
                        break;
                    case 1: //Exportar
                        ExportClients();
                        dialog.cancel();
                        break;
                    case 2: //Deletar tudo:
                        FunctionsTools.showPgDialog(ListClientActivity.this,"Apagando os clientes...");
                        TB_ADDRESS.DeleteAll();
                        TB_ADDITIONAL_INFORMATION.DeleteAll();
                        TB_LEGAL_PERSON.DeleteAll();
                        TB_PHYSICAL_PERSON.DeleteAll();
                        TB_CLIENT.DeleteAll();
                        AtualizarLista();
                        FunctionsTools.closePgDialog();
                        dialog.cancel();
                }
            });
            alert = builder.create();
            alert.setTitle("Escolha uma opção:");
            alert.show();
        }catch (Exception ex){
            FunctionsTools.showAlertDialog(this.View.getContext(),"Erro!",ex.getMessage(),"Fechar");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == RESULT_OK) this.ImportClients(data.getData());
        }catch (Exception ex){
            FunctionsTools.showAlertDialog(ListClientActivity.this,"Erro",ex.getMessage(),"Fechar");
        }
    }

    private void ExportClients(){
        try{
            ExportAsyncTask exportAsyncTask = new ExportAsyncTask(ListClientActivity.this){
                @Override
                protected void onPostExecute(Boolean isSuccess) {
                    FunctionsTools.closePgDialog();
                    if (isSuccess){
                        FunctionsTools.showAlertDialog(
                                ListClientActivity.this,
                                "Atenção!",
                                "Clientes exportados com sucesso para a pasta /WalletOfClients/ListOfClients_" + FunctionsTools.getCurrentDate("dd-MM-yyyy") + ".csv",
                                "Fechar");
                    }else{
                        FunctionsTools.showSnackBarLong(View,"Não foi possivel importar os dados. Tente novamente!");
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
                    FunctionsTools.closePgDialog();
                    if (isSuccess) {
                        AtualizarLista();
                        FunctionsTools.showAlertDialog(ListClientActivity.this,"Atenção!","Clientes importados com sucesso!","Fechar");
                    }else{
                        FunctionsTools.showSnackBarLong(View,"Não foi possivel importar os dados. Tente novamente!");
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

    View.OnClickListener onClickBtnSearch = v -> EntrarEmModoPesquisa();

    View.OnClickListener onClickBtnSearchExit = v -> {
        SairModoPesquisa();
        AtualizarLista();
    };

    View.OnClickListener onClickBtnFabClientLegalPerson = v -> {
        Bundle bundle = new Bundle();
        bundle.putString("TipoCadastro","J");
        FunctionsTools.startActivity(ListClientActivity.this,RegisterClientActivity.class,bundle);
    };

    View.OnClickListener onClickBtnFabClientPhysicalPerson = v -> {
        Bundle bundle = new Bundle();
        bundle.putString("TipoCadastro","F");
        FunctionsTools.startActivity(ListClientActivity.this,RegisterClientActivity.class,bundle);
    };

    View.OnClickListener onClickBtnFabNewTaks = v -> FunctionsTools.startActivity(ListClientActivity.this,NewTaskActivity.class,null);

    View.OnClickListener onClickBtnFabConsultTasks = v -> FunctionsTools.startActivity(ListClientActivity.this,SearchTaksActivity.class,null);

    View.OnClickListener onClickBtnFabContact = v -> FunctionsTools.startActivity(ListClientActivity.this,ContactActivity.class,null);

    View.OnClickListener onClickExport = v -> Exportar();
}
