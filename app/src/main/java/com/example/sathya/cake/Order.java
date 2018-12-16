package com.example.sathya.cake;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Order extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    ImageView image;
    Button dat;
    EditText na, mbl, msg;
    TextView item, pric, de, to, datt;
    String cat, pr, ima, des, amt;
    String name, mob, mg, qty, odate, tot, price, adv, status, item_id;
    Button sub;
    Spinner sp;
    EditText adv_amt;
    int pay;
    int total;
    float quan;
    Calendar myCalendar = Calendar.getInstance();
    java.sql.Timestamp cur_date = new java.sql.Timestamp(new java.util.Date().getTime());
DBhelper db=new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent i = getIntent();
        mbl = findViewById(R.id.mob);
        na = findViewById(R.id.name);
        msg = findViewById(R.id.msg);
        item_id = i.getStringExtra("item_id");
        cat = i.getStringExtra("items");
        pr = i.getStringExtra("price");
        ima = i.getStringExtra("images");
        des = i.getStringExtra("descrip");
        adv_amt = findViewById(R.id.adv);
        datt = findViewById(R.id.datedit);
        dat = findViewById(R.id.dat);
        dat.setOnClickListener(this);
        image = findViewById(R.id.fimage);
        item = findViewById(R.id.cat);
        pric = findViewById(R.id.pric);
        sub = findViewById(R.id.order);
        sub.setOnClickListener(this);
        de = findViewById(R.id.des);
        to = findViewById(R.id.tot);
        sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);

       // odate.setTimestamp(1, date);
        // Toast.makeText(this, "ima"+ima, Toast.LENGTH_SHORT).show();
        de.setText(des);
        item.setText(cat);
        pric.setText("Rs: " + pr + "/Per-kg");



        Picasso.with(Order.this).load(ima).into(image);
        dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Order.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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

        }
    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datt.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        status="1";
        name = na.getText().toString();
        mob = mbl.getText().toString();
        mg = msg.getText().toString();
        quan = Float.parseFloat(sp.getSelectedItem().toString());
        Toast.makeText(this, "Order was Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Billact.class);
        i.putExtra("cname", cat);
        i.putExtra("deldate", datt.getText().toString());
        i.putExtra("qty", quan);
        i.putExtra("tot", total);

        if (adv_amt.getText().length() == 0) {

            i.putExtra("adv", "0");
        } else {
            i.putExtra("adv", adv_amt.getText().toString());
        }
        if (adv_amt.getText().length() == 0) {
            //Toast.makeText(this, "em"+total, Toast.LENGTH_SHORT).show();
            i.putExtra("pay", total);
        } else {
            String ad = adv_amt.getText().toString();
            pay = total - Integer.parseInt(ad);
            //Toast.makeText(this, "pay" + pay, Toast.LENGTH_SHORT).show();
            i.putExtra("pay", pay);
        }

        if(na.length()==0){
            Toast.makeText(this, "Name Field is Empty", Toast.LENGTH_SHORT).show();
        }else{
            if(mbl.length()==0){
                Toast.makeText(this, "Fill Your Mobile Number", Toast.LENGTH_SHORT).show();
            }
            else{
                if(mbl.length()<10){
                    Toast.makeText(this, "Fill Your Mobile Number Correctly", Toast.LENGTH_SHORT).show();
                }


            else {
                if (msg.length() == 0) {
                    Toast.makeText(this, "Message Field is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if(adv_amt.length()==0){
                        db.insertdb(item_id,cat,pr,name,mob,mg,cur_date.toString(),datt.getText().toString(),amt,total,"0",total,status);
                    }
                    else{
                        db.insertdb(item_id,cat,pr,name,mob,mg,cur_date.toString(),datt.getText().toString(),amt,total,adv_amt.getText().toString(),pay,status);
                    }
                    reg();

                    startActivity(i);
                }


            }}}}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        float res;

        amt = sp.getSelectedItem().toString();
        res = Integer.parseInt(pr) * Float.parseFloat(amt);
        total = Math.round(res);
        to.setText("Rs: " + total + " /-");
        //Toast.makeText(this, "val"+res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    private void reg() {



        // Toast.makeText(this,""+sub+cla,Toast.LENGTH_LONG).show();


            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "https://sathyakumar.000webhostapp.com/sathya/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ;

                }
            }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("cake", cat);
                    params.put("item_id", item_id);
                    params.put("price/1kg", pr);
                    params.put("name", name);
                    params.put("mobile_no", mob);
                    params.put("message", mg);
                    params.put("d_date", datt.getText().toString());
                    params.put("qty/kg", amt);
                    params.put("total_price", String.valueOf(total));
                    params.put("order_date", String.valueOf(cur_date));
                    params.put("status", status);

                    if (adv_amt.getText().length() == 0) {
                        params.put("adv_amt", "0");
                    } else {
                        params.put("adv_amt", adv_amt.getText().toString());
                    }
                    if (adv_amt.getText().length() == 0) {
                        params.put("pay_amt", String.valueOf(total));
                    } else {
                        params.put("pay_amt", String.valueOf(pay));
                    }

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            //   reg1();

            //et1.setText("");
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();


        }
    }








