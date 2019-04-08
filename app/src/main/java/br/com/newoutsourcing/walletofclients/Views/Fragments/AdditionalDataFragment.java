package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;


public class AdditionalDataFragment extends Fragment implements FragmentsCallback {

    private EditText idEdtClientPFCellphone;
    private EditText idEdtClientPFTelephone;
    private EditText idEdtClientPFEmail;
    private EditText idEdtClientPFSite;
    private EditText idEdtClientPFObservation;

    public AdditionalDataFragment() {
    }

    public static AdditionalDataFragment newInstance(){
        return new AdditionalDataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_data, container, false);
        this.onInflate(view);
        this.onConfiguration();
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


        return save;
    }

    @Override
    public boolean onSave(Client client) {
        return true;
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
