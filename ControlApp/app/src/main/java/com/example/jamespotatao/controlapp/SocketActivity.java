package com.example.jamespotatao.controlapp;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditText;
    private TextView mTectView;
    private static final String TAG = "TAG";
    private EditText mip;
    private static final int PORT = 9999;
    private PrintWriter printWriter1;
    private BufferedReader in1;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;
    private EditText speed1;
    private EditText speed11x;
    private EditText speed2;
    private EditText speed22x;
    private EditText speed3;
    private EditText speed33x;
    private EditText speed0;
    private EditText speed00x;
    private EditText height0,height1,height2,height3, height4, height00x,height11x,height22x,height33x;
    private EditText Magnet;
    private EditText ligint;
    private EditText cirtime1;
    private EditText cirtime2;
    private EditText cirtime3;
    private EditText cirtime0;
    private EditText Temp;
    public static final String ACTION_INTENT_TEST = "com.example.jamespotato.controlapp";
    private boolean pump0_oc;
    private boolean pump1_oc;
    private boolean pump2_oc;
    private boolean pump3_oc;
    private boolean currentpump0=false;
    private boolean currentpump1=false,currentpump2=false,currentpump3=false;;;
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private Switch switch0;
    private TextView txspeed0;
    private TextView txrecy0;
    private boolean texspopen = false;
    private boolean texreopen = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        mEditText = findViewById(R.id.editText_order);
        mTectView = findViewById(R.id.textView_content);
        mip = findViewById(R.id.editText_ip);
        mExecutorService = Executors.newCachedThreadPool();

        speed1 = findViewById(R.id.edit_speed1);
        speed2 = findViewById(R.id.edit_speed2);
        speed3 = findViewById(R.id.edit_speed3);
        speed0 = findViewById(R.id.edit_speed0);
        speed00x = findViewById(R.id.edit_speed00x);
        speed11x = findViewById(R.id.edit_speed11x);
        speed22x = findViewById(R.id.edit_speed22x);
        speed33x = findViewById(R.id.edit_speed33x);

        height0 = findViewById(R.id.height0);
        height1 = findViewById(R.id.height1);
        height2 = findViewById(R.id.height2);
        height3 = findViewById(R.id.height3);
        height00x = findViewById(R.id.height00x);
        height11x = findViewById(R.id.height11x);
        height22x = findViewById(R.id.height22x);
        height33x = findViewById(R.id.height33x);

        Magnet = findViewById(R.id.edit_mag);
        ligint = findViewById(R.id.edit_light);
        Temp = findViewById(R.id.edit_temper);

        cirtime1 = findViewById(R.id.edit_recytime1);
        cirtime2 = findViewById(R.id.edit_recytime2);
        cirtime3 = findViewById(R.id.edit_recytime3);
        cirtime0 = findViewById(R.id.edit_recytime0);

        findViewById(R.id.button_connect).setOnClickListener(this);
        findViewById(R.id.button_disconnect).setOnClickListener(this);
        findViewById(R.id.button_send).setOnClickListener(this);
        findViewById(R.id.Tex_speed0).setOnClickListener(this);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        switch0 = findViewById(R.id.switch0);

        txspeed0 = findViewById(R.id.Tex_speed0);


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pump1_oc = true;

                }
                else{
                    pump1_oc = false;

                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pump2_oc = true;

                }
                else{
                    pump2_oc = false;
;
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pump3_oc = true;

                }
                else{
                    pump3_oc = false;

                }
            }
        });

        switch0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pump0_oc = true;

                }
                else{
                    pump0_oc = false;

                }
            }
        });


    }

    public void connect(View view){
        mExecutorService.execute(new connectService());
    }

    public void send(View view){

        String S000 = "";
        String S111 = "";
        String S222 = "";
        String S333 = "";

        if (mip.getText().toString().length()==0){
            Toast.makeText(SocketActivity.this,"请先输入有用的ip地址并connect",Toast.LENGTH_SHORT).show();
            return;
        }


        if (pump0_oc == true){
            if (speed0.getText().length()==0 || cirtime0.getText().length()==0 ||speed00x.getText().length()==0||height0.getText().length()==0||
                    height00x.getText().length()==0){
                S000 = "11ZR\n";
            }
            else{
                S000 = "11gV"+speed0.getText()+"IA"+height0.getText()+"OV"+speed00x.getText()+"A"+height00x.getText()+"G"+cirtime0.getText()+"R\n";
            }
        }

        if (pump0_oc == false){
            if (currentpump0 == true){
                S000 = "11T\n";
            }
        }

        if (pump1_oc == true){
            if (speed1.getText().length()==0 || cirtime1.getText().length()==0||speed11x.getText().length()==0||height1.getText().length()==0||
                    height11x.getText().length()==0){
                S111 = "21ZR\n";
            }
            else{
                S111 = "21gV"+speed1.getText()+"IA"+height1.getText()+"OV"+speed11x.getText()+"A"+height11x.getText()+"G"+cirtime1.getText()+"R\n";
            }
        }

        if (pump1_oc == false){
            if (currentpump1 == true){
                S111 = "21T\n";
            }
        }

        if (pump2_oc == true){
            if (speed2.getText().length()==0 || cirtime2.getText().length()==0||speed22x.getText().length()==0||height2.getText().length()==0||
                    height22x.getText().length()==0){
                S222 = "31ZR\n";
            }
            else{
                S222 = "31gV"+speed2.getText()+"IA"+height2.getText()+"OV"+speed22x.getText()+"A"+height22x.getText()+"G"+cirtime2.getText()+"R\n";
            }
        }

        if (pump2_oc == false){
            if (currentpump2 == true){
                S222 = "31T\n";
            }
        }

        if (pump3_oc == true){
            if (speed3.getText().length()==0 || cirtime3.getText().length()==0||speed33x.getText().length()==0||height3.getText().length()==0||
                    height33x.getText().length()==0){
                S333 = "41ZR\n";
            }
            else{
                S333 = "31gV"+speed3.getText()+"IA"+height3.getText()+"OV"+speed33x.getText()+"A"+height33x.getText()+"G"+cirtime3.getText()+"R\n";
            }
        }

        if (pump0_oc == false){
            if (currentpump0 == true){
                S333 = "41T\n";
            }
        }

        String sendMsg;

        sendMsg = S000+ S111+ S222+S333;
        mExecutorService.execute(new sendService(sendMsg));
        currentpump0 = pump0_oc;
        currentpump1 = pump1_oc;
        currentpump2 = pump2_oc;
        currentpump3 = pump3_oc;
    }


    public void disconnect(View view){
        mExecutorService.execute(new sendService("0"));
    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.button_send){
            send(v);
        }
        if (v.getId()==R.id.button_connect){
            connect(v);
        }
        if (v.getId()==R.id.button_disconnect){
            disconnect(v);
        }

        if (v.getId() == R.id.Tex_speed0){
            if (texspopen){
                txspeed0.setText("推荐值>");
                texspopen = false;
            }
            else{
                txspeed0.setText("1.实验常规操作 抽取速度=释放速度=2000，上升高度=3000，下降高度=0，循环次数=5"
                +"\n"+"2.开关说明，若不输入所有参数而调整泵开关，则视为对泵进行初始化或停止"+"\n"+"3.速度建议：慢速500，常速2000，快速3500，最大值5000"
                +"\n3.高度建议：试实验具体要求而定，最大值为5000"+"\n4.循环次数建议： 无限循环：0   循环一次：1  常规操作：5   最大值:5000"+"\n    再次点击收起"+"\n");
                texspopen = true;
            }
        }

    }




    private class sendService implements Runnable{

        private String msg;

        sendService(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            printWriter1.println(this.msg);
        }
    }

    private class connectService implements  Runnable{
        private String HOST1 =  mip.getText().toString();

        @Override
        public void run() {
            try {
                Socket socket = new Socket(HOST1,PORT);
                socket.setSoTimeout(600000);
                printWriter1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")),true);
                in1 = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                receiveMsg();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void receiveMsg(){


        try {
            ArrayList arrx = new ArrayList();
            ArrayList arry = new ArrayList();
            String desc = " ";
            while ((receiveMsg = in1.readLine()) != null) {

                if (receiveMsg.startsWith("*")) {
                    //Log.d(TAG, "receiveMsg:" + receiveMsg);
                    desc = String.format("%s \n %s", desc, receiveMsg);;
                }
                else if (receiveMsg.startsWith(">")){
                    Intent intent = new Intent(ACTION_INTENT_TEST);
                    intent.putExtra("shift",desc);
                    int x_size = arrx.size();
                    String[] arx = (String[])arrx.toArray(new String[x_size]);
                    intent.putExtra("x_value",arx);
                    int y_size = arry.size();
                    String[] ary = (String[])arry.toArray(new String[y_size]);
                    intent.putExtra("y_value",ary);
                    sendBroadcast(intent);
                    arrx.clear();
                    arry.clear();
                }
                else{
                    String[] str = receiveMsg.split("\t");
                    arrx.add(str[0]);
                    arry.add(str[1]);

                }

            }



        } catch (IOException e) {
            Log.e(TAG,"receiveMsg:");
            e.printStackTrace();

        }



    }
}
