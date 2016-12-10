package com.example.administrator.phone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.administrator.phone.MySqliteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */
public class FriendDao {
    private Context context;
    private SQLiteOpenHelper helper;

    public FriendDao(Context context) {
        super();
        this.context = context;
        helper = MySqliteOpenHelper.getInstance(context);
    }
    //總數據
    public int queryAllSize(){
        int size = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        if(db.isOpen()){
           Cursor cursor = db.query("friends", new String[]{"*"}, null, null, null, null, null);
            size = cursor.getCount();//獲取 cursor 大小
            cursor.close();
            db.close();
        }
        return size;
    }
    //分頁查
    public List<Friend> queryFriendsLimit(int startIndex,int block){
        List<Friend> infos = new ArrayList<Friend>();
       SQLiteDatabase db = helper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.query("friends", new String[]{"*"}, null, null, null, null, null, startIndex + "," + block);
            while (cursor.moveToNext()){
                int _id = cursor.getInt(0);
                String name =cursor.getString(1);
                String phone = cursor.getString(2);
                infos.add(new Friend(_id,name,phone));
            }
            cursor.close();
            db.close();
        }
        return infos;
    }
}
