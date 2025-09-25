# HF7000 Test App - Debug APK Build Guide for VFD 100

## 🎯 Generate Debug APK for VFD 100 Testing

This guide provides multiple methods to build and obtain the debug APK optimized for your VFD 100 device.

## Method 1: Local Build (Recommended)

### Prerequisites
- Android Studio or Android SDK installed
- Java 8+ 
- Internet connection for initial dependency download

### Step-by-Step Build Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/adrianvince7/hf7k.git
   cd hf7k
   ```

2. **Build the debug APK:**
   ```bash
   # Make build script executable
   chmod +x build_test_app.sh
   
   # Build debug APK
   ./gradlew testapp:assembleDebug
   ```

3. **Locate the generated APK:**
   ```bash
   # APK will be generated at:
   testapp/build/outputs/apk/debug/testapp-debug.apk
   ```

4. **Verify APK details:**
   ```bash
   # Check APK information
   aapt dump badging testapp/build/outputs/apk/debug/testapp-debug.apk
   ```

### Expected APK Properties
- **Package Name**: `com.praescient.hf7k.testapp`
- **Version Code**: 1
- **Version Name**: 1.0
- **Min SDK**: 17 (Android 4.2)
- **Target SDK**: 22 (Android 5.1 - Perfect for VFD 100)
- **APK Size**: ~2-4 MB
- **Architecture**: Universal (supports ARMv7 - compatible with MT6572)

## Method 2: Android Studio Build

1. **Open project in Android Studio:**
   - File → Open → Select hf7k directory
   - Wait for Gradle sync to complete

2. **Select testapp module:**
   - In Project view, select `testapp` module

3. **Build debug APK:**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - Or use keyboard shortcut: Ctrl+Shift+A → "Build APK"

4. **Locate APK:**
   - Android Studio will show "APK(s) generated successfully" notification
   - Click "locate" to find the APK file

## Method 3: Command Line Build (Advanced)

```bash
# Clean previous builds
./gradlew clean

# Build debug APK with verbose output
./gradlew testapp:assembleDebug --info --stacktrace

# Alternative: Build all variants
./gradlew testapp:build

# Generate signed debug APK (optional)
./gradlew testapp:assembleDebug -Pandroid.injected.signing.store.file=debug.keystore
```

## Method 4: GitHub Actions Build (If Configured)

If you have GitHub Actions set up, the APK can be built automatically on every commit:

```yaml
# .github/workflows/build.yml
name: Build APK
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - uses: actions/setup-java@v2
      with:
        java-version: '8'
        distribution: 'adopt'
    - run: chmod +x gradlew
    - run: ./gradlew testapp:assembleDebug
    - uses: actions/upload-artifact@v2
      with:
        name: debug-apk
        path: testapp/build/outputs/apk/debug/testapp-debug.apk
```

## 📱 VFD 100 Installation Instructions

### Transfer APK to VFD 100

**Option 1: USB Transfer**
```bash
# Connect VFD 100 via USB with USB debugging enabled
adb devices  # Should show: NZQ4WKGAFAEUYLGY device

# Install directly
adb install testapp/build/outputs/apk/debug/testapp-debug.apk

# Or push to device storage first
adb push testapp/build/outputs/apk/debug/testapp-debug.apk /sdcard/
```

**Option 2: File Transfer**
1. Copy APK to microSD card or internal storage
2. Use file manager on VFD 100 to navigate to APK
3. Tap APK file to install
4. Enable "Unknown sources" if prompted

**Option 3: Cloud Transfer**
1. Upload APK to cloud service (Google Drive, Dropbox)
2. Download on VFD 100 using browser
3. Install from Downloads folder

### Installation Verification

After installation, verify on VFD 100:

1. **App appears in launcher** as "HF7000 Test App"
2. **Launch the app** - should show compatibility status
3. **Check device detection** - should show "VFD 100 detected - HF7000 ready!"
4. **Memory usage** - should show reasonable RAM usage in Settings

## 🔧 Troubleshooting Build Issues

### Common Build Errors and Solutions

**Error: "Could not resolve dependencies"**
```bash
# Solution: Clear Gradle cache and retry
./gradlew clean --refresh-dependencies
rm -rf ~/.gradle/caches/
./gradlew testapp:assembleDebug
```

**Error: "SDK not found"**
```bash
# Solution: Set ANDROID_HOME environment variable
export ANDROID_HOME=/path/to/android/sdk
./gradlew testapp:assembleDebug
```

**Error: "Build failed with Java version"**
```bash
# Solution: Use Java 8
export JAVA_HOME=/path/to/java8
./gradlew testapp:assembleDebug
```

**Error: "Out of memory during build"**
```bash
# Solution: Increase Gradle memory
export GRADLE_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
./gradlew testapp:assembleDebug
```

### VFD 100 Specific Issues

**Issue: "App not compatible with this device"**
- **Cause**: Wrong API level or architecture
- **Solution**: APK is configured for API 22 and ARMv7 - should work fine

**Issue: "Installation blocked"**
- **Cause**: Security settings
- **Solution**: Enable "Unknown sources" in Settings → Security

**Issue: "Insufficient storage"**
- **Cause**: Low internal storage on VFD 100
- **Solution**: Clear cache, uninstall unused apps

## 📋 APK Build Checklist

Before distributing the APK, verify:

- [ ] **Target SDK**: 22 (Android 5.1)
- [ ] **Min SDK**: 17 (broad compatibility)
- [ ] **Architecture**: ARMv7 (MT6572 compatible)
- [ ] **Permissions**: Bluetooth, Location (for VFD 100)
- [ ] **Features**: NFC optional, Fingerprint optional
- [ ] **Memory**: Low-memory mode enabled
- [ ] **Size**: Under 5MB for easy transfer

## 🚀 Quick Build Commands Summary

```bash
# Essential commands for APK generation
git clone https://github.com/adrianvince7/hf7k.git
cd hf7k
chmod +x gradlew
./gradlew testapp:assembleDebug

# APK location
ls -la testapp/build/outputs/apk/debug/testapp-debug.apk

# Install on VFD 100
adb install testapp/build/outputs/apk/debug/testapp-debug.apk
```

## 📞 Support

If build issues persist:
1. Check Android SDK installation
2. Verify Java 8+ is installed
3. Ensure internet connection for dependencies
4. Try clean build: `./gradlew clean testapp:assembleDebug`
5. Check logs for specific error messages

The debug APK will be specifically optimized for VFD 100 testing with HF7000 G0603990 device integration!