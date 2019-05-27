package com.hufs.cdt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.os.Build.ID;

public class Post extends AppCompatActivity {
    EditText address,price, floor, room, option, guan, parking,seol;
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    private DatabaseReference mPostReference;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        address=(EditText)findViewById(R.id.editText);
        price=(EditText)findViewById(R.id.editText6);
        floor=(EditText)findViewById(R.id.editText2);
        room=(EditText)findViewById(R.id.editText3);
        option=(EditText)findViewById(R.id.editText4);
        guan=(EditText)findViewById(R.id.editText5);
        parking=(EditText)findViewById(R.id.editText1);
        seol=(EditText)findViewById(R.id.editText7);

        getFirebaseDatabase();
    }

    public void open(View view) {

        ArrayList<String> posts = new ArrayList<>();
            posts.add(address.getText().toString());
            posts.add(price.getText().toString());
            posts.add(floor.getText().toString());
            posts.add(room.getText().toString());
            posts.add(option.getText().toString());
            posts.add(guan.getText().toString());
            posts.add(parking.getText().toString());
            posts.add(seol.getText().toString());


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("올리시겠습니까?");


        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                postFirebaseDatabase(true);
                Toast.makeText(getApplicationContext(), "게시글이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindView.class);
                startActivity(intent);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Post.this, "게시글이 안올라갔습니다",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){

            FirebasePost post = new FirebasePost(ID,address.toString(),price.toString(),floor.toString(),room.toString(),option.toString(),guan.toString(),parking.toString(),seol.toString());
            postValues = post.toMap();
        }
        childUpdates.put("posting" + ID, postValues);
        mPostReference.updateChildren(childUpdates);
    }



    public void getFirebaseDatabase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    String[] info = {get.id,  String.valueOf(get.address), String.valueOf(get.price), String.valueOf(get.floor),String.valueOf(get.room),String.valueOf(get.option),String.valueOf(get.guan),String.valueOf(get.parking),String.valueOf(get.seol)};

                    String Result = setTextLength(info[0],10) + setTextLength(info[1],10) + setTextLength(info[2],10) + setTextLength(info[3],10);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3]);
                }
                //arrayAdapter.clear();
               // arrayAdapter.addAll(arrayData);
               // arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };

    }



    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }
}




