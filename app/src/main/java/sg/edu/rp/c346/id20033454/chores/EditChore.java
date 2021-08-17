package sg.edu.rp.c346.id20033454.chores;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class EditChore extends AppCompatActivity {

    EditText etID, etName, etDetails;
    Button btnDel, btnCancel, btnEdit;
    RatingBar rbImpt;
    TimePicker tp;
    RadioButton rb1, rb2, rb3, rb4, rb5 ,rb6, rb7;
    RadioGroup rgDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);

        setTitle(getTitle().toString() + " ~ " + "Edit Chore");

        etID = findViewById(R.id.etId);
        etName = findViewById(R.id.etChore);
        etDetails = findViewById(R.id.etDetails);
        btnDel = findViewById(R.id.btnDel);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);
        rbImpt = findViewById(R.id.rbImpt);
        tp = findViewById(R.id.timePicker);
        rgDay = findViewById(R.id.rgDay);
        rb1 = findViewById(R.id.rbMon);
        rb2 = findViewById(R.id.rbTue);
        rb3 = findViewById(R.id.rbWed);
        rb4 = findViewById(R.id.rbThur);
        rb5 = findViewById(R.id.rbFri);
        rb6 = findViewById(R.id.rbSat);
        rb7 = findViewById(R.id.rbSun);

        etID.setEnabled(false);

        Intent i = getIntent();
        final Chores currentChore = (Chores) i.getSerializableExtra("chore");

        etID.setText(currentChore.getId()+"");
        etDetails.setText(currentChore.getDetails());
        etName.setText(currentChore.getName());
        int hour = Integer.parseInt(currentChore.getTime().substring(0,2));
        int min = Integer.parseInt(currentChore.getTime().substring(3,5));
        tp.setCurrentMinute(min);
        tp.setCurrentHour(hour);
        rbImpt.setRating(currentChore.getImportance());
        String day = currentChore.getDay();

        switch (day){

            case "Monday":
                rb1.setChecked(true);
                break;

            case "Tuesday":
                rb2.setChecked(true);
                break;

            case "Wednesday":
                rb3.setChecked(true);
                break;

            case "Thursday":
                rb4.setChecked(true);
                break;

            case "Friday":
                rb5.setChecked(true);
                break;

            case "Saturday":
                rb6.setChecked(true);
                break;

            case "Sunday":
                rb7.setChecked(true);
                break;


        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditChore.this);
                currentChore.setName(etName.getText().toString().trim());
                currentChore.setDetails(etDetails.getText().toString().trim());
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
                currentChore.setDay(day);

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

                currentChore.setTime(time);

                int impt = Integer.valueOf(Math.round(rbImpt.getRating()));
                currentChore.setImportance(impt);
                int result = dbh.updateChore(currentChore);
                if (result>0){
                    Toast.makeText(EditChore.this, "Chore updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditChore.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditChore.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the chore\n"+ currentChore.getName());

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        DBHelper dbh = new DBHelper(EditChore.this);
                        int result = dbh.deleteChore(currentChore.getId());
                        if (result>0){
                            Toast.makeText(EditChore.this, "Chore deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(EditChore.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setPositiveButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditChore.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");

                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        finish();
                    }
                });
                myBuilder.setPositiveButton("Do not discard", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}