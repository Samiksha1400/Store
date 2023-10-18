package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ViewProduct extends AppCompatActivity
{
    ListView product_list;
    ArrayAdapter<CharSequence> ad;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Objects.requireNonNull(getSupportActionBar()).hide();
        product_list = findViewById(R.id.productlist);
        ad= new ArrayAdapter<CharSequence>(ViewProduct.this, android.R.layout.simple_list_item_1);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        db.collection("Product Details")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                ad.add(document.getId());
                                product_list.setAdapter(ad);
                            }
                        }
                    }
                });
    }
}