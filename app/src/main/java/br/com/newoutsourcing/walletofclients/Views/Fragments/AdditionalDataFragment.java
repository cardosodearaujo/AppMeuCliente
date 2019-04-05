package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.AdditionalDataCallback;


public class AdditionalDataFragment extends Fragment implements AdditionalDataCallback {

    private EditText idEdtClientPFCellphone;
    private EditText idEdtClientPFTelephone;

    public AdditionalDataFragment() {
    }

    public static AdditionalDataFragment newInstance(){
        return new AdditionalDataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_data, container, false);
        this.loadConfigurationToView(view);
        this.loadInformationToView();
        return view;
    }

    private void loadConfigurationToView(View view){
        this.idEdtClientPFCellphone = view.findViewById(R.id.idEdtClientPFCellphone);
        this.idEdtClientPFTelephone = view.findViewById(R.id.idEdtClientPFTelephone);
    }

    private void loadInformationToView(){
        this.idEdtClientPFCellphone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CELULAR, this.idEdtClientPFCellphone));
        this.idEdtClientPFTelephone.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_TELEFONE, this.idEdtClientPFTelephone));
    }

    @Override
    public Boolean Save() {
        FunctionsApp.showAlertDialog(this.getContext(),"Teste","Passou 2","OK");
        return true;
    }
}
