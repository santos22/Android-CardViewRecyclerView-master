package com.hathy.listsandcards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements View.OnClickListener {

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        // initialize views that belong to the items of our RecyclyerView
        PersonViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_position);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Cleveran> persons;
    private Context mContext;

    // constructor for custom adapter is given a handle to
    // the data that RecyclerView displays
    RVAdapter(Context context, List<Cleveran> persons){
        this.persons = persons;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // gets ViewHolder used for the item at given position
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onClick(View v) {
    }

    // called when views need to be created from the given ViewHolder
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        // each employee is a ViewHolder (sets name and job position)
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).position);

        final String about = persons.get(i).blurb;

        // load and set Clever employee images
        Picasso.with(mContext)
                .load(persons.get(i).image)
                .fit().centerCrop()
                .placeholder(R.drawable.csmall)
                .centerCrop()
                .noFade()
                .into(personViewHolder.personPhoto);

        // display employee About blurb on touch
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), about, Toast.LENGTH_LONG);

                // center toast message
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    // returns number of data items
    @Override
    public int getItemCount() {
        return persons.size();
    }
}
