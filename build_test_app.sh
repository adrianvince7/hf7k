#!/bin/bash

# Build script for HF7000 Test App
# This script builds the test Android application for HF7000 device testing

echo "🚀 Building HF7000 Test App..."
echo "================================="

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ Error: ANDROID_HOME environment variable is not set"
    echo "Please set ANDROID_HOME to your Android SDK installation directory"
    exit 1
fi

echo "✅ Android SDK found at: $ANDROID_HOME"

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build the bluetoothreader library
echo "📚 Building bluetoothreader library..."
./gradlew bluetoothreader:build

if [ $? -eq 0 ]; then
    echo "✅ bluetoothreader library built successfully"
else
    echo "❌ Failed to build bluetoothreader library"
    exit 1
fi

# Build the test app
echo "📱 Building HF7000 test app..."
./gradlew testapp:build

if [ $? -eq 0 ]; then
    echo "✅ HF7000 test app built successfully"
    echo ""
    echo "📦 APK Location:"
    find . -name "*.apk" -type f | head -5
    echo ""
    echo "🎉 Build completed successfully!"
    echo ""
    echo "To install on device:"
    echo "  ./gradlew testapp:installDebug"
    echo ""
    echo "To install on specific device:"
    echo "  adb -s DEVICE_ID install testapp/build/outputs/apk/debug/testapp-debug.apk"
else
    echo "❌ Failed to build test app"
    exit 1
fi

echo ""
echo "📖 For usage instructions, see: HF7000_TEST_APP_README.md"