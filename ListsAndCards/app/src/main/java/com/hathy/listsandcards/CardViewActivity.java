package com.hathy.listsandcards;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class CardViewActivity extends Activity {

    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String imageUrl = "https://www.baby-connect.com/images/baby2.gif";
//
//        Picasso.with(getApplicationContext()).load(imageUrl)
//                .placeholder(R.drawable.images).error(R.drawable.ic_launcher)
//                .into(imageView);
        ImageView imageView = (ImageView) findViewById(R.id.person_photo);

        Picasso.with(this)
                .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
                .into(imageView);

        setContentView(R.layout.cardview_activity);
        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

        personName.setText("Emma Wilson");
        personAge.setText("23 years old");
        personPhoto.setImageResource(R.drawable.emma);
    }
}
