package com.example.sathya.cake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Logg extends AppCompatActivity implements View.OnClickListener{
EditText user,pwd;
Button log;
String STname,STpass,usr,pass;
String[] error, mzone_status,mzid,mcid,k,msms,mimg,w_id,b_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logg);
        user=findViewById(R.id.user);
        pwd=findViewById(R.id.pwd);
        log=findViewById(R.id.log);
        log.setOnClickListener(this);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if(nInfo!=null && nInfo.isConnected()) {
            // Toast.makeText(this, "Network is available", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if ((user.getText().length() != 0) && (pwd.getText().length() != 0)) {
            usr = user.getText().toString();
            pass = pwd.getText().toString();
            log();



        } else {

            if ((user.getText().length() != 0)) {
                Toast.makeText(this, "Password Field is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username Field is Empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void log () {
        STpass = user.getText().toString();
        STname = pwd.getText().toString();
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, "http://welleservices.com/feetoBristo/api/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText (MainActivity.this, "res: "+response, Toast.LENGTH_LONG ).show ( );
                try {
                    loadIntoListViewlog("[" + response + "]");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", STname);
                params.put("password", STpass);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    private void loadIntoListViewlog(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);

        k = new String[jsonArray.length()];
        error=new String[jsonArray.length ()];

        mzid=new String[jsonArray.length ()];
        mcid=new String[jsonArray.length ()];
        w_id=new String[jsonArray.length ()];
        b_id=new String[jsonArray.length ()];




        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            k[i] = obj.getString("api_key");
            error[i]=obj.getString ( "error" );
            w_id[i]=obj.getString ( "waiter_id" );
            b_id[i]=obj.getString ( "branch_id" );


            // mcid[i]=obj.getString ( "c_id" );

        }
        for (int i = 0; i < jsonArray.length(); i++) {

            check ( error[i],k[i]);



        }


        Constant.waiter_id=w_id[0];Constant.branch_id=b_id[0];
        Intent v=new Intent(this,Home.class);
        startActivity(v);
        finish();


    }

    private void check(String con,String key){

        // Toast.makeText(this, "con"+con, Toast.LENGTH_SHORT).show();
        STpass=user.getText ().toString ();
        STname=pwd.getText ().toString ();
        if(con.equals ( "false" )) {


            SharedPreferences sharedPreferences = getSharedPreferences("userlog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            SharedPreferences.Editor logid=sharedPreferences.edit();
            editor.putString("api_key",key);

            Constant.idapi=key;


            editor.putString("username",STname);
            //editor.putString("url_img",constant.img_url);
            //editor.putString("zoenid",Z_id);
            //editor.putString("zcid",cid.toString ());
            logid.putString ( "pass",STpass );
            logid.putString("username",STname);
            editor.apply();
            logid.apply ();
            logid.commit ();

            editor.commit();

            //  Toast.makeText(this, "img::"+Url.img_url, Toast.LENGTH_SHORT).show();
           // String img=constant.img_url;




            //  Url.img_url=mimg[0];;
            //  editor.putString("img_url",Url.img_url);
            // Toast.makeText(this, ""+Url.img_url, Toast.LENGTH_SHORT).show();



            //startActivity(v);
        }
        else if(con.equals ( "true" )){
            Toast.makeText ( this, "UnAuthorized User", Toast.LENGTH_SHORT ).show ( );
        }
        else{

            Toast.makeText ( this, "Activated Zone", Toast.LENGTH_SHORT ).show ( );
        }

    }


}
