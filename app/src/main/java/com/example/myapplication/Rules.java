package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView textView=findViewById(R.id.textalpuan);
        EditText editText=findViewById(R.id.edittextdenemeskor);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ms = "4-1";
                String tahmin = editText.getText().toString();
                if ((ms.equals(tahmin)))
                {

                    textView.setText("Alacağınız puan: 3");
                }


                   else if (!ms.split("-")[0].equals(ms.split("-")[1]))
                    {try
                    {
                        if (!tahmin.split("-")[0].equals(tahmin.split("-")[1]))
                        {

                            if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == 1)
                            {
                                textView.setText("Alacağınız puan: 1");


                            }
                            if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == 1)
                            {
                                textView.setText("Alacağınız puan: 1");


                            }
                            if (ms.split("-")[0].equals(tahmin.split("-")[0]) && Integer.parseInt(ms.split("-")[1]) - Integer.parseInt(tahmin.split("-")[1]) == -1)
                            {
                                textView.setText("Alacağınız puan: 1");


                            }
                            if (ms.split("-")[1].equals(tahmin.split("-")[1]) && Integer.parseInt(ms.split("-")[0]) - Integer.parseInt(tahmin.split("-")[0]) == -1)
                            {
                                textView.setText("Alacağınız puan: 1");


                            }
                        }
                    }



                catch (Exception ex)
                {

                }}
                else
                textView.setText("Alacağınız puan: 0");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}