package com.example.sathya.cake;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Close_order extends AppCompatActivity implements View.OnClickListener {
    Button sea;
    String[] name, tot, adv, pay,ddate;
    String mob;
    GridView gv;
    EditText edt;
    int len;

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    LinearLayout inc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_order);
        gv = findViewById(R.id.gv);
        sea = findViewById(R.id.sea);
        sea.setOnClickListener(this);
        edt = findViewById(R.id.c_id);


    }

    @Override
    public void onClick(View view) {
        mob = edt.getText().toString();
        //llayout.setVisibility(View.VISIBLE);
        getJSON1("https://sathyakumar.000webhostapp.com/sathya/cdetail.php?cid=" + mob);

    }


    private void loadIntoListView(String json) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray(json);
            final String[] heroes = new String[jsonArray.length()];
            name = new String[jsonArray.length()];
            tot = new String[jsonArray.length()];
            adv = new String[jsonArray.length()];
            pay = new String[jsonArray.length()];
            ddate=new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                // heroes[i] = "\t\t\t\t\t" + obj.getString("title") + "\n\n\n" + obj.getString("datee") + "\t\t\t\t\tMsg NO:" + obj.getString("sno");
                //cid[i] = "" + obj.getString ( "cid" );
                name[i] = "" + obj.getString("name");
                tot[i] = "" + obj.getString("total_price");
                adv[i] = "" + obj.getString("adv_amt");
                pay[i] = "" + obj.getString("pay_amt");
                ddate[i] = "" + obj.getString("d_date");

            }
            len = name.length;
            Close_order.AppointmentAdapter adapter = new Close_order.AppointmentAdapter();
            gv.setAdapter(adapter);
           if(jsonArray.length()==0){
               Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
           }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

        }
    }


    private void getJSON1(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String sno1 = s;


                //   Toast.makeText(getApplicationContext(), sno3, Toast.LENGTH_SHORT).show();
                try {

                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            protected String doInBackground(Void... voids) {


                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    class AppointmentAdapter extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return len;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.closelist, null);
                LinearLayout llv=convertView.findViewById(R.id.lv);
                TextView na = convertView.findViewById(R.id.name);
                TextView total = convertView.findViewById(R.id.total);
                TextView ad = convertView.findViewById(R.id.adv);
                TextView pa = convertView.findViewById(R.id.pay);
                Button close = convertView.findViewById(R.id.cl);
                Button cancel = convertView.findViewById(R.id.cancel);
                TextView ddt=convertView.findViewById(R.id.dd);
                close.setOnClickListener(this);
                cancel.setOnClickListener(this);

                na.setText(name[position]);
                total.setText(tot[position]);
                ad.setText(adv[position]);
                pa.setText(pay[position]);
                ddt.setText(ddate[position]);

                // Picasso.with ( Categories.this ).load ( image[position] ).into ( ima );

//                 Toast.makeText ( Category.this, "ima"+cid[position], Toast.LENGTH_SHORT ).show ( );


            }
            return convertView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cl:
                    getJSON2("https://sathyakumar.000webhostapp.com/sathya/closesta.php?cid=" + mob);
                    //Toast.makeText(Close_order.this, "Order was Successfully Delivered", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.bottom);
                    break;
                case R.id.cancel:
                    getJSON2("https://sathyakumar.000webhostapp.com/sathya/cancelsta.php?cid="+mob);
                    //Toast.makeText(Close_order.this, "Order is Cancelled", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.cancel);
                    break;
            }

        }
    }

    private void getJSON2(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }

            }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
        }
    }
