#!/bin/bash

# HF7000 Test App - Debug APK Builder for VFD 100
# Optimized for offline and online building scenarios

echo "🏗️  Building HF7000 Test App Debug APK for VFD 100"
echo "=================================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo -e "${YELLOW}⚠️  Warning: ANDROID_HOME not set${NC}"
    echo "Please set ANDROID_HOME to your Android SDK installation directory"
    echo "Example: export ANDROID_HOME=/path/to/android/sdk"
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2)
echo -e "${BLUE}☕ Java Version: ${JAVA_VERSION}${NC}"

# Check device info
if [ -n "$1" ]; then
    echo -e "${BLUE}📱 Target Device: $1${NC}"
else
    echo -e "${BLUE}📱 Target Device: VFD 100 (Android 5.1, API 22)${NC}"
fi

echo ""
echo "🧹 Cleaning previous builds..."
./gradlew clean --quiet

echo ""
echo "🔨 Building debug APK..."

# Try building with different strategies
if ./gradlew testapp:assembleDebug --offline 2>/dev/null; then
    echo -e "${GREEN}✅ Offline build successful!${NC}"
elif ./gradlew testapp:assembleDebug --refresh-dependencies; then
    echo -e "${GREEN}✅ Online build successful!${NC}"
else
    echo -e "${RED}❌ Build failed. See DEBUG_APK_BUILD_GUIDE.md for troubleshooting${NC}"
    exit 1
fi

# Check if APK was generated
APK_PATH="testapp/build/outputs/apk/debug/testapp-debug.apk"

if [ -f "$APK_PATH" ]; then
    APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
    echo ""
    echo -e "${GREEN}🎉 APK Built Successfully!${NC}"
    echo -e "${BLUE}📦 Location: ${APK_PATH}${NC}"
    echo -e "${BLUE}📏 Size: ${APK_SIZE}${NC}"
    
    # Get APK info if aapt is available
    if command -v aapt &> /dev/null; then
        echo ""
        echo -e "${BLUE}📋 APK Information:${NC}"
        aapt dump badging "$APK_PATH" | grep -E "(package|sdkVersion|targetSdkVersion|application-label)"
    fi
    
    echo ""
    echo -e "${YELLOW}📱 VFD 100 Installation Instructions:${NC}"
    echo "1. Connect VFD 100 via USB with USB debugging enabled"
    echo "2. Run: adb devices (should show: NZQ4WKGAFAEUYLGY)"
    echo "3. Run: adb install $APK_PATH"
    echo ""
    echo "Alternative:"
    echo "1. Copy APK to VFD 100 storage"
    echo "2. Use file manager to install APK"
    echo "3. Enable 'Unknown sources' if prompted"
    
    # Offer to install directly if ADB device is connected
    if command -v adb &> /dev/null; then
        if adb devices | grep -q "device$"; then
            echo ""
            read -p "📲 Install on connected device now? (y/n): " -n 1 -r
            echo ""
            if [[ $REPLY =~ ^[Yy]$ ]]; then
                echo "🚀 Installing APK..."
                if adb install "$APK_PATH"; then
                    echo -e "${GREEN}✅ APK installed successfully!${NC}"
                    echo -e "${BLUE}🎯 Look for 'HF7000 Test App' in device launcher${NC}"
                else
                    echo -e "${YELLOW}⚠️  Installation failed. Try manual installation.${NC}"
                fi
            fi
        fi
    fi
    
    echo ""
    echo -e "${GREEN}🔧 Next Steps:${NC}"
    echo "1. Power on HF7000 device (Serial: G0603990)"
    echo "2. Pair HF7000 with VFD 100 via Bluetooth"
    echo "3. Launch 'HF7000 Test App' on VFD 100"
    echo "4. Verify device compatibility message"
    echo "5. Test fingerprint capture and NFC card reading"
    
else
    echo -e "${RED}❌ APK not found at expected location: $APK_PATH${NC}"
    echo -e "${YELLOW}Check build logs above for errors${NC}"
    exit 1
fi

echo ""
echo -e "${GREEN}🎊 Build complete! APK ready for VFD 100 testing.${NC}"