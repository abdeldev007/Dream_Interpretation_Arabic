package com.reccakun.clientapp.Controllers;
 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 import android.widget.Toast;

 import com.reccakun.clientapp.Models.Category;
 import com.reccakun.clientapp.Models.Dream;

 import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.List;

public class DBConnect extends SQLiteOpenHelper {
        private static String DB_NAME = "db_cat.enc";
        private static String DB_PATH = "";
        private static final int DB_VERSION = 1;


        private SQLiteDatabase mDataBase;
        private final Context mContext;
        private boolean mNeedUpdate = false;

        public DBConnect(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            if (android.os.Build.VERSION.SDK_INT >= 17)
                DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
            else
                DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
            this.mContext = context;

            copyDataBase();

            this.getReadableDatabase();
        }

        public void updateDataBase()  {
            if (mNeedUpdate) {
                File dbFile = new File(DB_PATH + DB_NAME);
                if (dbFile.exists())
                    dbFile.delete();

                copyDataBase();

                mNeedUpdate = false;
            }
        }

        private boolean checkDataBase() {
            File dbFile = new File(DB_PATH + DB_NAME);
            return dbFile.exists();
        }

        private void copyDataBase() {
            if (!checkDataBase()) {
                this.getReadableDatabase();
                this.close();
                try {
                    copyDBFile();
                } catch (IOException e) {
                    Toast.makeText(mContext,"CheckDataBase"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void copyDBFile() throws IOException {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
            OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0)
                mOutput.write(mBuffer, 0, mLength);
            mOutput.flush();
            mOutput.close();
            mInput.close();
        }

        public boolean openDataBase()   {
            mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            return mDataBase != null;
        }

        @Override
        public synchronized void close() {
            if (mDataBase != null)
                mDataBase.close();
            super.close();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion)
                mNeedUpdate = true;
        }

    public List<Dream> getDreamsByCat(int catId){
        List<Dream> temp =new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("select * from posts where po_category ="+catId,null);
            if (c==null)return  null ;
            c.moveToFirst();
            do {
                Dream d=new Dream(c.getString(c.getColumnIndex("po_name")),c.getString(c.getColumnIndex("po_description")).length()>60 ? c.getString(c.getColumnIndex("po_description")).substring(0,60)+"...":c.getString(c.getColumnIndex("po_description"))+"..." ,c.getString(c.getColumnIndex("po_description")),c.getInt(c.getColumnIndex("_poid")),c.getInt(c.getColumnIndex("po_category")));
                temp.add(d);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
            Toast.makeText(mContext,"err"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        return temp;
    }

    public List<Dream> getFavDreams(){
        List<Dream> temp =new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("select * from posts where po_favorite ="+1,null);
            if (c==null)return  null ;
            c.moveToFirst();
            do {
                Dream d=new Dream(c.getString(c.getColumnIndex("po_name")),c.getString(c.getColumnIndex("po_description")).length()>60 ? c.getString(c.getColumnIndex("po_description")).substring(0,60)+"...":c.getString(c.getColumnIndex("po_description"))+"..." ,c.getString(c.getColumnIndex("po_description")),c.getInt(c.getColumnIndex("_poid")),c.getInt(c.getColumnIndex("po_category")));
                temp.add(d);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
          //  Toast.makeText(mContext,"err"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        return temp;
    }
    public boolean isFavorite(int id){
        boolean temp=false ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("select * from posts where _poid="+id,null);
            if (c==null)return false ;
            c.moveToFirst();
            if (c.getInt(c.getColumnIndex("po_favorite"))==1)temp=true;
             else temp=false;

        }catch (Exception e){
            Toast.makeText(mContext,"err"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        return temp;
    }

    public List<Category> getAllCategory(){
        List<Category> temp =new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c,cc;
        try{
            c=db.rawQuery("select * from categories ",null);

            if (c==null)return  null ;
            c.moveToFirst();

            do {
                cc=db.rawQuery(" select count(*) as count from posts where po_category="+c.getInt(c.getColumnIndex("_caid")),null);
                cc.moveToFirst();

                int count =cc.getInt(cc.getColumnIndex("count"));
                Category d=new Category(" "+ c.getString(c.getColumnIndex("ca_name"))  ,c.getInt(c.getColumnIndex("_caid")),c.getString(c.getColumnIndex("ca_image")) );
                temp.add(d);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
            Toast.makeText(mContext,"err"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        return temp;
    }

    public List<Dream> getAllDreams(){
        List<Dream> temp =new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("select * from posts",null);
            if (c==null)return  null ;
            c.moveToFirst();
            do {
                Dream d=new Dream(c.getString(c.getColumnIndex("po_name")),c.getString(c.getColumnIndex("po_description")).length()>60 ? c.getString(c.getColumnIndex("po_description")).substring(0,60)+"...":c.getString(c.getColumnIndex("po_description"))+"..." ,c.getString(c.getColumnIndex("po_description")),c.getInt(c.getColumnIndex("_poid")),c.getInt(c.getColumnIndex("po_category")));
                temp.add(d);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){
            Toast.makeText(mContext,"err"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        return temp;
    }


    public boolean updateHandler(int ID ,int a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("po_favorite", a);
        boolean temp= db.update("posts", args, "_poid" + "=" + ID, null) > 0;
      //  Toast.makeText(mContext, ""+ID, Toast.LENGTH_SHORT).show();
        db.close();

        return temp;
    }
}

