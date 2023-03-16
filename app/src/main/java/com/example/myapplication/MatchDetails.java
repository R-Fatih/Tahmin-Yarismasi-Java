package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MatchDetails extends AppCompatActivity {
String username;
    RequestQueue mque,mque2;
    String mUsers[];
    String mHometeam[];
    String mAwayteam[];
    String mTarih[];
    String mMS[];
    String mHomepic[];
    String mAwaypic[];
    String mSkor[];
    String mPuan[];
    String mHafta[];
    ProgressDialog p;

    ArrayAdapter<CharSequence> adapter;

    Connection connect;
    String ConnectionResult="";
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        mque= Volley.newRequestQueue(this);
        mque2=Volley.newRequestQueue(this);
        new Ftp().execute();
        new Ftp2().execute();
        new Ftp4().execute();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public class GetAll extends AsyncTask{
       // Log.e("2",mUsers[0]);
        //TakımLogoları();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             p = new ProgressDialog(MatchDetails.this);
            p.setMessage("Lütfen bekleyin maçlar getiriliyor");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            MaclarıGetir();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            p.cancel();
            Adapterrr();
        }
    }
    public void Adapterrr(){
        ListView listView = findViewById(R.id.matchdetailslistview);
        benimAdapter adapter = new benimAdapter(this,mHometeam,mAwayteam,mTarih,mMS,mHomepic,mAwaypic,mSkor,mHafta,mPuan);
        listView.setAdapter(adapter);
    }
    String verilecekpuan="0";
    int tane2 = 0;
    char kara2=' ';
    ArrayList<String> teamurlcodes = new ArrayList<String>();
    ArrayList<String> teamnames = new ArrayList<String>();
    StringBuilder sb2=new StringBuilder();
    public class Ftp2 extends AsyncTask
    {
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

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            teamnames.set(3, "Çaykur Rizespor");
            teamnames.set(4, "Fatih Karagümrük");
            teamnames.set(10, "İstanbul Başakşehir");
            teamnames.set(16, "Yeni Malatyaspor");
            teamnames.set(19, "Adana Demirspor");
            teamnames.set(63, "MKE Ankaragücü");
        }
    }
    int tane3 = 0;
    char kara3=' ';
    StringBuilder sb3=new StringBuilder();
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void MaclarıGetir() {


        //ObservableList<Matches> matches=new ObservableArrayList<>();
        //List<Matches> Homes=new ArrayList<Matches>();
        List<String> Homes=new ArrayList<String>();
        List<String> Aways=new ArrayList<String>();
        List<String> K1=new ArrayList<String>();
        List<String> K2=new ArrayList<String>();
        List<String> K3=new ArrayList<String>();
        List<String> K4=new ArrayList<String>();
        List<String> K5=new ArrayList<String>();
        List<String> K6=new ArrayList<String>();
        List<String> K7=new ArrayList<String>();
        List<String> K8=new ArrayList<String>();
        List<String> K9=new ArrayList<String>();

        List<String> Tarih=new ArrayList<String>();
        List<String> MS=new ArrayList<String>();
        List<String> Skor=new ArrayList<String>();
        List <String>Homespic=new ArrayList<String>();
        List <String>Awayspic=new ArrayList<String>();
        List <String>Puan=new ArrayList<String>();
        List <String>Hafta=new ArrayList<String>();

//Matches matches=new Matches();
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    String query = "select * from allmatche where MS!='' and MS=" + username + " or (SUBSTRING(MS,1,1)!=SUBSTRING(MS,3,1) and SUBSTRING(" + username + ",1,1)!=SUBSTRING(" + username + ",3,1)  and SUBSTRING(MS,1,1)=SUBSTRING(" + username + ",1,1) and CAST(SUBSTRING(MS,3,1) as int)-cast(SUBSTRING(" + username + ",3,1) as int)=1) or SUBSTRING(MS,1,1)!=SUBSTRING(MS,3,1) and SUBSTRING(" + username + ",1,1)!=SUBSTRING(" + username + ",3,1)  and SUBSTRING(MS,3,1)=SUBSTRING(" + username + ",3,1) and CAST(SUBSTRING(MS,1,1) as int)-cast(SUBSTRING(" + username + ",1,1) as int)=1 or SUBSTRING(MS,1,1)!=SUBSTRING(MS,3,1) and SUBSTRING(" + username + ",1,1)!=SUBSTRING(" + username + ",3,1)  and SUBSTRING(MS,1,1)=SUBSTRING(" + username + ",1,1) and CAST(SUBSTRING(MS,3,1) as int)-cast(SUBSTRING(" + username + ",3,1) as int)=-1 or SUBSTRING(MS,1,1)!=SUBSTRING(MS,3,1) and SUBSTRING(" + username + ",1,1)!=SUBSTRING(" + username + ",3,1)  and SUBSTRING(MS,3,1)=SUBSTRING(" + username + ",3,1) and CAST(SUBSTRING(MS,1,1) as int)-cast(SUBSTRING(" + username + ",1,1) as int)=-1 order by   Cast(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(Date, 'Eylül', 'September'), 'Ekim', 'October'), 'Kasım', 'November'),'Aralık','December'),'Ocak','January'),'Şubat','February'),'Mart','March'),'Nisan','April'),'Mayıs','May'),'Haziran','June'),'Temmuz','July'),'Ağustos','August'),'.',':'),'|',' ') as date) desc";

                    Statement st = connect.createStatement();
                    ResultSet rst = st.executeQuery(query);

                    while (rst.next()) {
                        String ms = rst.getString("MS");
                        String tahmin = rst.getString(username);
if(ms.equals(tahmin))
        verilecekpuan="3";
    else
        verilecekpuan="1";
            AddListeler(Homes,Aways,Tarih,Skor,MS,Homespic,Awayspic,teamurlcodes,teamnames,rst,Hafta,Puan,verilecekpuan,0);




                    }

                }


            } catch (Exception ex) {
            }

        mHometeam= Homes.stream().toArray(String[]::new);
        mAwayteam= Aways.stream().toArray(String[]::new);
        mTarih= Tarih.stream().toArray(String[]::new);
        mMS= MS.stream().toArray(String[]::new);
        mSkor= Skor.stream().toArray(String[]::new);
        mPuan= Puan.stream().toArray(String[]::new);
        mHafta= Hafta.stream().toArray(String[]::new);

        mHomepic= Homespic.stream().toArray(String[]::new);
        mAwaypic= Awayspic.stream().toArray(String[]::new);

    }
    public void AddListeler(List<String>Homes,List<String>Aways,List<String>Tarih,List<String>Skor,List<String>MS,List<String>Homespic,List<String>Awayspic,ArrayList<String>teamurlcodes,ArrayList<String>teamnames,ResultSet rst,List<String>Hafta,List<String>Puan,String puan,int i){

      try {
          Homes.add(rst.getString("Home"));
        Aways.add(rst.getString("Away"));
        Tarih.add(rst.getString("Date"));
        Skor.add(rst.getString(username));
        MS.add(rst.getString("MS"));
        Homespic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Home"))) + ".png");
        Awayspic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Away"))) + ".png");
        Hafta.add(weeks2.get(weeks.indexOf(rst.getString("Week"))));
        Puan.add(puan);
      }catch (Exception e){}
    }
    class benimAdapter extends ArrayAdapter<String> {

        Context context;
        String rHometeam[];
        String rAwayteam[];
        String rTarih[];
        String rMS[];
        String rHomepic[];
        String rAwaypic[];
        String rSkor[];
        String rHafta[];
        String rPuan[];

        benimAdapter (Context c, String hometeam[], String awayteam[],String tarih[],String ms[],String homepic[],String awaypic[],String skor[],String hafta[],String puan[]){

            super(c, R.layout.custom_listviewmatchdetails, R.id.homteamtext, hometeam);
            this.context = c;
            this.rHometeam = hometeam;
            this.rAwayteam = awayteam;
            this.rTarih = tarih;
            this.rMS = ms;
            this.rHomepic = homepic;
            this.rAwaypic = awaypic;
            this.rSkor=skor;
            this.rHafta=hafta;
            this.rPuan=puan;

        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listviewmatchdetails,parent,false);
            RelativeLayout rlt = satir.findViewById(R.id.linear4);

            TextView home = satir.findViewById(R.id.hometeamisim);
            TextView away = satir.findViewById(R.id.awayteamisim);
            TextView tarih = satir.findViewById(R.id.matchdetailstarih);
            TextView ms = satir.findViewById(R.id.matchdetailsms);
            TextView tahmin = satir.findViewById(R.id.matchdetailstahmin);
            TextView hafta = satir.findViewById(R.id.matchdetailsweek);
            ImageView homepic=satir.findViewById(R.id.homeresim);
            ImageView awaypic=satir.findViewById(R.id.awayresim);
            TextView puan = satir.findViewById(R.id.matchdetailspuan);

            home.setText(rHometeam[position]);
            away.setText(rAwayteam[position]);

            tarih.setText(rTarih[position]);
            ms.setText(rMS[position]);
            tahmin.setText(rSkor[position]);
            hafta.setText(rHafta[position]);
            puan.setText(rPuan[position]);
            Picasso.get().load(rHomepic[position]).into(homepic);
            Picasso.get().load(rAwaypic[position]).into(awaypic);
            //PuanHesaplaması(date,time,rTarih[position],position,ms,k1,k2,k3,k4,k5,k6,k7,k8,k9,rlt);
            //s1h.setText(mS1[1]);



            return satir;



        }
    }
    int tane = 0;
    char kara=' ';
    List<String> weeks=new ArrayList<>();
    List<String> weeks2=new ArrayList<>();

    StringBuilder sb=new StringBuilder();

    public class Ftp extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                org.jsoup.nodes.Document doc= Jsoup.connect("https://raw.githubusercontent.com/R-Fatih/ACCDB/main/weekcodes.txt").get();
                sb.append(doc.text()+"\n");
            }catch (Exception e){e.printStackTrace();}
            String a=sb.toString();
            for(int i = 0; i < sb.length(); i++) {
                if(kara == a.charAt(i)) {
                    ++tane;
                }
            }
            for(int c = 0; c < tane+1; c++) {
                weeks.add(sb.toString().split(" ")[c]);
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new ArrayAdapter(getApplicationContext(),
                    android.R.layout.simple_spinner_item, weeks);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

           new GetAll().execute();
        }
    }
    int tane4 = 0;
    char kara4=' ';
    StringBuilder sb4=new StringBuilder();
    public class Ftp4 extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                org.jsoup.nodes.Document doc= Jsoup.connect("https://raw.githubusercontent.com/R-Fatih/ACCDB/main/weeknames.txt").get();
                sb4.append(doc.text()+"\n");
            }catch (Exception e){e.printStackTrace();}
            String a=sb4.toString();
            for(int i = 0; i < sb4.length(); i++) {
                if(kara4 == a.charAt(i)) {
                    ++tane4;
                }
            }
            for(int c = 0; c < tane4+1; c++) {
                weeks2.add(sb4.toString().split(" ")[c]);
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }
    }
}