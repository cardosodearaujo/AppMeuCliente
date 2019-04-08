package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;

public class AddressFragment extends Fragment implements FragmentsCallback {

    private EditText idEdtClientAddressCEP;
    private EditText idEdtClientAddressStreet;
    private EditText idEdtClientAddressNumber;
    private EditText idEdtClientAddressNeighborhood;
    private EditText idEdtClientAddressCity;
    private Spinner idSpnClientAddressState;
    private EditText idEdtClientAddressCounty;

    public AddressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        this.onInflate(view);
        this.onConfiguration();
        return view;
    }

    public static AddressFragment newInstance() {
        return new AddressFragment();
    }

    private void onInflate(View view){
        this.idEdtClientAddressCEP = view.findViewById(R.id.idEdtClientAddressCEP);
        this.idEdtClientAddressStreet = view.findViewById(R.id.idEdtClientAddressStreet);
        this.idEdtClientAddressNumber = view.findViewById(R.id.idEdtClientAddressNumber);
        this.idEdtClientAddressNeighborhood = view.findViewById(R.id.idEdtClientAddressNeighborhood);
        this.idEdtClientAddressCity = view.findViewById(R.id.idEdtClientAddressCity);
        this.idSpnClientAddressState = view.findViewById(R.id.idSpnClientAddressState);
        this.idEdtClientAddressCounty = view.findViewById(R.id.idEdtClientAddressCounty);
    }

    private void onConfiguration(){
        this.idEdtClientAddressCEP.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CEP, this.idEdtClientAddressCEP));
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
        this.idEdtClientAddressCEP.setText("");
        this.idEdtClientAddressStreet.setText("");
        this.idEdtClientAddressNumber.setText("");
        this.idEdtClientAddressNeighborhood.setText("");
        this.idEdtClientAddressCity.setText("");
        this.idSpnClientAddressState.setSelection(0);
        this.idEdtClientAddressCounty.setText("");
    }
}
