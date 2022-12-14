package com.example.bottomtesttwo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.bottomtesttwo.R;
import com.example.bottomtesttwo.fragments.Fragment1;
import com.example.bottomtesttwo.fragments.Fragment2;
import com.example.bottomtesttwo.fragments.Fragment3;
import com.example.bottomtesttwo.fragments.Fragment4;
import com.example.bottomtesttwo.fragments.fragment1.Calculator11Update;
import com.example.bottomtesttwo.fragments.fragment1.Calculator1_1;
import com.example.bottomtesttwo.fragments.fragment1.Calculator1_2;
import com.example.bottomtesttwo.fragments.fragment2.AddTarget;
import com.example.bottomtesttwo.fragments.fragment2.Calculator2;
import com.example.bottomtesttwo.fragments.fragment2.TargetListActivity;
import com.example.bottomtesttwo.fragments.fragment3.Calculator;
import com.example.bottomtesttwo.activity.login.LoginActivity;
import com.example.bottomtesttwo.fragments.fragment4.Frag4List_Personal;
import com.example.bottomtesttwo.fragments.fragment4.HeadImage;
import com.example.bottomtesttwo.serverd.DBOperator;
import com.example.bottomtesttwo.util.StatusBar.StatusBarUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {

    private BottomNavigationView mBottomNavigationView;//?????????????????????????????????
    private MenuItem mMenuItem;//????????????????????????????????????????????????
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    public FloatingActionButton addBottom;

    public static final int TAKE_PHOTO = 100;
    public static final int CHOOSE_PHOTO = 200;
    private ImageView picture;
    private Uri imageUri;

    public static MainActivity instance = null;

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    DBOperator dbOperator = DBOperator.getOperator();
    Cursor cursor;
    private int id ;

    private boolean loginOlineState = false;
//    private Fragment1 fragment1 = new Fragment1();
//    private Fragment2 fragment2 = new Fragment2();
//    private Fragment3 fragment3 = new Fragment3();
//    private Fragment4 fragment4 = new Fragment4();
//    List<Fragment> fragments = new



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //LitePal.getDatabase();
        //??????????????????(????????????????????????????????????????????????)
        getSupportActionBar().hide();
        //?????????????????????????????????????????????MainActivity???????????????????????????
        //isPadding-??????????????????????????????:true=??????false=fou;
        //dark:true=????????????  false=??????
        setStatusBar(this,false, true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new Fragment1());//????????????fragment1??????

        instance = this;
//        LoginActivity.instance.finish();


        //?????????????????????
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        //??????????????????
        addBottom = (FloatingActionButton)findViewById(R.id.float_add);;

        addBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMenuItem == null){
                    Fragment1 fragment1Temp = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    if(fragment1Temp.isFrag1FirstChange()){
                        Intent intent11 = new Intent(MainActivity.this, Calculator1_1.class);
                        startActivityForResult(intent11,1);
                    }else {
                        Intent intent12 = new Intent(MainActivity.this, Calculator1_2.class);
                        startActivityForResult(intent12,21);
                    }
                }else {
                    switch (mMenuItem.getItemId()){
                        case R.id.item_tab1:
                            Fragment1 fragment1Temp = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                            if(fragment1Temp.isFrag1FirstChange()){
                                Intent intent11 = new Intent(MainActivity.this, Calculator1_1.class);
                                startActivityForResult(intent11,1);
                            }else {
                                Intent intent12 = new Intent(MainActivity.this, Calculator1_2.class);
                                startActivityForResult(intent12,1);
                            }
                            break;
                        case R.id.item_tab2:
                            Intent intent2 = new Intent(MainActivity.this, Calculator2.class);
                            startActivityForResult(intent2,2);
                            break;
                        case R.id.item_tab3:
                            Intent intent3 =new Intent(MainActivity.this, Calculator.class);
                            startActivityForResult(intent3,3);
                            break;
                    }
                }
            }
        });

        //?????????????????????????????????
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mMenuItem = menuItem;
                switch (menuItem.getItemId()) {

                    case R.id.item_tab1://???????????????????????????????????????
                        replaceFragment(fragment1);
                        setStatusBar(MainActivity.this,false, true);
                        addBottom.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.item_tab2://???????????????????????????????????????
                        replaceFragment(fragment2);
                        setStatusBar(MainActivity.this,false, false);
                        addBottom.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.item_tab3://???????????????????????????????????????
                        replaceFragment(fragment3);
                        setStatusBar(MainActivity.this,false, false);
                        addBottom.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.item_tab4://????????????????????????????????????
                        replaceFragment(fragment4);
                        setStatusBar(MainActivity.this,false, false);
                        addBottom.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });

    }

    //??????fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bolck_fragmelayout,fragment);
        transaction.commit();
    }

    //????????????????????????????????????????????????util?????????StatusBar?????????
    //activity????????????????????????
    // isPadding????????????????????????????????????padding???true?????????false?????????(????????????????????????????????????????????????????????????)
    // dark??????????????????true=????????????  false=??????
    public static void setStatusBar(Activity activity, boolean isPadding, boolean dark){
        //???FitsSystemWindows?????? true ?????????????????????????????????????????????????????? padding
        StatusBarUtil.setRootViewFitsSystemWindows(activity,isPadding);
        //?????????????????????????????????????????????????????????
        StatusBarUtil.setTranslucentStatus(activity);
        //true=????????????  false=??????
        StatusBarUtil.setStatusBarDarkTheme(activity, dark);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Log.d("ZXYBACK","case 1");
                if(resultCode == RESULT_OK){
                    Log.d("ZXYBACK","case 1 OK");
                    Fragment1 fragment1Temp = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    fragment1Temp.initItem1List();
                    Toast.makeText(this,"you did it",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Log.d("ZXYBACK","case 1 OK");
                    Fragment2 fragment2Temp = (Fragment2)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    fragment2Temp.init();
//                    Toast.makeText(this,"you did it",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    Fragment3 fragment3Temp = (Fragment3)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    fragment3Temp.initFrag3Item();
                }
                break;
            case 20:
                if(resultCode == RESULT_OK){
                    Fragment1 fragment1Temp = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    fragment1Temp.initItem1List();
                }
                break;
            case 21:
                if(resultCode == RESULT_OK){
                    Fragment1 fragment1Temp = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                    fragment1Temp.initItem2List();
                }
                break;
            case 33:

//                Toast.makeText(this,"33",Toast.LENGTH_SHORT).show();
                break;

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // ??????????????????????????????
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:

                break;

            case 666:
                Fragment4 fragment4Temp = (Fragment4)getSupportFragmentManager().findFragmentById(R.id.bolck_fragmelayout);
                fragment4Temp.loadImage();
                break;
            default:
                break;
        }
    }


    private boolean isAddNew = true;
