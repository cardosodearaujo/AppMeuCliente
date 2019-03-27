package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
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
    private ViewPager idViewPager;
    private EditText idEdtClientPFDate;

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
        this.idViewPager = this.getActivity().findViewById(R.id.idViewPager);
        this.idEdtClientPFName = view.findViewById(R.id.idEdtClientPFName);
        this.idEdtClientPFNickName = view.findViewById(R.id.idEdtClientPFNickName);
        this.idEdtClientPFCPF = view.findViewById(R.id.idEdtClientPFCPF);
        this.idEdtClientPFRG = view.findViewById(R.id.idEdtClientPFRG);
        this.idSpnClientPFSexo = view.findViewById(R.id.idSpnClientPFSexo);
        this.idEdtClientPFSite = view.findViewById(R.id.idEdtClientPFSite);
        this.idEdtClientPFObservation = view.findViewById(R.id.idEdtClientPFObservation);
        this.idEdtClientPFDate = view.findViewById(R.id.idEdtClientPFDate);
    }

    private void LoadInformationToView(){
        this.idToolbar.setSubtitle("Pessoa física");
        this.idEdtClientPFDate.addTextChangedListener(new MaskEditTextChangedListener("##/##/####", this.idEdtClientPFDate));
        this.idEdtClientPFCPF.addTextChangedListener(new MaskEditTextChangedListener("###.###.###-##", this.idEdtClientPFCPF));
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.idEdtClientPFDate.setOnClickListener(this.onClickDate);
    }

    private View.OnClickListener onClickDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    getActivity(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear + 1) + "/"
                    + String.valueOf(year);
            idEdtClientPFDate.setText(data);
        }
    };

}
