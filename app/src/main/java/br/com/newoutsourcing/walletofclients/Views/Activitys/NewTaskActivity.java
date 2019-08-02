package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class NewTaskActivity extends AppCompatActivity {

    private EditText idEdtTaksDate;
    private EditText idEdtTaksHour;
    private Switch idSwtTaskDiaInteiro;
    private Button idBtnSave;
    private Button idBtnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        this.onInflate();
        this.onConfiguration();
    }

    private void onInflate(){
        this.idEdtTaksDate = this.findViewById(R.id.idEdtTaksDate);
        this.idEdtTaksHour = this.findViewById(R.id.idEdtTaksHour);
        this.idSwtTaskDiaInteiro = this.findViewById(R.id.idSwtTaskDiaInteiro);
        this.idBtnSave = this.findViewById(R.id.idBtnSave);
        this.idBtnClose = this.findViewById(R.id.idBtnClose);
    }

    private void onConfiguration(){
        this.idEdtTaksDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_DATA, this.idEdtTaksDate));
        this.idEdtTaksDate.setText(FunctionsApp.getCurrentDate());
        this.idEdtTaksDate.setOnClickListener(this.onClickDate);
        this.idEdtTaksHour.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_HORA, this.idEdtTaksHour));
        this.idEdtTaksHour.setText(FunctionsApp.getCurrentTime());
        this.idEdtTaksHour.setOnClickListener(this.onClickTime);
        this.idSwtTaskDiaInteiro.setOnClickListener(this.onClickAllDay);
        this.idBtnSave.setOnClickListener(this.onClickSave);
        this.idBtnClose.setOnClickListener(this.onClickClose);
    }

    private View.OnClickListener onClickClose = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FunctionsApp.closeActivity(NewTaskActivity.this);
        }
    };

    private View.OnClickListener onClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FunctionsApp.showSnackBarLong(view,"Em desenvolvimento, aguarde...");
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
