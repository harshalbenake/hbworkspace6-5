package com.example.harshalbenake.accountsetting;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * http://stackoverflow.com/a/24228529
 * Document in Raw folder.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountManager accountManager = AccountManager.get(this);
        Account account = new Account("HBAccount","com.example.harshalbenake.accountsetting.DEMOACCOUNT");
        boolean success = accountManager.addAccountExplicitly(account,"password",null);
        if(success){
            Toast.makeText(MainActivity.this,"Account created",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"Account creation failed. Look at previous logs to investigate",Toast.LENGTH_SHORT).show();
        }

        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account1 : accounts) {
            Toast.makeText(MainActivity.this, "Name: " + account1.name+" Type: " + account1.type,Toast.LENGTH_SHORT).show();
        }
    }
}
