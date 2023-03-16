package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.jsoup.Jsoup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    Connection connect;
    String ConnectionResult="";
    Boolean exist,bosluk,harfler;
    int specials = 0, digits = 0, letters = 0, spaces = 0;
    ArrayAdapter<CharSequence> adapter;
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText editText=(EditText) findViewById(R.id.editTextTextPersonName);
        Spinner spinner=findViewById(R.id.registerspinner);
        ImageView imageView=findViewById(R.id.registerresiö);
        adapter= ArrayAdapter.createFromResource(this,R.array.Teams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        new Ftp().execute();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Picasso.get().load(bein+spinner.getSelectedItem().toString().split("-")[1]+".png").into(imageView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    int tane = 0;
    char kara=' ';
    List<String> Weeks=new ArrayList<>();
    StringBuilder sb=new StringBuilder();
    String[] weeks;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void RegisterSql(View view){
        EditText editText=(EditText) findViewById(R.id.editTextTextPersonName);
        EditText editText2=(EditText) findViewById(R.id.editTextTextPersonName3);
        Spinner spinner=findViewById(R.id.registerspinner);
Controlss();
        if (exist==true || bosluk==true||harfler==true)
        {
            //label4.Visible = true;
Toast.makeText(getApplicationContext(),"Böyle bir kullanıcı mevcut veya Kullanıcı adınız rakam veya boşluk içeriyor.",Toast.LENGTH_LONG).show();        }
        else
        {
            //label4.Visible = false;

        if (editText.getText().toString() != "" && editText2.getText().toString() != "")
        {
            try{
                ConnectionHelper connectionHelper=new ConnectionHelper();
                connect=connectionHelper.connectionclass();
                if(connect!=null){
                    String query="insert into Users (UserName,Password,Team) values ('"+editText.getText().toString()+"','"+editText2.getText().toString()+"','"+spinner.getSelectedItem().toString().split("-")[1]+"')";
                    Statement st=connect.createStatement();
                    st.executeQuery(query);
                    connect.close();
                }
            }
            catch (Exception ex){}
            try{
                ConnectionHelper connectionHelper=new ConnectionHelper();
                connect=connectionHelper.connectionclass();
                if(connect!=null){
                    String query="insert into Standings (UserName,EytPoints,TotalPoints,Team) values ('"+editText.getText().toString()+"',"+0+","+0+",'"+spinner.getSelectedItem().toString().split("-")[1]+"')";
                    Statement st=connect.createStatement();
                    st.executeQuery(query);
                    connect.close();


                }
            }
            catch (Exception ex){}


                try{
                    ConnectionHelper connectionHelper=new ConnectionHelper();
                    connect=connectionHelper.connectionclass();
                    if(connect!=null){
                        String query = "Alter table allmatche Add " + editText.getText().toString() + " varchar(50)";
                        Statement st = connect.createStatement();
                        st.executeQuery(query);
                        connect.close();

                    }
                }
                catch (Exception ex){
                    Log.e("Strhata",ex.getMessage());
                }
            Toast.makeText(this, "Kayıt başarıyla oluşturuldu", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifre boş olamaz ya da kullanıcı adınız boşluk içeriyor", Toast.LENGTH_SHORT).show();
        }
        }
    }

    public void Controlss(){
        EditText editText=(EditText) findViewById(R.id.editTextTextPersonName);
        ImageView btn= (ImageView) findViewById(R.id.button2);

            specials = 0; digits = 0; letters = 0; spaces = 0;


            try{
                ConnectionHelper connectionHelper=new ConnectionHelper();
                connect=connectionHelper.connectionclass();
                if(connect!=null){
                    String query="Select * from Users where UserName='"+editText.getText().toString()+"'";
                    Statement st=connect.createStatement();
                    ResultSet resultSet=st.executeQuery(query);
                    if(resultSet.next()){
                        exist=true;
                    }else{
                        exist=false;
                    }
                }
            }

            catch (Exception ex){}
            if (editText.getText().toString().contains(" "))
                bosluk = true;
            else
                bosluk = false;

            for (int b = 0; b < editText.getText().length(); ++b) {
                char ch = editText.getText().charAt(b);
                if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isSpace(ch)) {
                    ++specials;
                } else if (Character.isDigit(ch)) {
                    ++digits;
                } else if (Character.isSpace(ch)) {
                    ++spaces;
                } else {
                    ++letters;
                }
                if(specials>0||digits>0||editText.getText().toString().contains("ı")||editText.getText().toString().contains("ü")||editText.getText().toString().contains("ğ")||editText.getText().toString().contains("ç")||editText.getText().toString().contains("ö")||editText.getText().toString().contains("ş"))
                    harfler=true;
                else
                    harfler=false;
            }

    }

    public class Ftp extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                org.jsoup.nodes.Document doc= Jsoup.connect("https://raw.githubusercontent.com/R-Fatih/ACCDB/main/week.txt").get();
                sb.append(doc.text()+"\n");
            }catch (Exception e){e.printStackTrace();}
            String a=sb.toString();
            for(int i = 0; i < sb.length(); i++) {
                if(kara == a.charAt(i)) {
                    ++tane;
                }
            }
            for(int c = 0; c < tane+1; c++) {
                Weeks.add(sb.toString().split(" ")[c]);
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            weeks= Weeks.stream().toArray(String[]::new);
            Log.e("wekslen"," "+weeks.length);

        }
    }
}