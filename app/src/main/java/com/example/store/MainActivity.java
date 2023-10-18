package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity 
{
    TextView thead,tchld;
    EditText sname,mno,addr,gstno,pin,suname,spass,sconfirmpass;
    Button reg;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle s)
    {
        super.onCreate(s);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        Objects.requireNonNull(getSupportActionBar()).hide();
        thead=findViewById(R.id.theadline);
        tchld=findViewById(R.id.tchildren);
        sname=findViewById(R.id.storename);
        mno=findViewById(R.id.mobilenumber);
        addr=findViewById(R.id.address);
        gstno=findViewById(R.id.gstnumber);
        pin=findViewById(R.id.pincode);
        suname=findViewById(R.id.storeusername);
        spass=findViewById(R.id.storepassword);
        sconfirmpass=findViewById(R.id.storeconfirmpassword);
        reg=findViewById(R.id.btnregister);
        
        View.OnClickListener regstrtn = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name=sname.getText().toString();
                String mobile =mno.getText().toString();
                String address=addr.getText().toString();
                String gstn=gstno.getText().toString();
                String pinc=pin.getText().toString();
                String uname=suname.getText().toString();
                String pass=spass.getText().toString();
                String cpass=sconfirmpass.getText().toString();
                if(mobile.length()!= 10)
                {
                    mno.setError("Enter 10 Digit Mobile-Number");

                }
                else
                {
                    if(gstn.length()!=15)
                        {
                        gstno.setError("Enter 15 Digit GST-Number");
                        }
                    else {
                        if(pinc.length()!=6)
                        {
                            pin.setError("Enter 6 Digit Pincode");
                        }
                    else {

                            if (pass.equals(cpass))
                            {
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name", name);
                                user.put("Mobile Number", mobile);
                                user.put("Address", address);
                                user.put("GST Number", gstn);
                                user.put("Pincode", pinc);
                                user.put("Username", uname);
                                user.put("Password", pass);
                                user.put("Confirm Password", cpass);
                                db.collection("Store Registration").document(suname.getText().toString())
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>()
                                        {
                                            @Override
                                            public void onSuccess(Void unused)
                                            {
                                                sname.setText("");
                                                mno.setText("");
                                                addr.setText("");
                                                gstno.setText("");
                                                pin.setText("");
                                                suname.setText("");
                                                spass.setText("");
                                                sconfirmpass.setText("");
                                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                Intent i =new Intent(MainActivity.this,LoginPage.class);
                                                startActivity(i);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else
                            {
                                sconfirmpass.setError("Does Not Match");
                            }
                        }
                    }
                }
                
            }
        };
        reg.setOnClickListener(regstrtn);

    }
}