package org.toolfactory.android.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * @since 06 April 2016
 * @author Arjun Prasad
 */
public interface SQLiteListener {

    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    void onException(Exception e);

}
