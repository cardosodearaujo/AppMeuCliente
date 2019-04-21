package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ClientAdapter;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class ListClientActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar idToolbar;
    private FloatingActionMenu idBtnFam;
    private FloatingActionButton idBtnFabClientLegalPerson;
    private FloatingActionButton idBtnFabClientPhysicalPerson;
    private FloatingActionButton idBtnFabClientExport;
    private FloatingActionButton idBtnFabClientConfig;
    private RecyclerView idRecycleView;
    private Button idBtnClose;
    private View idView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list_client);
        this.onInflate();
        this.onConfiguration();
        this.setSupportActionBar(this.idToolbar);
    }

    @Override
    public void onClick(View v) {
    }

    private void onInflate(){
        this.idView = this.findViewById(android.R.id.content);
        this.idToolbar = this.findViewById(R.id.idToolbar);
        this.idBtnFam = this.findViewById(R.id.idBtnFam);
        this.idBtnFabClientLegalPerson = this.findViewById(R.id.idBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson = this.findViewById(R.id.idBtnFabClientPhysicalPerson);
        this.idBtnFabClientExport = this.findViewById(R.id.idBtnFabClientExport);
        this.idBtnFabClientConfig = this.findViewById(R.id.idBtnFabClientConfig);
        this.idRecycleView = this.findViewById(R.id.idRecycleView);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
    }

    private void onConfiguration(){
        this.idBtnFabClientLegalPerson.setOnClickListener(this.onClickBtnFabClientLegalPerson);
        this.idBtnFabClientPhysicalPerson.setOnClickListener(this.onClickBtnFabClientPhysicalPerson);
        this.idBtnClose.setOnClickListener(this.onClickClose);
        this.idBtnFabClientExport.setOnClickListener(this.onClickExport);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListClientActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.idRecycleView.setLayoutManager(linearLayoutManager);
        this.idRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume(){
        super.onResume();
        try{
            if (this.idRecycleView != null){
                this.idRecycleView.setAdapter(new ClientAdapter(TB_CLIENT.Select()));
            }
        }catch (Exception ex){
            FunctionsApp.showSnackBarLong(this.idView,ex.getMessage());
        }

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

    View.OnClickListener onClickClose = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(ListClientActivity.this);
        }
    };

    View.OnClickListener onClickExport = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            try{
                 List<Client> clientList = TB_CLIENT.Select();

                 if (clientList.size() > 0) {

                     FunctionsApp.showPgDialog(v.getContext());
                     FunctionsApp.PG_DIALOG.setTitle("Exportando. Aguarde...");

                     String CSV =
                             "ID_CLIENT;IMAGE;TYPE;NAME_SOCIAL_NAME;NICKNAME_FANTASY_NAME;" +
                             "CPF_CNPJ;RG_IE;IM;BIRTH_DATE;SEX;CELLPHONE;TELEPHONE;EMAIL;SITE;" +
                             "OBSERVATION;CEP;STREET;NUMBER;NEIGHBORHOOD;CITY;STATE;COUNTRY;\n";

                     for (Client client : clientList) {
                         CSV += client.getClientId() + ";";
                         CSV += client.getImage() + ";";

                         if (client.getType() == 1) {
                             FunctionsApp.PG_DIALOG.setMessage("Cliente: " + client.getPhysicalPerson().getName());
                             CSV += "F;";
                             CSV += client.getPhysicalPerson().getName() + ";";
                             CSV += client.getPhysicalPerson().getNickname() + ";";
                             CSV += client.getPhysicalPerson().getCPF() + ";";
                             CSV += client.getPhysicalPerson().getRG() + ";";
                             CSV += ";";
                             CSV += client.getPhysicalPerson().getBirthDate() + ";";
                             CSV += client.getPhysicalPerson().getSex() + ";";
                         } else {
                             FunctionsApp.PG_DIALOG.setMessage("Cliente: " + client.getLegalPerson().getSocialName());
                             CSV += "J;";
                             CSV += client.getLegalPerson().getSocialName() + ";";
                             CSV += client.getLegalPerson().getFantasyName() + ";";
                             CSV += client.getLegalPerson().getCNPJ() + ";";
                             CSV += client.getLegalPerson().getIE() + ";";
                             CSV += client.getLegalPerson().getIM() + ";";
                             CSV += ";";
                             CSV += ";";
                         }

                         //Dados adicionais:
                         CSV += client.getAdditionalInformation().getCellphone() + ";";
                         CSV += client.getAdditionalInformation().getTelephone() + ";";
                         CSV += client.getAdditionalInformation().getEmail() + ";";
                         CSV += client.getAdditionalInformation().getSite() + ";";
                         CSV += client.getAdditionalInformation().getObservation() + ";";

                         //Endereço
                         CSV += client.getAddress().getCEP() + ";";
                         CSV += client.getAddress().getStreet() + ";";
                         CSV += client.getAddress().getNumber() + ";";
                         CSV += client.getAddress().getNeighborhood() + ";";
                         CSV += client.getAddress().getCity() + ";";
                         CSV += client.getAddress().getState() + ";";
                         CSV += client.getAddress().getCountry() + ";";

                         CSV += "\n";
                     }

                     String path = FunctionsApp.saveArchive(CSV,"ListOfClients_" + FunctionsApp.getCurrentDate("dd-MM-yyyy") + ".csv");
                     FunctionsApp.closePgDialog();
                     if (!path.isEmpty()){
                         FunctionsApp.showAlertDialog(v.getContext(),"Atenção!","Arquivo de exportação salvo em: " + path,"Fechar");
                     }else{
                         FunctionsApp.showSnackBarLong(v,"Não foi possivel gerar o arquivo de exportação. Tente novamente!");
                     }
                 }else{
                     FunctionsApp.showSnackBarLong(v,"Não há dados para exportar!");
                 }
            }catch (Exception ex){
                FunctionsApp.showMessageError(v.getContext(),"Erro!",ex.getMessage());
            }
        }
    };
}
