package br.com.newoutsourcing.walletofclients.Repository.Tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class ImportAsyncTask extends AsyncTask<Uri,String,Boolean> {

    private Context context;

    public ImportAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute(){
        FunctionsTools.showPgDialog(this.context,"Importando clientes...");
    }
    @Override
    protected Boolean doInBackground(Uri... uri) {
        try{
            InputStream inputStream = this.context.getContentResolver().openInputStream(uri[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String data;
            while((data = reader.readLine()) != null){
                String[] line = data.split(";");
                if (line.length == 22){
                    if (!line[0].equals("ID_CLIENT")){ ;

                        this.publishProgress("Importando cliente: " + line[3]);

                        Client client = new Client();
                        int typeClient = 1;
                        long idCliente;

                        if (line[2]!=null && line[5] != null){
                            if (line[2].equals("F")){
                                typeClient = 1;
                                idCliente = TB_PHYSICAL_PERSON.CheckCPF(line[5],null);
                                if (idCliente > 0){
                                    client = TB_CLIENT.Select(idCliente).get(0);
                                }
                            }else if (line[2].equals("J")){
                                typeClient = 2;
                                idCliente = TB_LEGAL_PERSON.CheckCNPJ(line[5],null);
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

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onProgressUpdate(String... Status){
        if (Status.length > 0) FunctionsTools.PG_DIALOG.setMessage(Status[0]);
     }
}
