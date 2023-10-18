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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateEmployee extends AppCompatActivity
{
    EditText ename,eaddress,esal;
    Button bupdtemp;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ename=findViewById(R.id.ename);
        eaddress=findViewById(R.id.eaddress);
        esal=findViewById(R.id.esal);

        bupdtemp=findViewById(R.id.btnupdtemp);
        View.OnClickListener updt=new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String empname =ename.getText().toString();
                String empaddr =eaddress.getText().toString();
                int empsal =Integer.parseInt(esal.getText().toString());
                Map<String,Object> edata =new HashMap<>();
                edata.put("Employee Name",empname);
                edata.put("Address",empaddr);
                edata.put("Salary",empsal);
                DocumentReference ref =db.collection("Employee Details").document(ename.getText().toString());
                ref.update(edata).addOnSuccessListener(new OnSuccessListener()
                {
                    @Override
                    public void onSuccess(Object o)
                    {
                        Toast.makeText(UpdateEmployee.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(UpdateEmployee.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        bupdtemp.setOnClickListener(updt);
    }
}