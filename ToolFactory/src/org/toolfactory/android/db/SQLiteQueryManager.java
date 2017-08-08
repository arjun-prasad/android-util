package org.toolfactory.android.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @since 07 April 2016
 * @author Arjun Prasad
 */
public class SQLiteQueryManager {

    private static SQLiteQueryManager sqLiteQueryManager=null;

    private SQLiteManager sqLiteManager;
    private SQLiteDatabase databaseRead;
    private SQLiteDatabase databaseWrite;

    private SQLiteQueryManager(){}

    public static SQLiteQueryManager getSingleton(SQLiteManager sqLiteManager){
        if(sqLiteQueryManager==null){
            sqLiteQueryManager=new SQLiteQueryManager();
            sqLiteQueryManager.sqLiteManager=sqLiteManager;
            sqLiteQueryManager.databaseRead=sqLiteManager.getDatabaseRead();
            sqLiteQueryManager.databaseWrite=sqLiteManager.getDatabaseWrite();
        }
        return sqLiteQueryManager;
    }

    public boolean insertData(ContentValues contentValues, String tableName) {
        long i = databaseWrite.insert(tableName, null, contentValues);
        return true;
    }


    public Cursor getData(String tableName, String condition, String columnNames) {
        String Columns;
        if (columnNames == null) {
            Columns = "*";
        } else {
            Columns = columnNames;
        }
        StringBuilder query = new StringBuilder();
        query.append("select " + Columns + " from " + tableName);
        if (condition != null) {
            query.append(" where " + condition);
        }
        Cursor res = databaseRead.rawQuery(query.toString(), null);
        return res;
    }

    public int numberOfRows(String tableName) {
        int numRows = (int) DatabaseUtils.queryNumEntries(databaseRead,
                tableName);
        return numRows;
    }

    public boolean updateData(ContentValues contentValues, String tableName,
                              String condition, String[] args) {
        databaseWrite.update(tableName, contentValues, condition, args);
        return true;
    }

    public Integer deleteData(String tableName, String condition, String[] args) {
        return databaseWrite.delete(tableName, condition, args);
    }

    public Cursor getData(String query) {
        Cursor res = databaseRead.rawQuery(query.toString(), null);
        return res;
    }



}
