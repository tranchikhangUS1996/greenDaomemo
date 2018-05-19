package com.example.lap60020_local.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class CreateActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText Name;
    private EditText Address;
    private EditText DateOfBirth;
    private EditText Class;
    private EditText Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProgressBar progressBar = findViewById(R.id.create_progressbar);
        Name = findViewById(R.id.create_name);
        Address = findViewById(R.id.create_address);
        DateOfBirth = findViewById(R.id.create_date);
        Class = findViewById(R.id.create_class);
        Result = findViewById(R.id.create_diem);
    }

    public void onAdd(View view) {
        SQLiteDatabase sqLiteDatabase = new DaoMaster.DevOpenHelper(this
                ,"USERDATABASE")
                .getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        String name = Name.getText().toString();
        String address = Address.getText().toString();
        String dateofbirth = DateOfBirth.getText().toString();
        String class_name = Class.getText().toString();
        int result = Integer.valueOf(Result.getText().toString());
        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setDateOfBirth(dateofbirth);
        user.setClassName(class_name);
        user.setResult(result);
        userDao.insert(user);
        Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show();
    }
}
