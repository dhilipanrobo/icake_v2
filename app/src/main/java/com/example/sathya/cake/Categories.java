package com.example.sathya.cake;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Categories extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{
    GridView gv;
    String[] cid,categ;
Button rep;
    int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        rep=findViewById(R.id.rep);
        rep.setOnClickListener(this);
        gv=findViewById ( R.id.gv );
        gv.setOnItemClickListener(this);
        Intent i=getIntent();
        getJSON1 ( "https://sathyakumar.000webhostapp.com/sathya/cake.php" );

    }

        private void loadIntoListView(String json) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray ( json );
            final String[] heroes = new String[jsonArray.length ( )];
            cid = new String[jsonArray.length ( )];
            categ=new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length ( ); i++) {
                JSONObject obj = jsonArray.getJSONObject ( i );
                // heroes[i] = "\t\t\t\t\t" + obj.getString("title") + "\n\n\n" + obj.getString("datee") + "\t\t\t\t\tMsg NO:" + obj.getString("sno");
                cid[i] = "" + obj.getString ( "cid" );
                categ[i]=""+obj.getString("category");

            }
            len = cid.length;
            Categories.AppointmentAdapter adapter = new Categories.AppointmentAdapter( );
            gv.setAdapter ( adapter );


        } catch (Exception e) {
            Toast.makeText ( getApplicationContext ( ), "No Internet", Toast.LENGTH_SHORT ).show ( );

        }
    }


    private void getJSON1(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute ( );
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute ( s );
                String sno1 = s;


                //   Toast.makeText(getApplicationContext(), sno3, Toast.LENGTH_SHORT).show();
                try {

                    loadIntoListView ( s );
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }


            @Override
            protected String doInBackground(Void... voids) {


                try {
                    //creating a URL
                    URL url = new URL ( urlWebService );

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = ( HttpURLConnection ) url.openConnection ( );

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder ( );

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader( con.getInputStream ( ) ) );

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine ( )) != null) {

                        //appending it to string builder
                        sb.append ( json + "\n" );
                    }

                    //finally returning the read string
                    return sb.toString ( ).trim ( );
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON ( );
        getJSON.execute ( );
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,Subcate.class);
        intent.putExtra("cid",cid[i]);
        intent.putExtra("category",categ[i]);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Report.class);
        startActivity(i);
    }


    class AppointmentAdapter extends BaseAdapter {

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
                convertView = getLayoutInflater ( ).inflate ( R.layout.categlis, null );
                TextView cat = convertView.findViewById ( R.id.cat );
                //TextView pri = convertView.findViewById ( R.id.pric );


                cat.setText(categ[position]);

               // Picasso.with ( Categories.this ).load ( image[position] ).into ( ima );

//                 Toast.makeText ( Category.this, "ima"+cid[position], Toast.LENGTH_SHORT ).show ( );



            }
            return convertView;
        }

    }



}



