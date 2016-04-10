package com.caozhiliang.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL2 extends SQLiteOpenHelper {

	public SQL2(Context context) {
		super(context, "ziliao1.db", null, 10);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
    //db.execSQL("CREATE TABLE class(phone char(20) ,name char(20), password char(20),addres char(20))");
		db.execSQL("CREATE TABLE class2(phone char(50) ,name char(50),address char(200),image char(200),key char(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
    //db.execSQL("DROP TABLE class");	
	//db.execSQL("CREATE TABLE class2(phone char(20) ,name char(20), password char(20),addres char(20))");
	}

}

