package com.example.sathya.cake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity implements View.OnClickListener{
Button ord,lbil,re,clo;
TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ord=findViewById(R.id.order);
        ord.setOnClickListener(this);
        lbil=findViewById(R.id.bill);
        lbil.setOnClickListener(this);
        re=findViewById(R.id.report);
        re.setOnClickListener(this);
        clo=findViewById(R.id.close);
        clo.setOnClickListener(this);
        text=findViewById(R.id.textView5);
        text.setText("Select Activity");

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.order:
                i=new Intent(this,Categories.class);
                startActivity(i);
                break;
            case R.id.bill:
                Toast.makeText(this, "Bill", Toast.LENGTH_SHORT).show();
                break;
            case R.id.report:
                i=new Intent(this,Report.class);
                startActivity(i);
                break;
            case R.id.close:
                i=new Intent(this,Close_order.class);
                startActivity(i);

        }

    }
}
