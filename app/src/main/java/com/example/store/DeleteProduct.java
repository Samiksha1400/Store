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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeleteProduct extends AppCompatActivity
{
    EditText pcategory;
    Button btndeltproduct;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pcategory=findViewById(R.id.pcategory);
        btndeltproduct=findViewById(R.id.btndeltproduct);
        View.OnClickListener dlt= new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference refproduct= db.collection("Product Details").document(pcategory.getText().toString());
                refproduct.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(DeleteProduct.this, "product Deleted", Toast.LENGTH_SHORT).show();
                            pcategory.setText("");
                        }
                        else
                        {
                            Toast.makeText(DeleteProduct.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        btndeltproduct.setOnClickListener(dlt);
    }
}