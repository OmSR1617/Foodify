package com.example.fooddeliveryapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fooddeliveryapp.Adapter.CartListAdapter;
import com.example.fooddeliveryapp.Helper.ManagementCart;
import com.example.fooddeliveryapp.Interface.ChangeNumberItemsListner;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.emergmes;
import com.example.fooddeliveryapp.support;

public class CartActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        bottomNavigation();
        calculateCart();

    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartbtn); // Changed from "cardBtn" to "cartBtn"

        if (homeBtn != null && cartBtn != null) {
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CartActivity.this, MainActivity.class));
                }
            });

            cartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CartActivity.this, CartActivity.class));
                }
            });
        }
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListner() {
            @Override
            public void changed() {
                adapter.notifyDataSetChanged();
                calculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
    }

    double total;
    double itemTotal;
    double delivery;
    double percentTax;
    private void calculateCart() {
         percentTax = 0.1;
         delivery = 50;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
         total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
         itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        totalFeeTxt.setText("₹" + itemTotal);
        taxTxt.setText("₹" + tax);
        deliveryTxt.setText("₹" + delivery);
        totalTxt.setText("₹" + total);

    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);

        recyclerViewList = findViewById(R.id.view);
        scrollView = findViewById(R.id.scrollView);

    }
    public void sendSMS(View view){
        Intent intent = new Intent(this, emergmes.class);
        startActivity(intent);
    }
    public void supp(View view){
        Intent intent = new Intent(this, support.class);
        startActivity(intent);
    }
}
