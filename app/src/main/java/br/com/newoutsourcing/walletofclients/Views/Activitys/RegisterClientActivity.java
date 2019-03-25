package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.SectionsPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AdressFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;

public class RegisterClientActivity extends AppCompatActivity {

    private ViewPager idViewPager;
    private Button idBtnClose;
    private Toolbar idToolbar;
    private SectionsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register_client);
        this.loadConfigurationView();
        this.loadInformationToView();
        super.setSupportActionBar(this.idToolbar);
    }

    private void loadConfigurationView(){
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idViewPager = this.findViewById(R.id.idViewPager);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
        this.pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.idBtnClose.setOnClickListener(this.onClickClose);
    }

    public void loadInformationToView(){
        switch (this.getIntent().getExtras().getString("TipoCadastro")){
            case "F":
                this.pagerAdapter.addFragment(PhysicalPersonFragment.newInstance());
                break;
            case "J":
                this.pagerAdapter.addFragment(LegalPersonFragment.newInstance());
                break;
            default:
                FunctionsApp.startActivity(RegisterClientActivity.this,ErrorActivity.class,null);
                FunctionsApp.closeActivity(RegisterClientActivity.this);
                break;
        }
        if (this.pagerAdapter.getCount()>0){
            this.pagerAdapter.addFragment(AdressFragment.newInstance());
            this.idViewPager.setAdapter(pagerAdapter);
        }
    }

    View.OnClickListener onClickClose = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(RegisterClientActivity.this);
        }
    };
}
