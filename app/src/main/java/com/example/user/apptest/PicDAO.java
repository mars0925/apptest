package com.example.user.apptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Mars on 2018/2/10.
 */

public class PicDAO {

        SQLiteDatabase db;
        Context context;

        public PicDAO(Context context)
        {
            this.context = context;
            MyDBHandler myDBHandler = new MyDBHandler(context);
            db = myDBHandler.getWritableDatabase();
        }


    //新增資料
    public boolean addpic(PicData s)
    {
        ContentValues cv = new ContentValues();

        cv.put("name",s.name);
        cv.put("path",s.path);
        db.insert("picture_data",null,cv);
        db.close();
        return true;
    }
    //依照日期
    public ArrayList<PicDataout> getListbydate() {
        ArrayList<PicDataout> mylist = new ArrayList<>();
        Cursor c = db.query("picture_data", new String[] {"_id", "name","path"}, null,null, null, null, null);
        if (c.moveToFirst())
        {
            PicDataout s1 = new PicDataout(c.getInt(0),c.getString(1),c.getString(2));

            mylist.add(s1);
            while(c.moveToNext())
            {
                PicDataout s = new PicDataout(c.getInt(0),c.getString(1),c.getString(2));
                mylist.add(s);
            }
        }
        return mylist;
    }



}
