package com.example.sathya.cake;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Report extends AppCompatActivity implements View.OnClickListener{
TextView top,fro,tod;
Button ca1,ca2,pend,clo;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        top=findViewById(R.id.textView5);
        top.setText("Reports");
        fro=findViewById(R.id.frod);
        tod=findViewById(R.id.tod);
        ca1=findViewById(R.id.ca1);
        ca2=findViewById(R.id.ca2);
        pend=findViewById(R.id.pend);
        clo=findViewById(R.id.clos);
        ca1.setOnClickListener(this);
        ca2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ca1:
                new DatePickerDialog(Report.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case  R.id.ca2:
                new DatePickerDialog(Report.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {



        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
            //updateLabel1();
        }

    };
    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {



        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //updateLabel();
            updateLabel1();
        }

    };




    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fro.setText(sdf.format(myCalendar.getTime()));


    }
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        // mfro.setText(sdf.format(myCalendar.getTime()));
        tod.setText(sdf.format(myCalendar.getTime()));

    }

}

