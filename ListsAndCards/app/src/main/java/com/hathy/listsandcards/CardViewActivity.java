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

        setContentView(R.layout.cardview_activity);
        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_position);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

    }
}
