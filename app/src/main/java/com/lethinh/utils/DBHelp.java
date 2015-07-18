package com.lethinh.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thinh on 13/07/2015.
 */
public class DBHelp extends SQLiteOpenHelper {
    private static final String DB_NAME="SALECARD";
    private static final String DB_TABLE="Cards";
    private static final int VERSION=1;
    private static final String CREAT_DB="create table Cards(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "product TEXT not null, code TEXT not null, seri text not null, price INTEGER not null," +
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, issold INTEGER DEFAULT 1, note TEXT);";
    private static final String ID="id";
    private static final String PRODUCT="product";
    private static final String CODE="code";
    private static final String SERI="seri";
    private static final String PRICE="price";
    private static final String CREATED_AT="created_at";
    private static final String ISSOLD="issold";
    private static final String NOTE="note";

    public DBHelp(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists Cards");
            onCreate(sqLiteDatabase);
    }
}
