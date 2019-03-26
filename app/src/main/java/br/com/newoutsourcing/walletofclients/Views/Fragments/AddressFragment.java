package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.R;

public class AddressFragment extends Fragment {

    private EditText idEdtClientAddressCEP;
    private ViewPager idViewPager;

    public AddressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        this.loadConfigurationToView(view);
        this.loadInformationToView();
        return view;
    }

    public static AddressFragment newInstance() {
        return new AddressFragment();
    }

    private void loadConfigurationToView(View view){
        this.idViewPager = this.getActivity().findViewById(R.id.idViewPager);
        this.idEdtClientAddressCEP = view.findViewById(R.id.idEdtClientAddressCEP);
        this.idEdtClientAddressCEP.addTextChangedListener(new MaskEditTextChangedListener("##.###.###", this.idEdtClientAddressCEP));
    }

    private void loadInformationToView(){

    }
}
