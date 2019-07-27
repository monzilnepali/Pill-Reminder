package com.example.androidalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidalarm.database.DBHandler;
import com.example.androidalarm.database.MedicineModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnStartAlarm, btnCancelAlarm;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private ArrayList<Integer> imageId=new ArrayList<>();
    private ArrayList<String> medicineName=new ArrayList<>();
    private ArrayList<String> medicineDosage=new ArrayList<>();
    private ArrayList<String> medicineType=new ArrayList<>();
    private ArrayList<String> medicineTime=new ArrayList<>();
    FloatingActionButton addBtn;
    private MediaPlayer mMediaPlayer;
    TextView noDataMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Android Alarm");
        //add btn
        noDataMessage=findViewById(R.id.noDataMessage);
         addBtn =findViewById(R.id.addBtn);
          addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btn clicked");

                //opening input screen activity
                Intent intent=new Intent(MainActivity.this,InputActivity.class);
                startActivity(intent);
            }
        });
            //recycle view
        initData();


    }

    private List<MedicineModel> getData() {
        System.out.println("reading database");

        List<MedicineModel> medicineList=new ArrayList<>();
        //getting data from dataset
        String query= "SELECT * FROM "+ MedicineModel.TABLE_NAME;
        DBHandler dbhandler=new DBHandler(MainActivity.this);
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MedicineModel mn=new MedicineModel();
                mn.setMedicineName(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_NAME)));
                mn.setMedicineDosage(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_DOSAGE)));
                mn.setMedicineType(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_TYPE)));
                mn.setMedicineTime(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_TIME)));
                medicineList.add(mn);
            } while (cursor.moveToNext());
        }
        db.close();
        return medicineList;
    }

    private void initData() {

//list length
        //if len=0 then show message "No medicine"

        List<MedicineModel> mn=getData();

        if(mn.isEmpty()){
            noDataMessage.setVisibility(View.VISIBLE);

        }else {
            noDataMessage.setVisibility(View.INVISIBLE);
            for (MedicineModel model : mn) {
                medicineName.add(model.getMedicineName());
                medicineDosage.add(model.getMedicineDosage());
                medicineTime.add(model.getMedicineTime());
                medicineType.add(model.getMedicineType());

                if (model.getMedicineType().equals("Capsule")) {
                    imageId.add(R.drawable.capsule);
                } else {
                    imageId.add(R.drawable.bottle);
                }
            }
            initRecyclerView();
        }

    }

    private void initRecyclerView() {


        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        RecycleAdapter adapter = new RecycleAdapter(this,imageId,medicineName,medicineDosage,medicineType,medicineTime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
