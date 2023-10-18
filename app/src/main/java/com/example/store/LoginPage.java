package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPage extends AppCompatActivity
{
    EditText uid,pass;
    Button login;
    TextView regstore,reguser,jtxt,jtxt2,thead;
    ProgressBar pb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int maxValue = 1;
    /*DocumentReference uref = db.collection("User Registration").document(uid.getText().toString());*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        uid=findViewById(R.id.userid);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.btnlogin);
        regstore=findViewById(R.id.txtregstore);
        reguser=findViewById(R.id.txtreguser);
        jtxt=findViewById(R.id.jtext);
        jtxt2=findViewById(R.id.jtext2);
        thead=findViewById(R.id.theadline);
        pb=findViewById(R.id.progressbar);
        pb.setVisibility(View.GONE);

        View.OnClickListener l =new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(uid.getText().toString().equals(""))
                {
                    uid.setError("Enter Username");
                }
                else if(pass.getText().toString().equals(""))
                {
                    pass.setError("Enter Password");
                }
                else
                {
                pb.setVisibility(View.VISIBLE);
                final Timer t = new Timer();
                TimerTask tt = new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        maxValue=maxValue*2;
                        pb.setProgress(maxValue);
                        if(maxValue==100)
                        {
                            t.cancel();
                        }
                    }
                    };
                t.schedule(tt,0,100);
                DocumentReference ref = db.collection("Store Registration").document(uid.getText().toString());
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                   {
                       @Override
                       public void onSuccess(DocumentSnapshot documentSnapshot)
                       {
                           if(documentSnapshot.exists())
                           {
                               Object i = documentSnapshot.get("Username");
                               Object p = documentSnapshot.get("Password");

                               if (i.equals(uid.getText().toString()))
                               {
                                   if (p.equals(pass.getText().toString()))
                                   {
                                       uid.setText("");
                                       pass.setText("");
                                       Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                       /*Intent for Admin or store page after Login page*/
                                       Intent adm = new Intent(LoginPage.this, AdminHomepage.class);
                                       startActivity(adm);
                                   } else
                                   {
                                       pass.setError("Wrong Password");
                                   }
                               }
                               else
                               {
                                   uid.setError("User-Id Does Not Exists");

                               }
                           }
                           else
                           {
                               DocumentReference ref = db.collection("User Registration").document(uid.getText().toString());
                               ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                               {
                                   @Override
                                   public void onSuccess(DocumentSnapshot documentSnapshot)
                                   {
                                       if(documentSnapshot.exists())
                                       {
                                           Object i = documentSnapshot.get("Username");
                                           Object p = documentSnapshot.get("Password");
                                           if (i.equals(uid.getText().toString()))
                                           {

                                               if (p.equals(pass.getText().toString()))
                                               {
                                                   uid.setText("");
                                                   pass.setText("");
                                                   Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                   /*Intent for User page after Login page*/
                                                   Intent usr = new Intent(LoginPage.this, UserHomePage.class);
                                                   startActivity(usr);
                                               }
                                               else
                                               {
                                                   pass.setError("Wrong Password");
                                               }
                                           }
                                           else
                                           {
                                               uid.setError("User-Id Does Not Exists");

                                           }
                                       }
                                       else
                                       {
                                           uid.setError("User-Id Does Not Exists");

                                       }
                                   }
                               }).addOnFailureListener(new OnFailureListener()
                               {
                                   @Override
                                   public void onFailure(@NonNull Exception e)
                                   {
                                       uid.setError("User-Id Does Not Exists");
                                   }
                               });


                           }


                       }
                   }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(LoginPage.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });



            }}
        };
        login.setOnClickListener(l);


        View.OnClickListener s =new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(LoginPage.this,MainActivity.class);
                startActivity(i);
            }
        };
        regstore.setOnClickListener(s);
        View.OnClickListener u =new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(LoginPage.this,UserReg.class);
                startActivity(i);
            }
        };
        reguser.setOnClickListener(u);
        

    }
}