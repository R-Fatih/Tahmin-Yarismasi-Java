package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;
import com.squareup.picasso.Picasso;

import net.sourceforge.jtds.jdbc.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

public class WeekMatches2 extends AppCompatActivity {
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    String weeknumber;
    int points = 0;
    DateTime zaman;
    int seconds = 300;
    int tahminmac;
  List<Match> matches=new ArrayList<Match>();

    ProgressDialog p;

    List<String> weeks=new ArrayList<>();
    List<String> weeks2=new ArrayList<>();

    //int mTarih[];
    private Timer timer;
    String week;
    Connection connect;
    String ConnectionResult="";
    Button btn;
    RequestQueue mque,mque2;
    String datecurrent;
    String time,date,username;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_matches2);
        Intent intent = getIntent();
        points=0;
Button btnkaydet=findViewById(R.id.kaydetbutton);
        GetUsers();
        //datecurrent = dateFormat.format(date);
        //DisplayDateTime.setText(time+" "+date);
        Locale loc = new Locale("tr", "TR");
        Date date = new Date();


        // DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",loc);
      //  Date d2=dateFormat.parse(date);
        DateFormat dateFormat2 = new SimpleDateFormat("dd MMMM yyyy",loc);

        datecurrent = dateFormat2.format(date);
new Ftp2().execute();

