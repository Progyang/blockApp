package com.example.block;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    private LinearLayout mLl;
    private int TextViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
    }

    private void initView() {
        mLl = (LinearLayout) findViewById(R.id.ll);
    }

    private void addV(){
        LinearLayout nll = new LinearLayout(this);
        LinearLayout.LayoutParams Lsize = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
    }
}
