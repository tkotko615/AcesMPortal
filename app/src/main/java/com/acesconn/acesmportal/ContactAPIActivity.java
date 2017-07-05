package com.acesconn.acesmportal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ContactAPIActivity extends AppCompatActivity {
    EditText edt_query;
    Button btn_getContacts;
    ListView lv_contacts;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactapi);

        edt_query = (EditText) findViewById(R.id.edt_query);
        btn_getContacts = (Button)findViewById(R.id.btn_getContacts);
        lv_contacts = (ListView)findViewById(R.id.list_Contacts);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btn_getContacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WebAPIQry doQuery = new WebAPIQry();
                doQuery.execute();
            }
        });
    }

    class WebAPIQry extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            String API_URL = "http://exweb.acesconn.com/AcesAPI/api/contacts";
            // Do some validation here

            try {
                //URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            //responseView.setText(response);
            Toast.makeText(ContactAPIActivity.this,response,Toast.LENGTH_SHORT).show();
        }
    }

}
