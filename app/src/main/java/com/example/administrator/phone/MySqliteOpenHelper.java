package com.example.administrator.phone;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/11.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySqliteOpenHelper(context, "itcast.db", null, 1);
        }
        return mInstance;
    }
    private MySqliteOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //創建表
        db.execSQL("create table friends(_id integer primary key autoincrement,"+"name text,"+"phone text)");
        //初始表
        ContentValues values = new ContentValues();
        for(int i = 0;i<100;i++){
            values.clear();
            values.put("name","小明"+i);
            values.put("phone","0910"+i);
            db.insert("friends",null,values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
