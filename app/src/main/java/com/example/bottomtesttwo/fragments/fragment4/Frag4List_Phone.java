package com.example.bottomtesttwo.fragments.fragment4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bottomtesttwo.R;
import com.example.bottomtesttwo.serverd.DBOperator;

public class Frag4List_Phone extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button button;
    Button button2;
    Button button3;
    EditText editText;
    LinearLayout layout;
    TextView textView1;
    TextView textView2;
    DBOperator dbOperator = DBOperator.getOperator();
    Cursor cursor = dbOperator.Query( "select * from user_info");
    private int id;


    private boolean playOut = false;
    private boolean have = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag4_list__phone);
        getSupportActionBar().hide();
        imageView = (ImageView)findViewById(R.id.frag4_list_phone_back);

        button = (Button)findViewById(R.id.frag4_list_phone_btn);
        editText = (EditText)findViewById(R.id.frag4_list_phone_edit1);
        layout = (LinearLayout)findViewById(R.id.frag4_list_phone_ll);
        textView1 = (TextView)findViewById(R.id.phone_bind_1);
        textView2 = (TextView)findViewById(R.id.phone_bind_2);
        button2 = (Button)findViewById(R.id.login_btn_login);
        button3 = (Button)findViewById(R.id.frag4_list_phone_cancle);

        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);


        cursor.moveToFirst();
        id = cursor.getInt(cursor.getColumnIndex("id"));
        if(cursor.getString(cursor.getColumnIndex("phoneNumber")).equals("")){
            Log.d("ZXY","???");
            notBind();
        }else {

            initBind(cursor.getString(cursor.getColumnIndex("phoneNumber")));
        }
        cursor.close();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag4_list_phone_btn:
                btnChange();
                break;
            case R.id.login_btn_login:
                updatePhoneNumber();
                break;
            case R.id.frag4_list_phone_back:
                finish();
                break;
            case R.id.frag4_list_phone_cancle:
                btnChange();
                break;
        }
    }

    private void btnChange(){
        if(playOut == false){
            button.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            playOut = true;
        }else if(playOut == true){
            button.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            playOut = false;
        }
    }

    private void notBind(){
            textView1.setText("???????????????????????????");
            textView2.setVisibility(View.GONE);
            button.setText("??????");
    }

    private void initBind(String phoneNumber){
        textView2.setVisibility(View.VISIBLE);
        textView2.setText(phoneNumber);
        button.setText("??????");
        textView1.setText("????????????????????????");
    }

    private void updatePhoneNumber(){
        textView2.setVisibility(View.VISIBLE);
        textView2.setText(editText.getText().toString());
        dbOperator.Cud("update user_info set phoneNumber='"+editText.getText().toString()+"' where id='"+id+"'");
        button.setText("??????");
        textView1.setText("????????????????????????");
        btnChange();
    }

}
