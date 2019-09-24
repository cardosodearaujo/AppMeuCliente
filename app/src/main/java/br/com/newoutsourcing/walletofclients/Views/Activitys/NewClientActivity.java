package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Tools.NotificationMessages;
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
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class NewClientActivity extends ActivityBase {

    protected @BindView(R.id.idViewPager) ViewPager idViewPager;
    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idTabLayout) TabLayout idTabLayout;
    protected @BindView(R.id.idBtnSave) Button idBtnSave;
    private MenuItem idItemNewTask;
    private MenuItem idItemShare;
    private MenuItem idItemDelete;
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
        this.setSupportActionBar(idToolbar);
        this.idTabLayout.setupWithViewPager(idViewPager);
        this.idBtnSave.setOnClickListener(onClickSave);
        this.onConfigurationFragments();
    }

    private void onConfigurationFragments() {
        try{
            Fragment fragment;
            this.typePerson = this.getIntent().getExtras().getString("TipoCadastro");
            Bundle bundle = this.getIntent().getExtras();

            if (bundle != null && bundle.containsKey("Client")){
                this.client = (Client)bundle.getSerializable("Client");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_new_client, menu);

        this.idItemNewTask = menu.findItem(R.id.idItemNewTask);
        this.idItemDelete = menu.findItem(R.id.idItemDelete);
        this.idItemShare = menu.findItem(R.id.idItemShare);

        if (client == null || client.getClientId() <= 0 ){
            this.idItemNewTask.setVisible(false);
            this.idItemShare.setVisible(false);
            this.idItemDelete.setVisible(false);
        }else{
            this.idItemNewTask.setVisible(true);
            this.idItemShare.setVisible(true);
            this.idItemDelete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.idItemShare:
                share();
                break;
            case R.id.idItemNewTask:
                newTask();
                break;
            case R.id.idItemDelete:
                delete();
                break;
            case R.id.idItemClose:
                close();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete(){
        if (client != null && client.getClientId() > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(View.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
            builder.setPositiveButton("Sim", (dialog, id) -> {
                TB_TASKS.DeleteByClientId(client.getClientId());
                TB_CLIENT.Delete(client);
                NotificationMessages.onNotificationClient(client, NotificationMessages.eCRUDOperation.DELETE);
                Toast.makeText(NewClientActivity.this,"Cliente excluido!",Toast.LENGTH_LONG).show();
                FunctionsTools.closeActivity(NewClientActivity.this);
                dialog.cancel();
            })
                    .setNegativeButton("Não", (dialog, id) -> dialog.cancel())
                    .setMessage("Tem ceteza que deseja excluir? Essa ação também exclui as tarefas!");

            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void close(){
        FunctionsTools.closeActivity(NewClientActivity.this);
    }

    private void newTask(){
        Bundle bundle = null;
        if (client != null && client.getClientId() > 0){
            bundle = new Bundle();
            bundle.putLong("ClientId",client.getClientId());
        }
        FunctionsTools.startActivity(NewClientActivity.this, NewTaskActivity.class, bundle);
    }

    private void share(){
        if (client != null && client.getClientId() > 0){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, FunctionsTools.getMessageClient(client));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    View.OnClickListener onClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                if (client == null || client.getClientId() <= 0){ client = new Client();}

                if (typePerson.equals("F")){client.setType(1);}else{client.setType(2);}
                if (client.getIdNuvem() <= 0) client.setIdNuvem(0);
                if (client.getUpdate().isEmpty() || client.getUpdate().equals("N")) client.setUpdate("S");
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

                                        NotificationMessages.onNotificationClient(client, NotificationMessages.eCRUDOperation.INSERT);
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

                                    idItemNewTask.setVisible(false);
                                    idItemShare.setVisible(false);
                                    idItemDelete.setVisible(false);

                                    NotificationMessages.onNotificationClient(client, NotificationMessages.eCRUDOperation.UPDATE);
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

}
