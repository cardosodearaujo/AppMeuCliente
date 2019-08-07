package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;


public class AdditionalInformationFragment extends Fragment implements FragmentsCallback {

    private EditText idEdtClientPFCellphone;
    private EditText idEdtClientPFTelephone;
    private EditText idEdtClientPFEmail;
    private EditText idEdtClientPFSite;
    private EditText idEdtClientPFObservation;

    public AdditionalInformationFragment() {
    }

    public static AdditionalInformationFragment newInstance(){
        return new AdditionalInformationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_information, container, false);
        this.onInflate(view);
        this.onConfiguration();
        this.onLoad((Client) getArguments().getSerializable("Client"));
        return view;
    }

    private void onInflate(View view){
        this.idEdtClientPFCellphone = view.findViewById(R.id.idEdtClientPFCellphone);
        this.idEdtClientPFTelephone = view.findViewById(R.id.idEdtClientPFTelephone);
        this.idEdtClientPFEmail = view.findViewById(R.id.idEdtClientPFEmail);
        this.idEdtClientPFSite = view.findViewById(R.id.idEdtClientPFSite);
        this.idEdtClientPFObservation = view.findViewById(R.id.idEdtClientPFObservation);
    }

    private void onConfiguration(){
        this.idEdtClientPFCellphone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CELULAR, this.idEdtClientPFCellphone));
        this.idEdtClientPFTelephone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_TELEFONE, this.idEdtClientPFTelephone));
    }

    @Override
    public boolean onValidate(){
        boolean save = true;

        if (!this.idEdtClientPFCellphone.getText().toString().isEmpty()){
            if (FunctionsApp.formatCellphone(this.idEdtClientPFCellphone.getText().toString()).length()!=11){
                this.idEdtClientPFCellphone.setError("O celular deve conter 2 digitos do DDD e 9 do número.");
                save = false;
            }else{
                this.idEdtClientPFCellphone.setError(null);
            }
        }

        if (!this.idEdtClientPFTelephone.getText().toString().isEmpty()){
            if (FunctionsApp.formatTelephone(this.idEdtClientPFTelephone.getText().toString()).length()!=10){
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
