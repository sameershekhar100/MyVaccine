package com.example.myvaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner s1,s2;
    RadioButton r1,r2;
    TextView textView;
    Button b1;
    private FirebaseAnalytics mFirebaseAnalytics;
    int stateno,districtno,age;
    ArrayList<State> myState=new ArrayList<>();
    ArrayList<District> myDistrict=new ArrayList<>();
    ArrayList<String> districtarray=new ArrayList<>();
    ArrayList<String> statearray=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(this);
        s1=findViewById(R.id.state);
        s2=findViewById(R.id.district);
        b1=findViewById(R.id.submit);
        r1=findViewById(R.id.youth);
        r2=findViewById(R.id.old);
        textView=findViewById(R.id.pin);


        districtarray.add("Enter district");
        myDistrict.add(new District("none","0"));
        myState.add(new State("none","0"));
        statearray.add("Enter State");

        getStates();
        if(stateno>0){
            getDistrict();
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PinSearch.class));
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stateno>0){
                    if(districtno==0){
                        Toast.makeText(MainActivity.this, "Please Select your Ditrict", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, MySlotActivity.class);
                        intent.putExtra("age", age + "");
                        intent.putExtra("stateID", stateno + "");
                        intent.putExtra("districtID", districtno + "");
                        startActivity(intent);
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "Select your State!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDistrict(){
        if(districtarray.size()>1)
        {

            districtarray.clear();
            districtarray.add("None");
        }
        String districtURL="https://cdn-api.co-vin.in/api/v2/admin/location/districts/"+(stateno);
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, districtURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("districts");

                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject state = jsonArray.getJSONObject(i);
                        String name = state.getString("district_name");
                        String id = state.getString("district_id");


                        myDistrict.add(new District(name,id));
                        districtarray.add(name);

                    }
                    setDistrictSpinner();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setDistrictSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, districtarray);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtno=Integer.parseInt(myDistrict.get(position).getDisID());
                System.out.println(districtno);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                districtno=Integer.parseInt(myDistrict.get(0).getDisID());
            }
        });
    }



    private void getStates() {
        String stateURL="https://cdn-api.co-vin.in/api/v2/admin/location/states";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, stateURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("states");

                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject state = jsonArray.getJSONObject(i);
                        String name = state.getString("state_name");
                        String id = state.getString("state_id");


                        myState.add(new State(name,id));
                        statearray.add(name);

                    }

                    setStateSpinner();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setStateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, statearray);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("abcd", "wtf!!");
                stateno = Integer.parseInt(myState.get(position).getStateID());
                getDistrict();
                System.out.println(stateno);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateno = Integer.parseInt(myState.get(0).getStateID());
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.youth:
                if (checked)
                    age=18;
                System.out.println(age);
                break;
            case R.id.old:
                if (checked)
                    age=45;
                break;
        }
    }
}