package com.athenaindotech.prak_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvOneTimeDate, tvOneTimeTime;
    private TextView tvRepeatingTime;
    private EditText edtOneTimeMessage, edtRepeatingMessage;
    private Button btnOneTimeDate,btnOneTimeTime, btnOneTime,btnRepeatingTime, btnRepeating, btnCancelRepeatingAlarm;
    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOneTimeDate = findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = findViewById(R.id.tv_one_time_alarm_time);
        edtOneTimeMessage = findViewById(R.id.edt_one_time_alarm_message);
        btnOneTimeDate = findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = findViewById(R.id.btn_one_time_alarm_time);
        btnOneTime = findViewById(R.id.btn_set_one_time_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeating_alarm_time);
        edtRepeatingMessage = findViewById(R.id.edt_repeating_time_alarm_message); //ADA TYPE DISINI DI BAPAKNYA
        btnRepeatingTime = findViewById(R.id.btn_repeating_time_alarm_time);
        btnRepeating = findViewById(R.id.btn_repeating_time_alarm);
        btnCancelRepeatingAlarm = findViewById(R.id.btn_cancel_repeating_time_alarm);
        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlarm.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();
        if(!TextUtils.isEmpty(alarmPreference.getOneTimeDate())){
            setOneTimeText();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_one_time_alarm_date){
            final Calendar currentDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calOneTimeDate.set(year, month, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));
                }
            },currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        }else if(v.getId() == R.id.btn_one_time_alarm_time){
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calOneTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    tvOneTimeTime.setText(dateFormat.format(calOneTimeTime.getTime()));
                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }else if(v.getId()==R.id.btn_set_one_time_alarm){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
            String oneTimeMessage = edtOneTimeMessage.getText().toString();
            alarmPreference.setOneTimeDate(oneTimeDate);
            alarmPreference.setOneTimeMessage(oneTimeMessage);
            alarmPreference.setOneTimeTime(oneTimeTime);
            alarmReceiver.setTypeOneAlarm(this,
                    AlarmReceiver.TYPE_ONE_TIME,
                    alarmPreference.getOneTimeDate(),
                    alarmPreference.getOneTimeTime(),
                    alarmPreference.getOneTimeMessage());
        }
    }

    private void setOneTimeText() {
        tvOneTimeTime.setText(alarmPreference.getOneTimeTime());
        tvOneTimeDate.setText(alarmPreference.getOneTimeDate());
        edtOneTimeMessage.setText(alarmPreference.getOneTimeMessage());
    }
}