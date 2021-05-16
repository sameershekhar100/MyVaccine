package com.example.myvaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PinSearch extends AppCompatActivity {
Button b;
EditText editText;
RadioButton r1,r2;
TextView t;
    String x;
int age=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_search);
        b=findViewById(R.id.submit1);
        editText=findViewById(R.id.edit);
        r1=findViewById(R.id.youth_);
        r2=findViewById(R.id.old_);
        t=findViewById(R.id.city);


        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinSearch.this,MainActivity.class));
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=editText.getText().toString();
                if(x.length()==6){

                    Intent i=new Intent(PinSearch.this,SlotActivity.class);
                            i.putExtra("pin",x);
                            i.putExtra("age_",age+"");
                    startActivity(i);

                }
                else
                    Log.d("pqrs",x);
                    Toast.makeText(PinSearch.this, " PIN="+x, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onRadioButtonClicked1(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.youth_:
                if (checked)
                    age=18;
                System.out.println(age);
                break;
            case R.id.old_:
                if (checked)
                    age=45;
                break;
        }
    }
}