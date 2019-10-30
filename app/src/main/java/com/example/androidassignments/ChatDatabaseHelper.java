package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String CLASS_NAME = "ChatDataBaseHelper";

    public static final String DATABASE_NAME = "messages.db";
    public static final int VERSION_NUM = 2;

    public static final String TABLE_MESSAGES = "messages";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";

    private static final String DATABASE_CREATE = "create table " + TABLE_MESSAGES
            + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";

    private static final String DATABASE_UPDATE = "DROP TABLE IF EXISTS " + TABLE_MESSAGES;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.i(CLASS_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer) {
        Log.w(SQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVer + " to "
                        + newVer);
        database.execSQL(DATABASE_UPDATE);
        onCreate(database);
        Log.i(CLASS_NAME, "Calling onUpgrade, oldVersion=" + oldVer + "newVersion=" + newVer);
    }
}
