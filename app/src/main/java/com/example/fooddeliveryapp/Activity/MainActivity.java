package com.example.fooddeliveryapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fooddeliveryapp.Adapter.categoryAdapter;
import com.example.fooddeliveryapp.Adapter.recommendedAdapter;
import com.example.fooddeliveryapp.Domain.CategoryDomain;
import com.example.fooddeliveryapp.Domain.FoodDomain;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.support;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();



    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cardBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });



    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager((linearLayoutManager));

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Pepperoni pizza","pizza1","slices pepperoni,mozzarella cheese,fresh organo,ground black paper,pizza sauce",13.0,5,20,1000));
        foodlist.add(new FoodDomain("Chese Burger","burger","beef,Gouda Cheese,Special sauce,Lettuce,tomato",15.20,4,18,1500));
        foodlist.add(new FoodDomain("Vegatable pizza","pizza3","olive oil,Vegetable oil,pitted Kalamata,cherry tomatoes,fresh oregano,basic",11.0,3,16,800));

         adapter2 = new recommendedAdapter(foodlist);
         recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categroryList = new ArrayList<>();
        categroryList.add(new CategoryDomain("Pizza","cat_1"));
        categroryList.add(new CategoryDomain("Burger","cat_2"));
        categroryList.add(new CategoryDomain("Hotdog","cat_3"));
        categroryList.add(new CategoryDomain("Drink","cat_4"));
        categroryList.add(new CategoryDomain("Donut","cat_5"));

        adapter = new categoryAdapter(categroryList);
        recyclerViewCategoryList.setAdapter(adapter);

    }
    public void supp(View view){
        Intent intent = new Intent(this, support.class);
        startActivity(intent);

    }

}