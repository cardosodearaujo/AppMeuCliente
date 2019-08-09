package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import java.util.Calendar;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import butterknife.BindView;

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

    public NewTaskActivity() {
        super(R.layout.activity_new_task);
    }

    @Override
    protected void onConfiguration(){
        this.idEdtTaksDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_DATA, this.idEdtTaksDate));
        this.idEdtTaksDate.setText(FunctionsTools.getCurrentDate());
        this.idEdtTaksDate.setOnClickListener(this.onClickDate);
        this.idEdtTaksHour.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_HORA, this.idEdtTaksHour));
        this.idEdtTaksHour.setText(FunctionsTools.getCurrentTime());
        this.idEdtTaksHour.setOnClickListener(this.onClickTime);
        this.idSwtTaskDiaInteiro.setOnClickListener(this.onClickAllDay);
        this.idBtnSave.setOnClickListener(this.onClickSave);
        this.idBtnClose.setOnClickListener(this.onClickClose);
    }

    private View.OnClickListener onClickClose = view -> FunctionsTools.closeActivity(NewTaskActivity.this);

    private View.OnClickListener onClickSave = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            try{

                if (idEdtTaksTitle.getText().toString().isEmpty()){

                }

                if (idSpnTaskClient.getSelectedItemId() <= 0){

                }

                if (idEdtTaksDate.getText().toString().isEmpty()){

                }

                if (idEdtTaksHour.getText().toString().isEmpty()){

                }

                if (idEdtClientPFObservation.getText().toString().isEmpty()){

                }

                Tasks tasks = new Tasks();
                tasks.setTitle(idEdtTaksTitle.getText().toString());
                tasks.setClienteId(idSpnTaskClient.getSelectedItemId());
                tasks.setAllDay(idSwtTaskDiaInteiro.isChecked()? 1 : 0);
                tasks.setDate(idEdtTaksDate.getText().toString());
                tasks.setHour(idEdtTaksHour.getText().toString());
                tasks.setObservation(idEdtClientPFObservation.getText().toString());

                tasks.setTasksId(TB_TASKS.Insert(tasks));

                FunctionsTools.showSnackBarLong(idView, "Tarefa salva com sucesso!");

            }catch (Exception ex){
                FunctionsTools.showSnackBarLong(idView,ex.getMessage());
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