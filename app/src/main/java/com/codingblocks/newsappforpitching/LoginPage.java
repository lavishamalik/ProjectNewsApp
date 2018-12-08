package com.codingblocks.newsappforpitching;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_pg);
        Button btnnext=findViewById(R.id.btnNextToDiscussion);
        final EditText etUsername=findViewById(R.id.etUserName);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=etUsername.getText().toString();
                Intent i=new Intent(LoginPage.this,Discussion.class);
                i.putExtra("Username",input);
                startActivity(i);
            }
            }
        );
    }
}
