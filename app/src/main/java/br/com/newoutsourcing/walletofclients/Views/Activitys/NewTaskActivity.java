package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import butterknife.BindView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;

public class NewTaskActivity extends BaseActivity {

    protected @BindView(R.id.idEdtTaksTitle) EditText idEdtTaksTitle;
    protected @BindView(R.id.idSpnTaskClient) Spinner idSpnTaskClient;
    protected @BindView(R.id.idEdtTaksDate) EditText idEdtTaksDate;
    protected @BindView(R.id.idEdtTaksHour) EditText idEdtTaksHour;
    protected @BindView(R.id.idSwtTaskDiaInteiro) Switch idSwtTaskDiaInteiro;
    protected @BindView(R.id.idEdtClientPFObservation) EditText idEdtClientPFObservation;
    protected @BindView(R.id.idBtnSave) Button idBtnSave;
    protected @BindView(R.id.idBtnClose) Button idBtnClose;
    protected @BindView(R.id.idBtnDelete) Button idBtnDelete;

    private Tasks tasks;

    public NewTaskActivity() {
        super(R.layout.activity_new_task);
    }

    @Override
    protected void onConfiguration(){
        idEdtTaksDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_DATA, idEdtTaksDate));
        idEdtTaksDate.setOnClickListener(onClickDate);
        idEdtTaksHour.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_HORA, idEdtTaksHour));
        idEdtTaksHour.setOnClickListener(onClickTime);
        idSwtTaskDiaInteiro.setOnClickListener(onClickAllDay);
        idBtnSave.setOnClickListener(onClickSave);
        idBtnClose.setOnClickListener(onClickClose);
        idBtnDelete.setOnClickListener(onClickDelete);
        onClear();
        onLoadTask();
    }

    private void onLoadTask(){
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.containsKey("Tasks")){
            tasks = (Tasks) bundle.getSerializable("Tasks");
        }else{
            idBtnDelete.setVisibility(View.INVISIBLE);
        }

        if (tasks != null && tasks.getTasksId() > 0){
            idEdtTaksTitle.setText(tasks.getTitle());
            onLoadClients(tasks.getClienteId());
        }else{
            onLoadClients(0);
        }
    }

    private  boolean onValidate(){
        boolean save = true;

        if (idEdtTaksTitle.getText().toString().isEmpty()){
            idEdtTaksTitle.setError("Informe o titulo!");
            idEdtTaksTitle.requestFocus();
            save = false;
        }else{
            idEdtTaksTitle.setError(null);
        }

        if (idSpnTaskClient.getSelectedItem() == null){
            FunctionsTools.showSnackBarLong(View,"Informe um cliente!");
            idSpnTaskClient.requestFocus();
            save = false;
        }

        if (idEdtTaksDate.getText().toString().isEmpty()){
            idEdtTaksDate.setError("Informe o dia!");
            idEdtTaksDate.requestFocus();
            save = false;
        }else{
            idEdtTaksDate.setError(null);
        }

        if (!idSwtTaskDiaInteiro.isChecked()){
            if (idEdtTaksHour.getText().toString().isEmpty()){
                idEdtTaksHour.setError("Informe o horário!");
                idEdtTaksHour.requestFocus();
                save = false;
            }else{
                idEdtTaksHour.setError(null);
            }
        }else{
            idEdtTaksHour.setError(null);
        }

        return save;
    }

    private void onClear(){
        idEdtTaksTitle.setText(null);
        idSpnTaskClient.setSelection(0);
        idEdtTaksDate.setText(null);
        idEdtTaksHour.setText(null);
        idSwtTaskDiaInteiro.setChecked(false);
        idEdtClientPFObservation.setText(null);
    }

    private void onLoadClients(long clientId){
        try{
            List<Client> list =  TB_CLIENT.Select();
            List<FunctionsTools.GernericObject> listGeneric = new ArrayList<>();
            FunctionsTools.GernericObject selectedClient = null;

            for(Client client: list){
                FunctionsTools.GernericObject gernericObject = new FunctionsTools.GernericObject();
                gernericObject.setId(client.getClientId());
                if (client.getType() == 1){
                    gernericObject.setDescricao(client.getPhysicalPerson().getName());
                }else{
                    gernericObject.setDescricao(client.getLegalPerson().getSocialName());
                }
                if (clientId > 0 && client.getClientId() == clientId){ selectedClient = gernericObject; }
                listGeneric.add(gernericObject);
            }

            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listGeneric);
            ((ArrayAdapter) adapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            idSpnTaskClient.setAdapter((SpinnerAdapter) adapter);

            if(selectedClient != null){ idSpnTaskClient.setSelection(((ArrayAdapter) adapter).getPosition(selectedClient)); }
        }catch (Exception ex){
            FunctionsTools.showSnackBarLong(View,ex.getMessage());
        }
    }

    private View.OnClickListener onClickClose = view -> FunctionsTools.closeActivity(NewTaskActivity.this);

    private View.OnClickListener onClickSave = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            try{
                if (onValidate()){
                    if (tasks == null){ new Tasks(); }
                    tasks.setTitle(idEdtTaksTitle.getText().toString());
                    tasks.setClienteId(((FunctionsTools.GernericObject)idSpnTaskClient.getSelectedItem()).getId());
                    tasks.setAllDay(idSwtTaskDiaInteiro.isChecked()? 1 : 0);
                    tasks.setDate(idEdtTaksDate.getText().toString());
                    tasks.setHour(idEdtTaksHour.getText().toString());
                    tasks.setObservation(idEdtClientPFObservation.getText().toString());

                    if (tasks.getTasksId() <= 0){
                        tasks.setTasksId(TB_TASKS.Insert(tasks));
                        FunctionsTools.showSnackBarLong(View, "Tarefa salva!");
                    }else{
                        TB_TASKS.Update(tasks);
                        FunctionsTools.showSnackBarLong(View, "Tarefa atualizada!");
                    }
                    onClear();
                }
            }catch (Exception ex){
                FunctionsTools.showSnackBarLong(View,ex.getMessage());
            }
        }
    };

    private View.OnClickListener onClickDelete = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            try{
                if (tasks != null && tasks.getTasksId() > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
                    builder
                            .setPositiveButton("Sim", (dialog, id) -> {
                                TB_TASKS.Delete(tasks);
                                Toast.makeText(NewTaskActivity.this,"Tarefa excluida!",Toast.LENGTH_LONG).show();
                                FunctionsTools.closeActivity(NewTaskActivity.this);
                                dialog.cancel();
                            })
                            .setNegativeButton("Não", (dialog, id) -> dialog.cancel())
                            .setMessage("Tem ceteza que deseja excluir?");

                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            }catch (Exception ex){
                FunctionsTools.showSnackBarLong(View,ex.getMessage());
            }
        }
    };

    private View.OnClickListener onClickAllDay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (idSwtTaskDiaInteiro.isChecked()) {
                idEdtTaksHour.setEnabled(false);
            } else {
                idEdtTaksHour.setEnabled(true);
            }
        }
    };

    private View.OnClickListener onClickDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int day,month,year;

            if (!idEdtTaksDate.getText().toString().equals("") && idEdtTaksDate.getText().toString().split("/").length > 0 ) {
                String data[] = idEdtTaksDate.getText().toString().split("/");
                day  = Integer.parseInt(data[0]);
                month = Integer.parseInt(data[1]) - 1;
                year = Integer.parseInt(data[2]);
            }else{
                Calendar cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
            }

            DatePickerDialog dialog = new DatePickerDialog(NewTaskActivity.this,
                    onDateSetListener,year,month,day);

            dialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = "";

            if (dayOfMonth < 10){
                data += "0" + dayOfMonth;
            }else{
                data += dayOfMonth;
            }

            data += "/";

            monthOfYear = monthOfYear + 1;

            if (monthOfYear < 10){
               data += "0" + monthOfYear;
            }else{
                data += (monthOfYear );
            }

            data += "/" + year;

            idEdtTaksDate.setText(data);
        }
    };

    private View.OnClickListener onClickTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int hour, minute;

            if (!idEdtTaksHour.getText().toString().equals("") && idEdtTaksHour.getText().toString().split(":").length > 0){
                String hours[] = idEdtTaksHour.getText().toString().split(":");
                hour = Integer.parseInt(hours[0]);
                minute = Integer.parseInt(hours[1]);
            }else{
                Calendar cal = Calendar.getInstance();
                hour = cal.get(Calendar.HOUR);
                minute = cal.get(Calendar.MINUTE);
            }

            TimePickerDialog dialog = new TimePickerDialog(NewTaskActivity.this,
                    onTimeSetListener,hour,minute, DateFormat.is24HourFormat(NewTaskActivity.this));
            dialog.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String Hour = "";

            if (hourOfDay < 10){
                Hour += "0" + hourOfDay;
            }else{
                Hour += hourOfDay;
            }

            Hour += ":";

            if (minute < 10){
                Hour += "0" + minute;
            }else{
                Hour += minute;
            }

            idEdtTaksHour.setText(Hour);
        }
    };

}
