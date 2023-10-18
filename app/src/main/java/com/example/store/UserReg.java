package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserReg extends AppCompatActivity
{
    EditText name,mno,addr,pin,uuname,upass,uconfirmpass,storename,storeuserid,storepass;
    Button reg;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        Objects.requireNonNull(getSupportActionBar()).hide();
        name=findViewById(R.id.name);
        mno=findViewById(R.id.mobilenumber);
        addr=findViewById(R.id.address);
        pin=findViewById(R.id.pincode);
        uuname=findViewById(R.id.username);
        upass=findViewById(R.id.password);
        uconfirmpass=findViewById(R.id.confirmpassword);
        storename=findViewById(R.id.storename);
        storeuserid=findViewById(R.id.storeuserid);
        storepass=findViewById(R.id.storepass);
        reg=findViewById(R.id.btnregister);
        View.OnClickListener regstrtn = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nname=name.getText().toString();
                String mobile =mno.getText().toString();
                String address=addr.getText().toString();
                String pinc=pin.getText().toString();
                String uname=uuname.getText().toString();
                String pass=upass.getText().toString();
                String cpass=uconfirmpass.getText().toString();
                String sname=storename.getText().toString();
                if(mobile.length()!= 10)
                {
                    mno.setError("Enter 10 Digit Mobile-Number");
                }
                else
                {
                        if(pinc.length()!=6)
                        {
                            pin.setError("Enter 6 Digit Pincode");
                        }
                        else
                        {
                            if (pass.equals(cpass))
                            {
                                DocumentReference ref = db.collection("Store Registration").document(storeuserid.getText().toString());
                                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                                {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                    {
                                        Object strname = documentSnapshot.get("Username");
                                        Object strpass = documentSnapshot.get("Password");
                                        if(strname.equals(storeuserid.getText().toString()) && strpass.equals(storepass.getText().toString()))
                                        {
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Name", nname);
                                            user.put("Mobile Number", mobile);
                                            user.put("Address", address);
                                            user.put("Pincode", pinc);
                                            user.put("Username", uname);
                                            user.put("Password", pass);
                                            user.put("Confirm Password", cpass);
                                            user.put("Store Name",sname);
                                            db.collection("User Registration").document(uuname.getText().toString())
                                                    .set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>()
                                                    {
                                                        @Override
                                                        public void onSuccess(Void unused)
                                                        {
                                                            uuname.setText("");
                                                            mno.setText("");
                                                            addr.setText("");
                                                            pin.setText("");
                                                            upass.setText("");
                                                            uconfirmpass.setText("");
                                                            storename.setText("");
                                                            storeuserid.setText("");
                                                            storepass.setText("");
                                                            Toast.makeText(UserReg.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                            Intent i =new Intent(UserReg.this,LoginPage.class);
                                                            startActivity(i);

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener()
                                                    {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e)
                                                        {
                                                            storeuserid.setError("Invalid UserId");
                                                            storepass.setError("Invalid Password");
                                                            Toast.makeText(UserReg.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                        else
                                        {
                                            storeuserid.setError("Invalid UserId");
                                            storepass.setError("Invalid Password");
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        Toast.makeText(UserReg.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                uconfirmpass.setError("Does Not Match");
                            }
                        }
                }
            }
        };
        reg.setOnClickListener(regstrtn);
    }
}