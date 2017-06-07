package com.acesconn.acesmportal;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsActivity extends AppCompatActivity {
    EditText edt_query;
    Button btn_getContacts;
    ListView lv_contacts;
    //ArrayAdapter<String> aryAdapter;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        edt_query = (EditText) findViewById(R.id.edt_query);
        btn_getContacts = (Button)findViewById(R.id.btn_getContacts);
        lv_contacts = (ListView)findViewById(R.id.list_Contacts);

        //簡易Listview
        //aryAdapter = new ArrayAdapter<>(ContactsActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1);
        //lv_contacts.setAdapter(aryAdapter);
        //自定Listview
        this.adapter = new SimpleAdapter(ContactsActivity.this, arraylist, R.layout.list_contacts, new String[] {"name","phone_ext","email"}, new int[] {R.id.name, R.id.phone_ext,R.id.email});
        lv_contacts.setAdapter(adapter);

        database =DatabaseUtil.getDatabase();

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
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference myRef = database.getReference("contacts");
                arraylist.clear();

                //查詢
                if (edt_query.getText().toString().equals("")){
                    //未下關鍵字
                    get_query("");
                }else {
                    //以關鍵字依序搜尋各欄位
                    get_query("name");
                    get_query("lastname");
                    get_query("phone_ext");
                    get_query("email");

                }
            }
        });

    }

    //查詢
    private void get_query(String child){
        DatabaseReference myRef = database.getReference("contacts");
        Query queryRef;
        if (child.equals("")){
            queryRef = myRef;
        }else {
            queryRef = myRef.orderByChild(child).equalTo(edt_query.getText().toString());
        }
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    HashMap<String, String> item = new HashMap<>();
                    if (ds.child("name").exists()){
                        item.put("name", ds.child("name").getValue().toString());
                    }
                    if (ds.child("phone_ext").exists()){
                        item.put("phone_ext", ds.child("phone_ext").getValue().toString());
                    }
                    if (ds.child("email").exists()){
                        item.put("email", ds.child("email").getValue().toString());
                    }
                    if (item.size() > 0){
                        arraylist.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                Snackbar.make(btn_getContacts, "資料筆數: "+ arraylist.size() , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Snackbar.make(btn_getContacts, "Failed to read value." + error.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
