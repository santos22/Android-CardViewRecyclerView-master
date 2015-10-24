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

    // RecyclerViews are used when data collections has elements
    // that change at runtime; i.e. Clever adds new employee
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

    // AsyncTask handles the parsing performed in the background asynchronously
    private class ParseURL extends AsyncTask<String, Void, ArrayList<Cleveran>> {
        ArrayList<Cleveran> cleverPeeps = new ArrayList<>();

        // execute parsing inside of another thread
        @Override
        protected ArrayList<Cleveran> doInBackground(String... strings) {
            Document doc;
            try{
                // need http protocol
                doc = Jsoup.connect("https://clever.com/about/").get();

                // retrieve and parse name, image, about, and position info
                Elements htmlParagraphs = doc.select("p");
                Elements htmlNames = doc.select(".modal-content").select("h3");
                Elements htmlPositions = doc.select("div.modal-content > h4");
                Elements htmlImages = doc.select(".modal-content").select("img[src~=(?i)\\.(png|jpe?g|gif)]");

                // declare list of empty Cleveran objects to represent each employee
                for(int i = 0; i < htmlNames.size(); i++)
                {
                    Cleveran peep = new Cleveran("", "", "", "", R.drawable.csmall);
                    cleverPeeps.add(peep);
                }

                // set name, position, and image properties of each Cleveran object
                for(int i = 0; i < cleverPeeps.size(); i++)
                {
                    cleverPeeps.get(i).name = htmlNames.get(i).text();
                    cleverPeeps.get(i).position = htmlPositions.get(i).text();
                    cleverPeeps.get(i).image = htmlImages.get(i).attr("src");
                }

                // parses image URLS differently...temporary workaround
                for(int i = 1; i < htmlParagraphs.size(); i+= 3)
                {
                    cleverPeeps.get(i/3).blurb = htmlParagraphs.get(i).text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return cleverPeeps;
        }

        // declare persons ArrayList before parsing
        @Override
        protected void onPreExecute() {
            persons = new ArrayList<>();
            super.onPreExecute();
        }

        // initialize data after parsing
        @Override
        protected void onPostExecute(ArrayList<Cleveran> s) {
            initializeData(s);
            initializeAdapter();
        }
    }

    private void initializeData(ArrayList<Cleveran> clever){
        String aboutSantos = "Santos is in his 4th year at Texas A&M University, and " +
                "in his 2nd year in computer science after switching over from civil engineering. " +
                "He fancied data structures a bit more than architectural structures, and is keen on" +
                " getting a summer internship at Clever. Gaining real world experience while making " +
                "a contribution in education sounds admirable, and would benefit Santos down the road " +
                "as he looks for a career in education technology. He would love to potentially bring " +
                "his technical talents to the team and looks forward to contributing to Clever’s " +
                "mission to improve the quality of education for students all over the world.";

        String imageSantos = "http://oi59.tinypic.com/10einix.jpg";

        // add each Cleveran object to
        for(int i = 0; i < clever.size(); i++)
        {
            persons.add(new Cleveran(clever.get(i).name, clever.get(i).position, clever.get(i).blurb, clever.get(i).image, R.drawable.csmall));
        }
        persons.add(new Cleveran("Santos Solorzano", "Engineering - Intern", aboutSantos, imageSantos, R.drawable.csmall));
    }

    // initialize and use adapter by calling constructor
    // and setAdapter method
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(this, persons);
        rv.setAdapter(adapter);
    }
}
