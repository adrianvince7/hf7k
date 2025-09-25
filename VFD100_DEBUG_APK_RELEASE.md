# HF7000 Test App - VFD 100 Debug APK Release

## 📱 Ready-to-Install Debug APK for VFD 100

This is the optimized debug build specifically configured for your VFD 100 device running Android 5.1.

### APK Specifications
- **Target Device**: VFD 100 (Serial: NZQ4WKGAFAEUYLGY) 
- **Android Version**: 5.1 (API 22) - Perfect Match
- **Architecture**: ARMv7 (Compatible with MediaTek MT6572)
- **Package**: com.praescient.hf7k.testapp
- **Version**: 1.0 (Build 1)
- **Size**: ~2-4 MB (Memory optimized)

### Quick Installation

#### Method 1: ADB Install (Recommended)
```bash
# 1. Enable USB debugging on VFD 100:
#    Settings → About Phone → Tap "Build number" 7 times
#    Settings → Developer Options → Enable USB Debugging

# 2. Connect VFD 100 to computer via USB

# 3. Verify device connection:
adb devices
# Should show: NZQ4WKGAFAEUYLGY	device

# 4. Install APK:
adb install testapp-debug.apk

# 5. Launch app on VFD 100
# Look for "HF7000 Test App" icon in app launcher
```

#### Method 2: Manual Install
```bash
# 1. Copy APK to VFD 100 (via USB, SD card, or download)
# 2. Use file manager to navigate to APK location  
# 3. Tap APK file to install
# 4. Enable "Unknown sources" in Settings → Security if prompted
# 5. Confirm installation
```

### Build Instructions (For Local APK Generation)

If you need to build the APK yourself:

```bash
# Clone repository
git clone https://github.com/adrianvince7/hf7k.git
cd hf7k

# Build debug APK
chmod +x build_debug_apk.sh
./build_debug_apk.sh

# APK will be generated at:
# testapp/build/outputs/apk/debug/testapp-debug.apk
```

### VFD 100 Optimization Features

The APK includes these VFD 100-specific optimizations:

✅ **Memory Management**
- Low-memory mode for 480MB RAM constraint
- Template cache limited to 5 items
- Efficient memory cleanup

✅ **Hardware Compatibility**
- No built-in NFC required (uses HF7000)
- No fingerprint scanner required (uses HF7000)
- Bluetooth LE support for HF7000 connection

✅ **Android 5.1 Specific**
- API 22 compatibility
- Install-time permissions (no runtime prompts)
- Support Library v22.2.1

✅ **Device Detection**
- Automatic VFD 100 recognition
- Device-specific configuration
- Compatibility status display

### Testing Checklist

After installing on VFD 100:

- [ ] **App Launch**: "HF7000 Test App" appears in launcher
- [ ] **Device Detection**: Shows "VFD 100 detected - HF7000 ready!"
- [ ] **UI Display**: All buttons and text visible correctly
- [ ] **Memory Usage**: App runs smoothly without crashes
- [ ] **Bluetooth Ready**: Can scan for HF7000 device

### HF7000 G0603990 Integration Test

With HF7000 connected:

- [ ] **Bluetooth Pairing**: HF7000 pairs successfully with VFD 100
- [ ] **Fingerprint Capture**: Places finger on HF7000, captures template
- [ ] **Template Storage**: Shows captured template in app
- [ ] **Fingerprint Match**: Compares new scan against stored template  
- [ ] **NFC Card Read**: Places card on HF7000, reads serial number
- [ ] **Results Display**: All operations show clear status and results

### APK Security & Permissions

The debug APK includes only necessary permissions:

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.NFC" />

<!-- Optional features (device doesn't require these) -->
<uses-feature android:name="android.hardware.nfc" android:required="false" />
<uses-feature android:name="android.hardware.fingerprint" android:required="false" />
```

### Troubleshooting

**Issue**: APK won't install
**Solution**: Enable "Unknown sources" in Settings → Security

**Issue**: App crashes on startup  
**Solution**: Check Android version is 5.1+, clear app cache

**Issue**: Can't connect to HF7000
**Solution**: Verify Bluetooth pairing, check HF7000 power and proximity

**Issue**: Out of memory errors
**Solution**: Close other apps, restart VFD 100, clear app data

### File Information

```
Filename: testapp-debug.apk
Package: com.praescient.hf7k.testapp  
Version Code: 1
Version Name: 1.0
Min SDK: 17 (Android 4.2+)
Target SDK: 22 (Android 5.1) 
Architecture: Universal (ARMv7 compatible)
Signature: Debug (suitable for testing only)
```

### Next Steps After Installation

1. **Power on HF7000 G0603990**
2. **Pair with VFD 100 via Bluetooth**
3. **Launch HF7000 Test App**
4. **Verify compatibility message**
5. **Test all functions**: Capture, Match, NFC Read
6. **Report results** for any issues or improvements needed

The APK is ready for immediate testing on your VFD 100 device with HF7000 G0603990!