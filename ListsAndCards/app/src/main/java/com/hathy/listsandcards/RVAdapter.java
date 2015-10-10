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

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
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

        final String about = persons.get(i).blurb;

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
