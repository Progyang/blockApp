package com.example.block;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {


    private TextView mGoodsName;
    private TextView mSerialNo;
    private TextView mPrice;
    private TextView mDayBetween;
    private TextView mTotalCash;

    private String[] datafrom1;
    private String[] datafrom2;

    /**
     * 确认付款
     */
    private Button mOrderbutton;
    private TextView mInsurePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        //接收从1,2传送来的数据,并形成账单
        Intent i = getIntent();
        datafrom1 = new String[3];
        datafrom2 = new String[2];
        datafrom1 = i.getStringArrayExtra("datafrom1");
        datafrom2 = i.getStringArrayExtra("datafrom2");
        mGoodsName.setText(datafrom1[0] + "旅游项目");
        mPrice.setText(datafrom1[1]);
        mSerialNo.setText(datafrom1[2]);
        mDayBetween.setText(datafrom2[0]);
        double totalCash = 0;
        totalCash = Double.valueOf(datafrom2[0]) * Double.valueOf(datafrom2[1]);
        mInsurePrice.setText(String.valueOf(totalCash));
        totalCash += Double.valueOf(datafrom1[1]);
        mTotalCash.setText(String.valueOf(totalCash));
    }

    private void initView() {
        mGoodsName = (TextView) findViewById(R.id.goodsName);
        mSerialNo = (TextView) findViewById(R.id.serialNo);
        mPrice = (TextView) findViewById(R.id.price);
        mDayBetween = (TextView) findViewById(R.id.dayBetween);
        mTotalCash = (TextView) findViewById(R.id.totalCash);
        mOrderbutton = (Button) findViewById(R.id.orderbutton);
        mOrderbutton.setOnClickListener(this);
        mInsurePrice = (TextView) findViewById(R.id.insurePrice);
    }

    private void translation(String host,int port) throws IOException {
        Socket socket=new Socket(host,port);
        ObjectOutputStream trans = new ObjectOutputStream(socket.getOutputStream());
        trans.writeObject(datafrom2);
        trans.flush();
        trans.close();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.orderbutton:
                //此处应向服务器传值,如有需要可添加传输的数据,如来自界面2的用户信息
                String host="192.168.191.1";
                int port=9091;
                try {
                    translation(host,port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
