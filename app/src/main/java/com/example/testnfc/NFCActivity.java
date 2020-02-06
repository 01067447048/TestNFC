package com.example.testnfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.testnfc.databinding.ActivityNfcBinding;
import java.util.Arrays;

public class NFCActivity extends AppCompatActivity {

    ActivityNfcBinding binding;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_nfc);

        Intent passedIntent = getIntent();
        if(passedIntent != null){
            onNewIntent(passedIntent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        String s = "";
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(data!=null){
            try{
                for(int i=0; i<data.length; i++){
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for(j=0; j<recs.length; j++){
                        if(recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN&& Arrays.equals(recs[j].getType(),NdefRecord.RTD_TEXT)){
                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0]&0200) ==0)?"UTF-8":"UTF-16";
                            int langCodeLen = payload[0]&0077;
                            s+=("\n"+new String(payload,langCodeLen+1,payload.length-langCodeLen-1,textEncoding));
                        }
                    }
                }
            }catch(Exception e){
                Log.e("TagDispatch",e.toString());
            }
        }
        binding.text2.setText(s);
    }
}
