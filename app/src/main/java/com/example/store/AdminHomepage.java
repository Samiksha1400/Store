package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AdminHomepage extends AppCompatActivity
{
    ImageButton lgt;
    Button add,bupdate,bdelete,bvw;
    int getspinner=0;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
        Objects.requireNonNull(getSupportActionBar()).hide();
        lgt=findViewById(R.id.logout);
        add=findViewById(R.id.btnadd);
        View.OnClickListener lg=new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder  alrt = new AlertDialog.Builder(AdminHomepage.this);
                alrt.setTitle("Confirm");
                alrt.setMessage("Do You Really Want to Logout ?");
                AlertDialog dialog = alrt.create();
                alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent u = new Intent(AdminHomepage.this,LoginPage.class);
                        startActivity(u);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                       
                    }
                });
                alrt.show();

            }
        };
        lgt.setOnClickListener(lg);

        /*Spinner*/
        Spinner spr=findViewById(R.id.sp);
        ArrayAdapter<CharSequence> adp =ArrayAdapter.createFromResource(this,R.array.StoreRes, android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spr.setAdapter(adp);
        spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                        if(i==0)
                        {
                            getspinner=0;
                            add.setVisibility(View.VISIBLE);
                            bupdate.setVisibility(View.VISIBLE);
                        }
                        else if (i==1)
                        {
                            getspinner=1;
                            add.setVisibility(View.VISIBLE);
                            bupdate.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            getspinner=2;
                            add.setVisibility(View.GONE);
                            bupdate.setVisibility(View.GONE);
                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        /*Add Button with Intent*/

        View.OnClickListener ad = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(getspinner==0)
                {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Intent prdut = new Intent(AdminHomepage.this,AddProduct.class);
                    prdut.putExtra("id",id);
                    startActivity(prdut);

                }
                else if (getspinner==1)
                {
                    Intent emp =new Intent(AdminHomepage.this,AddEmployee.class);
                    startActivity(emp);
                }


            }
        };
        add.setOnClickListener(ad);

        /*Update button with Intent*/
        bupdate=findViewById(R.id.btnupdate);
        View.OnClickListener updt = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(getspinner==0)
                {
                    Intent prdut = new Intent(AdminHomepage.this,UpdateProduct.class);
                    startActivity(prdut);

                }
                else if (getspinner==1)
                {
                    Intent emp =new Intent(AdminHomepage.this,UpdateEmployee.class);
                    startActivity(emp);
                }


            }
        };
        bupdate.setOnClickListener(updt);

        /*Delete Button with Intent*/
        bdelete=findViewById(R.id.btndelete);
        View.OnClickListener delete = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(getspinner==0)
                {
                    Intent prdut = new Intent(AdminHomepage.this,DeleteProduct.class);
                    startActivity(prdut);

                }
                else if (getspinner==1)
                {
                    Intent emp =new Intent(AdminHomepage.this,DeleteEmployee.class);
                    startActivity(emp);
                }
                else
                {
                    Intent user =new Intent(AdminHomepage.this,DeleteUser.class);
                    startActivity(user);
                }
            }
        };
        bdelete.setOnClickListener(delete);

        /*View Button with Intent*/
        bvw = findViewById(R.id.btnview);
        View.OnClickListener bview = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(getspinner==0)
                {
                    Intent prdut = new Intent(AdminHomepage.this,ViewProduct.class);
                    startActivity(prdut);

                }
                else if (getspinner==1)
                {
                    Intent emp =new Intent(AdminHomepage.this,ViewEmployee.class);
                    startActivity(emp);
                }
                else
                {
                    Intent user =new Intent(AdminHomepage.this,ViewUser.class);
                    startActivity(user);
                }
            }
        };
        bvw.setOnClickListener(bview);
    }
}