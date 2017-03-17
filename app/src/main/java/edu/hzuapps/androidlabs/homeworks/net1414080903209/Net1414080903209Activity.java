package edu.hzuapps.androidlabs.homeworks.net1414080903209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public  class Net1414080903209Activity extends AppCompatActivity implements View.OnClickListener {

    private String path;
    private Button btn_start;
    private EditText et_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtask);
        btn_start= (Button) findViewById(R.id.btn_start);
        et_path= (EditText) findViewById(R.id.et_path);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_start:{
                path=et_path.getText().toString().trim();
                if(path.equals("")) {
                    Toast.makeText(Net1414080903209Activity.this, "网址不能为空", Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(Net1414080903209Activity.this, "你输入的网址是" + path, Toast.LENGTH_LONG).show();
                    Log.i("System.out", path);

                    //跳转至主界面  传给路径
                    Intent intent = new Intent();
                    intent.setClass(Net1414080903209Activity.this,MainActivity.class);
                    intent.putExtra("addProgressbar",true);
                    intent.putExtra("path",path);
                    startActivity(intent);
                    Net1414080903209Activity.this.finish();
                }
            }break;
        }


    }


}
