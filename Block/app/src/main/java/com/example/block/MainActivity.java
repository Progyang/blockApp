package com.example.block;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HashMap<String, String> priceMap;
    private ImageView mImageView;
    private Spinner mLocationSpinner;
    private TextView mPrice;
    private TextView mSerial;

    private Calendar cal;
    private int year, month, day;
    private String [] data;
    /**
     * 确认购买
     */
    private Button mConformButton;

    /**
     * TextView
     */
    private TextView mDateText;
    /**
     * 选择
     */
    private Button mDatebutton;

    //初始化数据
    private void loadMap() {
        priceMap = new HashMap<String, String>();
        priceMap.put("富士山", "8000");
        priceMap.put("新加坡", "10000");
        priceMap.put("马尔代夫", "15000");
        priceMap.put("巴黎", "20000");
        priceMap.put("夏威夷", "15000");
    }

    //随机获取单号
    private String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMap();
        String serialno = new String();
        serialno = getRandomString(10);
        setContentView(R.layout.activity_main);
        initView();
        getDate();
        mSerial.setText(serialno);
        //监听下拉菜单,根据菜单改变价格和图片
        mLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrice.setText(priceMap.get(mLocationSpinner.getSelectedItem().toString()));
                switch (position) {
                    case 0:
                        mImageView.setImageResource(R.drawable.one);
                        break;
                    case 1:
                        mImageView.setImageResource(R.drawable.two);
                        break;
                    case 2:
                        mImageView.setImageResource(R.drawable.three);
                        break;
                    case 3:
                        mImageView.setImageResource(R.drawable.four);
                        break;
                    case 4:
                        mImageView.setImageResource(R.drawable.five);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPrice.setText("");
            }
        });

    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy", "year" + year);
        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
        mDateText.setText(year + "-" + (++month) + "-" + day);
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mLocationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        mPrice = (TextView) findViewById(R.id.price);
        mSerial = (TextView) findViewById(R.id.serial);
        mConformButton = (Button) findViewById(R.id.conformButton);
        mConformButton.setOnClickListener(this);
        mDateText = (TextView) findViewById(R.id.dateText);
        mDatebutton = (Button) findViewById(R.id.datebutton);
        mDatebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.conformButton://确认购买旅行产品按钮
                data = new String[3];
                data[0]=mLocationSpinner.getSelectedItem().toString();
                data[1]=mPrice.getText().toString();
                data[2]=mSerial.getText().toString();
                //将数据传送到界面2
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("data",data);
                startActivity(intent);
                break;
            case R.id.datebutton://日期选择
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        mDateText.setText(year + "-" + (++month) + "-" + day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
        }
    }
}
