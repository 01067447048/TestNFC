package com.example.testnfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.testnfc.databinding.ActivityNfcsendBinding;

import java.nio.charset.Charset;
import java.util.Locale;

public class NFCSendActivity extends AppCompatActivity {

    ActivityNfcsendBinding binding;
    private NfcAdapter nfcAdapter;
    private NdefMessage ndefMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_nfcsend);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null){
            binding.sendtext.setText("NFC 단말기에 접촉해주세요"+nfcAdapter);
        }
        else{
            binding.sendtext.setText("NFC 기능이 꺼져있습니다."+nfcAdapter);
        }

        ndefMessage = new NdefMessage(
                new NdefRecord[]{
                    createNewTextRecord("이름 : 홍길동", Locale.ENGLISH,true),
                    createNewTextRecord("학번 : 20201111",Locale.ENGLISH,true)
                }
        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter !=null){
            nfcAdapter.setNdefPushMessage(ndefMessage,this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nfcAdapter !=null){
            nfcAdapter.setNdefPushMessage(ndefMessage,this);
        }
    }

    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodelnUtf8){
        byte[] langByte = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodelnUtf8 ? Charset.forName("UTF-8"):Charset.forName("UTF-16");

        byte[] textBytes = text.getBytes(utfEncoding);
        int utfBit = encodelnUtf8?0:(1<<7);
        char status = (char)(utfBit+langByte.length);
        byte[] data = new byte[1+langByte.length+textBytes.length];
        data[0] = (byte)status;

        System.arraycopy(langByte,0,data,1,langByte.length);
        System.arraycopy(textBytes,0,data,1+langByte.length,textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT,new byte[0],data);
    }
}
