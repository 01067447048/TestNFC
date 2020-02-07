package com.example.testnfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.testnfc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        binding.receive.setOnClickListener(view->{
            Intent intent = new Intent(this,NFCReceiveActivity.class);
            startActivity(intent);
        });

        binding.Send.setOnClickListener(view->{
            Intent intent = new Intent(this,NFCSendActivity.class);
            startActivity(intent);
        });

        binding.Test2.setOnClickListener(view->{
            Intent intent  = new Intent(this,NFCTestActivity.class);
            startActivity(intent);
        });

    }

}
