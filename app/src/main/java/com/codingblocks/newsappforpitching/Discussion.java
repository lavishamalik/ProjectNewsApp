package com.codingblocks.newsappforpitching;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codingblocks.newsappforpitching.adapter.DiscussionrecyclerViewAdapter;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Discussion extends AppCompatActivity {

    ArrayList<Post>arrayList=new ArrayList<>();
    private static final int RC_SIGN_IN = 1000;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion_layout);
        /*String username = null;
        if(getIntent().hasExtra("Username"))
        {
            username = getIntent().getStringExtra("Username");
        }*/
        final EditText etinput=findViewById(R.id.etDiscussion);
        ImageButton imgButtonSend=findViewById(R.id.ImgbtnSendDiscussion);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView recyclerView=findViewById(R.id.recyclerViewDiscussion);
        final DiscussionrecyclerViewAdapter discussionrecyclerViewAdapter=new DiscussionrecyclerViewAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(discussionrecyclerViewAdapter);

        final DatabaseReference dbBaseRef=FirebaseDatabase.getInstance().getReference();
      //  dbBaseRef.child("Username").push().setValue(username);
        if(firebaseUser!=null)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            final String name = user.getDisplayName();
            imgButtonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String discussion=etinput.getText().toString();
                    dbBaseRef.child("name").setValue(name);
                    dbBaseRef.child("name").child("message").push().setValue(discussion);
                }
            });

            dbBaseRef.child("name").child("message").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  //Post data= dataSnapshot.getValue(Post.class);
                    String username= (String) dataSnapshot.child("name").getValue();
                    String message= (String) dataSnapshot.child("name").child("message").getValue();
                    Post post=new Post(username,message);
                   arrayList.add(post);
                   Log.e("Tag",""+arrayList.size());
                   discussionrecyclerViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                            .build(),
                    RC_SIGN_IN);

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //Sign in what has to be done
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }

                Log.e("TAG", "Sign-in error: ", response.getError());
            }
        }
    }
}
