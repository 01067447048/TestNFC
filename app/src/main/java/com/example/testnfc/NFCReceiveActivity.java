package com.example.testnfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.testnfc.databinding.ActivityNfcreceiveactivityBinding;

public class NFCReceiveActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;
    ActivityNfcreceiveactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_nfcreceiveactivity);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            binding.count.setText("NFC가 꺼짐");
        }
        else{
            binding.count.setText("NFC가 켜짐");
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
}
