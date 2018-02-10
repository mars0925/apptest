package com.example.user.apptest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mars on 2018/2/10.
 */

public class MyDBHandler extends SQLiteOpenHelper {
    final static String DB_NAME = "picture.sqlite";
    final static  int VERSION = 1;

    public MyDBHandler(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `picture_data` ( `_id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` TEXT, `path` TEXT )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
