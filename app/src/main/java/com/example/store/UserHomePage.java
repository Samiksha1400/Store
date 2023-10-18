package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserHomePage extends AppCompatActivity
{
    ListView lst;
    Button btnnext;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayAdapter<CharSequence> Product_Name;
    ArrayList <String> Product_Details = new ArrayList<String>();
    ArrayList <String> Product_Amount = new ArrayList<String>();
    ArrayList <String> Bill_Product_Name = new ArrayList<String>();
    ArrayList <Integer> Bill_Product_Amount = new ArrayList<Integer>();
    int product_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        btnnext=findViewById(R.id.sentobill);
        lst = findViewById(R.id.list);
        Product_Name= new ArrayAdapter<CharSequence>(UserHomePage.this, android.R.layout.simple_list_item_1);
        Product_Name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                                Product_Name.add(document.get("Category").toString());
                                Product_Details.add(document.get("Category")+" "+document.get("Product Name")+" "+document.get("Amount")+" "+document.get("Quantity"));
                                Product_Amount.add(document.get("Amount").toString());
                                lst.setAdapter(Product_Name);
                            }
                        }
                    }
                });
        String hhh =Integer.toString(Product_Name.getCount());
        Toast.makeText(UserHomePage.this, hhh, Toast.LENGTH_SHORT).show();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                int TempI = i;
                AlertDialog.Builder dlg = new AlertDialog.Builder(UserHomePage.this);
                dlg.setTitle("Add to Cart");
                dlg.setMessage("\n"+"\t"+"Category :" +Product_Name.getItem(i)+"\n"+"\n"+"\t"+"Amount :" +Product_Amount.get(i));
                AlertDialog dialog = dlg.create();

                dlg.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                            if(product_counter>=0)
                            {
                                product_counter = product_counter+1;
                            }
                            else
                            {
                                product_counter=1;
                            }

                            Toast.makeText(UserHomePage.this, Integer.toString(product_counter), Toast.LENGTH_SHORT).show();
                            Bill_Product_Name.add(lst.getItemAtPosition(TempI).toString());
                            Bill_Product_Amount.add(Integer.parseInt(Product_Amount.get(TempI)));


                    }
                });

                if(product_counter>=1)
                {
                    dlg.setNegativeButton("Remove", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            product_counter=product_counter-1;
                        }
                    });
                }
                else if(product_counter<=0)
                {
                    dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                        }
                    });
                }
                dlg.show();

            }
        });
            btnnext.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent i = new Intent(UserHomePage.this,Bill.class);
                    i.putExtra("product name",Bill_Product_Name);
                    i.putExtra("product amount",Bill_Product_Amount);
                    startActivity(i);
                }
            });

    }
}
