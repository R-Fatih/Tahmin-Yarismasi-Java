package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.icu.util.Output;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private String filename = "username.txt";
    private String filename2 = "pass.txt";
    private static final int REQUEST_CODE = 100;
    private static final String ONESIGNAL_APP_ID = "a6b5fc88-8cb1-40a6-9ac2-d3225f3e1140";
    RequestQueue mque;
    private DownloadManager downloadManager;
    private long downloadReference;
    EditText editText,editText2;
    TextView textView,textView2;
    Connection connect;
    String ConnectionResult="";
    String sizeapp;
    SharedPreferences sharedPref;
    Intent mServiceIntent;
    public String updatenotes;
    String versionNumber;
    Timer timer = new Timer();
public String gelenuser;
Button btn3;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            for(int i=0;i<17;i++) {

                File f = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/2.0." + i + ".apk")));
                if (f.exists())
                    f.delete();
            }
        }catch (Exception e){}
        editText=(EditText) findViewById(R.id.editTextTextPersonName);
        editText2=(EditText) findViewById(R.id.editTextTextPersonName3);
        mque= Volley.newRequestQueue(this);
        readData();
        OneSignalSdk();


        //readFiletxt();
sharedPref= this.getPreferences(Context.MODE_PRIVATE);
        btn3=findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Others.class);
                startActivity(intent);            }
        });

        textView=findViewById(R.id.guncelsurum);
        textView2=findViewById(R.id.guncelsurum2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
GetVersion();
        textView.setText(versionNumber);
        textView2.setText(BuildConfig.VERSION_NAME);

        UpdateNotes();




    }

    @Override
    protected void onResume() {
        super.onResume();

    } int ch;
    String a;
    String line;
    StringBuilder str=new StringBuilder();


    public void ShowHidePass(View view ){
        editText2=(EditText) findViewById(R.id.editTextTextPersonName3);
Button btn=findViewById(R.id.show_pass_btn);

            if(editText2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                //Show Password
                editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{

                //Hide Password
                editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());


        }
    }
    public void UpdateNotes(){
        String URL = "https://raw.githubusercontent.com/R-Fatih/ACCDB/main/updatenotes.json";
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
                            JSONArray contacts = response.getJSONArray("Notes");

                            // looping through All Contacts
                                JSONObject c = contacts.getJSONObject(0);
                                updatenotes=c.getString("Not");
                                Log.e("de",updatenotes);


                                // adding each child node to HashMap key => value


                                // adding contact to contact list



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
        // sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        String savedString = sharedPref.getString(BuildConfig.VERSION_NAME+"bo","0");
        Log.e("bool", String.valueOf(savedString));
       // Log.e("ver", String.valueOf(versionNumber));

        if(savedString.equals("0")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                             AlertNots();   }
            }, 1000);
             //sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(BuildConfig.VERSION_NAME+"bo","1"); //string değer ekleniyor
            editor.commit();

        }
    }
    public void GetVersion(){
        String URL = "https://raw.githubusercontent.com/R-Fatih/ACCDB/main/version.json";
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
                            JSONArray contacts = response.getJSONArray("Version");

                            // looping through All Contacts
                            JSONObject c = contacts.getJSONObject(0);
                            versionNumber=c.getString("V");
                            Log.e("V",versionNumber);

                            // adding each child node to HashMap key => value


                            // adding contact to contact list



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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    //btn3.setVisibility(View.VISIBLE);
                    AlterVersion();
                }
        }, 4000);

        mque.add(objectRequest);
        // sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);




        }

public void AlterVersion(){
        try {
            if (!versionNumber.equals(BuildConfig.VERSION_NAME)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Yeni güncelleme var.");
                builder.setPositiveButton("İndir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Download(getApplicationContext());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }
                });
                builder.create().show();
            }
        }catch (Exception e){}}
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void OneSignalSdk() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        //OneSignal.sendTag();

    }
    public void AlertNots(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("v "+versionNumber+" güncelleme notları");
        builder.setMessage(updatenotes);
        builder.create().show();

    }
    public void LoginSql(View view){


        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from Users where UserName='"+editText.getText().toString()+"' and Password='"+editText2.getText().toString()+"'";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                if(rst.next()){
                    writeData();
                    Intent intent = new Intent(MainActivity.this, UserPlatform.class);
                    intent.putExtra("username",editText.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex){}
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void GoReg(View view){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
    private void writeData() {

        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String data = editText.getText().toString();
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            FileOutputStream fos = openFileOutput(filename2, Context.MODE_PRIVATE);
            String data = editText2.getText().toString();
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readData() {
        try {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }

            // setting text from the file.
            editText.setText(temp.toString());
            gelenuser=temp.toString();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fin = openFileInput(filename2);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }

            // setting text from the file.
            editText2.setText(temp.toString());
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    private void requestPermission(){

    }
    public void ChechUpdate(){
        try{
            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect=connectionHelper.connectionclass();
            if(connect!=null){
                String query="Select * from Version";
                Statement st=connect.createStatement();
                ResultSet rst=st.executeQuery(query);
                while (rst.next()){


                    versionNumber=rst.getString("VersionNumber");

                }


            }
        }
        catch (Exception ex){}
    }


    public void Download(Context context) throws FileNotFoundException {
        File f = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/"+versionNumber+".apk")));
        if (f.exists())
            f.delete();

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse("https://github.com/R-Fatih/ACCDB/blob/main/app-debug.apk?raw=true");
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("KK Yeni sürüm");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDescription("KK Yeni sürüm indiriliyor.");
        request.allowScanningByMediaScanner();
        if(versionNumber!=null)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, versionNumber+".apk");
        else
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "new.apk");

        //Enqueue a new download and same the referenceId
        downloadReference = downloadManager.enqueue(request);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                //BroadcastReceiver on Complete
                if (f.exists()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri apkUri = FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".fileprovider", f);
                        intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                        intent.setData(apkUri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        Uri apkUri = Uri.fromFile(f);
                        intent = new Intent(Intent.ACTION_VIEW);
                        //intent.setDataAndType(apkUri, manager.getMimeTypeForDownloadedFile(downloadId));
                        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    startActivity(intent);
                }else{

                }
                context.unregisterReceiver(this);
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                int file_size = Integer.parseInt(String.valueOf(f.length()/1024));
Log.e("s", String.valueOf(file_size));
Log.e("s", String.valueOf(sizeapp));

                //t

                }


            @Override
            public void onFinish() {

            }
        }.start();
    }


}