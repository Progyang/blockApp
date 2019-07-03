package com.example.block;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 20
     */
    private TextView mPrice;
    private TextView mStartdate;

    private Calendar cal;
    private int year, month, day;
    private String Sdate, Edate;
    private String[] datafrom1;
    private String[] datato3;
    /**
     * 选择
     */
    private Button mStartbutton;
    private TextView mEnddate;
    /**
     * 选择
     */
    private Button mEndbutton;
    private TextView mTotal;
    /**
     * 购买保险
     */
    private Button mBuybutton;
    /**
     * First Name
     */
    private EditText mFirstnameText;
    /**
     * Last Name
     */
    private EditText mLastnameText;
    private EditText mEMailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        getDate();
        //接收从界面1传输来的数据
        Intent i = getIntent();
        datafrom1 = new String[3];
        datafrom1 = i.getStringArrayExtra("data");
    }

    private void initView() {
        mPrice = (TextView) findViewById(R.id.price);
        mStartdate = (TextView) findViewById(R.id.startdate);
        mStartbutton = (Button) findViewById(R.id.startbutton);
        mStartbutton.setOnClickListener(this);
        mEnddate = (TextView) findViewById(R.id.enddate);
        mEndbutton = (Button) findViewById(R.id.endbutton);
        mEndbutton.setOnClickListener(this);
        mTotal = (TextView) findViewById(R.id.total);
        mBuybutton = (Button) findViewById(R.id.buybutton);
        mBuybutton.setOnClickListener(this);
        mFirstnameText = (EditText) findViewById(R.id.firstnameText);
        mLastnameText = (EditText) findViewById(R.id.lastnameText);
        mEMailText = (EditText) findViewById(R.id.E_mailText);
    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日
        month = cal.get(Calendar.MONTH);     //月份从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
        mStartdate.setText(year + "-" + (++month) + "-" + day);
        mEnddate.setText(year + "-" + month + "-" + day);
        Sdate = changeTostring(year, month, day);
        Edate = changeTostring(year, month, day);
        mTotal.setText(String.valueOf(dayBetween()));
    }
    //为间隔天数计算做准备,将日期转换为字符串
    private String changeTostring(int y, int m, int d) {
        String sy = Integer.toString(y);
        String sm = new String();
        String sd = new String();
        if (m <= 9) {
            sm = "0" + Integer.toString(m);
        } else {
            sm = Integer.toString(m);
        }
        if (d <= 9) {
            sd = "0" + Integer.toString(d);
        } else {
            sd = Integer.toString(d);
        }
        return sy + sm + sd;
    }

    //间隔天数计算
    private double dayBetween() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(Sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(Edate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
        //System.out.println("\n相差" + dayCount + "天");
        return dayCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.startbutton://设置结束日期
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        int mm = 0;
                        mm = month + 1;
                        Sdate = changeTostring(year, mm, day);
                        mStartdate.setText(year + "-" + mm + "-" + day);
                        mTotal.setText(String.valueOf(dayBetween()));
                        //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(Main2Activity.this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
            case R.id.endbutton://设置开始日期
                DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        int mmm = 0;
                        mmm = month + 1;
                        Edate = changeTostring(year, mmm, day);
                        mEnddate.setText(year + "-" + mmm + "-" + day);
                        mTotal.setText(String.valueOf(dayBetween()));
                        //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog2 = new DatePickerDialog(Main2Activity.this, DatePickerDialog.THEME_HOLO_LIGHT, listener2, year, month, day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog2.show();
                break;
            case R.id.buybutton://购买保险按钮，点击后调用界面3
                //如果持续天数小于等于零,或者姓名邮箱为空,则点不动
                if(Double.valueOf((mTotal.getText()).toString())<=0||mFirstnameText.length()==0||mLastnameText.length()==0||mEMailText.length()==0){
                    break;
                }
                datato3 = new String[2];
                datato3[0] = mTotal.getText().toString();
                datato3[1] = mPrice.getText().toString();
                //将需要的数据发送至第三个界面
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("datafrom1", datafrom1);
                intent.putExtra("datafrom2", datato3);
                startActivity(intent);
                break;
        }
    }
}
