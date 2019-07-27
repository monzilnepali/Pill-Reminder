package com.example.androidalarm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidalarm.database.DBHandler;
import com.example.androidalarm.database.MedicineModel;

public class activity_alert extends AppCompatActivity {
  private TextView title,dosage,time;
  private Button okBtn;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        //getting data from intent

       int id =getIntent().getIntExtra("id",0);
     //   System.out.println("the id in activity alert is "+id);

         mediaPlayer = MediaPlayer.create(activity_alert.this, R.raw.notify);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

        //getting ui compoent object

        title=findViewById(R.id.medicineName);
        dosage=findViewById(R.id.medicineDosage);
        time=findViewById(R.id.medicineTime);
        okBtn=findViewById(R.id.okBtn);

        okBtn.setOnClickListener(closeApplicationHandler);

        //getting data from database via id

        MedicineModel mn =getData(id);

        title.setText(mn.getMedicineName());
        dosage.setText(mn.getMedicineDosage());
        time.setText(mn.getMedicineTime());





    }

    private View.OnClickListener closeApplicationHandler=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //closing applciation
            mediaPlayer.stop();
            finish();

        }
    } ;

    private MedicineModel getData(int id) {
        System.out.println("database handler called");
        String query= "SELECT * FROM "+ MedicineModel.TABLE_NAME +" WHERE "+MedicineModel.COLUMN_ID+" = "+id;
        DBHandler dbhandler=new DBHandler(activity_alert.this);
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MedicineModel mn=new MedicineModel();
        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            mn.setMedicineName(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_NAME)));
            mn.setMedicineDosage(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_DOSAGE)));
            mn.setMedicineType(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_TYPE)));
            mn.setMedicineTime(cursor.getString(cursor.getColumnIndex(MedicineModel.COLUMN_TIME)));

        }
        // close the db connection
        cursor.close();

        return mn;


    }
}
