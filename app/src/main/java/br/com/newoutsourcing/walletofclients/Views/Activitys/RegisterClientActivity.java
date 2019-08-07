package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AdditionalInformationFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.AddressFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;
import butterknife.BindView;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class RegisterClientActivity extends BaseActivity {

    protected @BindView(R.id.idViewPager) ViewPager idViewPager;
    protected @BindView(R.id.idBtnClose) Button idBtnClose;
    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idTabLayout) TabLayout idTabLayout;
    protected @BindView(R.id.idBtnSave) Button idBtnSave;
    protected @BindView(R.id.idBtnDelete) Button idBtnDelete;

    private TabPagerAdapter pagerAdapter;
    private FragmentsCallback physicalPersonCallback;
    private FragmentsCallback legalPersonCallback;
    private FragmentsCallback addressCallback;
    private FragmentsCallback additionalInformationCallback;
    private String typePerson;
    private Client client;

    public RegisterClientActivity() {
        super(R.layout.activity_register_client);
    }

    @Override
    protected void onConfiguration() {
        this.client = new Client();
        this.pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        this.setSupportActionBar(this.idToolbar);
        this.idTabLayout.setupWithViewPager(this.idViewPager);
        this.idBtnClose.setOnClickListener(this.onClickClose);
        this.idBtnSave.setOnClickListener(this.onClickSave);
        this.idBtnDelete.setOnClickListener(this.onClickDelete);
        this.onConfigurationFragments();
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
                FunctionsApp.showAlertDialog(RegisterClientActivity.this,"Erro","Opção não encontrada!","Fechar");
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
            FunctionsApp.showAlertDialog(RegisterClientActivity.this,"Erro",ex.getMessage(),"Fechar");
        }
    }

    View.OnClickListener onClickDelete = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (client.getClientId() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
                builder.setPositiveButton("Sim", (dialog, id) -> {
                    TB_CLIENT.Delete(client);
                    Toast.makeText(RegisterClientActivity.this,"Cliente excluido com sucesso!",Toast.LENGTH_LONG).show();
                    FunctionsApp.closeActivity(RegisterClientActivity.this);
                    dialog.cancel();
                })
                        .setNegativeButton("Não", (dialog, id) -> dialog.cancel())
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

                                    idBtnDelete.setVisibility(View.INVISIBLE);

                                    FunctionsApp.showSnackBarLong(v,"Cliente atualizado!");
                                }
                            }
                        }
                    }
                }
            }catch (Exception ex) {
                FunctionsApp.showAlertDialog(RegisterClientActivity.this,"Erro",ex.getMessage(),"Fechar");
            }
        }
    };

    View.OnClickListener onClickClose = v -> FunctionsApp.closeActivity(RegisterClientActivity.this);
}
