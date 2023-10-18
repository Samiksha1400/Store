package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateProduct extends AppCompatActivity
{
    EditText pname,pqty,pamt;
    Button bupdtproduct;
    Spinner sp;
    String spcat;
    ArrayAdapter ad;
    ArrayList<String> catarr=new ArrayList<String>();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pname=findViewById(R.id.pname);
        pqty=findViewById(R.id.pqty);
        pamt=findViewById(R.id.pamt);
        sp=findViewById(R.id.spcategory);
        bupdtproduct=findViewById(R.id.btnupdtproduct);

        CollectionReference ref =db.collection("Product Details");
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document: task.getResult())
                    {
                        ad= new ArrayAdapter(UpdateProduct.this, android.R.layout.simple_spinner_item,catarr);
                        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp.setAdapter(ad);
                        String tt=  document.get("Category").toString();
                        catarr.add(tt);
                    }
                }
                else
                {
                    Toast.makeText(UpdateProduct.this, "Task Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(UpdateProduct.this, "Task Failed", Toast.LENGTH_SHORT).show();
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
               spcat=sp.getItemAtPosition(i).toString();
               spcat=pname.getText().toString();
                Toast.makeText(UpdateProduct.this,spcat, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Toast.makeText(UpdateProduct.this, "Selection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener updt=new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String cat=sp.toString();
                /*Toast.makeText(UpdateProduct.this, sp.toString(), Toast.LENGTH_SHORT).show();*/
                String productname=pname.getText().toString();
                int productqty=Integer.parseInt(pqty.getText().toString());
                int productamt=Integer.parseInt(pamt.getText().toString());
                Map<String,Object> pdata=new HashMap<>();
                pdata.put("Category",cat);
                pdata.put("Product Name",productname);
                pdata.put("Quantity",productqty);
                pdata.put("Amount",productamt);
                DocumentReference ref = db.collection("Product Details").document(pname.getText().toString());
                ref.update(pdata).addOnSuccessListener(new OnSuccessListener()
                {
                    @Override
                    public void onSuccess(Object o)
                    {
                        Toast.makeText(UpdateProduct.this,"Data Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(UpdateProduct.this,"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        bupdtproduct.setOnClickListener(updt);
    }
}