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
    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            binding.text.setText("NFC 사용가능");
        }
        else{
            binding.text.setText("NFC 불가능");
        }

        Intent intent = new Intent(this,NFCActivity.class);
        pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try{
            ndefIntent.addDataType("*/*");
        }catch(Exception e){
            Log.e("TagDispatch",e.toString());
        }
        mIntentFilters = new IntentFilter[]{ndefIntent,};

        mNFCTechLists = new String[][]{new String[]{NfcF.class.getName()}};

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(nfcAdapter!=null){
            nfcAdapter.enableForegroundDispatch(this,pendingIntent,mIntentFilters,mNFCTechLists);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
            finish();
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }*/
}