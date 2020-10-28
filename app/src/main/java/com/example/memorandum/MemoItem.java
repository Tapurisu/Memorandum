package com.example.memorandum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoItem {
    private int id;
    private String memoName;
    private String memoContent;
    private String creationTime;
    private String lastModificationTime;

    public MemoItem(){
        //创建备忘录时，默认标题为"新的备忘录"，内容为空，创建时间自动给定
        super();

        memoName = "新的备忘录";
        memoContent = "";
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        creationTime = formatter.format(new Date(System.currentTimeMillis()));
        lastModificationTime = "";
    }

    public MemoItem(int id){
        //传入参数id创建备忘录
        super();
        this.id = id;
        memoName = "新的备忘录";
        memoContent = "";
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        creationTime = formatter.format(new Date(System.currentTimeMillis()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

}
