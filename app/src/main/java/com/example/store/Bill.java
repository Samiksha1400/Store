package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bill extends AppCompatActivity
{
    TextView dateTime,storename;
    EditText cust_name,mobileno;
    Button btngeneratebill;
    ListView Listnames;
    //ArrayAdapter<CharSequence> P_Name;
    /*String Bill_Product_Name;
    int Bill_Product_Amount;*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dateTime = findViewById(R.id.dateTime);
        storename = findViewById(R.id.storename);
        cust_name = findViewById(R.id.cust_name);
        mobileno = findViewById(R.id.mobileno);
        Listnames =findViewById(R.id.listpname);

        /*Get the Product_Name And Product_Amount
        Bill_Product_Name = getIntent().getStringExtra("Bill_Product_Name");
        listpname.getId();
        Bill_Product_Amount = getIntent().getIntExtra("Bill_Product_Amount",0);
        listpamount.getId();*/

        //Get Current Date and Time
        SimpleDateFormat datee = new SimpleDateFormat("dd-MM-yyyy");
        String cdate = datee.format(new Date());
        dateTime.setText(cdate);

        /*DocumentReference ref = db.collection("User Registration").document(storename.getText().toString());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Object sname = documentSnapshot.get("Store Name");
                storename.setText(sname.toString());
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                storename.setError("Error");
            }
        });*/

        //Generate Bill button
        btngeneratebill = findViewById(R.id.btngeneratebill);
        View.OnClickListener btnbill = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Map<String,Object> cdata = new HashMap<>();
                cdata.put("Customer Name",cust_name);
                cdata.put("Mobile Number",mobileno);
                cdata.put("Store Name",storename);
                cdata.put("Date",dateTime);
                db.collection("Bill").document("Customer Name").set(cdata)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(Bill.this, "Bill Generated successfully", Toast.LENGTH_SHORT).show();
                                cust_name.setText("");
                                mobileno.setText("");
                                dateTime.setText("");
                                storename.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(Bill.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };
        btngeneratebill.setOnClickListener(btnbill);
        /*Intent i = getIntent();
        P_Name= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1);
        P_Name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        P_Name.add(i.getStringExtra("product name"));
        Listnames.setAdapter(P_Name);*/
    }
}