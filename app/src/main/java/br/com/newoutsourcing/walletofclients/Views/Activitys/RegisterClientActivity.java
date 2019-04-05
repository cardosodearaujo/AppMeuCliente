package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.AdditionalDataCallback;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.AddressCallback;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.LegalPersonCallback;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.PhysicalPersonCallback;
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
    private PhysicalPersonCallback physicalPersonCallback;
    private LegalPersonCallback legalPersonCallback;
    private AddressCallback addressCallback;
    private AdditionalDataCallback additionalDataCallback;
    private Button idBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.onInflate();
        this.onConfiguration();
        this.onConfigurationFragments();
    }

    private void onInflate(){
        super.setContentView(R.layout.activity_register_client);
        this.pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idViewPager = this.findViewById(R.id.idViewPager);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
        this.idTabLayout = this.findViewById(R.id.idTabLayout);
        this.idBtnSave = this.findViewById(R.id.idBtnSave);
    }

    private void onConfiguration() {
        this.setSupportActionBar(this.idToolbar);
        this.idTabLayout.setupWithViewPager(this.idViewPager);
        this.idBtnClose.setOnClickListener(this.onClickClose);
        this.idBtnSave.setOnClickListener(onClickSave);
    }

    private void onConfigurationFragments() {
        Fragment fragment;
        switch (this.getIntent().getExtras().getString("TipoCadastro")) {
            case "F":
                fragment = PhysicalPersonFragment.newInstance();
                this.physicalPersonCallback = (PhysicalPersonCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações");
                break;
            case "J":
                fragment = LegalPersonFragment.newInstance();
                this.legalPersonCallback = (LegalPersonCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações");
                break;
            default:
                FunctionsApp.startActivity(RegisterClientActivity.this, ErrorActivity.class, null);
                FunctionsApp.closeActivity(RegisterClientActivity.this);
                break;
        }

        if (this.pagerAdapter.getCount() > 0) {
            fragment = AdditionalDataFragment.newInstance();
            this.additionalDataCallback = (AdditionalDataCallback) fragment;
            this.pagerAdapter.addFragment(fragment, "Inf.Adicionais");

            fragment = AddressFragment.newInstance();
            this.addressCallback = (AddressCallback) fragment;
            this.pagerAdapter.addFragment(fragment, "Endereço");

            this.idViewPager.setAdapter(pagerAdapter);
        }
    }

    View.OnClickListener onClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            idViewPager.setCurrentItem(0);
            if (physicalPersonCallback.Save()){
                idViewPager.setCurrentItem(1);
                if (additionalDataCallback.Save()){
                    idViewPager.setCurrentItem(2);
                    if (addressCallback.Save()){
                        idViewPager.setCurrentItem(0);
                        FunctionsApp.showSnackBarLong(v, "Cliente salvo com sucesso!");
                    }
                }
            }
        }
    };

    View.OnClickListener onClickClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(RegisterClientActivity.this);
        }
    };
}
