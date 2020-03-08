package com.example.liemie;

import android.os.AsyncTask;

import java.io.IOException;

public class MyThread extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {
        Test test = new Test();

        try {
            return test.run(url[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Bug";
    }
}
