package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeleteEmployee extends AppCompatActivity
{
    EditText ename;
    Button btndltemp;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ename= findViewById(R.id.ename);
        btndltemp =findViewById(R.id.btndltemp);
        View.OnClickListener dlt= new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference refemp= db.collection("Employee Details").document(ename.getText().toString());
                refemp.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(DeleteEmployee.this, "Employee Data Deleted", Toast.LENGTH_SHORT).show();
                            ename.setText("");
                        }
                        else
                        {
                            Toast.makeText(DeleteEmployee.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        btndltemp.setOnClickListener(dlt);
    }
}