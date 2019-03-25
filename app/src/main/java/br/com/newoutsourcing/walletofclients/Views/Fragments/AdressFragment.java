package br.com.newoutsourcing.walletofclients.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.newoutsourcing.walletofclients.R;

public class AdressFragment extends Fragment {


    public AdressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adress, container, false);
    }

    public static AdressFragment newInstance() {
        return new AdressFragment();
    }
}
