package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ClientAdapter;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

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
    private AdView idAdsView;
    private LinearLayout idLLMessageEmpty;

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
        this.idAdsView = this.findViewById(R.id.idAdsView);
        this.idLLMessageEmpty = this.findViewById(R.id.idLLMessageEmpty);
    }

    private void onConfiguration(){
        MobileAds.initialize(ListClientActivity.this, "@string/str_app_admob_id");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.idAdsView.loadAd(adRequest);

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
            if (this.idRecycleView != null && TB_CLIENT != null){
                List<Client> clientList = TB_CLIENT.Select();
                if (clientList.size() > 0){
                    this.idRecycleView.setAdapter(new ClientAdapter(ListClientActivity.this,clientList));
                    this.idLLMessageEmpty.setVisibility(View.INVISIBLE);
                    this.idRecycleView.setVisibility(View.VISIBLE);
                }else{
                    this.idLLMessageEmpty.setVisibility(View.VISIBLE);
                    this.idRecycleView.setVisibility(View.INVISIBLE);
                }

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

                AlertDialog alert;
                ArrayList<String> itens = new ArrayList<String>();

                itens.add("Importar");
                itens.add("Exportar");

                ArrayAdapter adapter = new ArrayAdapter(ListClientActivity.this, R.layout.alert_dialog_question, itens);
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ListClientActivity.this);
                builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idOption) {
                        Intent intent;
                        switch (idOption) {
                            case 0: //Importar
                                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                intent.setType("text/*");
                                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(Intent.createChooser(intent,"Selecione um arquivo .CSV"),FunctionsApp.IMAGEM_INTERNA);
                                dialog.cancel();
                                break;
                            case 1: //Exportar
                                ExportClients();
                                dialog.cancel();
                                break;
                        }
                    }
                });
                alert = builder.create();
                alert.setTitle("Escolha uma opção:");
                alert.show();
            }catch (Exception ex){
                FunctionsApp.showMessageError(v.getContext(),"Erro!",ex.getMessage());
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == RESULT_OK) this.ImportClients(data.getData());
        }catch (Exception ex){
            FunctionsApp.showMessageError(ListClientActivity.this,"Erro",ex.getMessage());
        }
    }

    private void ExportClients(){
        try{
            List<Client> clientList = TB_CLIENT.Select();

            if (clientList.size() > 0) {
                FunctionsApp.showPgDialog(ListClientActivity.this);
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
                    FunctionsApp.showAlertDialog(ListClientActivity.this,"Atenção!","Arquivo de exportação salvo em: " + path,"Fechar");
                }else{
                    FunctionsApp.showSnackBarLong(this.idView,"Não foi possivel gerar o arquivo de exportação. Tente novamente!");
                }
            }else{
                FunctionsApp.showSnackBarLong(this.idView,"Não há dados para exportar!");
            }
        }catch (Exception ex){
            throw ex;
        }finally {
            FunctionsApp.closePgDialog();
        }
    }

    private void ImportClients(Uri uri) throws Exception {
        try{
            FunctionsApp.showPgDialog(ListClientActivity.this);
            FunctionsApp.PG_DIALOG.setTitle("Importando. Aguarde...");

            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String data;
            while((data = reader.readLine()) != null){
                String[] line = data.split(";");
                if (line.length == 22){
                    if (!line[0].equals("ID_CLIENT")){
                        FunctionsApp.PG_DIALOG.setMessage("Cliente: " + line[3]);

                        Client client = new Client();
                        int typeClient = 1;
                        long idCliente;

                        if (line[2]!=null && line[5] != null){
                            if (line[2].equals("F")){
                                typeClient = 1;
                                idCliente = TB_PHYSICAL_PERSON.CheckCPF(line[5]);
                                if (idCliente > 0){
                                    client = TB_CLIENT.Select(idCliente).get(0);
                                }
                            }else if (line[2].equals("J")){
                                typeClient = 2;
                                idCliente = TB_LEGAL_PERSON.CheckCNPJ(line[5]);
                                if (idCliente > 0){
                                    client = TB_CLIENT.Select(idCliente).get(0);
                                }
                            }
                        }

                        //Cliente:
                        if (line[1]!=null) client.setImage(line[1]);
                        if (line[2]!=null) client.setType(typeClient);

                        if (client.getType()==1){ //Pessoa fisica:
                            if (line[3]!=null) client.getPhysicalPerson().setName(line[3]);
                            if (line[4]!=null) client.getPhysicalPerson().setNickname(line[4]);
                            if (line[5]!=null) client.getPhysicalPerson().setCPF(line[5]);
                            if (line[6]!=null) client.getPhysicalPerson().setRG(line[6]);
                            if (line[8]!=null) client.getPhysicalPerson().setBirthDate(line[8]);
                            if (line[9]!=null) client.getPhysicalPerson().setSex(line[9]);
                        }else{ //Pessoa juridica:
                            if (line[3]!=null) client.getLegalPerson().setSocialName(line[3]);
                            if (line[4]!=null) client.getLegalPerson().setFantasyName(line[4]);
                            if (line[5]!=null) client.getLegalPerson().setCNPJ(line[5]);
                            if (line[6]!=null) client.getLegalPerson().setIE(line[6]);
                            if (line[7]!=null) client.getLegalPerson().setIM(line[7]);
                        }

                        //Informação adicional:
                        if (line[10]!=null) client.getAdditionalInformation().setCellphone(line[10]);
                        if (line[11]!=null) client.getAdditionalInformation().setTelephone(line[11]);
                        if (line[12]!=null) client.getAdditionalInformation().setEmail(line[12]);
                        if (line[13]!=null) client.getAdditionalInformation().setSite(line[13]);
                        if (line[14]!=null) client.getAdditionalInformation().setObservation(line[14]);

                        //Endereço:
                        if (line[15]!=null) client.getAddress().setCEP(line[15]);
                        if (line[16]!=null) client.getAddress().setStreet(line[16]);
                        if (line[17]!=null) client.getAddress().setNumber(Integer.parseInt(line[17]));
                        if (line[18]!=null) client.getAddress().setNeighborhood(line[18]);
                        if (line[19]!=null) client.getAddress().setCity(line[19]);
                        if (line[20]!=null) client.getAddress().setState(line[20]);
                        if (line[21]!=null) client.getAddress().setCountry(line[21]);

                        if (client.getClientId() <= 0){//Insere:
                            long clientId = TB_CLIENT.Insert(client);
                            if (client.getType()==1){
                                client.getPhysicalPerson().setClientId(clientId);
                                TB_PHYSICAL_PERSON.Insert(client.getPhysicalPerson());
                            }else{
                                client.getLegalPerson().setClientId(clientId);
                                TB_LEGAL_PERSON.Insert(client.getLegalPerson());
                            }
                            client.getAdditionalInformation().setClientId(clientId);
                            TB_ADDITIONAL_INFORMATION.Insert(client.getAdditionalInformation());
                            client.getAddress().setClientId(clientId);
                            TB_ADDRESS.Insert(client.getAddress());
                        }else{//Atualiza
                            TB_CLIENT.Update(client);
                            if (client.getType()==1){
                                TB_PHYSICAL_PERSON.Update(client.getPhysicalPerson());
                            }else{
                                TB_LEGAL_PERSON.Update(client.getLegalPerson());
                            }
                            TB_ADDITIONAL_INFORMATION.Update(client.getAdditionalInformation());
                            TB_ADDRESS.Update(client.getAddress());
                        }
                    }
                }else{
                    throw new Exception("Arquivo fora do padrão!");
                }
            }
            inputStream.close();
            reader.close();

            FunctionsApp.showAlertDialog(ListClientActivity.this,"Atenção!","Clientes importados com sucesso!","Fechar");
        }catch (Exception ex){
            throw ex;
        }finally {
            FunctionsApp.closePgDialog();
        }
    }



}
