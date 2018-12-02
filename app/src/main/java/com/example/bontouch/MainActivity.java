package com.example.bontouch;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<WordItem> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillWordList();

        AutoCompleteTextView editText = findViewById(R.id.actv);
        AutoCompleteWordAdapter adapter = new AutoCompleteWordAdapter(this, wordList);
        editText.setAdapter(adapter);
    }

    private void fillWordList() {
        wordList = new ArrayList<>();
        try {
            URL url = new URL("http://runeberg.org/words/ss100.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
            String str;
            while ((str = in.readLine()) != null) {
                wordList.add(new WordItem(str));
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}