package com.example.jamespotatao.controlapp;

import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Main1Activity extends ActivityGroup implements View.OnClickListener {
    private static final String TAG = "QQChatActivity";
    private Bundle mBundle = new Bundle();
    private LinearLayout ll_container, ll_first, ll_second, ll_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ll_container = findViewById(R.id.ll_container);
        ll_first = findViewById(R.id.ll_first);
        ll_second = findViewById(R.id.ll_second);
        ll_third = findViewById(R.id.ll_third);
        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        mBundle.putString("tag",TAG);
        changeCotainerView(ll_third);
        changeCotainerView(ll_second);
        changeCotainerView(ll_first);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.ll_first || v.getId() == R.id.ll_second || v.getId() == R.id.ll_third){
            changeCotainerView(v);
        }
    }

    private void changeCotainerView(View v){
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        v.setSelected(true);
        if (v == ll_first){
            toActivity("first",SocketActivity.class);
        }
        if (v == ll_second){
            toActivity("second",MessageActivity.class);
        }
        if (v == ll_third){
            toActivity("third",SpectraActivity.class);
        }
    }

    private void toActivity(String label, Class<?> cls){
       Intent intent = new Intent(this, cls).putExtras(mBundle);
       ll_container.removeAllViews();
       View v = getLocalActivityManager().startActivity(label, intent).getDecorView();
       v.setLayoutParams(new LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
       ll_container.addView(v);
    }

    /**　　假设这个容器是一个LinearLayout，id是linearContainer,  Activity中获取视图：
     　　　　　　　　　　LinearLayout linearContainer= (LinearLayout) findViewById(R.id.container);
     　　　　　　　　　　linearContainer.dispatchCreate(savedInstanceState);

     　　实例化LocalActivityManager：
     　　　　　　　　　　LocalActivityManager local= getLocalActivityManager();

     最后把OtherActivity塞进linearContainer里面：
     　　　　　　　　　　linearContainer.addView((local.startActivity("OtherActivityView", new Intent(this, OtherActivity.class))).getDecorView());**/


}























