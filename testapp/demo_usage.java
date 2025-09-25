/*
 * HF7000 Test App - Demo Usage Example
 * 
 * This file shows how to integrate the HF7000 Bluetooth Reader 
 * library into your Android application.
 */

// Example integration code for your Android app
public class HF7000Integration {
    
    private ReaderReceiver readerReceiver;
    private String storedTemplate = null;
    
    // 1. Setup the broadcast receiver in onCreate()
    private void setupHF7000() {
        readerReceiver = new ReaderReceiver();
        
        // Set up listeners for HF7000 responses
        ReaderReceiver.captureListener = new ReaderReceiver.CaptureListener() {
            @Override
            public void isCaptured(String template) {
                // Handle fingerprint capture result
                storedTemplate = template;
                Log.d("HF7000", "Fingerprint captured: " + (template != null ? "Success" : "Failed"));
            }
        };
        
        ReaderReceiver.matchListener = new ReaderReceiver.MatchListener() {
            @Override
            public void isMatch(boolean value) {
                // Handle fingerprint match result
                Log.d("HF7000", "Fingerprint match: " + (value ? "MATCHED" : "NOT MATCHED"));
            }
        };
        
        ReaderReceiver.cardListener = new ReaderReceiver.CardListener() {
            @Override
            public void isRead(String value) {
                // Handle NFC card read result
                Log.d("HF7000", "NFC Card Serial: " + value);
            }
        };
        
        // Register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.reader.capture");
        filter.addAction("com.reader.match");
        filter.addAction("com.reader.card");
        registerReceiver(readerReceiver, filter);
    }
    
    // 2. Capture a fingerprint
    public void captureFingerprint() {
        Intent intent = new Intent(this, BluetoothReader.class);
        intent.putExtra("action", "capture");
        startActivity(intent);
    }
    
    // 3. Match a fingerprint against stored template
    public void matchFingerprint() {
        if (storedTemplate != null && !storedTemplate.isEmpty()) {
            Intent intent = new Intent(this, BluetoothReader.class);
            intent.putExtra("action", "match");
            intent.putExtra("template", storedTemplate);
            startActivity(intent);
        }
    }
    
    // 4. Read NFC card
    public void readNFCCard() {
        Intent intent = new Intent(this, BluetoothReader.class);
        // No action specified = NFC card reading mode
        startActivity(intent);
    }
    
    // 5. Clean up in onDestroy()
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (readerReceiver != null) {
            unregisterReceiver(readerReceiver);
        }
    }
}

/*
 * Required permissions in AndroidManifest.xml:
 * 
 * <uses-permission android:name="android.permission.BLUETOOTH" />
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 * <uses-permission android:name="android.permission.NFC" />
 */

/*
 * Required dependencies in build.gradle:
 * 
 * dependencies {
 *     implementation project(':bluetoothreader')
 *     implementation 'com.github.interwap:BT01:v1.0'
 * }
 */