package com.acesconn.acesmportal;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsActivity extends AppCompatActivity {
    Button btn_getContacts;
    ListView lv_contacts;
    //ArrayAdapter<String> aryAdapter;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        btn_getContacts = (Button)findViewById(R.id.btn_getContacts);
        lv_contacts = (ListView)findViewById(R.id.list_Contacts);

        //aryAdapter = new ArrayAdapter<>(ContactsActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1);
        //lv_contacts.setAdapter(aryAdapter);
        this.adapter = new SimpleAdapter(ContactsActivity.this, arraylist, R.layout.list_contacts, new String[] {"name","email"}, new int[] {R.id.name, R.id.email});
        lv_contacts.setAdapter(adapter);

        btn_getContacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*
                //測試Firebase資料庫
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("Hello, World!");

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        //Log.d(TAG, "Value is: " + value);
                        Snackbar.make(ibtn_app_01, "Value is: " + value, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //Log.w(TAG, "Failed to read value.", error.toException());
                        Snackbar.make(ibtn_app_01, "Failed to read value." + error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                */
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("contacts");

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //aryAdapter.clear();
                        arraylist.clear();
                        for (DataSnapshot ds :dataSnapshot.getChildren()){
                            //aryAdapter.add(ds.child("name").getValue().toString());

                            HashMap<String, String> item = new HashMap<>();
                            item.put("name", ds.child("name").getValue().toString());
                            item.put("email", ds.child("email").getValue().toString());
                            arraylist.add(item);
                            adapter.notifyDataSetChanged();
                        }
                        Snackbar.make(btn_getContacts, "資料筆數: "+ dataSnapshot.getChildrenCount() , Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //Log.w(TAG, "Failed to read value.", error.toException());
                        Snackbar.make(btn_getContacts, "Failed to read value." + error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });
            }
        });

    }
}
