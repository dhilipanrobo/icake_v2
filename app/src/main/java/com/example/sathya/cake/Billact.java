package com.example.sathya.cake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Billact extends AppCompatActivity implements View.OnClickListener{
TextView cname,qty,tot_amt,adv_amt,pay_amt,del_dat;
String ca,dd,ad;
Button print;
int total,pay;
float qt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billact);
        Intent i=getIntent();
        ca=i.getStringExtra("cname");
        dd=i.getStringExtra("deldate");

        total=i.getIntExtra("tot",0);
        ad=i.getStringExtra("adv");
        pay=i.getIntExtra("pay",0);

        Bundle bundle = getIntent().getExtras();
        qt = bundle.getFloat("qty");
        cname=findViewById(R.id.cname);
        qty=findViewById(R.id.qty);
        tot_amt=findViewById(R.id.tot_amt);
        adv_amt=findViewById(R.id.adv_amt);
        pay_amt=findViewById(R.id.pay_amt);
        del_dat=findViewById(R.id.del_dat);
        print=findViewById(R.id.pri);
        print.setOnClickListener(this);

        cname.setText(""+ca);
        qty.setText(""+qt+"kg");
        tot_amt.setText(""+total);
        adv_amt.setText(""+ad);
        pay_amt.setText(""+pay);
        del_dat.setText(""+dd);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Categories.class);
        startActivity(i);
    }
}
