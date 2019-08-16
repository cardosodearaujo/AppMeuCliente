package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Views.Adapters.Task.TaskAdapter;
import br.com.newoutsourcing.walletofclients.Views.Bases.ActivityBase;
import butterknife.BindView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class ListTasksActivity extends ActivityBase implements android.view.View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idBtnNewTask) FloatingActionButton idBtnNewTask;
    protected @BindView(R.id.idBtnConfig) FloatingActionButton idBtnConfig;
    protected @BindView(R.id.idRecycleView) RecyclerView idRecycleView;
    protected @BindView(R.id.idTvwSizeClient) TextView idTvwSizeClient;
    protected @BindView(R.id.idAdsView) AdView idAdsView;
    protected @BindView(R.id.idLLMessageEmpty) LinearLayout idLLMessageEmpty;
    protected @BindView(R.id.idSwipeContainer) SwipeRefreshLayout idSwipeContainer;

    public ListTasksActivity() {
        super(R.layout.activity_list_tasks);
    }

    @Override
    protected void onConfiguration() {
        this.setSupportActionBar(this.idToolbar);
        MobileAds.initialize(ListTasksActivity.this, "@string/str_app_admob_id");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.idAdsView.loadAd(adRequest);

        this.idBtnNewTask.setOnClickListener(this.onClickBtnFabNewtasks);
        this.idBtnConfig.setOnClickListener(this.onClickConfig);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListTasksActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.idSwipeContainer.setOnRefreshListener(this);
        this.idSwipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(android.view.View v) {

    }

    @Override
    public void onRefresh() {
        if (this.AtualizarLista()) this.idSwipeContainer.setRefreshing(false);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.AtualizarLista();
    }

    public boolean AtualizarLista(){
        try{
            if (this.idRecycleView != null && TB_TASKS != null){
                List<Tasks> list = TB_TASKS.Select();
                if (list != null && list.size() > 0){
                    this.idTvwSizeClient.setText("Total: " + list.size());
                    this.idRecycleView.setAdapter(new TaskAdapter(list));
                    this.idTvwSizeClient.setVisibility(View.VISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.INVISIBLE);
                    this.idRecycleView.setVisibility(View.VISIBLE);
                }else{
                    this.idTvwSizeClient.setVisibility(View.INVISIBLE);
                    this.idLLMessageEmpty.setVisibility(View.VISIBLE);
                    this.idRecycleView.setVisibility(View.INVISIBLE);
                }
            }
            return true;
        }catch (Exception ex){
            FunctionsTools.showSnackBarLong(this.View,ex.getMessage());
            return false;
        }
    }

    private void onClose(){
        FunctionsTools.closeActivity(ListTasksActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_default_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.idItemClose:
                onClose();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void config(){
        try{
            AlertDialog alert;
            ArrayList<String> itens = new ArrayList<String>();

            itens.add("Apagar todos as tarefas");

            ArrayAdapter adapter = new ArrayAdapter(ListTasksActivity.this, R.layout.alert_dialog_question, itens);
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ListTasksActivity.this,R.style.Theme_MaterialComponents_Light_Dialog);
            builder.setSingleChoiceItems(adapter, 0, (dialog, idOption) -> {
                switch (idOption) {
                    case 0: //Deletar agendamentos
                        FunctionsTools.showPgDialog(ListTasksActivity.this,"Apagando as tarefas...");
                        TB_TASKS.DeleteAll();
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

    android.view.View.OnClickListener onClickBtnFabNewtasks = v -> FunctionsTools.startActivity(ListTasksActivity.this,NewTaskActivity.class,null);

    android.view.View.OnClickListener onClickConfig = v -> config();
}