btnkaydet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        GetAll();
        Intent intent = new Intent(WeekMatches2.this, Others.class);
        startActivity(intent);
    }
});
ImageView btngrsave=findViewById(R.id.gerceksavebutton);
btngrsave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       new  SaveMatches(getApplicationContext()).execute();
        //Toastms();

    }
});
        username = intent.getStringExtra("username");
        mque=Volley.newRequestQueue(this);
        mque2=Volley.newRequestQueue(this);
        TextView textView=findViewById(R.id.idcountdown);
        spinner=findViewById(R.id.spinner);


        DayScrollDatePicker  mPicker = (DayScrollDatePicker) findViewById(R.id.day_date_picker);
        mPicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                Locale loc = new Locale("tr", "TR");
                 //datecurrent = dateFormat.format(date);
                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy",loc);
                 datecurrent = dateFormat.format(date);

                points=0;

                GetAll();
                //Toast.makeText(WeekMatches2.this, strDate, Toast.LENGTH_SHORT).show();

            }
        });
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {

                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                textView.setText("00:00:00");
            Intent intent1=new Intent(getApplicationContext(),UserPlatform.class);
            intent1.putExtra("username",username);
            if(isRunning(getApplicationContext()))
            startActivity(intent1);
                        }
        }.start();
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
    public static boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }
        return false;
    }
    public class SaveMatches extends AsyncTask{
        private Context mContext;

        public SaveMatches (Context context){
            mContext = context;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            p.cancel();
            Toastms();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(WeekMatches2.this);
            p.setMessage("Maçlar kaydediliyor.");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            for (int i = 0; i < matches.size(); i++) {

                if (!(matches.get(i) ==null)) {
                    try {

                        ConnectionHelper connectionHelper = new ConnectionHelper();
                        connect = connectionHelper.connectionclass();
                        if (connect != null) {
                            String query = "update allmatche set " + username + "='" + matches.get(i).username.toString() + "' where ID='"+ matches.get(i).id +"'";
                            Log.e("doInBackground: ", matches.get(i).id);
                            Statement st = connect.createStatement();
                            st.executeQuery(query);
                            connect.close();
                        }
                    } catch (Exception ex) {
                        Log.e( "doInBackground: ", ex.getMessage());
                    }
                }else
                    Log.e( "doInBackground: ", "anlama");
            }
            //Log.e( "doInBackground: ", String.valueOf(mSkor.length));
            return null;
        }
    }
    int tane = 0;
    char kara=' ';
    StringBuilder sb=new StringBuilder();

    int tane4 = 0;
    char kara4=' ';
    StringBuilder sb4=new StringBuilder();

    int tane2 = 0;
    char kara2=' ';
    ArrayList<String> teamurlcodes = new ArrayList<String>();
    ArrayList<String> teamnames = new ArrayList<String>();
    StringBuilder sb2=new StringBuilder();
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
            GetAll();

        }
    }
    int tane3 = 0;
    char kara3=' ';
    StringBuilder sb3=new StringBuilder();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void GetUsers(){

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GetAll(){

        GetTime();
        MaclarıGetir();
        //TakımLogoları();
        ListView listView = findViewById(R.id.week2list);
       // makeright();
        benimAdapter adapter = new benimAdapter(this);
        listView.setAdapter(adapter);


    }

void makeright() {
    //ExpandableListView listView = findViewById(R.id.week2list);
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    Resources r = getResources();
    int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            50, r.getDisplayMetrics());
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
        //listView.setIndicatorBounds(width - px, width);
    } else {
        //listView.setIndicatorBoundsRelative(width - px, width);

    }

}
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void MaclarıGetir() {
        matches.clear();

        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from allmatche where Date LIKE '%"+datecurrent+"%' order by Date asc";
                Log.e( "MaclarıGetir: ",query );
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){
Match match=new Match(
                    rst.getString("Home").substring(0,3).toUpperCase(),
                    rst.getString("Away").substring(0,3).toUpperCase(),
                    rst.getString("Date"),
        rst.getString("ID"),
       rst.getString("MS"),
                   rst.getString(username),
         bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Home"))) + ".png",
                   bein + teamurlcodes.get(teamnames.indexOf(rst.getString("Away"))) + ".png");
                matches.add(match);
                }

            }



        }

        catch (Exception ex){Log.e("Mes",ex.getMessage());}


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

                            Log.e("a",datecurrent);
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
@RequiresApi(api = Build.VERSION_CODES.N)
public String GetOthers(String home, String away, String usr){
    List <String>Skor=new ArrayList<String>();
String skor="";
    try{
        ConnectionHelper connectionHelper=new ConnectionHelper();
        connect=connectionHelper.connectionclass();
        if(connect!=null){
            String query="Select * from "+spinner.getSelectedItem().toString().split("-")[1]+" where Home='"+home+"' and Away='"+away+"'";
            Statement st=connect.createStatement();
            ResultSet rst=st.executeQuery(query);
            while (rst.next()) {
                skor=rst.getString(usr);
                //Log.e("SKORBA","den"+rst.getString(usr));
            }
            }
}catch (SQLException e){Log.e("Sqlspin",e.getLocalizedMessage());}
    if(skor==null)
        skor="-";
    return skor ;
}
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void PuanHesaplaması(String zaman, String saat, String tarih2, int i, EditText skors, TextView msss) {
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
        //puanlar2.setText(String.valueOf(timeSpan)+"  "+String.valueOf(timesaat));
        //int timesaat = Convert.ToInt32(listView.Items[i].SubItems[2].Text.Split('|')[1].ToString().Split('.')[0]) - zaman.Hour;
        //int timedakika = Convert.ToInt32(listView.Items[i].SubItems[2].Text.Split('|')[1].ToString().Split('.')[1]) - zaman.Minute;
        // int timesaat= (int) TimeUnit.MILLISECONDS.toHours(mDate22.getTime() - mDate33.getTime());
        if (timeSpan < 0)
        {
            skors.setEnabled( false);

        }

        else if (timeSpan == 0 && timesaat <= 0&&timedakika <= 0)
        {
            skors.setEnabled( false);
        }else{
            skors.setEnabled( true);

        }



        String ms = msss.getText().toString();
        String tahmin = skors.getText().toString();
        if ((ms.equals(tahmin)))
        {
            skors.setTextColor( Color.rgb(0, 241, 85));

            points = points + 3;
        }
        try
        {
            if (!ms.split("-")[0].equals(ms.split("-")[1]))
            {
                if (!tahmin.split("-")[0].equals(tahmin.split("-")[1]))
                {

                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == 1)
                    {
                        skors.setTextColor(Color.YELLOW);

                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == 1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == -1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == -1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                }
            }


        }
        catch (Exception ex)
        {

        }
        //TextView txt=findViewById(R.id.totalponts);
        //txt.setText(String.valueOf(points));
        //puanlar2.setText("Bu hafta kazanılan puan:"+String.valueOf(points));

    }
    private void PuanHesaplaması2(TextView skors, String msss) {
        //TextView       puanlar2=findViewById(R.id.puanlar);
        skors.setTextColor(Color.BLACK);



        String ms = msss.toString();
        String tahmin = skors.getText().toString();
        if ((ms.equals(tahmin)))
        {
            skors.setTextColor( Color.rgb(24,255,255));

            points = points + 3;
        }
        try
        {
            if (!ms.split("-")[0].equals(ms.split("-")[1]))
            {
                if (!tahmin.split("-")[0].equals(tahmin.split("-")[1]))
                {

                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == 1)
                    {
                        skors.setTextColor(Color.YELLOW);

                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == 1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                    if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == -1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                    if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == -1)
                    {
                        skors.setTextColor(Color.YELLOW);
                        points = points + 1;

                    }
                }
            }


        }
        catch (Exception ex)
        {

        }
        //TextView txt=findViewById(R.id.totalponts);
        //txt.setText(String.valueOf(points));
        //puanlar2.setText("Bu hafta kazanılan puan:"+String.valueOf(points));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    class benimAdapter extends ArrayAdapter {

        Context context;
        benimAdapter (Context c){

            super(c, R.layout.custom_listviewmatches2, R.id.homteamtext, matches);
            this.context = c;

        }


        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View satir, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             satir = layoutInflater.inflate(R.layout.custom_listviewmatches2,parent,false);
            TextView home = satir.findViewById(R.id.hometeamisim);
            TextView away = satir.findViewById(R.id.awayteamisim);
            EditText skor=satir.findViewById(R.id.skor);

            TextView tarih = satir.findViewById(R.id.tarihsaat);
            TextView ms = satir.findViewById(R.id.macsounucu);
            ImageView homepic=satir.findViewById(R.id.homeresim);
            ImageView awaypic=satir.findViewById(R.id.awayresim);


            home.setText(matches.get(position).home);
            away.setText(matches.get(position).away);

            skor.setText(matches.get(position).username);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),TeamFixtures.class);
                    intent.putExtra("team",home.getText());
                    startActivity(intent);
                }
            });
            away.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),TeamFixtures.class);
                    intent.putExtra("team",away.getText());
                    startActivity(intent);
                }
            });
            tarih.setText(matches.get(position).date.substring(matches.get(position).date.toString().indexOf('|')+1,matches.get(position).date.toString().length()));
            ms.setText(matches.get(position).ms);
            Picasso.get().load(matches.get(position).hometeamid).into(homepic);
            Picasso.get().load(matches.get(position).awayteamid).into(awaypic);
            PuanHesaplaması(date,time,matches.get(position).date,position,skor,ms);
           //s1h.setText(mS1[1]);

            skor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Spinner spinner = findViewById(R.id.spinner);

                    matches.get(position).setUsername( String.valueOf(skor.getText()));//GetAll();

                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            return satir;



        }}




    @RequiresApi(api = Build.VERSION_CODES.N)
    private void TakımLogoları() {

    }
    public void SetChilds(){

    }
    public void Toastms() {
        Toast.makeText(this, "Maç(lar) başarıyla kaydedildi.", Toast.LENGTH_LONG).show();


    }

    }


