package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserSettings extends AppCompatActivity {
    Connection connect;
    String ConnectionResult="";
    String pass,username,team;
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ImageView btn=findViewById(R.id.buttonsaveuser);
        Spinner spinner=findViewById(R.id.teamsspinner);
        EditText editText=findViewById(R.id.edit1);
        EditText editText1=findViewById(R.id.edit2);
        EditText editText2=findViewById(R.id.edit3);
        ImageView imageView=findViewById(R.id.usersresim);
        GetPassDb();

        adapter= ArrayAdapter.createFromResource(this,R.array.Teams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Picasso.get().load(bein+spinner.getSelectedItem().toString().split("-")[1]+".png").into(imageView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < adapter.getCount(); i++)
        {
            if (adapter.getItem(i).toString().split("-")[1].equals(team))
            {
                spinner.setSelection(i);

            }

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().equals(editText1.getText().toString()) && editText2.getText().toString().equals(pass))
                {
                    UpdateSets();
                }
                else{
                    Toaster();
                }
            }
        });
    }
    void Toaster(){

        Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_SHORT).show();

    }
    public void GetPassDb(){
        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from Users where UserName='"+username+"'";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    team=rst.getString("Team").toString();
                    pass=rst.getString("Password");
                }


            }
        }
        catch (Exception ex){}
    }
    public void UpdateSets(){
        EditText editText=findViewById(R.id.edit1);
        Spinner spinner=findViewById(R.id.teamsspinner);
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "update Users set Password='"+editText.getText()+"',Team='"+spinner.getSelectedItem().toString().split("-")[1]+"' where UserName='"+username+"'";
                Statement st = connect.createStatement();
                st.executeQuery(query);
                connect.close();
                Toast.makeText(this, "Yeni ayarlar başarıyla kaydedildi.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
        }
    }
}