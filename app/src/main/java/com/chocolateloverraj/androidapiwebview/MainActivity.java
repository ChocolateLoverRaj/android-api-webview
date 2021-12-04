package com.chocolateloverraj.androidapiwebview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView openText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openText = findViewById(R.id.openText);
    }

    public void onOpen(View view) {
        System.out.println(openText.getText());
        Intent intent = new Intent(this, PageViewer.class);
        Uri uri = Uri.parse(openText.getText().toString());
        if (uri.getScheme() == null)
            uri = Uri.fromParts("android-api-webview", uri.getPath(), uri.getFragment());
        else if (!uri.getScheme().equals("https")) {
            Toast.makeText(this, "Url must be https", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(uri);
        System.out.println(uri.getScheme() + ", " + uri.getHost() + ", " + uri.getPath() + ", " + uri.getFragment());
        intent.setData(uri);
        startActivity(intent);
    }
}