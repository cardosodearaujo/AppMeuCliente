package br.com.newoutsourcing.walletofclients.Repository.Tasks;

import android.os.AsyncTask;
import br.com.newoutsourcing.walletofclients.Tools.EMailSender;

public class SendEmailAsyncTask extends AsyncTask<Void,Void,Void> {

    private String smtpUser;
    private String smtpPassword;
    private String subject;
    private String body;
    private String recipient;

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public void setSmtpPassword(String smtPassword) {
        this.smtpPassword = smtPassword;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public SendEmailAsyncTask(){
        this.subject = "";
        this.body = "";
        this.recipient = "";
    }

    public SendEmailAsyncTask(String subject, String body, String recipient){
        this.subject = subject;
        this.body = body;
        this.recipient = recipient;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new EMailSender().SendEmail(smtpUser, smtpPassword, subject, body, recipient);
        return null;
    }


}
