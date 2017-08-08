package org.toolfactory.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @since 07 April 2016
 * @author Arjun Prasad
 */
public class SQLiteManager{

    private Logger logger=Logger.getLogger(SQLiteManager.class.getSimpleName());
    private static SQLiteManager sqLiteManager;

    private SQLiteListener sqLiteListener;
    private SQLiteDatabase databaseRead;
    private SQLiteDatabase databaseWrite;

    private SQLiteHelper sqLiteHelper;

    public static SQLiteManager getSingleton(Context context,String dbName,int dbVersion,SQLiteListener sqLiteListener) throws Exception{
        if(sqLiteManager==null){
            sqLiteManager=new SQLiteManager();
            sqLiteManager.sqLiteListener=sqLiteListener;
            sqLiteManager.sqLiteHelper=sqLiteManager.new SQLiteHelper(context,dbName,dbVersion);
            sqLiteManager.databaseRead=sqLiteManager.sqLiteHelper.getReadableDatabase();
            sqLiteManager.databaseWrite=sqLiteManager.sqLiteHelper.getWritableDatabase();
            sqLiteManager.logger.log(Level.INFO,"Database Started");
        }
        return sqLiteManager;
    }

    public void close(){
        if(sqLiteHelper!=null){
            sqLiteHelper.close();
        }
    }

    public SQLiteListener getSqLiteListener() {
        return sqLiteListener;
    }

    public void setSqLiteListener(SQLiteListener sqLiteListener) {
        this.sqLiteListener = sqLiteListener;
    }

    public SQLiteDatabase getDatabaseRead() {
        return databaseRead;
    }

    public void setDatabaseRead(SQLiteDatabase databaseRead) {
        this.databaseRead = databaseRead;
    }

    public SQLiteDatabase getDatabaseWrite() {
        return databaseWrite;
    }

    public void setDatabaseWrite(SQLiteDatabase databaseWrite) {
        this.databaseWrite = databaseWrite;
    }

    public class SQLiteHelper extends SQLiteOpenHelper{

        private SQLiteHelper(Context context,String dbName,int dbVersion){
            super(context,dbName,null,dbVersion);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            logger.log(Level.INFO,"onCreate sqLiteListener="+sqLiteListener);
            if(sqLiteListener!=null){
                sqLiteListener.onCreate(db);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            logger.log(Level.INFO,"onUpgrade sqLiteListener="+sqLiteListener);
            if(sqLiteListener!=null){
                sqLiteListener.onUpgrade(db,oldVersion,newVersion);
            }
        }
    }
}
