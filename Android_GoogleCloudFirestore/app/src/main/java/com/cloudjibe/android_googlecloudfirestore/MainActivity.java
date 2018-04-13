package com.cloudjibe.android_googlecloudfirestore;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity" ;
    private RecyclerView mRecyclerList;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();
    private List<SensorDataModel> sensorDataList;
    private SensorDataListAdaptor sensorDataListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.humidity);

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null)
            Log.d("Firebase Token", token);

        //ListView
        /*
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ListView listView = (ListView) findViewById(R.id.listv);
        listView.setAdapter(adapter);
        */
        //Recycler View
        mRecyclerList = (RecyclerView)findViewById(R.id.recyclerlist);
        mRecyclerList.setHasFixedSize(true);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(this));


        sensorDataList = new ArrayList<SensorDataModel>();
        sensorDataListAdaptor = new SensorDataListAdaptor(sensorDataList);
        mRecyclerList.setAdapter(sensorDataListAdaptor);

        //icons - https://material.io/icons/#ic_account_balance
        //https://firebase.google.com/docs/android/setup
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        db.collection("th-sensordata")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                listItems.add(document.getId() + " => " + document.getData());
//                                adapter.notifyDataSetChanged();
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

        db.collection("th-sensordata").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                    for(DocumentChange doc : documentSnapshots.getDocumentChanges())
                    {
                        if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED) {
                            /*
                            String humidity = doc.getDocument().getDouble("humidity").toString();
                            String temperature = doc.getDocument().getDouble("temperature").toString();
                            listItems.add("humidity = " + humidity + "%, temperature = " + temperature + "F");
                            adapter.notifyDataSetChanged();
                            */

                            //RecyclerView
                            SensorDataModel sensordata = doc.getDocument().toObject(SensorDataModel.class);
                            sensorDataList.add(sensordata);
                            sensorDataListAdaptor.notifyDataSetChanged();
                        }
                    }
                } else if (e != null) {
                    Log.w(TAG, "An exception occured", e);
                }
            }
        });


        //Just for one document - live update
//        DocumentReference usersDocRef = db.collection("vu-owner").document("OLxYrtU5zupT2XT45V3m");
//        usersDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
//                if (documentSnapshot != null || documentSnapshot.exists()) {
//                    String name = documentSnapshot.getString("ownername");
//
//                    listItems.add("value"+name);
//                    adapter.notifyDataSetChanged();
//
//
//                } else if (e != null) {
//                    Log.w(TAG, "An exception occured", e);
//                }
//            }
//        });


    }
}
