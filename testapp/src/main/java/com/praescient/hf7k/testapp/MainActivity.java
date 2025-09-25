package com.praescient.hf7k.testapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.praescient.components.bluetoothreader.BluetoothReader;
import com.praescient.components.bluetoothreader.ReaderReceiver;

public class MainActivity extends AppCompatActivity implements 
        ReaderReceiver.CaptureListener, 
        ReaderReceiver.MatchListener, 
        ReaderReceiver.CardListener {

    private TextView tvStatus;
    private TextView tvStoredTemplate;
    private TextView tvResult;
    private Button btnCapture;
    private Button btnMatch;
    private Button btnReadCard;
    private Button btnClear;

    private ReaderReceiver readerReceiver;
    private String storedTemplate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupReaderReceiver();
        setupClickListeners();
        updateUI();
    }

    private void initViews() {
        tvStatus = findViewById(R.id.tvStatus);
        tvStoredTemplate = findViewById(R.id.tvStoredTemplate);
        tvResult = findViewById(R.id.tvResult);
        btnCapture = findViewById(R.id.btnCapture);
        btnMatch = findViewById(R.id.btnMatch);
        btnReadCard = findViewById(R.id.btnReadCard);
        btnClear = findViewById(R.id.btnClear);
    }

    private void setupReaderReceiver() {
        readerReceiver = new ReaderReceiver();
        ReaderReceiver.captureListener = this;
        ReaderReceiver.matchListener = this;
        ReaderReceiver.cardListener = this;

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.reader.capture");
        filter.addAction("com.reader.match");
        filter.addAction("com.reader.card");
        registerReceiver(readerReceiver, filter);
    }

    private void setupClickListeners() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureFingerprint();
            }
        });

        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchFingerprint();
            }
        });

        btnReadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNFCCard();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStoredTemplate();
            }
        });
    }

    private void captureFingerprint() {
        updateStatus("Starting fingerprint capture...");
        updateResult("");
        
        Intent intent = new Intent(this, BluetoothReader.class);
        intent.putExtra("action", "capture");
        startActivity(intent);
    }

    private void matchFingerprint() {
        if (storedTemplate == null || storedTemplate.isEmpty()) {
            Toast.makeText(this, "Please capture a fingerprint first", Toast.LENGTH_SHORT).show();
            return;
        }

        updateStatus("Starting fingerprint matching...");
        updateResult("");
        
        Intent intent = new Intent(this, BluetoothReader.class);
        intent.putExtra("action", "match");
        intent.putExtra("template", storedTemplate);
        startActivity(intent);
    }

    private void readNFCCard() {
        updateStatus("Starting NFC card reading...");
        updateResult("");
        
        Intent intent = new Intent(this, BluetoothReader.class);
        // NFC card reading uses default action (no action parameter)
        startActivity(intent);
    }

    private void clearStoredTemplate() {
        storedTemplate = null;
        updateUI();
        updateStatus("Template cleared");
        updateResult("");
    }

    private void updateStatus(String status) {
        tvStatus.setText(status);
    }

    private void updateResult(String result) {
        tvResult.setText(result);
    }

    private void updateUI() {
        if (storedTemplate != null && !storedTemplate.isEmpty()) {
            tvStoredTemplate.setText("Template stored: " + storedTemplate.substring(0, Math.min(50, storedTemplate.length())) + "...");
            btnMatch.setEnabled(true);
        } else {
            tvStoredTemplate.setText(getString(R.string.no_template_stored));
            btnMatch.setEnabled(false);
        }
    }

    // ReaderReceiver.CaptureListener implementation
    @Override
    public void isCaptured(String template) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                storedTemplate = template;
                updateStatus("Fingerprint captured successfully!");
                updateResult("Template: " + (template != null ? template.substring(0, Math.min(100, template.length())) + "..." : "null"));
                updateUI();
            }
        });
    }

    // ReaderReceiver.MatchListener implementation
    @Override
    public void isMatch(boolean value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateStatus("Fingerprint matching completed");
                updateResult("Match result: " + (value ? "MATCHED" : "NOT MATCHED"));
            }
        });
    }

    // ReaderReceiver.CardListener implementation
    @Override
    public void isRead(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateStatus("NFC card read successfully!");
                updateResult("Card Serial: " + value);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (readerReceiver != null) {
            unregisterReceiver(readerReceiver);
        }
    }
}