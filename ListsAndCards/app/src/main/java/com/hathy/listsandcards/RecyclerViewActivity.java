package com.hathy.listsandcards;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.util.Log;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.squareup.picasso.Picasso;

// http://stackoverflow.com/questions/30731121/how-can-i-return-an-arraylist-from-my-asynctask

public class RecyclerViewActivity extends Activity {

    static class Cleverian {
        String name;
        String position;
        String image;
        String paragraph;
    }

    private ArrayList<Person> persons;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        String siteUrl = "https://clever.com/about/";
        new ParseURL().execute(new String[]{siteUrl});

        //Picasso.with(getApplicationContext()).load("https://prismic-io.s3.amazonaws.com/clever/1479b7352e83c6f633cbcfe7ff995b4302650c25_dan.png").into(cleverImage);
        //myDrawable = cleverImage.getDrawable();

        //initializeData();
        //initializeAdapter();
    }

    private class ParseURL extends AsyncTask<String, Void, ArrayList<Person>> {
        ArrayList<Person> cleverPeeps = new ArrayList<>();

        @Override
        protected ArrayList<Person> doInBackground(String... strings) {
            Document doc;
            try{
                doc = Jsoup.connect("https://clever.com/about/").get();

                Elements htmlNames = doc.select(".modal-content").select("h3");
                Elements htmlPositions = doc.select("div.modal-content > h4");
                //Elements htmlImages = doc.select(".modal-content").select("img[src~=(?i)\\.(png|jpe?g|gif)]");

                for(int i = 0; i < htmlNames.size(); i++)
                {
                    Person peep = new Person("", "", R.drawable.emma);
                    cleverPeeps.add(peep);
                }

                for(int i = 0; i < cleverPeeps.size(); i++)
                {
                    cleverPeeps.get(i).name = htmlNames.get(i).text();
                    cleverPeeps.get(i).age = htmlPositions.get(i).text();
                    //cleverPeeps.get(i).image = htmlImages.get(i).attr("src");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return cleverPeeps;
        }

        @Override
        protected void onPreExecute() {
            persons = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Person> s) {
            initializeData(s);
            initializeAdapter();
        }
    }

    private void initializeData(ArrayList<Person> blah){

        for(int i = 0; i < blah.size(); i++)
        {
            persons.add(new Person(blah.get(i).name, blah.get(i).age, R.drawable.emma));
        }
        persons.add(new Person("Santos Solorzano", "Intern", R.drawable.emma));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
