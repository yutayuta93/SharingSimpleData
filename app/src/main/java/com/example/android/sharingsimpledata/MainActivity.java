package com.example.android.sharingsimpledata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)){
                handleSendText(intent);
            }else if(type.startsWith("image/")){
                handleSendBinary(intent);
            }
        }else if(Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null){
            if(type.startsWith("image/")){
                handleMultiple(intent);
            }
        }else{

        }

    }


    public void sendMultiple(View view) {
        Intent sendIntent = new Intent();
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image_1));
        imageUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image_2));
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
        sendIntent.setType("image/*");
        startActivity(Intent.createChooser(sendIntent,"Share images to:"));



    }

    public void sendBinary(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        //Uri.parse()でStringをUriに変換し、putExtra(String,Parcelable)に入れている
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("android:resource://" + getPackageName
                () + "/" + R.raw.image_1));
        sendIntent.setType("image/*");
        startActivity(sendIntent);
    }

    public void sendText(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT,"This is my text to send");
        startActivity(Intent.createChooser(sendIntent,getResources().getString(R.string.send_to)));
    }

    void handleSendText(Intent intent){
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(text != null){
            TextView textView = (TextView) findViewById(R.id.hello_text);
            textView.setText(text);
        }
    }

    void handleSendBinary(Intent intent){
        Uri sharedUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(sharedUri != null){
            ImageView imageView = (ImageView)findViewById(R.id.inage1);
            imageView.setImageURI(sharedUri);
        }
    }

    void handleMultiple(Intent intent){
        ArrayList<Uri> sharedUriList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if(sharedUriList != null){
            ImageView image1 = (ImageView)findViewById(R.id.inage1);
            ImageView image2 = (ImageView)findViewById(R.id.image2);
            image1.setImageURI(sharedUriList.get(0));
            image2.setImageURI(sharedUriList.get(1));
        }
    }
}
