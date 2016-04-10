package com.caozhiliang.sql;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caozhiliang.httpdata.UserBean;

public class PersonServier2 {
	private SQL2 sq;
	private SQLiteDatabase db; 
	public PersonServier2(Context context)
	{
		this.sq=new SQL2(context);
		
	}
  public void mo()
  {
	  db=sq.getWritableDatabase();
  }
	
   public void tian(UserBean user)
   {
	mo();
	db.execSQL("insert into class2(phone,name,address,image,key) values(?,?,?,?,?,?)",
	new Object[] { user.getPhone(),user.getName(),user.getAddress(),user.getImage(),user.getKey()});
	 db.close();
   }
   /*public void shan(Person person)
   {
	 SQLiteDatabase db=sq.getWritableDatabase();
	 db.execSQL("delete from class where name=? and id=? ",
	 new Object[] {person.getName(),person.getId()});
   }
   */
   public void gai(UserBean user)
   {
	  mo();
	   db.execSQL("update class2 set name=?,phone=?,key=?,address=?,image=?",
	   new Object[]{user.getName(),user.getPhone(),user.getKey(),
			   user.getAddress(),user.getImage()} );
	   db.close();
   }
 
   public UserBean cha()
   {
	   mo();
	   Cursor cursor= db.rawQuery("select * from class2",null);
	   if(cursor.moveToFirst())
	   {
		 String name= cursor.getString(cursor.getColumnIndex("name"));
		 String phone= cursor.getString(cursor.getColumnIndex("phone"));
		 String address= cursor.getString(cursor.getColumnIndex("address"));
		 String key= cursor.getString(cursor.getColumnIndex("key"));
		 String image= cursor.getString(cursor.getColumnIndex("image"));
		 UserBean use=new UserBean();
		 use.setName(name);
		 use.setAddress(address);
		 use.setPhone(phone);
		 use.setImage(image);
		 use.setKey(key);
		 return use;
		 
	   }
	  if(!cursor.moveToFirst())
	  {
		  return new UserBean();
	  }
	   cursor.close();
	   db.close();
	   return null;
   }
   /*public void close()
   {
	   db.close();
   }*/
   
}
