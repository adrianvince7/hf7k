# HF7000 Test Android App

This repository now includes a test Android application (`testapp` module) that demonstrates how to connect to and interact with HF7000 devices using the Bluetooth Reader library.

## Features

The test app provides a simple UI to test all major HF7000 device functionalities:

### 🔍 Fingerprint Operations
- **Capture Fingerprint**: Captures a fingerprint template from the HF7000 device
- **Match Fingerprint**: Compares a live fingerprint scan against a stored template

### 📱 NFC Card Operations  
- **Read NFC Card**: Reads the serial number from NFC cards/tags

### 📊 Real-time Status & Results
- Live status updates during operations
- Template storage and management
- Detailed operation results

## Project Structure

```
hf7k/
├── bluetoothreader/          # Library module for HF7000 communication
│   ├── src/main/java/com/praescient/components/bluetoothreader/
│   │   ├── BluetoothReader.java      # Main HF7000 communication class
│   │   ├── ReaderReceiver.java       # Broadcast receiver for results
│   │   └── Popup.java                # UI dialogs
│   └── build.gradle
├── testapp/                  # Test application module
│   ├── src/main/java/com/praescient/hf7k/testapp/
│   │   └── MainActivity.java         # Main test UI
│   ├── src/main/res/
│   │   ├── layout/activity_main.xml  # Main UI layout
│   │   └── values/                   # String resources & styles
│   └── build.gradle
└── build.gradle             # Root build configuration
```

## Building the App

### Prerequisites
- Android Studio 3.1+
- Android SDK API Level 28
- Gradle 4.10.3+
- Java 8+

### Build Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/adrianvince7/hf7k.git
   cd hf7k
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Choose "Open an existing Android Studio project"
   - Navigate to the cloned `hf7k` directory and select it

3. **Build the project**:
   ```bash
   ./gradlew clean build
   ```

4. **Install on device**:
   ```bash
   ./gradlew testapp:installDebug
   ```

## Usage Instructions

### 1. Device Setup
- Ensure your HF7000 device is powered on
- Enable Bluetooth on your Android device
- Make sure the HF7000 device is in pairing mode

### 2. App Operations

#### Fingerprint Capture
1. Tap "Capture Fingerprint"
2. The app will open the Bluetooth connection dialog
3. Select your HF7000 device from the list
4. Place finger on the HF7000 sensor when prompted
5. The captured template will appear in the "Stored Template" section

#### Fingerprint Matching
1. First capture a fingerprint (see above)
2. Tap "Match Fingerprint" 
3. Place the same or different finger on the HF7000 sensor
4. The app will display "MATCHED" or "NOT MATCHED" in the results

#### NFC Card Reading
1. Tap "Read NFC Card"
2. Connect to the HF7000 device
3. Place an NFC card near the HF7000 reader
4. The card serial number will appear in the results

### 3. UI Elements

- **Status**: Shows current operation status
- **Stored Template**: Displays preview of captured fingerprint template
- **Result**: Shows detailed results of operations
- **Clear**: Clears the stored template

## HF7000 Device Commands

The library supports these HF7000 commands:

| Command | Code | Description |
|---------|------|-------------|
| CMD_CAPTUREHOST | 0x08 | Capture fingerprint template |
| CMD_MATCH | 0x09 | Match fingerprint against template |
| CMD_WRITECARD | 0x0A | Write data to card |
| CMD_READCARD | 0x0B | Read data from card |
| CMD_CARDSN | 0x0C | Read card serial number |
| CMD_GETBAT | 0x0D | Get battery level |
| CMD_GETIMAGE | 0x0E | Get fingerprint image |
| CMD_GETCHAR | 0x0F | Get fingerprint characteristics |

## Broadcast Intents

The app communicates using these broadcast intents:

- `com.reader.capture` - Fingerprint capture result
- `com.reader.match` - Fingerprint match result  
- `com.reader.card` - NFC card read result

## Permissions Required

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.NFC" />
```

## Troubleshooting

### Connection Issues
- Ensure HF7000 device is powered and in pairing mode
- Check Bluetooth is enabled on Android device
- Try clearing Bluetooth cache in Android settings

### Build Issues
- Ensure Android SDK is properly installed
- Check that API Level 28 is available
- Verify Gradle version compatibility

### Runtime Issues
- Check that all required permissions are granted
- Ensure BT01 dependency is properly included
- Verify HF7000 device firmware is compatible

## Integration in Your App

To use this library in your own app:

1. Add the dependency:
   ```gradle
   dependencies {
       implementation project(':bluetoothreader')
       implementation 'com.github.interwap:BT01:v1.0'
   }
   ```

2. Register the broadcast receiver:
   ```java
   ReaderReceiver readerReceiver = new ReaderReceiver();
   ReaderReceiver.captureListener = this;
   ReaderReceiver.matchListener = this;
   ReaderReceiver.cardListener = this;
   
   IntentFilter filter = new IntentFilter();
   filter.addAction("com.reader.capture");
   filter.addAction("com.reader.match");
   filter.addAction("com.reader.card");
   registerReceiver(readerReceiver, filter);
   ```

3. Start operations:
   ```java
   Intent intent = new Intent(this, BluetoothReader.class);
   intent.putExtra("action", "capture"); // or "match"
   intent.putExtra("template", storedTemplate); // for matching
   startActivity(intent);
   ```

## License

Copyright 2017 Ikomi Moses

Licensed under the Apache License, Version 2.0. See LICENSE file for full details.