package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.App.RecyclerViewTouchListenerApp;
import br.com.newoutsourcing.walletofclients.R;

public class ListClientActivity extends AppCompatActivity implements RecyclerViewTouchListenerApp.RecyclerViewOnClickListenerHack,
        NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor> ,
        SwipeRefreshLayout.OnRefreshListener{

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
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idBtnFam = this.findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = this.findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = this.findViewById(R.id.idBtnFabClientPhysicalPerson);
        this.idBtnFabError = this.findViewById(R.id.idBtnFabError);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnFabError.setOnClickListener(this.onClickBtnFabError);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        this.idRecycleView.setHasFixedSize(true);
        this.idRecycleView.addOnItemTouchListener(new RecyclerViewTouchListenerApp(ListClientActivity.this,this.idRecycleView,this));
        this.idRecycleView.setLayoutManager(linearLayoutManager);
    }

    private void loadInformationView(){
        this.idRecycleView.setAdapter(null);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
