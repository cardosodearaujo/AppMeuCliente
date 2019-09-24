package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
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

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.ActivityBase;
import butterknife.BindView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_TASKS;
import static br.com.newoutsourcing.walletofclients.Tools.NotificationMessages.onNotificationNewTask;
import static br.com.newoutsourcing.walletofclients.Tools.NotificationMessages.onNotificationUpdateTask;

public class NewTaskActivity extends ActivityBase {

    protected @BindView(R.id.idEdttasksTitle) EditText idEdttasksTitle;
    protected @BindView(R.id.idToolbar) Toolbar idToolbar;
    protected @BindView(R.id.idSpnTaskClient) Spinner idSpnTaskClient;
    protected @BindView(R.id.idEdttasksDate) EditText idEdttasksDate;
    protected @BindView(R.id.idEdttasksHour) EditText idEdttasksHour;
    protected @BindView(R.id.idSwtTaskDiaInteiro) Switch idSwtTaskDiaInteiro;
    protected @BindView(R.id.idEdtClientPFObservation) EditText idEdtClientPFObservation;
    protected @BindView(R.id.idBtnSave) Button idBtnSave;
    private MenuItem idItemDelete;

    private Tasks tasks;

    public NewTaskActivity() {
        super(R.layout.activity_new_task);
    }

    @Override
    protected void onConfiguration(){
        this.setSupportActionBar(idToolbar);
        idEdttasksDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_DATA, idEdttasksDate));
        idEdttasksDate.setOnClickListener(onClickDate);
        idEdttasksHour.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_HORA, idEdttasksHour));
        idEdttasksHour.setOnClickListener(onClickTime);
        idSwtTaskDiaInteiro.setOnClickListener(onClickAllDay);
        idBtnSave.setOnClickListener(onClickSave);
        onClear();
        onLoadTask();
    }

    private void onLoadTask(){
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.containsKey("Tasks")){
            tasks = (Tasks) bundle.getSerializable("Tasks");
        }

        if (tasks != null && tasks.getTasksId() > 0){
            idEdttasksTitle.setText(tasks.getTitle());
            idEdttasksDate.setText(tasks.getDate());
            idEdtClientPFObservation.setText(tasks.getObservation());
            if (tasks.getAllDay() == 0){
                idSwtTaskDiaInteiro.setChecked(false);
                idEdttasksHour.setEnabled(true);
                idEdttasksHour.setText(tasks.getHour());
            }else{
                idSwtTaskDiaInteiro.setChecked(true);
                idEdttasksHour.setEnabled(false);
                idEdttasksHour.setText(null);
            }
            onLoadClients(tasks.getClienteId());
        }else{
            long clientId = 0;
            if (bundle != null && bundle.containsKey("ClientId")){
                clientId = bundle.getLong("ClientId");
                clientId = clientId < 0 ? 0 : clientId;
                onLoadClients(clientId);
            }else{
                onLoadClients(clientId);
            }
        }
    }

    private  boolean onValidate(){
        boolean save = true;

        if (idEdttasksTitle.getText().toString().isEmpty()){
            idEdttasksTitle.setError("Informe o titulo!");
            idEdttasksTitle.requestFocus();
            save = false;
        }else{
            idEdttasksTitle.setError(null);
        }

        if (idSpnTaskClient.getSelectedItem() == null){
            FunctionsTools.showSnackBarLong(View,"Informe um cliente!");
            idSpnTaskClient.requestFocus();
            save = false;
        }

        if (idEdttasksDate.getText().toString().isEmpty()){
            idEdttasksDate.setError("Informe o dia!");
            idEdttasksDate.requestFocus();
            save = false;
        }else{
            idEdttasksDate.setError(null);
        }

        if (!idSwtTaskDiaInteiro.isChecked()){
            if (idEdttasksHour.getText().toString().isEmpty()){
                idEdttasksHour.setError("Informe o horário!");
                idEdttasksHour.requestFocus();
                save = false;
            }else{
                idEdttasksHour.setError(null);
            }
        }else{
            idEdttasksHour.setError(null);
        }

        return save;
    }

    private void onClear(){
        tasks = new Tasks();
        idEdttasksTitle.setText(null);
        idSpnTaskClient.setSelection(0);
        idEdttasksDate.setText(null);
        idEdttasksHour.setText(null);
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

    private void close(){
        FunctionsTools.closeActivity(NewTaskActivity.this);
    }

    private void delete(){
        try{
            if (tasks != null && tasks.getTasksId() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(View.getContext(),R.style.Theme_MaterialComponents_Light_Dialog);
                builder
                        .setPositiveButton("Sim", (dialog, id) -> {
                            TB_TASKS.Delete(tasks);
                            Toast.makeText(NewTaskActivity.this,"Tarefa concluida!",Toast.LENGTH_LONG).show();
                            FunctionsTools.closeActivity(NewTaskActivity.this);
                            dialog.cancel();
                        })
                        .setNegativeButton("Não", (dialog, id) -> dialog.cancel())
                        .setMessage("Tem ceteza que deseja concluir essa tarefa?");

                final AlertDialog alert = builder.create();
                alert.show();
            }
        }catch (Exception ex){
            FunctionsTools.showSnackBarLong(View,ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        this.idItemDelete = menu.findItem(R.id.idItemDelete);
        if (tasks == null || tasks.getTasksId() <= 0 ){
            this.idItemDelete.setVisible(false);
        }else{
            this.idItemDelete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.idItemDelete:
                delete();
                break;
            case R.id.idItemClose:
                close();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickSave = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            try{
                if (onValidate()){
                    if (tasks == null){ tasks = new Tasks(); }
                    tasks.setTitle(idEdttasksTitle.getText().toString());
                    tasks.setClienteId(((FunctionsTools.GernericObject)idSpnTaskClient.getSelectedItem()).getId());
                    tasks.setAllDay(idSwtTaskDiaInteiro.isChecked()? 1 : 0);
                    tasks.setDate(idEdttasksDate.getText().toString());
                    tasks.setHour(idEdttasksHour.getText().toString());
                    tasks.setObservation(idEdtClientPFObservation.getText().toString());
                    if (tasks.getIdNuvem() <= 0) tasks.setIdNuvem(0);
                    if (tasks.getUpdate().isEmpty() || tasks.getUpdate().equals("N")) tasks.setUpdate("S");

                    if (tasks.getTasksId() <= 0){
                        tasks.setTasksId(TB_TASKS.Insert(tasks));
                        FunctionsTools.showSnackBarLong(View, "Tarefa salva!");
                        onNotificationNewTask();
                    }else{
                        TB_TASKS.Update(tasks);
                        FunctionsTools.showSnackBarLong(View, "Tarefa atualizada!");
                        onNotificationUpdateTask();
                    }
                    onClear();
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
                idEdttasksHour.setEnabled(false);
            } else {
                idEdttasksHour.setEnabled(true);
            }
        }
    };

    private View.OnClickListener onClickDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int day,month,year;

            if (!idEdttasksDate.getText().toString().equals("") && idEdttasksDate.getText().toString().split("/").length > 0 ) {
                String data[] = idEdttasksDate.getText().toString().split("/");
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

            idEdttasksDate.setText(data);
        }
    };

    private View.OnClickListener onClickTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int hour, minute;

            if (!idEdttasksHour.getText().toString().equals("") && idEdttasksHour.getText().toString().split(":").length > 0){
                String hours[] = idEdttasksHour.getText().toString().split(":");
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

            idEdttasksHour.setText(Hour);
        }
    };
}
