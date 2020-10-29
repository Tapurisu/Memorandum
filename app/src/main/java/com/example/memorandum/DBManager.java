package com.example.memorandum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    //实现向数据库插入操作
    public void add(MemoItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("memoName", item.getMemoName());
        values.put("memoContent", item.getMemoContent());
        values.put("creationTime", item.getCreationTime());
        values.put("lastModificationTime", item.getLastModificationTime());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<MemoItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(MemoItem item : list){
            ContentValues values = new ContentValues();
            values.put("id", item.getId());
            values.put("memoName", item.getMemoName());
            values.put("memoContent", item.getMemoContent());
            values.put("creationTime", item.getCreationTime());
            values.put("lastModificationTime", item.getLastModificationTime());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    //实现按照id删除记录的操作
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    //修改数据库记录
    public void update(MemoItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("memoName", item.getMemoName());
        values.put("memoContent", item.getMemoContent());
        values.put("creationTime", item.getCreationTime());
        values.put("lastModificationTime", item.getLastModificationTime());
        db.update(TBNAME, values, "id=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    //获取数据库中的全部记录，存放于表中
    public List<MemoItem> listAll(){
        List<MemoItem> memolist = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null,null,null,null, null, null);
        if(cursor!=null){
            memolist = new ArrayList<MemoItem>();
            while(cursor.moveToNext()){
                MemoItem item = new MemoItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setMemoName(cursor.getString(cursor.getColumnIndex("MEMONAME")));
                item.setMemoContent(cursor.getString(cursor.getColumnIndex("MEMOCONTENT")));
                item.setLastModificationTime(cursor.getString(cursor.getColumnIndex("LASTMODIFICATIONTIME")));
                item.setCreationTime(cursor.getString(cursor.getColumnIndex("CREATIONTIME")));

                memolist.add(item);
            }
            cursor.close();
        }
        db.close();
        return memolist;
    }

    public MemoItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null,"id=?", new String[]{String.valueOf(id)}, null, null, null);
        MemoItem item = new MemoItem();
        if(cursor!=null && cursor.moveToFirst()){
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setMemoName(cursor.getString(cursor.getColumnIndex("MEMONAME")));
            item.setMemoContent(cursor.getString(cursor.getColumnIndex("MEMOCONTENT")));
            item.setLastModificationTime(cursor.getString(cursor.getColumnIndex("LASTMODIFICATIONTIME")));
            item.setCreationTime(cursor.getString(cursor.getColumnIndex("CREATIONTIME")));
            cursor.close();
        }
        db.close();
        return item;
    }
}
