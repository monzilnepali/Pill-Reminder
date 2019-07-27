package com.example.androidalarm.database;

public class MedicineModel {

    public static final String TABLE_NAME = "Medicinedb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "medicineName";
    public static final String COLUMN_TYPE = "medicineType";
    public static final String COLUMN_DOSAGE="medicineDosage";
    public static final String COLUMN_TIME="medicineTime";

    private int id;
    private String medicineName;
    private String medicineType;
    private String medicineDosage;
    private String medicineTime;

    public static final String CREATE_TABLE=
                     "CREATE TABLE " + TABLE_NAME +"("
                             + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                             + COLUMN_NAME+ " VARCHAR(100),"
                              + COLUMN_TYPE+ " VARCHAR(50),"
                              + COLUMN_DOSAGE +" VARCHAR(50),"
                              + COLUMN_TIME+" VARCHAR(100)"
                              + ")";



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineDosage() {
        return medicineDosage;
    }

    public void setMedicineDosage(String medicineDosage) {
        this.medicineDosage = medicineDosage;
    }

    public String getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(String medicineTime) {
        this.medicineTime = medicineTime;
    }
}
