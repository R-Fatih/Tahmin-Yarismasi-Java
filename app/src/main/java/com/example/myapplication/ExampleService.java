package com.example.myapplication;

import static com.example.myapplication.App.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class ExampleService extends Service {

    Connection connect;
    String week;
    String time,date,saniye;
    long timeSpan = 0,timesaat = 0,timedakika=0,timesaniye=0;
    int tamam=0;int tamam2=0;
    int gonsayı=0;
    int points=0;
    String ConnectionResult="";
    RequestQueue mque;
    public String mID[];
    public String mHometeam[];
    public String mAwayteam[];
     public String mSkor[];
    public String mTarih[];
    public String mMS[];
    public String mNotif[];
    public String mHomepic[];
    public String mAwaypic[];
    public int counter=0;
public  String username;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();


        final StartMyOwnForeground[] startMyOwnForeground = {null};
        mque = Volley.newRequestQueue(this);
        CountDownTimer countDownTimer=new CountDownTimer(2115569260,1000) {
            @Override
            public void onTick(long l) {
                if(Build.VERSION.SDK_INT >Build.VERSION_CODES.O)
                {
                    MainActivity mainActivity=new MainActivity();

                    Log.e("User","asd"+username);
                    startMyOwnForeground[0] = new StartMyOwnForeground();
                    startMyOwnForeground[0].execute();

                }
                else

                    startForeground(1,new Notification());
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    int dene=0;
    @RequiresApi(Build.VERSION_CODES.O)
    public class  StartMyOwnForeground extends AsyncTask
    {


        @Override
        protected Object doInBackground(Object[] objects) {
            String NOTIFICATION_CHANNEL_ID = "example.permanence";
            String channelName = "Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.xml.custom_notif);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ExampleService.this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder

                    .build();
            startForeground(-1, notification);


                    //GetAll();
                    //Nots("a,a,a,a,a",""," "," ",",",",",1);
                    points=0;
                    tamam=0;
                    tamam2=0;
                    not=false;
                    List <String>Homes=new ArrayList<String>();
                    List <String>Aways=new ArrayList<String>();
                    List <String>Skor=new ArrayList<String>();
                    List <String>Tarih=new ArrayList<String>();
                    List <String>MS=new ArrayList<String>();
                    List <String>ID=new ArrayList<String>();
                    List <String>Notify=new ArrayList<String>();
                    List <String>Saatkaldı=new ArrayList<String>();

                    Homes.clear();
                    Aways.clear();
                    Skor.clear();
                    Tarih.clear();
                    MS.clear();
                    ID.clear();
                    Notify.clear();
                    Saatkaldı.clear();
                    GetTime();
                    GetWeek();
                    MaclarıGetir(Homes,Aways,Skor,Tarih,MS,ID,Notify);
                    try {


                        for (int i = 0; i < mHometeam.length; i++) {
                            GetBetween(date, time, saniye, mTarih[i]);

                            Log.e("Ta", mHometeam[i]);
                            Log.e("Timespan", timeSpan + "");
                            Log.e("saat", timesaat + "");
                            Log.e("dakika", timedakika + "");
                            Log.e("saniye", timesaniye + "");
                             Log.e("skor", mSkor[i] + "");
                            Log.e("ms", mMS[i] + "");
                            Log.e("not", mNotif[i] + "");

                            if (timeSpan == 0&&timesaat==1&&timedakika==0&&timesaniye<=3 )
                            Saatkaldı.add(String.valueOf(i));
                            if ( mMS[i].contains("-")&&!mNotif[i].equals("1")) {
                                tamam2 = i;
                            }
                        }
                        Log.e("Deneme","a"+Saatkaldı.get(0));
                        for(int abc=0;abc<Saatkaldı.size();abc++) {
                            GetBetween(date, time, saniye, mTarih[Integer.parseInt(Saatkaldı.get(abc))]);

                            if (timeSpan == 0 && timesaat == 1 && timedakika == 0 && timesaniye <= 3) {
                                int allahbelanıversin = Integer.parseInt(Saatkaldı.get(abc));
                                Nots(mHometeam[allahbelanıversin], mAwayteam[allahbelanıversin], mSkor[allahbelanıversin], "1 saat", Integer.parseInt(mID[allahbelanıversin]));
                            } else {
                                Log.e("Tamam", "__" + timeSpan + timedakika);

                            }
                        }
                        if ( mMS[tamam2].contains("-")&&!mNotif[tamam2].equals("1")) {
                            Nots2(mHometeam[tamam2], mAwayteam[tamam2],mSkor[tamam2],mMS[tamam2]);
                            UpdateNotify(mHometeam[tamam2], mAwayteam[tamam2]);
                        }

                    }catch (Exception e){
                        Log.e("Deneme",e.getLocalizedMessage());
                    }


            return null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Nots();
        username = intent.getStringExtra("username");
        startTimer();
        return START_STICKY;

        //startForeground(1,notification);



        //this.onDestroy();


        //do heavy work on a background thread

    }
    int idd=1;
    boolean not=false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("RestartService");
        broadcastIntent.setClass(this, RestartService.class);
        this.sendBroadcast(broadcastIntent);

    }
    private void readData() {
        try {
            FileInputStream fin = openFileInput("username.txt");
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }

            // setting text from the file.
            //editText.setText(temp.toString());
            //username=temp.toString();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void Nots(String home,String away,String skor,String kalansüre,int id){
        //Notification("Tahmininizi halâ değiştirebilirsiniz.",home+"-"+away+" maçına "+kalansüre+" kaldı.");
        String message;
        if(!skor.contains("-"))
            message="Tahmin yapmadınız.";
        else
            message="Tahmininiz:"+skor;
        createNotif(home+"-"+away+" maçına "+kalansüre+" kaldı.",message,id);



    }

    public void Nots2(String home,String away,String skor,String Ms){
        //Notification("Maç sonucu: "+Ms  ,home+"-"+away+" maçı tamamlandı.");
        PuanHesapla(skor,Ms);
        createNotif(home+"-"+away+" maçı tamamlandı.","MS: "+Ms+" Tahmininiz: "+skor+" Puan: "+points,89);

    }

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
                            String jsonArray3=response.getString("seconds");

                            time=jsonArray;
                            date=jsonArray2;
                            saniye=jsonArray3;
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
    public void GetBetween(String zaman, String saat,String saniye, String tarih2){
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat sdf2
                = new SimpleDateFormat(
                "dd MMMM yyyy|HH.mm.ss",new Locale("tr", "TR"));
        try {
            Date d1 = sdf.parse(zaman + " " + saat+":"+saniye);
            Date d2 = sdf2.parse(tarih2+".00");
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
            timesaniye=difference_In_Seconds;
        }catch (Exception ex)
        {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void MaclarıGetir(List<String> Homes,List<String> Aways, List<String> Skor,List<String> Tarih,List<String> MS,List<String>ID,List<String>Notify) {
        ArrayList<String> teamnames = new ArrayList<String>(20);
        teamnames.add( "Alanyaspor");
        teamnames.add( "Antalyaspor");
        teamnames.add( "Beşiktaş");
        teamnames.add( "Çaykur Rizespor");
        teamnames.add( "Fatih Karagümrük");
        teamnames.add( "Fenerbahçe");
        teamnames.add( "Galatasaray");
        teamnames.add( "Gaziantep");
        teamnames.add( "Göztepe");
        teamnames.add( "Hatayspor");
        teamnames.add( "İstanbul Başakşehir");
        teamnames.add( "Kasımpaşa");
        teamnames.add( "Kayserispor");
        teamnames.add( "Konyaspor");
        teamnames.add( "Sivasspor");
        teamnames.add( "Trabzonspor");
        teamnames.add( "Yeni Malatyaspor");
        teamnames.add( "Giresunspor");
        teamnames.add( "Altay");
        teamnames.add( "Adana Demirspor" );

        ArrayList<String> teamurlcodes = new ArrayList<String>(20);
        teamurlcodes.add("13_2");
        teamurlcodes.add( "21_1");
        teamurlcodes.add( "3_1");
        teamurlcodes.add( "46");
        teamurlcodes.add( "62");
        teamurlcodes.add( "2_1");
        teamurlcodes.add( "1_4");
        teamurlcodes.add( "67");
        teamurlcodes.add( "74");
        teamurlcodes.add( "78");
        teamurlcodes.add( "82");
        teamurlcodes.add( "90");
        teamurlcodes.add( "93_1");
        teamurlcodes.add( "101");
        teamurlcodes.add( "129");
        teamurlcodes.add( "139_1");
        teamurlcodes.add( "105");
        teamurlcodes.add( "72_1");
        teamurlcodes.add( "15");
        teamurlcodes.add( "6" );

        //ObservableList<Matches> matches=new ObservableArrayList<>();
        //List<Matches> Homes=new ArrayList<Matches>();
        List <String>Homespic=new ArrayList<String>();
        List <String>Awayspic=new ArrayList<String>();
//Matches matches=new Matches();
        //Spinner spinner=findViewById(R.id.spinner);

        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from "+ week+" order by Date asc";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){
                    ID.add(rst.getString("ID"));
                    Homes.add(rst.getString("Home"));
                    Aways.add(rst.getString("Away"));
                    if(username!=null)
                    Skor.add(rst.getString(username));
                    Tarih.add(rst.getString("Date"));

                    MS.add(rst.getString("MS"));
                    Notify.add(rst.getString("Notification"));

                    //matches.Time.set(rst.getString("Time"));
                    //matches.usersname.set(rst.getString("asıluser"));
                    //matches.MS.set(rst.getString("MS"));






                }

            }



        }

        catch (Exception ex){}
        mID= ID.stream().toArray(String[]::new);
        mHometeam= Homes.stream().toArray(String[]::new);
        mAwayteam= Aways.stream().toArray(String[]::new);
        mSkor= Skor.stream().toArray(String[]::new);
        mTarih= Tarih.stream().toArray(String[]::new);
        mMS= MS.stream().toArray(String[]::new);
        mNotif= Notify.stream().toArray(String[]::new);
        mHomepic= Homespic.stream().toArray(String[]::new);
        mAwaypic= Awayspic.stream().toArray(String[]::new);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    String result = "string.Empty";
@RequiresApi(api = Build.VERSION_CODES.O)
void Notyeni(){
    String NOTIFICATION_CHANNEL_ID = "example.permanence";
    String channelName = "New Notification2";
    NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
    chan.setLightColor(Color.BLUE);
    chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    assert manager != null;
    manager.createNotificationChannel(chan);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ExampleService.this, NOTIFICATION_CHANNEL_ID);
    Notification notification = notificationBuilder
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setSmallIcon(R.drawable.soccer)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .build();
manager.notify(1,notification);

}
    private void createNotif(String title,String message,int id3)
    {
        String id = "my_channel_id_01";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel =manager.getNotificationChannel(id);
            if(channel ==null)
            {
                channel = new NotificationChannel(id,"Channel Title2", NotificationManager.IMPORTANCE_NONE);
                channel.setLightColor(Color.BLUE);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                manager.createNotificationChannel(channel);

            }
        }
        Notification builder = new NotificationCompat.Builder(this,id)
                .setSmallIcon(R.drawable.soccer)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        //builder.setContentIntent(contentIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        //id to generate new notification in list notifications menu
        m.notify(id3,builder);

    }

    public void PuanHesapla(String skor,String mss){
    points=0;
        if (skor.equals( mss))
        {
            //skors.setTextColor( Color.GREEN);

            points = points + 3;
        }

        String ms = mss;
        String tahmin = skor;
        try
        {
            if (!ms.split("-")[0].equals(ms.split("-")[1]))
            {
                if (!tahmin.split("-")[0].equals(tahmin.split("-")[1]))
                {

                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == 1)
                    {
                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == 1)
                    {
                        points = points + 1;
                    }
                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == -1)
                    {
                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == -1)
                    {
                        points = points + 1;

                    }
                }
            }


        }
        catch (Exception ex)
        {

        }
    }

    public void UpdateNotify(String home,String away){
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "update " + week + " set " + "Notification" + "='true' where Home='" + home + "' and Away='" + away + "'";
                Statement st = connect.createStatement();
                st.executeQuery(query);
                connect.close();
            }
        } catch (Exception ex) {
        }
    }
    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                Log.i("Count", "=========  "+ (counter++));
            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}