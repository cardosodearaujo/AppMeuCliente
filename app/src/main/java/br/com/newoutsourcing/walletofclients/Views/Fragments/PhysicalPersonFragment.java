package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class PhysicalPersonFragment extends Fragment implements FragmentsCallback {

    private Toolbar idToolbar;
    private EditText idEdtClientPFName;
    private EditText idEdtClientPFNickName;
    private EditText idEdtClientPFCPF;
    private EditText idEdtClientPFRG;
    private Spinner idSpnClientPFSexo;
    private EditText idEdtClientPFDate;
    private FragmentsCallback imageCallback;
    private ImageFragment imageFragment;
    private String clientId;

    public PhysicalPersonFragment() {
    }


    public static PhysicalPersonFragment newInstance() {
        return new PhysicalPersonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_physical_person, container, false);
        this.onInflate(view);
        this.onConfiguration();
        this.onLoad((Client)this.getArguments().getSerializable("Client"));
        return view;
    }


    private void onInflate(View view){
        this.clientId = null;
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idEdtClientPFName = view.findViewById(R.id.idEdtClientPFName);
        this.idEdtClientPFNickName = view.findViewById(R.id.idEdtClientPFNickName);
        this.idEdtClientPFCPF = view.findViewById(R.id.idEdtClientPFCPF);
        this.idEdtClientPFRG = view.findViewById(R.id.idEdtClientPFRG);
        this.idSpnClientPFSexo = view.findViewById(R.id.idSpnClientPFSexo);
        this.idEdtClientPFDate = view.findViewById(R.id.idEdtClientPFDate);
    }

    private void onConfiguration(){
        this.idToolbar.setSubtitle("Pessoa física");
        this.idEdtClientPFDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_DATA, this.idEdtClientPFDate));
        this.idEdtClientPFCPF.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CPF, this.idEdtClientPFCPF));
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.idEdtClientPFDate.setOnClickListener(this.onClickDate);
        this.onCreateFragment(false);
    }

    private void onCreateFragment(Boolean createClean){
        this.imageFragment = ImageFragment.newInstance();
        this.imageCallback = imageFragment;
        if (createClean){
            FunctionsApp.startFragment(this.imageFragment,R.id.idFrlImg,this.getFragmentManager(),null);
        }else{
            FunctionsApp.startFragment(this.imageFragment,R.id.idFrlImg,this.getFragmentManager(),this.getArguments());
        }
    }

    @Override
    public boolean onValidate(){
        Boolean save = true;

        if (this.idEdtClientPFName.getText().toString().trim().isEmpty()){
            this.idEdtClientPFName.setError("Informe o nome.");
            save = false;
        }else{
            this.idEdtClientPFName.setError(null);
        }

        if (this.idEdtClientPFCPF.getText().toString().trim().isEmpty()){
            this.idEdtClientPFCPF.setError("Informe o CPF.");
            this.idEdtClientPFCPF.requestFocus();
            save = false;
        }else{
            this.idEdtClientPFCPF.setError(null);
        }

        if (!this.idEdtClientPFCPF.getText().toString().trim().isEmpty()){
            if ( FunctionsApp.formatCPF(this.idEdtClientPFCPF.getText().toString()).length() != 11){
                this.idEdtClientPFCPF.setError("O CPF deve conter 11 digitos.");
                this.idEdtClientPFCPF.requestFocus();
                save = false;
            }else{
                this.idEdtClientPFCPF.setError(null);
            }
        }

        if (!this.idEdtClientPFCPF.getText().toString().trim().isEmpty()){
            if (FunctionsApp.formatCPF(this.idEdtClientPFCPF.getText().toString()).length() == 11){
                if (TB_PHYSICAL_PERSON.CheckCPF(this.idEdtClientPFCPF.getText().toString(),this.clientId ) > 0) {
                    this.idEdtClientPFCPF.setError("O CPF está em uso em outro cadastro!");
                    this.idEdtClientPFCPF.requestFocus();
                    save = false;
                }else{
                    this.idEdtClientPFCPF.setError(null);
                }
            }
        }

        if (this.idEdtClientPFRG.getText().toString().trim().isEmpty()){
            this.idEdtClientPFRG.setError("Informe o RG.");
            save = false;
        }else{
            this.idEdtClientPFRG.setError(null);
        }

        if (this.idEdtClientPFDate.getText().toString().trim().isEmpty()){
            this.idEdtClientPFDate.setError("Informe a data de nascimento.");
            save = false;
        }else{
            this.idEdtClientPFDate.setError(null);
        }

        return save;
    }

    @Override
    public Client onSave(Client client) {
        try{
            if (this.onValidate()){
                client = this.imageCallback.onSave(client);
                client.getPhysicalPerson().setName(this.idEdtClientPFName.getText().toString());
                client.getPhysicalPerson().setNickname(this.idEdtClientPFNickName.getText().toString());
                client.getPhysicalPerson().setCPF(this.idEdtClientPFCPF.getText().toString());
                client.getPhysicalPerson().setRG(this.idEdtClientPFRG.getText().toString());
                client.getPhysicalPerson().setBirthDate(this.idEdtClientPFDate.getText().toString());
                if (this.idSpnClientPFSexo.getSelectedItemPosition() == 1){
                    client.getPhysicalPerson().setSex("F");
                }else if (this.idSpnClientPFSexo.getSelectedItemPosition() == 2){
                    client.getPhysicalPerson().setSex("M");
                }else{
                    client.getPhysicalPerson().setSex("I");
                }

                client.getPhysicalPerson().setSuccess(true);
            }else{
                client.getPhysicalPerson().setSuccess(false);
            }
            return client;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void onLoad(Client client){
        if (client != null){
            this.clientId = String.valueOf(client.getClientId());
            this.idEdtClientPFName.setText(client.getPhysicalPerson().getName());
            this.idEdtClientPFNickName.setText(client.getPhysicalPerson().getNickname());
            this.idEdtClientPFCPF.setText(client.getPhysicalPerson().getCPF());
            this.idEdtClientPFRG.setText(client.getPhysicalPerson().getRG());
            this.idEdtClientPFDate.setText(client.getPhysicalPerson().getBirthDate());
            this.idSpnClientPFSexo.setSelection(FunctionsApp.getSex(client.getPhysicalPerson().getSex()));
        }
    }

    @Override
    public void onClear() {
        this.clientId = null;
        this.idEdtClientPFName.setError(null);
        this.idEdtClientPFNickName.setError(null);
        this.idEdtClientPFCPF.setError(null);
        this.idEdtClientPFRG.setError(null);
        this.idEdtClientPFDate.setError(null);
        this.idEdtClientPFName.setText("");
        this.idEdtClientPFNickName.setText("");
        this.idEdtClientPFCPF.setText("");
        this.idEdtClientPFRG.setText("");
        this.idSpnClientPFSexo.setSelection(0);
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.onCreateFragment(true);
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
            String data = dayOfMonth + "/"
                    + (monthOfYear + 1) + "/"
                    + year;
            idEdtClientPFDate.setText(data);
        }
    };
}
