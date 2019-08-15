package br.com.newoutsourcing.walletofclients.Tools;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public class NofiticationMessages {

    public enum eCRUDOperation {
        INSERT,
        UPDATE,
        DELETE
    }

    public static void onNotificationUse(){
        FunctionsTools
                .sendEmailNotification("O app foi acessado as "
                        + FunctionsTools.getCurrentDate() + " "
                        + FunctionsTools.getCurrentTime() + " na versão: "
                        + FunctionsTools.VersaoApp);
    }

    public static void onNotificationClient(Client client, eCRUDOperation operationClientx){
        String message ="Em " + FunctionsTools.getCurrentDate() + " as " + FunctionsTools.getCurrentTime() + " ";

        if (operationClientx == eCRUDOperation.INSERT){
            message += "um cliente foi criado:";
        }else if (operationClientx == eCRUDOperation.UPDATE){
            message += "um cliente foi atualizado:";
        }else{
            message += "um cliente foi excluido:";
        }

        message += "\n\n";

        if (client.getClientId() > 0){
            message += "Codigo: " + client.getClientId();
        }

        if (client.getType() == 1){
            message += "\nNome: " + client.getPhysicalPerson().getName();
            message += "\nApelido: " + client.getPhysicalPerson().getNickname();
            message += "\nCPF: " + client.getPhysicalPerson().getCPF();
            message += "\nRG: " + client.getPhysicalPerson().getRG();
            message += "\nDt. aniversário: " + client.getPhysicalPerson().getBirthDate();
            message += "\nSexo: " + client.getPhysicalPerson().getSex();
        }else{
            message += "\nRazão social: " + client.getLegalPerson().getSocialName();
            message += "\nNome fantasia: " + client.getLegalPerson().getFantasyName();
            message += "\nCNPJ: " + client.getLegalPerson().getCNPJ();
            message += "\nIE " + client.getLegalPerson().getIE();
            message += "\nIM: " + client.getLegalPerson().getIM();
        }

        if (client.getAddress() != null && client.getAddress().getAddressId() > 0){
            message += "\nCEP: " + client.getAddress().getCEP();
            message += "\nRua: " + client.getAddress().getStreet();
            message += "\nNumero: " + client.getAddress().getNumber();
            message += "\nBairro: " + client.getAddress().getNeighborhood();
            message += "\nCidade: " + client.getAddress().getCity();
            message += "\nEstado: " + client.getAddress().getState();
            message += "\nPais: " + client.getAddress().getCountry();
        }

        if (client.getAdditionalInformation() != null && client.getAdditionalInformation().getAdditionalInformationId() > 0){
            message += "\nCelular: " + client.getAdditionalInformation().getCellphone();
            message += "\nTelefone: " + client.getAdditionalInformation().getTelephone();
            message += "\nEmail: " + client.getAdditionalInformation().getEmail();
            message += "\nSite: " + client.getAdditionalInformation().getSite();
            message += "\nObservação: " + client.getAdditionalInformation().getObservation();
        }

        FunctionsTools.sendEmailNotification(message);
    }

    public static void onNotificationNewTask(){
        String message = "Tarefa criada as "
        + FunctionsTools.getCurrentDate() + " "
                + FunctionsTools.getCurrentTime() + " na versão: "
                + FunctionsTools.VersaoApp;
        FunctionsTools.sendEmailNotification(message);
    }

    public static void onNotificationUpdateTask(){
        String message = "Tarefa atualizada as "
                + FunctionsTools.getCurrentDate() + " "
                + FunctionsTools.getCurrentTime() + " na versão: "
                + FunctionsTools.VersaoApp;
        FunctionsTools.sendEmailNotification(message);
    }
}
