package com.hathy.listsandcards;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewActivity extends Activity {

    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ImageView imageView = (ImageView) findViewById(R.id.person_photo);

        setContentView(R.layout.cardview_activity);
        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_position);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

//        personName.setText("Emma Wilson");
//        personAge.setText("23 years old");
//        personPhoto.setImageResource(R.drawable.emma);
    }
}
