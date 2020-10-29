package com.example.memorandum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoAdictor extends AppCompatActivity {
    private final String TAG = "MemoAdictor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_adictor);

        Intent choosedMemo = getIntent();

        EditText memoNameEditText = findViewById(R.id.memoNameEditText), memoContentEditText = findViewById(R.id.memoContentEditText);
        TextView lastModificationTime = findViewById(R.id.LMTTextView);
        memoNameEditText.setText(choosedMemo.getStringExtra("memoName"));
        memoContentEditText.setText(choosedMemo.getStringExtra("memoContent"));
        lastModificationTime.setText(choosedMemo.getStringExtra("lastModificationTime"));
    }

    public void starMemo(View view) {

    }

    public void deleteMemo(View view) {

    }

    public void saveMemo(View view) {

    }
}