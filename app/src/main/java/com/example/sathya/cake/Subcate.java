package com.example.sathya.cake;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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

public class Subcate extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView gv;
    String[] item,price,image,des,item_id;
TextView tex;
    String cid,ct;
    int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcate);
        gv=findViewById ( R.id.gv );
        gv.setOnItemClickListener(this);
        tex=findViewById(R.id.textView5);
        Intent i=getIntent();
        cid=i.getStringExtra("cid");
        ct=i.getStringExtra("category");
        tex.setText(ct);


        getJSON1 ( "https://sathyakumar.000webhostapp.com/sathya/subcake.php?cid="+cid);

    }

    private void loadIntoListView(String json) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray ( json );
            final String[] heroes = new String[jsonArray.length ( )];
            item = new String[jsonArray.length ( )];
            price=new String[jsonArray.length()];
            image=new String[jsonArray.length()];
            des=new String[jsonArray.length()];
            item_id=new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length ( ); i++) {
                JSONObject obj = jsonArray.getJSONObject ( i );
                // heroes[i] = "\t\t\t\t\t" + obj.getString("title") + "\n\n\n" + obj.getString("datee") + "\t\t\t\t\tMsg NO:" + obj.getString("sno");
                item[i] = "" + obj.getString ( "items" );
                price[i]=""+obj.getString("price");
                image[i]=""+obj.getString("images");
                des[i]=""+obj.getString("descrip");
                item_id[i]=""+obj.getString("item_id");
            }
            len = item.length;
            Subcate.AppointmentAdapter adapter = new Subcate.AppointmentAdapter( );
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
        Intent inten=new Intent(this,Order.class);
        inten.putExtra("items",item[i]);
        inten.putExtra("price",price[i]);
        inten.putExtra("images",image[i]);
        inten.putExtra("descrip",des[i]);
        inten.putExtra("item_id",item_id[i]);
        startActivity(inten);

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
                convertView = getLayoutInflater ( ).inflate ( R.layout.sublist, null );
                TextView categ = convertView.findViewById ( R.id.cat );
                TextView pri = convertView.findViewById ( R.id.pric );
                ImageView ima=convertView.findViewById ( R.id.image );


                categ.setText(item[position]);
                pri.setText("Rs: "+price[position]+"/Per-kg");


                 Picasso.with ( Subcate.this ).load ( image[position] ).into ( ima );

//                 Toast.makeText ( Category.this, "ima"+cid[position], Toast.LENGTH_SHORT ).show ( );



            }
            return convertView;
        }

    }



}



