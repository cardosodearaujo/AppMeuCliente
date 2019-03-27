package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.R;

public class LegalPersonFragment extends Fragment {
    private Toolbar idToolbar;
    private TextView idTxwClientPJDescriptionData;
    private EditText idEdtClientPJSocialName;
    private EditText idEdtClientPJFantasyName;
    private EditText idEdtClientPJCNPJ;
    private EditText idEdtClientPJIE;
    private EditText idEdtClientPJIM;
    private TextView idTxwClientPJDescriptionAdditionalData;
    private EditText idEdtClientPJSite;
    private EditText idEdtClientPJObservation;
    private ViewPager idViewPager;

    public LegalPersonFragment() {
    }

    public static LegalPersonFragment newInstance() {
        return new LegalPersonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legal_person, container, false);
        this.loadConfigurationToView(view);
        this.loadInformationToView();
        return view;
    }

    private void loadConfigurationToView(View view){
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idViewPager = this.getActivity().findViewById(R.id.idViewPager);
        this.idEdtClientPJSocialName = view.findViewById(R.id.idEdtClientPJSocialName);
        this.idEdtClientPJFantasyName = view.findViewById(R.id.idEdtClientPJFantasyName);
        this.idEdtClientPJCNPJ = view.findViewById(R.id.idEdtClientPJCNPJ);
        this.idEdtClientPJIE = view.findViewById(R.id.idEdtClientPJIE);
        this.idEdtClientPJIM = view.findViewById(R.id.idEdtClientPJIM);
    }

    private void loadInformationToView(){
        this.idToolbar.setSubtitle("Pessoa juridica");
        this.idEdtClientPJCNPJ.addTextChangedListener(new MaskEditTextChangedListener("##.###.###.####/##", this.idEdtClientPJCNPJ));
    }
}
