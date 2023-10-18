package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddProduct extends AppCompatActivity
{
    EditText pcategory,pname,pqty,pamt;
    Button baddproduct;
    int id=0;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pcategory=findViewById(R.id.pcategory);
        pname=findViewById(R.id.pname);
        pqty=findViewById(R.id.pqty);
        pamt=findViewById(R.id.pamt);
        baddproduct=findViewById(R.id.btnaddproduct);

        View.OnClickListener adproduct = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) 
            {
                String productname=pname.getText().toString();
                String productcategory=pcategory.getText().toString();
                int productqty=Integer.parseInt(pqty.getText().toString());
                int productamt=Integer.parseInt(pamt.getText().toString());
                Map<String,Object> pdata=new HashMap<>();
                pdata.put("Product Name",productname);
                pdata.put("Category",productcategory);
                pdata.put("Quantity",productqty);
                pdata.put("Amount",productamt);
                db.collection("Product Details").document(pcategory.getText().toString()).set(pdata)
                        .addOnSuccessListener(new OnSuccessListener<Void>() 
                        {
                            @Override
                            public void onSuccess(Void unused) 
                            {
                                Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                                pname.setText("");
                                pqty.setText("");
                                pamt.setText("");
                                pcategory.setText("");
                                
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() 
                        {
                            @Override
                            public void onFailure(@NonNull Exception e) 
                            {
                                Toast.makeText(AddProduct.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        baddproduct.setOnClickListener(adproduct);
    }
}