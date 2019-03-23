package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import br.com.newoutsourcing.walletofclients.R;

public class LegalPersonFragment extends Fragment {

    private Toolbar toolbar;

    public LegalPersonFragment() {
    }

    public static LegalPersonFragment newInstance() {
        return new LegalPersonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadConfigurationView();
        this.loadInformationView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_legal_person, container, false);
    }

    private void loadConfigurationView(){
        this.toolbar = getActivity().findViewById(R.id.idToolbar);
    }

    private void loadInformationView(){
        this.toolbar.setSubtitle("Pessoa juridica");
    }
}
