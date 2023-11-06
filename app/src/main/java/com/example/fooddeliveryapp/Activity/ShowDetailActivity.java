package com.example.fooddeliveryapp.Activity;

import static java.util.ResourceBundle.getBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.Domain.FoodDomain;
import com.example.fooddeliveryapp.Helper.ManagementCart;
import com.example.fooddeliveryapp.R;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShowDetailActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView addToCartBtn;
    private  TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt,totalPriceTxt,starTxt,caloryTxt,timeTxt;
    private ImageView plusBtn,minusBtn,picFood;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    private FoodDomain object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        initView();
        getBundle();

    }

    private void getBundle() {

        object = (FoodDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("₹"+object.getFee());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        caloryTxt.setText(object.getCalories()+" Calories");
        starTxt.setText(object.getStar()+"");
        timeTxt.setText(object.getTime()+" minutes");
        totalPriceTxt.setText("₹"+numberOrder*object.getFee());


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder+1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText("₹"+numberOrder*object.getFee());
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder>1){
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText("₹"+numberOrder*object.getFee());
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);

                DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid()).collection("Items").document();

                Map<String,Object> note = new HashMap<>();
                note.put("Food name",object.getTitle());
                note.put("Price","₹"+numberOrder*object.getFee());


                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Item Added successfully....",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShowDetailActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to Add Item....",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private void initView() {

        addToCartBtn = findViewById(R.id.addtoCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);

        descriptionTxt = findViewById(R.id.descriptionTxt);

        numberOrderTxt = findViewById(R.id.numberItemTxt);

        plusBtn = findViewById(R.id.plusCardBtn);

        minusBtn = findViewById(R.id.minusCardBtn);

        picFood = findViewById(R.id.foodPic);

        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        starTxt = findViewById(R.id.starTxt);

        caloryTxt = findViewById(R.id.caloriesTxt);
        timeTxt = findViewById(R.id.timeTxt);



    }
}