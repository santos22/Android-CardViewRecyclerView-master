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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

//        ParseURL task = new ParseURL();
//        ArrayList<Cleverian> testList = task.execute(new String[]{siteUrl}).get();

        //new ParseURL(this).execute();

//        Thread downloadThread = new Thread() {
//            public void run() {
//                Document doc;
//
//                try{
//                    doc = Jsoup.connect("https://clever.com/about/").get();
//
//                    Elements htmlNames = doc.select(".modal-content").select("h3");
//                    Elements htmlPositions = doc.select("div.modal-content > h4");
//
//                    for(int i = 0; i < htmlNames.size(); i++)
//                    {
//                        Cleverian peep = new Cleverian();
//                        cleverPeeps.add(peep);
//                    }
//
//                    for(int i = 0; i < cleverPeeps.size(); i++)
//                    {
//                        cleverPeeps.get(i).name = htmlNames.get(i).text();
//                        cleverPeeps.get(i).position = htmlPositions.get(i).text();
//                        //cleverPeeps.get(i).image = htmlImages.get(i).attr("src");
//                    }
//
//                    System.out.println(cleverPeeps.get(0).name);
//
//                    //persons.add(new Person("EMMA", "YUH", R.drawable.emma));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        downloadThread.start();

        //initializeData();
        //initializeAdapter();
    }

    public static ArrayList<Person> testList = new ArrayList<>();

    private class ParseURL extends AsyncTask<String, Void, ArrayList<Person>> {
        ArrayList<Person> cleverPeeps = new ArrayList<>();

        @Override
        protected ArrayList<Person> doInBackground(String... strings) {
            Document doc;
            try{
                doc = Jsoup.connect("https://clever.com/about/").get();

                Elements htmlNames = doc.select(".modal-content").select("h3");
                Elements htmlPositions = doc.select("div.modal-content > h4");

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

                System.out.println(cleverPeeps.get(78).name);

                //persons.add(new Person("EMMA", "YUH", R.drawable.emma));
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

            //System.out.println(s.get(0).name);
            testList = s;
            //System.out.println(testList.get(0).name);
            initializeData(s);
            initializeAdapter();
            //super.onPostExecute(s);
//            respText.setText(s);
        }
    }

    private void initializeData(ArrayList<Person> blah){

        //persons = new ArrayList<>();

        System.out.println(blah.get(1).name);

        for(int i = 0; i < blah.size(); i++)
        {
            System.out.println(blah.get(i).name);
            persons.add(new Person(blah.get(i).name, blah.get(i).age, R.drawable.emma));
        }
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
