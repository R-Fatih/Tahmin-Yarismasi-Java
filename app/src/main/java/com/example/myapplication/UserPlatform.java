package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class UserPlatform extends AppCompatActivity {
    String username;
    TextView textView;
    Connection connect;
    String ConnectionResult="";
    Date now;
    LocalDate calNow;
    Calendar calNow2;
    int year;
    int month;
    int dayOfMonth;
    int time;
    int minute;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_platform);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        calNow = LocalDate.now();
        calNow2=Calendar.getInstance();
        year = calNow.getYear();
        month = calNow.getMonthValue();
        dayOfMonth = calNow.getDayOfMonth();
        time=calNow2.get(Calendar.HOUR);
        minute=calNow2.get(Calendar.MINUTE);
UpdateStands();
        ImageView btn=(ImageView) findViewById(R.id.button3);
        ImageView btn2=(ImageView) findViewById(R.id.button4);
        ImageView btn3=(ImageView) findViewById(R.id.button5);
        ImageView btn4=(ImageView) findViewById(R.id.button6);
        ImageView btn5=(ImageView) findViewById(R.id.button7);
        ImageView btn6=(ImageView) findViewById(R.id.button8);

        textView=findViewById(R.id.textviewmerhaba);
        textView.setText("Merhaba: "+username);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPlatform.this, WeekMatches2.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPlatform.this, Standings.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPlatform.this, LeagueStandings.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPlatform.this, UserSettings.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    btn5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UserPlatform.this, Statics.class);
            startActivity(intent);
        }
    });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPlatform.this, Rules.class);
                startActivity(intent);
            }
        });
    }
public void UpdateStands(){
    try {

        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connectionclass();
        if (connect != null) {
            String query = "update Standings set Version='"+BuildConfig.VERSION_NAME+"',LastEnter='"+dayOfMonth+"."+month+"."+year+" "+time+"."+minute+"' where UserName='"+username+"'";
            Statement st = connect.createStatement();
            st.executeQuery(query);
            connect.close();
        }
    } catch (Exception ex) {
    }
}
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }
}