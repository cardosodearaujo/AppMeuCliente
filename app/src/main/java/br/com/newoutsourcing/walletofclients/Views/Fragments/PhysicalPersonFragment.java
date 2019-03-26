package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.R;

public class PhysicalPersonFragment extends Fragment {

    private Toolbar idToolbar;
    private TextView idTxwClientPFDescriptionData;
    private EditText idEdtClientPFName;
    private EditText idEdtClientPFNickName;
    private EditText idEdtClientPFCPF;
    private EditText idEdtClientPFRG;
    private Spinner idSpnClientPFSexo;
    private TextView idTxwClientPFDescriptionAdditionalData;
    private EditText idEdtClientPFSite;
    private EditText idEdtClientPFObservation;
    private Button idBtnClientPFNext;

    public PhysicalPersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_physical_person, container, false);
        this.LoadConfigurationToView(view);
        this.LoadInformationToView();
        return view;
    }

    public static PhysicalPersonFragment newInstance() {
        return new PhysicalPersonFragment();
    }

    private void LoadConfigurationToView(View view){
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idTxwClientPFDescriptionData = view.findViewById(R.id.idTxwClientPFDescriptionData);
        this.idEdtClientPFName = view.findViewById(R.id.idEdtClientPFName);
        this.idEdtClientPFNickName = view.findViewById(R.id.idEdtClientPFNickName);
        this.idEdtClientPFCPF = view.findViewById(R.id.idEdtClientPFCPF);
        this.idEdtClientPFRG = view.findViewById(R.id.idEdtClientPFRG);
        this.idSpnClientPFSexo = view.findViewById(R.id.idSpnClientPFSexo);
        this.idTxwClientPFDescriptionAdditionalData = view.findViewById(R.id.idTxwClientPFDescriptionAdditionalData);
        this.idEdtClientPFSite = view.findViewById(R.id.idEdtClientPFSite);
        this.idEdtClientPFObservation = view.findViewById(R.id.idEdtClientPFObservation);
        this.idBtnClientPFNext = view.findViewById(R.id.idBtnClientPFNext);
    }

    private void LoadInformationToView(){
        this.idToolbar.setSubtitle("Pessoa física");
        this.idEdtClientPFCPF.addTextChangedListener(new MaskEditTextChangedListener("###.###.###-##", this.idEdtClientPFCPF));
    }
}
