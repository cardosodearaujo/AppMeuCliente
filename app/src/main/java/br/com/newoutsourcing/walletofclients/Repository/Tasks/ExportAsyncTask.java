package br.com.newoutsourcing.walletofclients.Repository.Tasks;
import android.content.Context;
import android.os.AsyncTask;
import java.util.List;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class ExportAsyncTask extends AsyncTask<Void,String,Boolean> {

    private Context context;

    public ExportAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute(){
        FunctionsApp.showPgDialog(this.context,"Exportando clientes...");
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            List<Client> clientList = TB_CLIENT.Select();

            if (clientList.size() > 0) {
                String CSV =
                        "ID_CLIENT;IMAGE;TYPE;NAME_SOCIAL_NAME;NICKNAME_FANTASY_NAME;" +
                        "CPF_CNPJ;RG_IE;IM;BIRTH_DATE;SEX;CELLPHONE;TELEPHONE;EMAIL;SITE;" +
                        "OBSERVATION;CEP;STREET;NUMBER;NEIGHBORHOOD;CITY;STATE;COUNTRY;\n";

                for (Client client : clientList) {
                    CSV += client.getClientId() + ";";
                    CSV += client.getImage() + ";";

                    if (client.getType() == 1) {
                        this.publishProgress("Cliente: " + client.getPhysicalPerson().getName());
                        CSV += "F;";
                        CSV += client.getPhysicalPerson().getName() + ";";
                        CSV += client.getPhysicalPerson().getNickname() + ";";
                        CSV += client.getPhysicalPerson().getCPF() + ";";
                        CSV += client.getPhysicalPerson().getRG() + ";";
                        CSV += ";";
                        CSV += client.getPhysicalPerson().getBirthDate() + ";";
                        CSV += client.getPhysicalPerson().getSex() + ";";
                    } else {
                        this.publishProgress("Cliente: " + client.getLegalPerson().getSocialName());
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

                FunctionsApp.saveArchive(CSV,"ListOfClients_" + FunctionsApp.getCurrentDate("dd-MM-yyyy") + ".csv");
                return true;
            }else{
                throw new Exception("Não há dados para exportar!");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onProgressUpdate(String... Status){
        if (Status.length > 0) FunctionsApp.PG_DIALOG.setMessage(Status[0]);
    }
}
