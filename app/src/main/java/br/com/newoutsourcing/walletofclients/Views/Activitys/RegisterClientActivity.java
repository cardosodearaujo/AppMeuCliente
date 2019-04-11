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
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AdditionalDataFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AddressFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class RegisterClientActivity extends AppCompatActivity {

    private ViewPager idViewPager;
    private Button idBtnClose;
    private Toolbar idToolbar;
    private TabPagerAdapter pagerAdapter;
    private TabLayout idTabLayout;
    private Button idBtnSave;
    private FragmentsCallback physicalPersonCallback;
    private FragmentsCallback legalPersonCallback;
    private FragmentsCallback addressCallback;
    private FragmentsCallback additionalDataCallback;
    private String typePerson;

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
        this.typePerson = this.getIntent().getExtras().getString("TipoCadastro");
        switch (typePerson) {
            case "F":
                fragment = PhysicalPersonFragment.newInstance();
                this.physicalPersonCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações");
                break;
            case "J":
                fragment = LegalPersonFragment.newInstance();
                this.legalPersonCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações");
                break;
            default:
                FunctionsApp.startActivity(RegisterClientActivity.this, ErrorActivity.class, null);
                FunctionsApp.closeActivity(RegisterClientActivity.this);
                break;
        }

        if (this.pagerAdapter.getCount() > 0) {
            fragment = AdditionalDataFragment.newInstance();
            this.additionalDataCallback = (FragmentsCallback) fragment;
            this.pagerAdapter.addFragment(fragment, "Inf.Adicionais");

            fragment = AddressFragment.newInstance();
            this.addressCallback = (FragmentsCallback) fragment;
            this.pagerAdapter.addFragment(fragment, "Endereço");

            this.idViewPager.setAdapter(pagerAdapter);
        }
    }

    View.OnClickListener onClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Client client = new Client();

                if (typePerson.equals("F")){
                    client.setType(1);
                }else {
                    client.setType(2);
                }
                client.setSuccess(true);

                if (client.isSuccess()){
                    boolean proceed;
                    idViewPager.setCurrentItem(0);

                    if (client.getType()==1){
                        client = physicalPersonCallback.onSave(client);
                        proceed = client.getPhysicalPerson().isSuccess();
                    }else{
                        client = legalPersonCallback.onSave(client);
                        proceed = client.getLegalPerson().isSuccess();
                    }

                    if (proceed){
                        idViewPager.setCurrentItem(1);
                        client = additionalDataCallback.onSave(client);

                        if (client.getAdditionalInformation().isSuccess()){
                            idViewPager.setCurrentItem(2);
                            client = addressCallback.onSave(client);

                            if (client.getAddress().isSuccess()){
                                idViewPager.setCurrentItem(0);
                                client.setClientId(TB_CLIENT.Insert(client));

                                if (client.getClientId() > 0){

                                    if (client.getType() == 1){
                                        client.getPhysicalPerson().setClientId(client.getClientId());
                                        TB_PHYSICAL_PERSON.Insert(client.getPhysicalPerson());
                                    }else{
                                        client.getLegalPerson().setClientId(client.getClientId());
                                        TB_LEGAL_PERSON.Insert(client.getLegalPerson());
                                    }

                                    client.getAdditionalInformation().setClientId(client.getClientId());
                                    TB_ADDITIONAL_INFORMATION.Insert(client.getAdditionalInformation());

                                    client.getAddress().setClientId(client.getClientId());
                                    TB_ADDRESS.Insert(client.getAddress());

                                    if (client.getType() == 1){
                                        physicalPersonCallback.onClear();
                                    }else{
                                        legalPersonCallback.onClear();
                                    }
                                    additionalDataCallback.onClear();
                                    addressCallback.onClear();

                                    FunctionsApp.showSnackBarLong(v,"Cliente salvo com sucesso!");
                                }
                            }
                        }
                    }
                }
            }catch (Exception ex) {
                FunctionsApp.showSnackBarLong(v, ex.getMessage());
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
