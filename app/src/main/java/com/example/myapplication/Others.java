package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import net.sourceforge.jtds.jdbc.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

public class Others extends AppCompatActivity {
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    String weeknumber;
    int points = 0;
    DateTime zaman;
    int seconds = 300;
    int tahminmac;
    String mUsers[];
    String mHometeam[];
    String mAwayteam[];
    String mTarih[];
    String mMS[];
    String mHomepic[];
    String mAwaypic[];
    String mTakım[];
    String mK1[];
    Spinner spinner;
    //int mTarih[];
    private Timer timer;
    String week;
    Connection connect;
    String ConnectionResult="";
    Button btn;
    RequestQueue mque,mque2;
    String time,date,username;
    List<String> weeks=new ArrayList<>();
    StringBuilder sb=new StringBuilder();
    ArrayAdapter<CharSequence> adapter;
    String words = null;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        Intent intent = getIntent();
        new Ftp().execute();
        new Ftp2().execute();

        points=0;
        spinner=findViewById(R.id.spinnerothers);

        // username = intent.getStringExtra("username");
        mque=Volley.newRequestQueue(this);
        mque2=Volley.newRequestQueue(this);
        //Button btn=findViewById(R.id.kaydetbutton);
GetUsers();

        GetWeek();
        GetWeek();

        GetAll();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                points=0;
                GetAll();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

    }
    int tane = 0;
char kara=' ';

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
                weeks.add(sb.toString().split(" ")[c]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            spinner=findViewById(R.id.spinnerothers);
            adapter = new ArrayAdapter(getApplicationContext(),
                    android.R.layout.simple_spinner_item, weeks);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            for (int i = 0; i < adapter.getCount(); i++)
            {
                if (adapter.getItem(i).toString().split("-")[1].equals(week))
                {
                    spinner.setSelection(i);

                }

            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GetAll(){
        GetTime();
        MaclarıGetir();
        Log.e("2",mUsers[0]);
        //TakımLogoları();

        ListView listView = findViewById(R.id.yeni2list);
        benimAdapter adapter = new benimAdapter(this,mHometeam,mAwayteam,mTarih,mMS,mHomepic,mAwaypic,mK1);
        listView.setAdapter(adapter);

    }
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

        }
    }
    int tane3 = 0;
    char kara3=' ';
    StringBuilder sb3=new StringBuilder();
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void GetUsers(){
        List<String> Users=new ArrayList<String>();

        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from Standings";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    Users.add(rst.getString("UserName"));


                    //matches.Time.set(rst.getString("Time"));
                    //matches.usersname.set(rst.getString("asıluser"));
                    //matches.MS.set(rst.getString("MS"));


                }

            }
            mUsers= Users.stream().toArray(String[]::new);

            TextView textView=findViewById(R.id.MSSS);
            RelativeLayout layout=findViewById(R.id.rlt);
            HorizontalScrollView hrz=findViewById(R.id.scr);
