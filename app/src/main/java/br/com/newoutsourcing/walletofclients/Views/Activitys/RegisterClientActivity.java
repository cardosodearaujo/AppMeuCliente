package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AdditionalInformationFragment;
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
    private FragmentsCallback additionalInformationCallback;
    private String typePerson;
    private View idView;
    private Client client;
    private Button idBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register_client);
        this.onInflate();
        this.onConfiguration();
        this.onConfigurationFragments();
    }

    private void onInflate(){
        this.client = new Client();
        this.idView = this.findViewById(android.R.id.content);
        this.pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idViewPager = this.findViewById(R.id.idViewPager);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
        this.idTabLayout = this.findViewById(R.id.idTabLayout);
        this.idBtnSave = this.findViewById(R.id.idBtnSave);
        this.idBtnDelete = this.findViewById(R.id.idBtnDelete);
    }

    private void onConfiguration() {
        this.setSupportActionBar(this.idToolbar);
        this.idTabLayout.setupWithViewPager(this.idViewPager);
        this.idBtnClose.setOnClickListener(this.onClickClose);
        this.idBtnSave.setOnClickListener(this.onClickSave);
        this.idBtnDelete.setOnClickListener(this.onClickDelete);
    }

    private void onConfigurationFragments() {
        try{
            Fragment fragment;
            this.typePerson = this.getIntent().getExtras().getString("TipoCadastro");
            Bundle bundle = this.getIntent().getExtras();

            if (bundle != null && bundle.containsKey("Client")){
                this.client = (Client)bundle.getSerializable("Client");
            }else{
                this.idBtnDelete.setVisibility(View.INVISIBLE);
            }

            if (typePerson.equals("F")){
                fragment = PhysicalPersonFragment.newInstance();
                this.physicalPersonCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações",bundle);
            }else if (typePerson.equals("J")){
                fragment = LegalPersonFragment.newInstance();
                this.legalPersonCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Informações",bundle);
            }else{
                FunctionsApp.startActivity(RegisterClientActivity.this, ErrorActivity.class, bundle);
                FunctionsApp.showMessageError(RegisterClientActivity.this,"Erro","Opção não encontrada!");
                FunctionsApp.closeActivity(RegisterClientActivity.this);
            }

            if (this.pagerAdapter.getCount() > 0) {
                fragment = AdditionalInformationFragment.newInstance();
                this.additionalInformationCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Inf.Adicionais",bundle);

                fragment = AddressFragment.newInstance();
                this.addressCallback = (FragmentsCallback) fragment;
                this.pagerAdapter.addFragment(fragment, "Endereço",bundle);

                this.idViewPager.setAdapter(pagerAdapter);
            }
        }catch (Exception ex){
            FunctionsApp.showMessageError(RegisterClientActivity.this,"Erro",ex.getMessage());
        }
    }

    View.OnClickListener onClickDelete = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (client.getClientId() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                TB_CLIENT.Delete(client);
                                Toast.makeText(RegisterClientActivity.this,"Cliente excluido com sucesso!",Toast.LENGTH_LONG).show();
                                FunctionsApp.closeActivity(RegisterClientActivity.this);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.cancel();
                            }
                        })
                        .setMessage("Tem ceteza que deseja excluir?");

                final AlertDialog alert = builder.create();
                alert.show();
            }
        }
    };

    View.OnClickListener onClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                if (client == null || client.getClientId() <= 0){ client = new Client();}

                if (typePerson.equals("F")){client.setType(1);}else{client.setType(2);}
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
                        client = additionalInformationCallback.onSave(client);

                        if (client.getAdditionalInformation().isSuccess()){
                            idViewPager.setCurrentItem(2);
                            client = addressCallback.onSave(client);

                            if (client.getAddress().isSuccess()){
                                idViewPager.setCurrentItem(0);

                                if (client.getClientId() <= 0){ /**Inclui cliente**/
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

                                        if (client.getType() == 1){physicalPersonCallback.onClear();}else{legalPersonCallback.onClear();}
                                        additionalInformationCallback.onClear();
                                        addressCallback.onClear();

                                        FunctionsApp.showSnackBarLong(v,"Cliente salvo!");
                                    }
                                }else{/**Atualiza cliente**/
                                    TB_CLIENT.Update(client);
                                    if (client.getType() == 1){TB_PHYSICAL_PERSON.Update(client.getPhysicalPerson());}else{TB_LEGAL_PERSON.Update(client.getLegalPerson());}
                                    TB_ADDITIONAL_INFORMATION.Update(client.getAdditionalInformation());
                                    TB_ADDRESS.Update(client.getAddress());

                                    if (client.getType() == 1){physicalPersonCallback.onClear();}else{legalPersonCallback.onClear();}
                                    additionalInformationCallback.onClear();
                                    addressCallback.onClear();

                                    FunctionsApp.showSnackBarLong(v,"Cliente atualizado!");
                                }
                            }
                        }
                    }
                }
            }catch (Exception ex) {
                FunctionsApp.showMessageError(RegisterClientActivity.this,"Erro",ex.getMessage());
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
