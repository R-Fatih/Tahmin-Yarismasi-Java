package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statics extends AppCompatActivity {
    RequestQueue mque,mque2;
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    String mTakım[];
    String mSayı[];
    String mPic[];
    String mSıra[];
    String mOyuncu[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);
        TabLayout tabLayout=findViewById(R.id.tabloya);
        ViewPager viewPager=findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter=new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.AddFragment(new Fragment1(),"En Çok Gol Atanlar");
        vpAdapter.AddFragment(new Fragment2(),"En Çok Gol Yiyenler");
        vpAdapter.AddFragment(new Fragment3(),"En Başarılı pas");
        vpAdapter.AddFragment(new Fragment4(),"Gol Krallığı");
        vpAdapter.AddFragment(new Fragment5(),"Asist Krallığı");

        viewPager.setAdapter(vpAdapter);
        mque= Volley.newRequestQueue(this);
        GetStats("encokgolatan.json");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Adatpe(1);
            }
        }, 1000);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0) {
                    GetStats("encokgolatan.json");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Adatpe(1);
                        }
                    }, 1000);
                }
                if(tab.getPosition()==1){
                GetStats("encokgolyiyen.json");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Adatpe(2);
                    }
                }, 1000);
            }
                if(tab.getPosition()==2) {
                    GetStats("baspas.json");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Adatpe(3);
                        }
                    }, 1000);
                }
                if(tab.getPosition()==3) {
                    GetStats("golkral.json");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Adatpe(4);
                        }
                    }, 1000);
                }
                if(tab.getPosition()==4) {
                    GetStats("asistkral.json");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Adatpe(5);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void GetStats(String name){


            String URL = "https://raw.githubusercontent.com/R-Fatih/ACCDB/main/"+name;
            //RequestQueue requestQueue= Volley.newRequestQueue(this);
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
            List<String> Takım=new ArrayList<String>();
            List<String> Sıra=new ArrayList<String>();
            List<String> Sayı=new ArrayList<String>();
            List<String> Oyuncu=new ArrayList<String>();
            List<String> Pic=new ArrayList<String>();
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
                                for (int i = 0; i < 10; i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    String sıra=c.getString("Sıra");
                                    String takım = c.getString("Takım");
                                    String sayı = c.getString("Sayı");


                                    Sıra.add(sıra);
                                    Takım.add(takım);
                                    Sayı.add(sayı);
                                    Pic.add(bein + teamurlcodes.get(teamnames.indexOf(c.getString("Takım"))) + ".png");
                                    if(name.equals("golkral.json") || name.equals("asistkral.json")){
                                        String oyuncu = c.getString("Oyuncu");
                                        Oyuncu.add(oyuncu);
                                    }
                                    // adding each child node to HashMap key => value


                                    // adding contact to contact list
                                }

                                mSıra= Sıra.stream().toArray(String[]::new);
                                mTakım= Takım.stream().toArray(String[]::new);
                                mSayı= Sayı.stream().toArray(String[]::new);
                                mPic= Pic.stream().toArray(String[]::new);
                                mOyuncu=Oyuncu.stream().toArray(String[]::new);
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



    }
    public void Adatpe(int listview){
        ListView listView = null;
        if(listview==1)
        listView = findViewById(R.id.listviewstatsfr1);
        if(listview==2)
            listView = findViewById(R.id.listviewstatsfr2);
        if(listview==3)
            listView = findViewById(R.id.listviewstatsfr3);
        if(listview==4)
            listView = findViewById(R.id.listviewstatsfr4);
        if(listview==5)
            listView = findViewById(R.id.listviewstatsfr5);
        benimAdapter adapter = new benimAdapter(this, mSıra, mTakım,mSayı,mPic,mOyuncu);
        listView.setAdapter(adapter);
    }
    class benimAdapter extends ArrayAdapter<String> {
        Context context;
        String rSıra[];
        String rTakım[];
        String rSayı[];
        String rPic[];
        String rOyuncu[];

        benimAdapter (Context c, String sıra[], String takım[],String sayı[],String pic[],String oyuncu[]){
            super(c, R.layout.custom_listviewstats, R.id.statssıra, sıra);
            this.context = c;
            this.rSıra = sıra;
            this.rTakım = takım;
            this.rSayı = sayı;
            this.rPic=pic;
            this.rOyuncu=oyuncu;
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listviewstats,parent,false);
            TextView sıra = satir.findViewById(R.id.statssıra);
            TextView takım = satir.findViewById(R.id.statstakım);
            TextView sayı = satir.findViewById(R.id.statssayı);
            ImageView resim=satir.findViewById(R.id.statsresim);

            sıra.setText(rSıra[position]);
            takım.setText(rTakım[position]);
            sayı.setText(rSayı[position]);
            Picasso.get().load(rPic[position]).into(resim);
            try{
                takım.setText(rOyuncu[position]);
            }catch (Exception e){}
            //Picasso.get().load(rAwaypic[position]).into(awaypic);



            return satir;



        }
    }

}