package com.example.myvaccine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SlotActivity extends AppCompatActivity {

    RecyclerView r;
    public int age=0,pin=0;

    TextView textView,Checker;
    ArrayList<Vaccine> arrayList = new ArrayList<>();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        textView = findViewById(R.id.noslot1);
        Checker=findViewById(R.id.slot1);
        progressBar=findViewById(R.id.progress1);
        Intent intent = getIntent();
        pin=Integer.parseInt(intent.getStringExtra("pin"));
        age=Integer.parseInt(intent.getStringExtra("age_"));

        System.out.println(age+"\n"+pin);

        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        String currentdate=dateFormat.format(date);
        r = findViewById(R.id.recycler1);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        r.setLayoutManager(manager);

        fetchSlots(currentdate);
    }

    private void fetchSlots(String currentdate) {

    String slotURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pin+"&date="+currentdate;
    JsonObjectRequest jsonArrayRequest1 = new JsonObjectRequest(Request.Method.GET, slotURL, null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray jsonArray = response.getJSONArray("centers");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject hospital = jsonArray.getJSONObject(i);
                    JSONArray session = hospital.getJSONArray("sessions");
                    if (age == session.getJSONObject(0).getInt("min_age_limit") || age == 0) {
                        String name = hospital.getString("name");
                        String address = hospital.getString("address")+","+hospital.getString("block_name")+","+
                                hospital.getString("district_name")+","+hospital.getString("state_name");

                        String feeType = hospital.getString("fee_type");
                        String vaccine = "";
                        String date = "";
                        String capacity = "";

                        for (int j = 0; j < session.length(); j++) {
                            JSONObject xx=session.getJSONObject(j);
                            vaccine += xx.getString("vaccine") + "\n";
                            date += xx.getString("date") + "\n";
                            capacity += xx.getInt("available_capacity") + "\n";
                        }
                        if(!vaccine.equals(""))
                            arrayList.add(new Vaccine(name, address, vaccine,date,capacity,feeType));
                    }
                }
                System.out.println(arrayList.size());
//                    mv.update(arrayList);
                VaccineAdaptar vv = new VaccineAdaptar(arrayList);
                r.setAdapter(vv);

                if (arrayList.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(SlotActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    });
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest1);
        progressBar.setVisibility(View.GONE);
}
}