if(mUsers.length>10){
layout.getLayoutParams().width=2040+(mUsers.length*60);
layout.requestLayout();}
            for(int i=0;i< mUsers.length;i++) {
                TextView textView2 = new TextView(getApplicationContext());
                textView2.setX(500 + (i * 170));
                textView2.setY(50);
                textView2.setTextSize(8);
            textView2.setText(mUsers[i]);
                layout.addView(textView2);

            }
          //  RelativeLayout rlt = satir.findViewById(R.id.linear4);

        }

        catch (Exception ex){}
    }
    int userssıraso=0;
    String sr;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void MaclarıGetir() {

        userssıraso=0;
        //ObservableList<Matches> matches=new ObservableArrayList<>();
        //List<Matches> Homes=new ArrayList<Matches>();
        List<String> Homes=new ArrayList<String>();
        List<String> Aways=new ArrayList<String>();
        List<String> K1=new ArrayList<String>();
        List<String> Tarih=new ArrayList<String>();
        List<String> MS=new ArrayList<String>();
        List <String>Homespic=new ArrayList<String>();
        List <String>Awayspic=new ArrayList<String>();
        K1.clear();
//Matches matches=new Matches();
        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from "+spinner.getSelectedItem().toString().split("-")[1]+" order by Date asc";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    Homes.add(rst.getString("Home"));
                    Aways.add(rst.getString("Away"));

                    Tarih.add(rst.getString("Date"));
                    //Skor.add(rst.getString(username));
                    MS.add(rst.getString("MS"));

                    Homespic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Home"))) + ".png");
                    Awayspic.add(bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Away"))) + ".png");
                   for(int i=0;i<mUsers.length;i++) {
                       try {
                           if (rst.getString(mUsers[i]) == null)
                               sr = " -";
                           else
                               sr = rst.getString(mUsers[i]);
                           K1.add(sr);


                       } catch (Exception e) {
                           Log.e("niye", e.getMessage());
                       }
                   }

                }

            }



        }

        catch (Exception ex){}
        mHometeam= Homes.stream().toArray(String[]::new);
        mAwayteam= Aways.stream().toArray(String[]::new);
        mTarih= Tarih.stream().toArray(String[]::new);
        mMS= MS.stream().toArray(String[]::new);
        mK1= K1.stream().toArray(String[]::new);
        mHomepic= Homespic.stream().toArray(String[]::new);
        mAwaypic= Awayspic.stream().toArray(String[]::new);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)

    public void GetTime(){
        String URL="https://www.timeapi.io/api/Time/current/zone?timeZone=Europe/Istanbul";
        //RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String jsonArray=response.getString("time");
                            String jsonArray2=response.getString("date");
                            time=jsonArray;
                            date=jsonArray2;
                            //DisplayDateTime.setText(time+" "+date);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response",error.toString());

                    }
                }
        );
        mque.add(objectRequest);
        //JSONObject jsonObject = new JSONObject(json);

    }
    long timeSpan = 0,timesaat = 0,timedakika;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void PuanHesaplaması(String zaman, String saat, String tarih2, TextView msss,String skor, TextView k1,RelativeLayout rlt) {
        //TextView       puanlar2=findViewById(R.id.puanlar);

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "MM/dd/yyyy HH:mm");
        SimpleDateFormat sdf2
                = new SimpleDateFormat(
                "dd MMMM yyyy|HH.mm",new Locale("tr", "TR"));
        try {
            Date d1 = sdf.parse(zaman + " " + saat);
            Date d2 = sdf2.parse(tarih2);
            long difference_In_Time
                    = d2.getTime() - d1.getTime();
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;
            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;
            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;
            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;
            timeSpan = difference_In_Days;
            timesaat = difference_In_Hours;
            timedakika=difference_In_Minutes;
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        TextView[] textViews=new TextView[]{k1};
        //puanlar2.setText(String.valueOf(timeSpan)+"  "+String.valueOf(timesaat));
        //int timesaat = Convert.ToInt32(listView.Items[i].SubItems[2].Text.Split('|')[1].ToString().Split('.')[0]) - zaman.Hour;
        //int timedakika = Convert.ToInt32(listView.Items[i].SubItems[2].Text.Split('|')[1].ToString().Split('.')[1]) - zaman.Minute;
        // int timesaat= (int) TimeUnit.MILLISECONDS.toHours(mDate22.getTime() - mDate33.getTime());
        if (timeSpan < 0)
        {
            for(int as=0;as<1;as++) {
                textViews[as].setVisibility(View.VISIBLE);
                rlt.setVisibility(View.VISIBLE);
            }
        }

        else if (timeSpan == 0 && timesaat <= 0&&timedakika <= 0)
        {
            for(int as=0;as<1;as++) {
                textViews[as].setVisibility(View.VISIBLE);
                rlt.setVisibility(View.VISIBLE);

            }        }else{
            for(int as=0;as<1;as++) {
                textViews[as].setVisibility(View.INVISIBLE);
                rlt.setVisibility(View.INVISIBLE);

            }
        }


        for(int as=0;as<1;as++) {

            String ms = msss.getText().toString();
            String tahmin = skor;
            if ((ms.equals(tahmin)))
            {
                textViews[as].setTextColor( Color.GREEN);

                points = points + 3;
            }
            try
            {
                if (!ms.split("-")[0].equals(ms.split("-")[1]))
                {
                    if (!tahmin.split("-")[0].equals(tahmin.split("-")[1])) {

                        if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == 1) {
                            textViews[as].setTextColor(Color.YELLOW);

                            points = points + 1;

                        }
                        if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == 1) {
                            textViews[as].setTextColor(Color.YELLOW);
                            points = points + 1;

                        }
                        if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == -1) {
                            textViews[as].setTextColor(Color.YELLOW);
                            points = points + 1;

                        }
                        if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == -1) {
                            textViews[as].setTextColor(Color.YELLOW);
                            points = points + 1;

                        }
                    }
                }



            }
            catch (Exception ex)
            {

            }
        }
        //TextView txt=findViewById(R.id.totalponts);
        //txt.setText(String.valueOf(points));
        //puanlar2.setText("Bu hafta kazanılan puan:"+String.valueOf(points));

    }

    public void GetWeek(){
        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from WeeksMatchesNumber";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){


                    week=rst.getString("CurrentWeek");

                    //matches.Time.set(rst.getString("Time"));
                    //matches.usersname.set(rst.getString("asıluser"));
                    //matches.MS.set(rst.getString("MS"));


                }


            }
        }
        catch (Exception ex){}
    }

    class benimAdapter extends ArrayAdapter<String> {

        Context context;
        String rHometeam[];
        String rAwayteam[];
        String rTarih[];
        String rMS[];
        String rHomepic[];
        String rAwaypic[];
        String rK1[];
        String rK2[];
        String rK3[];
        String rK4[];
        String rK5[];
        String rK6[];
        String rK7[];
        String rK8[];
        String rK9[];
        String rTakım[];
        benimAdapter (Context c, String hometeam[], String awayteam[],String tarih[],String ms[],String homepic[],String awaypic[],String k1[]){

            super(c, R.layout.custom_listviewotherusers, R.id.homteamtext, hometeam);
            this.context = c;
            this.rHometeam = hometeam;
            this.rAwayteam = awayteam;
            this.rTarih = tarih;
            this.rMS = ms;
            this.rHomepic = homepic;
            this.rAwaypic = awaypic;
            this.rK1 = k1;

        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listviewotherusers,parent,false);
            RelativeLayout rlt = satir.findViewById(R.id.linear4);

            TextView home = satir.findViewById(R.id.hometeamisim);
            TextView away = satir.findViewById(R.id.awayteamisim);
            //TextView tarih = satir.findViewById(R.id.tarihsaat);
            TextView ms = satir.findViewById(R.id.macsounucu1);


            ImageView homepic=satir.findViewById(R.id.homeresim);
            ImageView awaypic=satir.findViewById(R.id.awayresim);

            home.setText(rHometeam[position]);
            away.setText(rAwayteam[position]);

           // tarih.setText(rTarih[position]);
            ms.setText(rMS[position]);
try {
    for (int i = 0; i < mUsers.length; i++) {
        TextView textView = new TextView(getApplicationContext());
        textView.setX(500 + (i * 170));
        textView.setY(25);
        if(position==0){
            PuanHesaplaması(date,time,rTarih[position],ms,rK1[i],textView,rlt);
                 textView.setText(rK1[i]);}
        else {
            PuanHesaplaması(date,time,rTarih[position],ms,rK1[(i * position) + (mUsers.length * position) - (i * (position - 1))],textView,rlt);
            textView.setText(rK1[(i * position) + (mUsers.length * position) - (i * (position - 1))]);
        }
        rlt.addView(textView);

    }
}catch (Exception e){Log.e("texts",e.getMessage());}
            Picasso.get().load(rHomepic[position]).into(homepic);
            Picasso.get().load(rAwaypic[position]).into(awaypic);
            //s1h.setText(mS1[1]);



            return satir;



        }
    }
    class benimAdapter2 extends ArrayAdapter<String> {

        Context context;
        String rHometeam[];

        benimAdapter2 (Context c, String hometeam[]){

            super(c, R.layout.custom_listviewotherusers, R.id.homteamtext, hometeam);
            this.context = c;
            this.rHometeam = hometeam;

        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listviewotherusers,parent,false);

            TextView home = satir.findViewById(R.id.hometeamisim);

            //s1h.setText(mS1[1]);



            return satir;



        }
    }
    class HorizantalAdapter extends ArrayAdapter<String> {

        Context context;
        String rHometeam[];

        HorizantalAdapter(Context c, String hometeam[]) {

            super(c, R.layout.customhorizantal, R.id.name, hometeam);
            this.context = c;
            this.rHometeam = hometeam;
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.customhorizantal, parent, false);

            TextView home = satir.findViewById(R.id.name);

            home.setText("Deneme");


            return satir;


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void TakımLogoları() {

    }
    public void Toastms() {
        Toast.makeText(this, "Maç başarıyla kaydedildi.", Toast.LENGTH_LONG).show();

    }


}