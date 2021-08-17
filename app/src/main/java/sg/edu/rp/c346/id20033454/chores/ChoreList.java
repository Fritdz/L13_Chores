package sg.edu.rp.c346.id20033454.chores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChoreList extends AppCompatActivity {

    ListView lvChore;
    Button btnAdd;
    ArrayList<Chores> choreList;
    CustomAdapter adapter;
    int requestCode = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_list);

        setTitle(getTitle().toString() + " ~ " + "Show Chores");

        lvChore = (ListView) this.findViewById(R.id.lvChore);
        btnAdd = (Button) this.findViewById(R.id.btnAdd);

        DBHelper dbh = new DBHelper(this);
        choreList = dbh.getAllChores();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, choreList);
        lvChore.setAdapter(adapter);

        lvChore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ChoreList.this, EditChore.class);
                i.putExtra("chore", choreList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            choreList.clear();
            choreList.addAll(dbh.getAllChores());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}