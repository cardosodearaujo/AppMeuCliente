package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AdditionalDataFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AddressFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;

public class RegisterClientActivity extends AppCompatActivity {

    private ViewPager idViewPager;
    private Button idBtnClose;
    private Toolbar idToolbar;
    private TabPagerAdapter pagerAdapter;
    private TabLayout idTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register_client);
        this.loadConfigurationView();
        this.loadInformationToView();
    }

    private void loadConfigurationView(){
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idViewPager = this.findViewById(R.id.idViewPager);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
        this.idTabLayout = this.findViewById(R.id.idTabLayout);
        this.pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        this.idBtnClose.setOnClickListener(this.onClickClose);
        this.idTabLayout.setupWithViewPager(this.idViewPager);
        this.setSupportActionBar(this.idToolbar);
    }

    public void loadInformationToView(){
        AddressFragment address;
        switch (this.getIntent().getExtras().getString("TipoCadastro")){
            case "F":
                this.pagerAdapter.addFragment(PhysicalPersonFragment.newInstance(),"Informações");
                break;
            case "J":
                this.pagerAdapter.addFragment(LegalPersonFragment.newInstance(),"Informações");
                break;
            default:
                FunctionsApp.startActivity(RegisterClientActivity.this,ErrorActivity.class,null);
                FunctionsApp.closeActivity(RegisterClientActivity.this);
                break;
        }
        if (this.pagerAdapter.getCount()>0){
            this.pagerAdapter.addFragment(AddressFragment.newInstance(),"Endereço");
            this.pagerAdapter.addFragment(AdditionalDataFragment.newInstance(),"Observações");
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
