package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class ListClientActivity extends AppCompatActivity {

    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.idBtnFam = findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = findViewById(R.id.idBtnFabClientPhysicalPerson);

        this.idBtnFabClientLegalPerson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FunctionsApp.iniciarActivity(ListClientActivity.this,RegisterClientActivity.class,null);
            }
        });
        this.idBtnFabClientPhysicalPerson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FunctionsApp.iniciarActivity(ListClientActivity.this,RegisterClientActivity.class,null);
            }
        });
    }
}
