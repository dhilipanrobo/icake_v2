package com.example.sathya.cake;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);

    }
    static final String DATABASE_NAME="cake.db";
    private static final int DATABASE_VERSION=1;
    private String TABLE_NAME ="customer";
    private String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"(sno INTEGER PRIMARY KEY AUTOINCREMENT,item_id VARCHAR(20),cake VARCHAR(20),price_1kg VARCHAR(20),name VARCHAR(20),mobile_num VARCHAR(20),message VARCHAR(20),order_date  DATETIME default current_timestamp,d_date  DATE,qty_kg VARCHAR(20),total_price VARCHAR(20),adv_amt VARCHAR(15),pay_amt VARCHAR(55),status VARCHAR(10))";
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL ("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }


    public long insertdb(String item, String cake, String price, String name, String mob_num, String message, String order_date, String d_date, String qty, int tot_price, String adv_amt, int pay_amt,String status)
    {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("item_id",item);
        content.put("cake",cake);
        content.put("name",name);
        content.put("mobile_num",mob_num);
        content.put("price_1kg",price);
        content.put("message",message);
        content.put("order_date",order_date);
        content.put("d_date",d_date);
        content.put("qty_kg",qty);
        content.put("total_price",tot_price);
        content.put("adv_amt",adv_amt);
        content.put("pay_amt",pay_amt);
        content.put("status",status);

        return db.insert(TABLE_NAME,null,content);
    }




}