//    Fragment3 fragment3Control = (Fragment3)getSupportFragmentManager().findFragmentById(R.id.float_add);
    public void addItem(String tip,int imageId,double number,long date){
        fragment3.addFrag3Item(tip,imageId,number,date);
    }

    private void login(){
        if(loginOlineState == false){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        textDate.setText(String.format("%d???%d???",year,month+1));
        fragment3.textDate.setText(String.format("%d???%d???",year,month+1));
        fragment3.frag3Date = String.format("%02d%02d00",year%100,(month+1)%100);
//        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();
        fragment3.initFrag3Item();
    }

    public void calculator2_onClick1(View view){
        switch (view.getId()){
            case R.id.frag2_2_1:
                Intent intent221 = new Intent(this, TargetListActivity.class);
                startActivityForResult(intent221,2);
                break;
            case R.id.frag2_2_2:
                Intent intent222 = new Intent(this, AddTarget.class);
                startActivityForResult(intent222,33);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag4_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(null);//????????????, ??????????????????
                builder.setTitle("????????????");
                builder.setMessage("??????????????????????????????");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1){
                        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        prefEditor = pref.edit();
                        prefEditor.putString("id","0");
                        prefEditor.putString("email","0");
                        prefEditor.putString("password","0");
                        prefEditor.apply();

                        Intent outIntent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(outIntent);
                        finish();
                        Toast.makeText(MainActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0,int arg1){

                    }
                });
                AlertDialog b = builder.create();
                b.show();//???????????????
                break;
            case R.id.frag4_user_info:
//                Intent userinfoIntent = new Intent(this, Frag4List_Personal.class);
//                startActivity(userinfoIntent);
                break;
            case R.id.second_head:
//                if(ContextCompat.checkSelfPermission(MainActivity.instance, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.instance,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else {
//                    openAlbum();
//                }
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
//                } else {
//                    openAlbum();
//                }
                Intent intentHead = new Intent(this, HeadImage.class);
                startActivityForResult(intentHead,666);
                break;
        }
    }



}