package com.example.sanalgazete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

    //5042b6d940884bcc9b107837bba7aaf3

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVHaber);
        categoryRV = findViewById(R.id.idRVKategori);
        loadingPB = findViewById(R.id.idPBYükleniyor);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All", "https://drive.google.com/uc?export=view&id=1_Y91OHWGApZbxroFkrijV55_vDLS4nR_"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://drive.google.com/uc?export=view&id=1OpbRKp2gXptScxmC9efyZrz_wpl7BrjC"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://drive.google.com/uc?export=view&id=1dZVSAgtWkBpGa_pqvHjcN3kmBGCHnvfz"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports", "https://drive.google.com/uc?export=view&id=1z0lES2fui4QaHGf6X1jeP7bGWCzK_Sav"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://drive.google.com/uc?export=view&id=1hI3ahSBHkpY7jzkIuRhu8V9GFJJ1uZXE"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://drive.google.com/uc?export=view&id=1I9TENtoCtSTlTFBKw792JsD4vDKYVT6Z"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://drive.google.com/uc?export=view&id=1Ao0xwiETVbQveVKyUMLm8uIOn62m7O8T"));
        categoryRVAdapter.notifyDataSetChanged();
    }


    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=tr&category="+category+"&apiKey=5042b6d940884bcc9b107837bba7aaf3";
        System.out.println(category);
        String url = "https://newsapi.org/v2/top-headlines?country=tr&sortBy=publishedAt&language=tr&apiKey=5042b6d940884bcc9b107837bba7aaf3";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }
        else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i = 0; i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Haberler alınırken hata oluştu.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getApiName();
        getNews(category);
    }
}