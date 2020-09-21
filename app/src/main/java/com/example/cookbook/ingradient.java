package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ingradient<dish1> extends AppCompatActivity {
    private TextView res_name;
    private RequestQueue mQueue;
    private TextView cat_area;
    private ImageView cover_img;
    private TextView ingra;
    private TextView meas;
    private TextView desc;
    private Button ytbtn;
    private Button sourcebtn;

    public String yt = "";
    public String brolink = "";
    private String dish1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingradient);

        String dish1 = getIntent().getStringExtra("keyname");
        res_name = (TextView) findViewById(R.id.res_name);
        cat_area = (TextView) findViewById(R.id.cat_area);
        ytbtn = (Button) findViewById(R.id.youtubebtn);
        meas = (TextView) findViewById(R.id.meas);
        desc = (TextView) findViewById(R.id.desc);
        cover_img = (ImageView) findViewById(R.id.cover_img);
        ingra = (TextView) findViewById(R.id.ingra);
        sourcebtn = (Button) findViewById(R.id.sourcebtn);
        mQueue = Volley.newRequestQueue(this);

//        jsonParse(dish1);

        sourcebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(brolink));
                startActivity(browserIntent);
            }
        });

        // youtube button event
        ytbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(yt.toString())));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(yt));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });


        String name1 = "https://www.themealdb.com/api/json/v1/1/search.php?s="+dish1;
        FetchAnActivity faa = new FetchAnActivity(name1,cat_area,res_name,cover_img,ingra,meas,desc);
        faa.execute();
    }

    /////////////////////////////


    public class FetchAnActivity extends AsyncTask<Void,Void,String> {
        TextView txt;
        String url1;
        private TextView res_name;
        //    private RequestQueue mQueue;
        private TextView cat_area;
        private ImageView cover_img;
        private TextView ingra;
        private TextView meas;
        private TextView desc;
//    ProgressBar progressBar;

        public FetchAnActivity(String url1,TextView cat_area, TextView res_name, ImageView cover_img,
                               TextView ingra, TextView meas, TextView desc) {
            this.cat_area = cat_area;
            this.res_name = res_name;
            this.cover_img = cover_img;
            this.ingra = ingra;
            this.meas = meas;
            this.desc = desc;
//            this.yt = yt;
//            this.brolink = brolink;
            this.url1 = url1;
//        this.progressBar = progressBar;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
//            URL url = new URL("https://www.themealdb.com/api/json/v1/1/search.php?s=dal");
                URL url = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                if(inputStream==null)
                    return "data is not fetched";
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while((line=br.readLine())!=null)
                {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("Data is not fetched")){
                txt.setText("data not fetched");
            }
            else{
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray jsonArray = object.getJSONArray("meals");
//                JSONArray jsonArray = new object.getJSONArray("meals");
                    for (int i = 0; i < 1/*jsonArray.length()*/; i++) {
                        JSONObject meals = jsonArray.getJSONObject(i);
                        String recipe_name = meals.getString("strMeal");
                        String area = meals.getString("strArea");
                        String cata = meals.getString("strCategory");
                        String pho = meals.getString("strMealThumb");
                        String ytlink = meals.getString("strYoutube");
                        yt = ytlink;

                        String soulink = meals.getString("strSource");
                        brolink = soulink;
                        String ingrad1 = meals.getString("strIngredient1");
                        String ingrad2 = meals.getString("strIngredient2");
                        String ingrad3 = meals.getString("strIngredient3");
                        String ingrad4 = meals.getString("strIngredient4");
                        String ingrad5 = meals.getString("strIngredient5");
                        String ingrad6 = meals.getString("strIngredient6");
                        String ingrad7 = meals.getString("strIngredient7");
                        String ingrad8 = meals.getString("strIngredient8");

                        String meas1 = meals.getString("strMeasure1");
                        String meas2 = meals.getString("strMeasure2");
                        String meas3 = meals.getString("strMeasure3");
                        String meas4 = meals.getString("strMeasure4");
                        String meas5 = meals.getString("strMeasure5");
                        String meas6 = meals.getString("strMeasure6");
                        String meas7 = meals.getString("strMeasure7");
                        String meas8 = meals.getString("strMeasure8");

                        String descrip = meals.getString("strInstructions");


                        Picasso.get().load(pho).into(cover_img);
                        res_name.setText(recipe_name);
                        cat_area.setText(cata+"/"+area);
                        desc.setText(descrip);
                        ingra.setText(ingrad1+"\n\n"+ingrad2+"\n\n"+ingrad3+"\n\n"+ingrad4+"\n\n"+
                                ingrad5+"\n\n"+ingrad6+"\n\n"+ingrad7+"\n\n"+ingrad8+"\n\n");
                        meas.setText(meas1+"\n\n"+meas2+"\n\n"+meas3+"\n\n"+meas4+"\n\n"+
                                meas5+"\n\n"+meas6+"\n\n"+meas7+"\n\n"+meas8+"\n\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}