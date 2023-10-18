package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddUser extends AppCompatActivity
{
    EditText uid,uname,uaddress;
    Button badduser;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        uid=findViewById(R.id.uid);
        uname=findViewById(R.id.uname);
        uaddress=findViewById(R.id.uaddress);
        badduser=findViewById(R.id.btnadduser);
        View.OnClickListener aduser=new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                int userid =Integer.parseInt(uid.getText().toString());
                String username =uname.getText().toString();
                String useraddr=uaddress.getText().toString();
                Map<String,Object> udata =new HashMap<>();
                udata.put("User ID",userid);
                udata.put("User Name",username);
                udata.put("Address",useraddr);
                db.collection("User Details").document(uname.getText().toString()).set(udata)
                        .addOnSuccessListener(new OnSuccessListener<Void>() 
                        {
                            @Override
                            public void onSuccess(Void unused) 
                            {
                                Toast.makeText(AddUser.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                                uid.setText("");
                                uname.setText("");
                                uaddress.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() 
                        {
                            @Override
                            public void onFailure(@NonNull Exception e) 
                            {
                                Toast.makeText(AddUser.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        badduser.setOnClickListener(aduser);
    }
}