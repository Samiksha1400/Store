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

public class AddEmployee extends AppCompatActivity
{
    EditText eid,ename,eaddress,esal;
    Button baddemp;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Objects.requireNonNull(getSupportActionBar()).hide();
        eid=findViewById(R.id.eid);
        ename=findViewById(R.id.ename);
        eaddress=findViewById(R.id.eaddress);
        esal=findViewById(R.id.esal);
        baddemp=findViewById(R.id.btnaddemp);
        View.OnClickListener ademp =new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               int empid =Integer.parseInt(eid.getText().toString());
               String empname =ename.getText().toString();
               String empaddr =eaddress.getText().toString();
               int empsal =Integer.parseInt(esal.getText().toString());
                Map<String,Object> edata =new HashMap<>();
                edata.put("Employee ID",empid);
                edata.put("Employee Name",empname);
                edata.put("Address",empaddr);
                edata.put("Salary",empsal);
                db.collection("Employee Details").document(ename.getText().toString()).set(edata)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(AddEmployee.this,"Employee Added Successfully",Toast.LENGTH_SHORT).show();
                                eid.setText("");
                                ename.setText("");
                                eaddress.setText("");
                                esal.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(AddEmployee.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        baddemp.setOnClickListener(ademp);
    }
}