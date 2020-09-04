package com.example.internetverificacion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Network network;
    private WebView wvMain;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        network = new Network();
        getSupportActionBar().hide();

        // Verificamos conexion a internet cuando arranca la aplicacion
        if(network.isOnline(getApplicationContext())) {
            Log.d("TAG", "En linea");
            try {
                if (network.connectedToInternet()) {
                    Log.d("Tag", "Se puede navegar");
                } else {
                    Log.d("Tag", "No se puede navegar");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("TAG", "No en linea");
        }

        // WebView Settings
        wvMain = findViewById(R.id.wvMain);
        wvMain.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error", error.getDescription().toString());
            }
        });

        wvMain.getSettings().setJavaScriptEnabled(true);
        wvMain.loadUrl("https://google.com");

    }



    private void presentToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}