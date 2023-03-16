package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamFixtures extends AppCompatActivity {
String team;
    Connection connect;
    String ConnectionResult="";
    String mHometeam[];
    String mAwayteam[];
    String mSkor[];
    String mHomepic[];
    String mAwaypic[];
    String mHafta[];
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    int weeek;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_fixtures);
        Intent intent = getIntent();
        team = intent.getStringExtra("team");
        getweek();
        new Ftp2().execute();


    }
    int tane2 = 0;
    char kara2=' ';
    ArrayList<String> teamurlcodes = new ArrayList<String>();
    ArrayList<String> teamnames = new ArrayList<String>();
    StringBuilder sb2=new StringBuilder();
    int tane3 = 0;
    char kara3=' ';
    StringBuilder sb3=new StringBuilder();
    public class Ftp2 extends AsyncTask
    {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                org.jsoup.nodes.Document doc= Jsoup.connect("https://raw.githubusercontent.com/R-Fatih/ACCDB/main/teamcodes.txt").get();
                sb2.append(doc.text()+"\n");
            }catch (Exception e){e.printStackTrace();}
            String a=sb2.toString();
            for(int i = 0; i < sb2.length(); i++) {
                if(kara2 == a.charAt(i)) {
                    ++tane2;
                }
            }
            for(int c = 0; c < tane2+1; c++) {
                teamurlcodes.add(sb2.toString().split(" ")[c]);
            }



            try {
                org.jsoup.nodes.Document doc= Jsoup.connect("https://raw.githubusercontent.com/R-Fatih/ACCDB/main/teamnames.txt").get();
                sb3.append(doc.text()+"\n");
            }catch (Exception e){e.printStackTrace();}
            String a2=sb3.toString();
            for(int i = 0; i < sb3.length(); i++) {
                if(kara3 == a2.charAt(i)) {
                    ++tane3;
                }
            }
            for(int c = 0; c < tane3+1; c++) {
                teamnames.add(sb3.toString().split(" ")[c]);
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            teamnames.set(3, "Çaykur Rizespor");
            teamnames.set(4, "Fatih Karagümrük");
            teamnames.set(10, "İstanbul Başakşehir");
            teamnames.set(16, "Yeni Malatyaspor");
            teamnames.set(19, "Adana Demirspor");
            teamnames.set(63, "MKE Ankaragücü");
            Log.e("teamsayı", "a"+tane3);
            MaclarıGetir();

        }
    }
    public void getweek(){
        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="select * from WeekInte";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    weeek=Integer.parseInt(rst.getString("Week"));

                }

            }



        }

        catch (Exception ex){
            Log.e("Mes",ex.getMessage());}
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void MaclarıGetir() {




        //ObservableList<Matches> matches=new ObservableArrayList<>();
        //List<Matches> Homes=new ArrayList<Matches>();
        List<String> Homes=new ArrayList<String>();
        List<String> Aways=new ArrayList<String>();
        List<String> Skor=new ArrayList<String>();
        List <String>Homespic=new ArrayList<String>();
        List <String>Awayspic=new ArrayList<String>();
        List <String>Hafta=new ArrayList<String>();

//Matches matches=new Matches();

        Spinner spinner=findViewById(R.id.spinner);

        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="select * from allmatches where Home='"+team+"' or Away='"+team+"'";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    Homes.add(rst.getString("Home"));
                    Aways.add(rst.getString("Away"));
                    Skor.add(rst.getString("Score"));
                    Hafta.add(rst.getString("Week"));


                    //matches.Time.set(rst.getString("Time"));
                    //matches.usersname.set(rst.getString("asıluser"));
                    //matches.MS.set(rst.getString("MS"));



try {
    Homespic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Home"))) + ".png");
    Awayspic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Away"))) + ".png");
}catch (Exception e){Log.e("mess",e.getLocalizedMessage()+teamnames.get(2));}

                }

            }



        }

        catch (Exception ex){
            Log.e("Mes",ex.getMessage());}
        mHometeam= Homes.stream().toArray(String[]::new);
        mAwayteam= Aways.stream().toArray(String[]::new);
        mSkor= Skor.stream().toArray(String[]::new);
        mHomepic= Homespic.stream().toArray(String[]::new);
        mAwaypic= Awayspic.stream().toArray(String[]::new);
        mHafta= Hafta.stream().toArray(String[]::new);
        ListView listView = findViewById(R.id.teamsfixturelist);
        benimAdapter adapter = new benimAdapter(this,mHometeam,mAwayteam,mSkor,mHomepic,mAwaypic,mHafta);
        listView.setAdapter(adapter);
        listView.setSelection(weeek-5);
    }
    class benimAdapter extends ArrayAdapter {

        Context context;
        String rHometeam[];
        String rAwayteam[];
        String rSkor[];
        String rHomepic[];
        String rAwaypic[];
        String rHafta[];

        benimAdapter (Context c, String hometeam[], String awayteam[],String skor[],String homepic[],String awaypic[],String hafta[]){

            super(c, R.layout.custom_listviewteamfixtures, R.id.homteamtext, hometeam);
            this.context = c;
            this.rHometeam = hometeam;
            this.rAwayteam = awayteam;
            this.rSkor = skor;
            this.rHomepic = homepic;
            this.rAwaypic = awaypic;
            this.rHafta = hafta;

        }


        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View satir, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            satir = layoutInflater.inflate(R.layout.custom_listviewteamfixtures,parent,false);
            TextView hafta = satir.findViewById(R.id.hafta);
            TextView home = satir.findViewById(R.id.hometeamisim);
            TextView away = satir.findViewById(R.id.awayteamisim);
            TextView skor=satir.findViewById(R.id.skor);
            ImageView homepic=satir.findViewById(R.id.homeresim);
            ImageView awaypic=satir.findViewById(R.id.awayresim);
            TextView renk = satir.findViewById(R.id.renk);
            TextView renkyazı = satir.findViewById(R.id.renkyazı);


            home.setText(rHometeam[position]);
            away.setText(rAwayteam[position]);
            skor.setText(rSkor[position]);
            hafta.setText(rHafta[position]);

if(rHometeam[position].equals(team)){
    Log.e("str",skor.getText().toString().split("-")[0]);
try{
    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))>Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(0, 255, 0)));
        renkyazı.setText("G");
        renkyazı.setTextColor(Color.WHITE);
    }
    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))<Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 0, 0)));
        renkyazı.setText("M");
        renkyazı.setTextColor(Color.WHITE);
    }
    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))==Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 255, 0)));
        renkyazı.setText("B");
        renkyazı.setTextColor(Color.BLACK);
    }

}catch (Exception e){}
    }
            if(rAwayteam[position].equals(team)){
                Log.e("str",skor.getText().toString().split("-")[0]);
                try{
                    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))<Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
                        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(0, 255, 0)));
                        renkyazı.setText("G");
                        renkyazı.setTextColor(Color.WHITE);
                    }
                    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))>Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
                        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 0, 0)));
                        renkyazı.setText("M");
                        renkyazı.setTextColor(Color.WHITE);
                    }
                    if(Integer.parseInt(skor.getText().toString().split("-")[0].replace(" ",""))==Integer.parseInt(skor.getText().toString().split("-")[1].replace(" ",""))) {
                        renk.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 255, 0)));
                        renkyazı.setText("B");
                        renkyazı.setTextColor(Color.BLACK);
                    }

                }catch (Exception e){}
            }

            Picasso.get().load(rHomepic[position]).into(homepic);
            Picasso.get().load(rAwaypic[position]).into(awaypic);
            //PuanHesaplaması(date,time,rTarih[position],position,skor,ms);
            //s1h.setText(mS1[1]);



            return satir;



        }}

}