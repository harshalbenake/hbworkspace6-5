package com.example.harshalbenake.loaderviewanimation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;

public class MainActivity extends AppCompatActivity {

    private int WAIT_DURATION = 5000;
    private DummyWait dummyWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }

    private void loadData() {
        if (dummyWait != null) {
            dummyWait.cancel(true);
        }
        dummyWait = new DummyWait();
        dummyWait.execute();
    }

    private void postLoadData() {
        ((TextView)findViewById(R.id.txt_name)).setText("Harshal Benake");
        ((TextView)findViewById(R.id.txt_title)).setText("Android Dev");
        ((TextView)findViewById(R.id.txt_phone)).setText("7588871488");
        ((TextView)findViewById(R.id.txt_email)).setText("harshalbenake@gmail.com");
        ((ImageView)findViewById(R.id.image_icon)).setImageResource(R.drawable.ic_launcher);
    }

    public void resetLoader(View view) {
        ((LoaderTextView)findViewById(R.id.txt_name)).resetLoader();
        ((LoaderTextView)findViewById(R.id.txt_title)).resetLoader();
        ((LoaderTextView)findViewById(R.id.txt_phone)).resetLoader();
        ((LoaderTextView)findViewById(R.id.txt_email)).resetLoader();
        ((LoaderImageView)findViewById(R.id.image_icon)).resetLoader();
        loadData();
    }

    class DummyWait extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(WAIT_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            postLoadData();
        }
    }

}
