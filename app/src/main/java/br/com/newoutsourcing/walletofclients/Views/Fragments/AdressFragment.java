package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.R;

public class AdressFragment extends Fragment {

    private EditText idEdtClientAddressCEP;


    public AdressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adress, container, false);
        this.loadConfigurationToView(view);
        this.loadInformationToView();
        return view;
    }

    public static AdressFragment newInstance() {
        return new AdressFragment();
    }

    private void loadConfigurationToView(View view){
        this.idEdtClientAddressCEP = view.findViewById(R.id.idEdtClientAddressCEP);
        this.idEdtClientAddressCEP.addTextChangedListener(new MaskEditTextChangedListener("##.###.###", this.idEdtClientAddressCEP));
    }

    private void loadInformationToView(){

    }
}
