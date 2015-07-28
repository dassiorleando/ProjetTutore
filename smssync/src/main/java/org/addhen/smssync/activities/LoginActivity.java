package org.addhen.smssync.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.addhen.smssync.App;
import org.addhen.smssync.R;

public class LoginActivity extends ActionBarActivity {

    private EditText pseudo;
    private EditText pwd;
    private Button logIn;

    private String logInError = "";
    private Boolean logInBool = false;

    private App APP = (App) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pseudo = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pass_word);
        logIn = (Button) findViewById(R.id.logIn);

        final Intent goMain = new Intent(this.getApplicationContext(), MainActivity.class);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkForm();

                if (!logInBool) {

                    String username = pseudo.getText().toString();
                    String password = pwd.getText().toString();

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {

                            if (e == null) {
                                if (parseUser != null) {
                                    startActivity(goMain);

                                    ParseUser p = ParseUser.getCurrentUser();
                                    APP.setCurrentUser(p);
                                }
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(view.getContext(), logInError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkForm(){
        logInBool = false;

        if(TextUtils.isEmpty(pseudo.getText().toString())) {
            logInError += "pseudo must not be null ";
            logInBool = true;
        }

        if(TextUtils.isEmpty(pwd.getText().toString())) {
            logInError += "pass word must not be null";
            logInBool = true;
        }

        if(pwd.getText().toString().length() < 6) {
            logInError += "pass word must be at least 6";
            logInBool = true;
        }
    }

}
