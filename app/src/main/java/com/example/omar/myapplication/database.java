package com.example.omar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class database  {
    DB db;
    List<ChatMessage>result;
    ChatMessage res;
    List<Integer> r;
    public database(Context context) {

        db=new DB(context);
    }

    public int getCart()
    {
        SQLiteDatabase data=db.getWritableDatabase();
        //SQLiteQueryBuilder qb =new SQLiteQueryBuilder();

        String[] sqlSelect={"COUNT(message)AS count,message"};
        //String sqlTable="orders_info";

        //qb.setTables("orders_info");
      //  $sql = "select COUNT(*)`from_id` from messages group by `from_id`";
        //Cursor c = data.query("signal", new String[]{ "COUNT(message)" }, null, null, "message", null, null);

        Cursor c =data.query("signal",sqlSelect,null,null,"message",null,"count DESC");
        r=new ArrayList<>();
        while (c.moveToNext())
        {
            int uid=c.getInt(0);
            int id=c.getInt(1);


            r.add(uid);
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            return c.getInt(1);

        }

        return 0;
    }
    public int getMin(  List<Integer> values){
        int ret = values.get(0);
        for(int i = 1; i < values.size(); i++)
            ret = Math.min(ret,values.get(i));
        return ret;
    }


    public long addToCart(ChatMessage order)
    {
        SQLiteDatabase data=db.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("time",order.getDevice());
        contentValues.put("device",order.getTime());
        contentValues.put("message",order.getMessage());
        long id=data.insert("signal",null,contentValues);
        return id;
    }
    public void cleenCart()
    {
        SQLiteDatabase data=db.getWritableDatabase();
        String query=String.format("DELETE FROM signal");
        data.execSQL(query);
    }



}

class DB  extends SQLiteOpenHelper{

    public static final String database_name="B";
    public static final String table_name="Signal";
    public static final String uid="productId";
    public static final String time="time";
    public static final String message="message";
    public static final String device="device";


    public static final String clen="DELETE FROM "+table_name;
    private static final String DROP="DROP TABLE IF EXISTS "+table_name;
    private static final String CREATE_TABLE="CREATE TABLE "+table_name +" ( "+uid +" VARCHAR(255)  , "+time+" VARCHAR(255) ,"+message+" VARCHAR(255),"+device+" VARCHAR(255) );" ;

    Context context;


    public DB(Context context) {
        super(context,database_name, null,1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Toast.makeText(context,"on created ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DROP);
        onCreate(sqLiteDatabase);
        Toast.makeText(context,"on onUpgrade ",Toast.LENGTH_SHORT).show();
    }
}
