package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeagueStandings extends AppCompatActivity {
    RequestQueue mque;
    ArrayList<HashMap<String, String>> contactList;
    String mSıra[];
    String mTakım[];
    String mPuan[];
    String mO[];
    String mG[];
    String mB[];
    String mM[];
    String mAG[];
    String mYG[];
    String mA[];
    String mPic[];
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_standings);
        mque= Volley.newRequestQueue(this);
        GetTime();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetAll();
            }
        }, 1000);
        Button btn=findViewById(R.id.btnsn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAll();
            }
        });
       // ListView listView = findViewById(R.id.listViewslstandings);
       // benimAdapter adapter = new benimAdapter(this, mSıra, mTakım, mPuan, mO, mG, mB, mM, mAG, mYG, mA);
        //listView.setAdapter(adapter);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GetAll(){
        GetTime();
        //TakımLogoları();
         ListView listView = findViewById(R.id.listViewslstandings);
         benimAdapter adapter = new benimAdapter(this, mSıra, mTakım, mPuan, mO, mG, mB, mM, mAG, mYG, mA,mPic);
        listView.setAdapter(adapter);
    }

    public void GetTime() {
        ArrayList<String> teamnames = new ArrayList<String>(19);
        teamnames.add( "Alanyaspor");
        teamnames.add( "Antalyaspor");
        teamnames.add( "Beşiktaş");
        teamnames.add( "Fatih Karagümrük");
        teamnames.add( "Fenerbahçe");
        teamnames.add( "Galatasaray");
        teamnames.add( "Gaziantep");
        teamnames.add( "Hatayspor");
        teamnames.add( "İstanbul Başakşehir");
        teamnames.add( "Kasımpaşa");
        teamnames.add( "Kayserispor");
        teamnames.add( "Konyaspor");
        teamnames.add( "Sivasspor");
        teamnames.add( "Trabzonspor");
        teamnames.add( "Giresunspor");
        teamnames.add( "Adana Demirspor" );
        teamnames.add( "MKE Ankaragücü");
        teamnames.add( "İstanbulspor");
        teamnames.add( "Ümraniyespor" );
        ArrayList<String> teamurlcodes = new ArrayList<String>(19);
        teamurlcodes.add("13_2");
        teamurlcodes.add( "21_1");
        teamurlcodes.add( "3_1");
        teamurlcodes.add( "62");
        teamurlcodes.add( "2_1");
        teamurlcodes.add( "1_4");
        teamurlcodes.add( "67");
        teamurlcodes.add( "78");
        teamurlcodes.add( "82");
        teamurlcodes.add( "90");
        teamurlcodes.add( "93_1");
        teamurlcodes.add( "101");
        teamurlcodes.add( "129");
        teamurlcodes.add( "139_1");
        teamurlcodes.add( "72_1");
        teamurlcodes.add( "6" );
        teamurlcodes.add( "F1713");
        teamurlcodes.add( "83");
        teamurlcodes.add( "15577" );
        List<String> Sıra=new ArrayList<String>();
        List<String> Takım=new ArrayList<String>();
        List<String> Puan=new ArrayList<String>();
        List<String> O=new ArrayList<String>();
        List<String> G=new ArrayList<String>();
        List<String> B=new ArrayList<String>();
        List<String> M=new ArrayList<String>();
        List<String> AG=new ArrayList<String>();
        List<String> YG=new ArrayList<String>();
        List<String> A=new ArrayList<String>();
        List<String> Pic=new ArrayList<String>();

        String URL = "https://raw.githubusercontent.com/R-Fatih/ACCDB/main/json.json";
        //RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contacts = response.getJSONArray("Teams");

                            // looping through All Contacts
                            for (int i = 0; i < 19; i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String sıra=c.getString("Sıra");
                                String takım = c.getString("Takım");
                                String puan = c.getString("Puan");
                                String o = c.getString("O");
                                String g = c.getString("G");
                                String b = c.getString("B");
                                String m = c.getString("M");
                                String aG = c.getString("AG");
                                String yG = c.getString("YG");
                                String a = c.getString("A");

                                Sıra.add(sıra);
                                Takım.add(takım);
                                Puan.add(puan);
                                O.add(o);
                                G.add(g);
                                B.add(b);
                                M.add(m);
                                AG.add(aG);
                                YG.add(yG);
                                A.add(a);
                                try {
                                    Pic.add(bein + teamurlcodes.get(teamnames.indexOf(c.getString("Takım"))) + ".png");
                                }catch (Exception e){}
                                // adding each child node to HashMap key => value


                                // adding contact to contact list
                            }

                            mSıra= Sıra.stream().toArray(String[]::new);
                            mTakım= Takım.stream().toArray(String[]::new);
                            mPuan= Puan.stream().toArray(String[]::new);
                            mO= O.stream().toArray(String[]::new);
                            mG= G.stream().toArray(String[]::new);
                            mB= B.stream().toArray(String[]::new);
                            mM= M.stream().toArray(String[]::new);
                            mAG= AG.stream().toArray(String[]::new);
                            mYG= YG.stream().toArray(String[]::new);
                            mA= A.stream().toArray(String[]::new);
                            mPic= Pic.stream().toArray(String[]::new);
                            TextView textView = findViewById(R.id.slstandstext);
                            textView.setText(mSıra[10]);
                            //TextView textView = findViewById(R.id.slstandstext);
                            //textView.setText(contactList.get(1).toString());
                            //DisplayDateTime.setText(time+" "+date);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());

                    }
                }
        );
        mque.add(objectRequest);
        //JSONObject jsonObject = new JSONObject(json);

    }
    class benimAdapter extends ArrayAdapter<String> {
        Context context;
        String rSıra[];
        String rTakım[];
        String rPuan[];
        String rO[];
        String rG[];
        String rB[];
        String rM[];
        String rAG[];
        String rYG[];
        String rA[];
        String rPic[];

        benimAdapter (Context c, String sıra[], String takım[],String puan[],String o[],String g[],String b[],String m[],String ag[],String yg[],String a[],String pic[]){
            super(c, R.layout.custom_listviewslstandings, R.id.sıranumarasıslstand, sıra);
            this.context = c;
            this.rSıra = sıra;
            this.rTakım = takım;
            this.rPuan = puan;
            this.rO = o;
            this.rG = g;
            this.rB = b;
            this.rM = m;
            this.rAG = ag;
            this.rYG = yg;
            this.rA = a;
            this.rPic=pic;
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listviewslstandings,parent,false);
            TextView sıra = satir.findViewById(R.id.sıranumarasıslstand);
            TextView takım = satir.findViewById(R.id.slstandsteam);
            TextView puan = satir.findViewById(R.id.slstandingpuan);
            TextView o = satir.findViewById(R.id.slstandsoynadı);
            TextView g = satir.findViewById(R.id.slstandingsgalip);
            TextView b = satir.findViewById(R.id.slstandingsberaberlik);
            TextView m = satir.findViewById(R.id.slstandingsmaglu);
            TextView ag = satir.findViewById(R.id.slstandingsattıgol);
            TextView yg = satir.findViewById(R.id.slstandingyedigol);
            TextView a = satir.findViewById(R.id.slstandingsavrj);
            ImageView resim=satir.findViewById(R.id.standsresim);
            RelativeLayout rlt =satir.findViewById(R.id.linearslstand);

            sıra.setText(rSıra[position]);
            takım.setText(rTakım[position]);

            puan.setText(rPuan[position]);
            o.setText(rO[position]);
            g.setText(rG[position]);
            b.setText(rB[position]);
            m.setText(rM[position]);
            ag.setText(rAG[position]);
            yg.setText(rYG[position]);
            a.setText(rA[position]);
try {
    Picasso.get().load(rPic[position]).into(resim);
}catch (Exception e){}
            //Picasso.get().load(rAwaypic[position]).into(awaypic);

if(position==0){
    rlt.setBackgroundColor(Color.parseColor("#B0EE90"));
rlt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "UEFA Şampiyonlar Ligi üçüncü eleme turu", Toast.LENGTH_LONG).show();
    }
});
}
            if(position==1||position==2){
                rlt.setBackgroundColor(Color.parseColor("#BBF3FF"));
                rlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "UEFA Şampiyonlar Ligi birinci eleme turu", Toast.LENGTH_LONG).show();
                    }
                });
            }


            if(position==15||position==16||position==17||position==18){
                rlt.setBackgroundColor(Color.parseColor("#FFBBBB"));
                rlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "1. Lig", Toast.LENGTH_LONG).show();
                    }
                });
            }
            rlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),TeamFixtures.class);
                    intent.putExtra("team",takım.getText());
                    startActivity(intent);
                }
            });
            return satir;



        }
    }

}


