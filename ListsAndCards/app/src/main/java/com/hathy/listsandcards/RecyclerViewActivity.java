package com.hathy.listsandcards;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecyclerViewActivity extends Activity {

    private ArrayList<Cleveran> persons;
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

        //initializeData();
        //initializeAdapter();
    }

    private class ParseURL extends AsyncTask<String, Void, ArrayList<Cleveran>> {
        ArrayList<Cleveran> cleverPeeps = new ArrayList<>();

        @Override
        protected ArrayList<Cleveran> doInBackground(String... strings) {
            Document doc;
            try{
                doc = Jsoup.connect("https://clever.com/about/").get();

                Elements htmlParagraphs = doc.select("p");
                Elements htmlNames = doc.select(".modal-content").select("h3");
                Elements htmlPositions = doc.select("div.modal-content > h4");
                Elements htmlImages = doc.select(".modal-content").select("img[src~=(?i)\\.(png|jpe?g|gif)]");

                for(int i = 0; i < htmlNames.size(); i++)
                {
                    Cleveran peep = new Cleveran("", "", "", "", R.drawable.csmall);
                    cleverPeeps.add(peep);
                }

                for(int i = 0; i < cleverPeeps.size(); i++)
                {
                    cleverPeeps.get(i).name = htmlNames.get(i).text();
                    cleverPeeps.get(i).position = htmlPositions.get(i).text();
                    cleverPeeps.get(i).image = htmlImages.get(i).attr("src");
                }

                for(int i = 1; i < htmlParagraphs.size(); i+= 3)
                {
                    cleverPeeps.get(i/3).blurb = htmlParagraphs.get(i).text();
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
        protected void onPostExecute(ArrayList<Cleveran> s) {
            initializeData(s);
            initializeAdapter();
        }
    }

    private void initializeData(ArrayList<Cleveran> clever){
        String aboutSantos = "Santos is in his 4th year at Texas A&M University, and" +
                "in his 2nd year in computer science after switching over from civil engineering. " +
                "He fancied data structures a bit more than architectural structures, and is keen on" +
                " getting a summer internship at Clever. Gaining real world experience while making " +
                "a contribution in education sounds admirable, and would benefit Santos down the road " +
                "as he looks for a career in education technology. He would love to potentially bring " +
                "his technical talents to the team and looks forward to contributing to Clever’s " +
                "mission to improve the quality of education for students all over the world.";

        for(int i = 0; i < clever.size(); i++)
        {
            persons.add(new Cleveran(clever.get(i).name, clever.get(i).position, clever.get(i).blurb, clever.get(i).image, R.drawable.csmall));
        }
        persons.add(new Cleveran("Santos Solorzano", "Intern", aboutSantos, clever.get(0).blurb, R.drawable.csmall));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(this, persons);
        rv.setAdapter(adapter);
    }
}
