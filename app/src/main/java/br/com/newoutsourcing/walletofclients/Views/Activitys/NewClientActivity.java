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
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.NofiticationMessages;
import br.com.newoutsourcing.walletofclients.Views.Adapters.TabPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Bases.ActivityBase;
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

public class NewClientActivity extends ActivityBase {

    protected @BindView(R.id.idViewPager) ViewPager idViewPager;
    protected @BindView(R.id.idBtnClose) Button idBtnClose;
    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idTabLayout) TabLayout idTabLayout;
    protected @BindView(R.id.idBtnSave) Button idBtnSave;
    protected @BindView(R.id.idBtnDelete) Button idBtnDelete;
    protected @BindView(R.id.idBtnNewTask) Button idBtnNewTask;

    private TabPagerAdapter pagerAdapter;
    private FragmentsCallback physicalPersonCallback;
    private FragmentsCallback legalPersonCallback;
    private FragmentsCallback addressCallback;
    private FragmentsCallback additionalInformationCallback;
    private String typePerson;
    private Client client;

    public NewClientActivity() {
        super(R.layout.activity_new_client);
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
        this.idBtnNewTask.setOnClickListener(this.onClickNewTask);
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
                FunctionsTools.showAlertDialog(NewClientActivity.this,"Erro","Opção não encontrada!","Fechar");
                FunctionsTools.closeActivity(NewClientActivity.this);
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
            FunctionsTools.showAlertDialog(NewClientActivity.this,"Erro",ex.getMessage(),"Fechar");
        }
    }

    View.OnClickListener onClickDelete = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (client != null && client.getClientId() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
                builder.setPositiveButton("Sim", (dialog, id) -> {
                    TB_CLIENT.Delete(client);
                    NofiticationMessages.onNotificationClient(client, NofiticationMessages.eCRUDOperation.DELETE);
                    Toast.makeText(NewClientActivity.this,"Cliente excluido!",Toast.LENGTH_LONG).show();
                    FunctionsTools.closeActivity(NewClientActivity.this);
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

                                        idViewPager.setCurrentItem(0);
                                        if (client.getType() == 1){physicalPersonCallback.onClear();}else{legalPersonCallback.onClear();}

                                        idViewPager.setCurrentItem(1);
                                        additionalInformationCallback.onClear();

                                        idViewPager.setCurrentItem(2);
                                        addressCallback.onClear();

                                        idViewPager.setCurrentItem(0);

                                        NofiticationMessages.onNotificationClient(client, NofiticationMessages.eCRUDOperation.INSERT);
                                        FunctionsTools.showSnackBarLong(v,"Cliente salvo!");
                                    }
                                }else{/**Atualiza cliente**/
                                    TB_CLIENT.Update(client);
                                    if (client.getType() == 1){TB_PHYSICAL_PERSON.Update(client.getPhysicalPerson());}else{TB_LEGAL_PERSON.Update(client.getLegalPerson());}
                                    TB_ADDITIONAL_INFORMATION.Update(client.getAdditionalInformation());
                                    TB_ADDRESS.Update(client.getAddress());

                                    idViewPager.setCurrentItem(0);
                                    if (client.getType() == 1){physicalPersonCallback.onClear();}else{legalPersonCallback.onClear();}

                                    idViewPager.setCurrentItem(1);
                                    additionalInformationCallback.onClear();

                                    idViewPager.setCurrentItem(2);
                                    addressCallback.onClear();

                                    idViewPager.setCurrentItem(0);

                                    idBtnDelete.setVisibility(View.INVISIBLE);

                                    NofiticationMessages.onNotificationClient(client, NofiticationMessages.eCRUDOperation.UPDATE);
                                    FunctionsTools.showSnackBarLong(v,"Cliente atualizado!");
                                }
                                client = new Client();
                            }
                        }
                    }
                }
            }catch (Exception ex) {
                FunctionsTools.showAlertDialog(NewClientActivity.this,"Erro",ex.getMessage(),"Fechar");
            }
        }
    };

    View.OnClickListener onClickClose = v -> FunctionsTools.closeActivity(NewClientActivity.this);

    View.OnClickListener onClickNewTask  = new View.OnClickListener(){
        @Override
        public void onClick (android.view.View v){
            Bundle bundle = null;
            if (client != null && client.getClientId() > 0){
                bundle = new Bundle();
                bundle.putLong("ClientId",client.getClientId());
            }
            FunctionsTools.startActivity(NewClientActivity.this, NewTaskActivity.class, bundle);
        }
    };
}
