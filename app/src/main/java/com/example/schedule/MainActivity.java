package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<RecyclerViewItem> mList;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    //장비목록 Spinner
    private Spinner spinner;
    private TextView tv_result;
    //재생날짜 Calendar
    private TextView start_dt;
    private TextView end_dt;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    //재생시간
    private TextView start_time;
    private TextView end_time;
    private TimePickerDialog.OnTimeSetListener time_callbackMethod;

    //시작&종료 날짜,시간 선택
    public void OnClickHandler(View view) {

        switch(view.getId()) {
            case R.id.start_dt_btn:
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        start_dt.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
                    }
                };
                DatePickerDialog dialog1 = new DatePickerDialog(this, callbackMethod, 2022, 6, 20); //시작 날짜 설정
                dialog1.show();
                break;
            case R.id.end_dt_btn:
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        end_dt.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
                    }
                };
                DatePickerDialog dialog2 = new DatePickerDialog(this, callbackMethod, 2022, 6, 20); //시작 날짜 설정
                dialog2.show();
                break;
            case R.id.start_time_btn:
                time_callbackMethod = new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                        start_time.setText(hourOfDay + "시" + minute + "분");
                    }
                };
                TimePickerDialog dialog3 = new TimePickerDialog(this, time_callbackMethod, 00, 00, true);
                dialog3.show();
                break;
            case R.id.end_time_btn:
                time_callbackMethod = new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                        end_time.setText(hourOfDay + "시" + minute + "분");
                    }
                };
                TimePickerDialog dialog4 = new TimePickerDialog(this, time_callbackMethod, 00, 00, true);
                dialog4.show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.InitializeView();
        //this.InitializeListener();

        start_dt = (TextView) findViewById(R.id.start_dt);
        end_dt = (TextView) findViewById(R.id.end_dt);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView)  findViewById(R.id.end_time);

        //재생요일 CheckBox
        CheckBox Mon = (CheckBox)findViewById(R.id.Mon);
        CheckBox Tue = (CheckBox)findViewById(R.id.Tue);
        CheckBox Wed = (CheckBox)findViewById(R.id.Wed);
        CheckBox Thu = (CheckBox)findViewById(R.id.Thu);
        CheckBox Fri = (CheckBox)findViewById(R.id.Fri);
        CheckBox Sat = (CheckBox)findViewById(R.id.Sat);
        CheckBox Sun = (CheckBox)findViewById(R.id.Sun);
        //순번
        EditText sort = findViewById(R.id.sort);
        //노출시간
        EditText exposure_time = findViewById(R.id.exposure_time);
        //저장 버튼
        Button submit = (Button)findViewById(R.id.submit);
        //결과 확인
        TextView result = (TextView)findViewById(R.id.result);

        spinner = (Spinner)findViewById(R.id.spinner);
        tv_result = (TextView)findViewById(R.id.tv_result);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firstInit();

        for(int i=0;i<5;i++){
            addItem("iconName", "MAC" + i, "loaction");
        }

        mRecyclerViewAdapter = new RecyclerViewAdapter(mList);

        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "onItemClick position : " + pos, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerViewAdapter.setOnLongItemClickListener(new RecyclerViewAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "onLingItemClick position : " + pos, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); 세로
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)); //가로

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result_data = "";

                result_data += start_dt.getText().toString();
                result_data += end_dt.getText().toString() + "\n";

                if (Mon.isChecked() == true) result_data += Mon.getText().toString();
                if (Tue.isChecked() == true) result_data += Tue.getText().toString();
                if (Wed.isChecked() == true) result_data += Wed.getText().toString();
                if (Thu.isChecked() == true) result_data += Thu.getText().toString();
                if (Fri.isChecked() == true) result_data += Fri.getText().toString();
                if (Sat.isChecked() == true) result_data += Sat.getText().toString();
                if (Sun.isChecked() == true) result_data += Sun.getText().toString();

                result_data += "\n" + start_time.getText().toString();
                result_data += end_time.getText().toString();

                result_data += "\n" + sort.getText().toString();
                result_data += "\n" + exposure_time.getText().toString();
                result_data += "\n" + tv_result.getText().toString();

                result.setText("결과: " + result_data);
            }
        });

    }

    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
    }

    public void addItem(String imgName, String mainText, String subText){
        RecyclerViewItem item = new RecyclerViewItem();

        item.setImgName(imgName);
        item.setMainText(mainText);
        item.setSubText(subText);

        mList.add(item);
    }

   /* private void InitializeView() {
        start_dt = (TextView) findViewById(R.id.start_dt);
        end_dt = (TextView) findViewById(R.id.end_dt);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView)  findViewById(R.id.end_time);
    }

    private void InitializeListener() {

    }*/

}