package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.widget.EditText;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.FragmentBase;
import butterknife.BindView;

public class AdditionalInformationFragment extends FragmentBase {

    protected @BindView(R.id.idEdtClientPFCellphone) EditText idEdtClientPFCellphone;
    protected @BindView(R.id.idEdtClientPFTelephone) EditText idEdtClientPFTelephone;
    protected @BindView(R.id.idEdtClientPFEmail) EditText idEdtClientPFEmail;
    protected @BindView(R.id.idEdtClientPFSite) EditText idEdtClientPFSite;
    protected @BindView(R.id.idEdtClientPFObservation) EditText idEdtClientPFObservation;

    public AdditionalInformationFragment() {
        super(R.layout.fragment_additional_information);
    }

    public static AdditionalInformationFragment newInstance(){
        return new AdditionalInformationFragment();
    }

    @Override
    protected void onConfiguration(){
        this.onLoad((Client) getArguments().getSerializable("Client"));
        this.idEdtClientPFCellphone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_CELULAR, this.idEdtClientPFCellphone));
        this.idEdtClientPFTelephone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_TELEFONE, this.idEdtClientPFTelephone));
    }

    @Override
    public boolean onValidate(){
        boolean save = true;

        if (!this.idEdtClientPFCellphone.getText().toString().isEmpty()){
            if (FunctionsTools.formatCellphone(this.idEdtClientPFCellphone.getText().toString()).length()!=11){
                this.idEdtClientPFCellphone.setError("O celular deve conter 2 digitos do DDD e 9 do número.");
                save = false;
            }else{
                this.idEdtClientPFCellphone.setError(null);
            }
        }

        if (!this.idEdtClientPFTelephone.getText().toString().isEmpty()){
            if (FunctionsTools.formatTelephone(this.idEdtClientPFTelephone.getText().toString()).length()!=10){
                this.idEdtClientPFTelephone.setError("O telephone deve conter 2 digitos do DDD e 8 do número.");
                save = false;
            }else{
                this.idEdtClientPFTelephone.setError(null);
            }
        }

        return save;
    }

    @Override
    public Client onSave(Client client) {
        try{
            if (this.onValidate()){
                client.getAdditionalInformation().setCellphone(this.idEdtClientPFCellphone.getText().toString());
                client.getAdditionalInformation().setTelephone(this.idEdtClientPFTelephone.getText().toString());
                client.getAdditionalInformation().setEmail(this.idEdtClientPFEmail.getText().toString());
                client.getAdditionalInformation().setSite(this.idEdtClientPFSite.getText().toString());
                client.getAdditionalInformation().setObservation(this.idEdtClientPFObservation.getText().toString());
                client.getAdditionalInformation().setSuccess(true);
            }else{
                client.getAdditionalInformation().setSuccess(false);
            }
            return client;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void onLoad(Client client){
        if (client != null){
            this.idEdtClientPFCellphone.setText(client.getAdditionalInformation().getCellphone());
            this.idEdtClientPFTelephone.setText(client.getAdditionalInformation().getTelephone());
            this.idEdtClientPFEmail.setText(client.getAdditionalInformation().getEmail());
            this.idEdtClientPFSite.setText(client.getAdditionalInformation().getSite());
            this.idEdtClientPFObservation.setText(client.getAdditionalInformation().getObservation());
        }
    }

    @Override
    public void onClear() {
        this.idEdtClientPFCellphone.setText("");
        this.idEdtClientPFTelephone.setText("");
        this.idEdtClientPFEmail.setText("");
        this.idEdtClientPFSite.setText("");
        this.idEdtClientPFObservation.setText("");
    }
}
