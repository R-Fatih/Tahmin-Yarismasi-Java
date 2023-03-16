package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Standings extends AppCompatActivity {
    String bein = "https://media01.tr.beinsports.com/img/teams/1/";
    ListView listView;
    String mKullanıcı[];
    String mToplam[];
    String mEyt[];
    String mResim[];
    String username,team;
    Connection connect;
    String ConnectionResult="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Listeyigetir();
        //textView.setText("username");
        listView = findViewById(R.id.listView);
        benimAdapter adapter = new benimAdapter(this,mKullanıcı,mToplam,mEyt,mResim);
        listView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

    }

    class benimAdapter extends ArrayAdapter<String> {
        Context context;
        String rBaslik[];
        String rAciklama[];
        String rEyt[];
        String rResim[];

        benimAdapter (Context c, String baslik[], String Aciklama[],String eyt[],String resim[]){
            super(c, R.layout.custom_listview, R.id.homteamtext, baslik);
            this.context = c;
            this.rBaslik = baslik;
            this.rAciklama = Aciklama;
            this.rEyt = eyt;
            this.rResim = resim;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View satir = layoutInflater.inflate(R.layout.custom_listview,parent,false);
            TextView benimBaslik = satir.findViewById(R.id.homteamtext);
            TextView benimAciklama = satir.findViewById(R.id.totalpoints);
            TextView eyt = satir.findViewById(R.id.awayteamtext);
            Button btn=satir.findViewById(R.id.matchdetails);
            RelativeLayout rlt =satir.findViewById(R.id.linear1);
            TextView sıra=satir.findViewById(R.id.sıranumarası);
            ImageView img=satir.findViewById(R.id.standingstakım);
           /* ImageButton imageButton=satir.findViewById(R.id.addfriend);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        ConnectionHelper connectionHelper = new ConnectionHelper();
                        connect = connectionHelper.connectionclass();
                        if (connect != null) {
                            String query = "update Standings set Friends=Friends"+","+rBaslik[position]+" where UserName="+"'"+username+"'";
                            Statement st = connect.createStatement();
                            st.executeQuery(query);
                            connect.close();
                        }
                    } catch (Exception ex) {
                    }
                }
            });*/
            if(rBaslik[position].length()>12)
            benimBaslik.setText(rBaslik[position].substring(0,12)+"...");
            else
                benimBaslik.setText(rBaslik[position]);
            benimAciklama.setText(rAciklama[position]);
            sıra.setText(String.valueOf(position+1));
            eyt.setText(rEyt[position]);
            if(username.toLowerCase(Locale.ROOT).equals(rBaslik[position].toLowerCase(Locale.ROOT))){
                rlt.setBackgroundColor(Color.GRAY);
            }

            Picasso.get().load(bein+rResim[position]+".png").into(img);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
Intent intent=new Intent(Standings.this,MatchDetails.class);
intent.putExtra("username",rBaslik[position]);
startActivity(intent);
    }
});
            return satir;



        }
    }

    public class User
    {
        public  String UserName;
        public  String Eytpoints;
        public  String Points;

        public User(String home, String away, String date) {
            UserName = home;
            Eytpoints = away;
            Points = date;

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Listeyigetir(){
        List<String> Homes=new ArrayList<String>();
        List<String> Aways=new ArrayList<String>();
        List<String> Eyt=new ArrayList<String>();
        List<String> Resim=new ArrayList<String>();

        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from Standings ORDER BY TotalPoints desc,EytPoints asc";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){

                    Homes.add(rst.getString("UserName"));
                    Eyt.add(rst.getString("EytPoints"));
                    Aways.add(rst.getString("TotalPoints"));
                    Resim.add(rst.getString("Team"));

                    //matches.Time.set(rst.getString("Time"));
                    //matches.usersname.set(rst.getString("asıluser"));
                    //matches.MS.set(rst.getString("MS"));


                }
                mKullanıcı= Homes.stream().toArray(String[]::new);
                mToplam= Aways.stream().toArray(String[]::new);
                mEyt= Eyt.stream().toArray(String[]::new);
                mResim= Resim.stream().toArray(String[]::new);

            }
        }
        catch (Exception ex){}
    }
}