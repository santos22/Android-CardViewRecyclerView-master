package com.hathy.listsandcards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import com.squareup.picasso.Picasso;
import android.net.Uri;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements View.OnClickListener {

//    private ArrayList<Person> persons;
//    private Context mContext;
//
//    public RVAdapter(Context context, ArrayList<Person> persons) {
//        this.persons = persons;
//        this.mContext = context;
//    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        //private final Context context;


        PersonViewHolder(View itemView) {
            super(itemView);

            //context = itemView.getContext();

            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    List<Person> persons;
    private Context mContext;

    RVAdapter(Context context, List<Person> persons){
        this.persons = persons;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

//        personViewHolder.personPhoto = imageView;

        //String uri = "@drawable/myresource";  // where myresource.png is the file
        // extension removed from the String

//        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//
//        imageview= (ImageView)findViewById(R.id.person_photo);
//        Drawable res = getResources().getDrawable(imageResource);
//        imageView.setImageDrawable(res);

//        URL url = new URL(persons.get(i).image);
//
//        Uri uri = Uri.parse(persons.get(i).image);
//
//        InputStream stream = getContentResolver().openInputStream(uri);
//
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        img_downloaded.setImageBitmap(bmp);
//
        //ImageView imageView = (ImageView) findViewById(R.id.person_photo);

        //final String name = persons.get(i).name;
        final String about = persons.get(i).blurb;

        //new ImageLoadTask(persons.get(i).image, imageView).execute();

        //String url = persons.get(i).image;

        Picasso.with(mContext)
                .load(persons.get(i).image)
                .fit().centerCrop()
                .placeholder(R.drawable.csmall)
                .centerCrop()
                .noFade()
                .into(personViewHolder.personPhoto);

        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), CleverInfo.class);
                //you can pass on the Pojo with PARCELABLE
                //Toast.makeText(v.getContext(), name, Toast.LENGTH_LONG).show();
                Toast.makeText(v.getContext(), about, Toast.LENGTH_LONG).show();
                //v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
