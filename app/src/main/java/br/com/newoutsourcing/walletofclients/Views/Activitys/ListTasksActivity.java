package br.com.newoutsourcing.walletofclients.Views.Activitys;

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
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Views.Adapters.Task.TaskAdapter;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import butterknife.BindView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class ListTasksActivity extends BaseActivity implements android.view.View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idBtnNewTask) FloatingActionButton idBtnNewTask;
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

    android.view.View.OnClickListener onClickBtnFabNewtasks = v -> FunctionsTools.startActivity(ListTasksActivity.this,NewTaskActivity.class,null);
}
