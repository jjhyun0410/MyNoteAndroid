package com.example.webdisign.mynote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView noteList;
    EditText noteContent;
    Button btnAdd,btnNew,btnDelete,btnFinish;
    EditText noteTitle;
    int count = 0;
    int selPos = -1;

    private static final ArrayList<String> list = new ArrayList<String>();
    private static final ArrayList<String> content = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(i);

        //다시 돌아와서 다음 작업

        noteList = (ListView)findViewById(R.id.noteList);
        noteContent = (EditText)findViewById(R.id.noteContent);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnNew = (Button)findViewById(R.id.btnNew);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        noteTitle = (EditText)findViewById(R.id.noteTitle);

        //새로운 함수 : ListView 보이기
        // ShowListView();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        noteList.setAdapter(adapter);

        list.add("TEST1");
        list.add("TEST2");
        list.add("TEST3");

        content.add("CONTENT1");
        content.add("CONTENT2");
        content.add("CONTENT3");

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyToast("클릭" + position);

                noteTitle.setText(list.get(position));
                noteContent.setText(content.get(position));
                selPos = position;
            }
        });
        //리스트뷰 자동 업데이트를 위한 어뎁터
    }

    void ShowListView(){
        //1. 리스트뷰에 나열될 항목을 미리 정한다.
        String list[] = {"기생충", "어벤저스", "지킬앤 하이드", "미녀와 야수","아무생각이 없네"};



        //2.어댑터가 필요하다.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list );
        noteList.setAdapter(adapter);
    }



    void noteWrite(View view){
        String title = noteTitle.getText().toString().trim();
        String content = noteContent.getText().toString().trim();

        if (title.length() < 1){
            MyToast("제목을 입력하세요");
            noteTitle.requestFocus();
            return;
        }

        if (content.length() < 1){
            MyToast("내용을 입력하세요");
            noteTitle.requestFocus();
            return;
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("[yy/MM/dd HH:mm:ss]");
        String str = format.format(date);

        list.add(str + title);
        this.content.add(content);
        adapter.notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(noteContent.getWindowToken(), 0);
    }

    void newNote(View view){
        noteTitle.setText("");
        noteContent.setText("");

    }
    void noteDelete(View view){
        if (selPos==-1){
            MyToast("삭제할 항목을 선택하세요.");
            return;
        }

        list.remove(selPos);
        content.remove(selPos);
        adapter.notifyDataSetChanged();
        noteTitle.setText("");
        noteContent.setText("");
        selPos = -1;

    }
    void finish(View view){

        AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
        msg.setTitle("확인");
        msg.setMessage("종료하시겠습니까?");
        msg.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();  //앱종료
            }
        });
        msg.setNegativeButton("아니오", null);
        msg.show();
    }

    void MyToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}















