
package com.example.sqlitedatabasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
        dbHelper = new MyDatabaseHelper(this, "StuInfo.db", null, 5);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String Sql=editText.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //db.execSQL("insert into Book(name,author,pages,price)values(?,?,?,?)",new String[]{"Android","google","800","50"});
                // editText.setText("");
                ContentValues values = new ContentValues();//临时变量
                values.put("stuName", "郑瑞瑩");
                values.put("stuNumber",53);
                values.put("stuAge",22);
                values.put("stuAddress","广东省");
                values.put("stUCollege","广东理工学院");
                db.insert("Student",null, values); // 插入第一条数据 values); // 插入第一条数据
                values.clear();
                // 开始组装第二条数据
                values.put("stuName", "李四");
                values.put("stuNumber",1);
                values.put("stuAge",20);
                values.put("stuAddress","广东省");
                values.put("stUCollege","广东理工学院");
                db.insert("Student",null, values); // 插入第一条数据 values);
            }
        });
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("stuCollege","广东理工学院信息技术学院");
                db.update("Student", values,"stuCollege=?",new String[]{"广东理工学院"});
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Student","stuNumber<?", new String[] { "20"});
            }
        });
        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Student",null, null, null, null, null, null);
                StringBuilder content=new StringBuilder();//转义字符
                content.append("id"+"\t\t\t"+"stuName"+"\t\t\t"+"stuNumber"+"\t\t\t"+"stuAge"+"\t\t\t"+"stuAddress"+"\t\t\t"+"stuCollege"+"\n");
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        int id=cursor.getInt(cursor.getColumnIndex("id"));
                        String stuName = cursor.getString(cursor.getColumnIndex("stuName"));
                        int stuNumber = cursor.getInt(cursor.getColumnIndex("stuNumber"));
                        int stuAge= cursor.getInt(cursor.getColumnIndex("stuAge"));
                        String stuAddress= cursor.getString(cursor.getColumnIndex("stuAddress"));
                        String stuCollege=cursor.getString(cursor.getColumnIndex("stuCollege"));
                        content.append(id+"\t\t\t"+stuName+"\t\t"+stuNumber+"\t\t"+stuAge+"\t\t"+stuAddress+"\t\t"+stuCollege+"\n");
                    } while (cursor.moveToNext());
                }
                cursor.close();
                textView.setText(content.toString());
            }
        });
    }

}
