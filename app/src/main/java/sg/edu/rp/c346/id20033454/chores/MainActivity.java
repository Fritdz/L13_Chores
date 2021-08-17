package sg.edu.rp.c346.id20033454.chores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnView, btnSubmit;
    EditText etChore, etDetails;
    RadioGroup rgDay;
    TimePicker tp;
    RatingBar rbImpt;
    RadioButton rbMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnView=findViewById(R.id.btnView);
        btnSubmit=findViewById(R.id.btnSubmit);
        etChore=findViewById(R.id.etChore);
        etDetails=findViewById(R.id.etDetails);
        rgDay=findViewById(R.id.rgDay);
        rbImpt=findViewById(R.id.rbImpt);
        rbMon=findViewById(R.id.rbMon);
        tp=findViewById(R.id.timePicker);

        tp.setCurrentHour(0);
        tp.setCurrentMinute(0);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etChore.getText().toString().trim();
                String details = etDetails.getText().toString().trim();
                if (name.length() == 0 || details.length() == 0){
                    Toast.makeText(MainActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String day = "";
                int radioID = rgDay.getCheckedRadioButtonId();

                switch (radioID){

                    case R.id.rbMon:
                        day = "Monday";
                        break;

                    case R.id.rbTue:
                        day = "Tuesday";
                        break;

                    case R.id.rbWed:
                        day = "Wednesday";
                        break;

                    case R.id.rbThur:
                        day = "Thursday";
                        break;

                    case R.id.rbFri:
                        day = "Friday";
                        break;

                    case R.id.rbSat:
                        day = "Saturday";
                        break;

                    case R.id.rbSun:
                        day = "Sunday";
                        break;


                }
                String time = "";
                int hour = tp.getCurrentHour();
                if(hour < 10){
                    time+= "0" + hour;
                 }else{
                    time+= hour;
                }
                time+=":";
                int minute = tp.getCurrentMinute();
                if(minute < 10){
                    time+= "0" + minute;
                }else{
                    time+= minute;
                }

                int impt = Integer.valueOf(Math.round(rbImpt.getRating()));

                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertChore(name, details, day, time, impt);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Chore inserted", Toast.LENGTH_LONG).show();
                    etChore.setText("");
                    etDetails.setText("");
                    rbImpt.setRating(0);
                    rbMon.setChecked(true);
                    tp.setCurrentHour(0);
                    tp.setCurrentMinute(0);
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }


            }

        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChoreList.class);
                startActivity(i);
            }
        });
    }
}