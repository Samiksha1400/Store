package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeleteUser extends AppCompatActivity
{
    EditText uname;
    Button btndltuser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        uname=findViewById(R.id.uname);
        btndltuser=findViewById(R.id.btndltuser);
        View.OnClickListener dltuser = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference refuser= db.collection("User Details").document(uname.getText().toString());
                refuser.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(DeleteUser.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                            uname.setText("");
                        }
                        else
                        {
                            Toast.makeText(DeleteUser.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        btndltuser.setOnClickListener(dltuser);
    }
}