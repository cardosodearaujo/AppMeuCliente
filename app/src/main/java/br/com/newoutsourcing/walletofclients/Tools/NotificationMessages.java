package br.com.newoutsourcing.walletofclients.Tools;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public class NotificationMessages {

    public enum eCRUDOperation {
        INSERT,
        UPDATE,
        DELETE
    }

    public static void onNotificationUse(){
        //FunctionsTools
                //.sendEmailNotification("O app foi acessado as "
                        //+ FunctionsTools.getCurrentDate() + " "
                        //+ FunctionsTools.getCurrentTime() + " na versão: "
                        //+ FunctionsTools.VersaoApp);
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

        message += FunctionsTools.getMessageClient(client);

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

    public static void onNotificationError(String funcao, Exception ex){
        String message
                = "Log de erro na função: " + funcao +"\n"
                + ex.getMessage() + "\n"
                + ex.getStackTrace();
        FunctionsTools.sendEmailNotification(message);
    }

    public static void onNotificationError(String funcao, Throwable ex){
        String message
                = "Log de erro na função: " + funcao +"\n"
                + ex.getMessage();
        FunctionsTools.sendEmailNotification(message);
    }

    public static void onNotificationNewData(){
        String message = "Novos clientes/tarefas foram sicronizados.";
        FunctionsTools.sendEmailNotification(message);
    }
}
