package com.xian.scooter.module.stores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xian.scooter.R;

public class MembersActivity extends AppCompatActivity {

    private String[] data = {"Sub1","Sub2","Sub3","Sub4","Sub5","Sub6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

    }
}
