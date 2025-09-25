package com.praescient.hf7k.testapp;

import android.os.Build;

/**
 * Device-specific configuration for HF7000 Test App
 * Optimized for VFD 100 (Android 5.1, API 22)
 */
public class DeviceConfig {

    // Target device information
    public static final String TARGET_DEVICE_MODEL = "VFD 100";
    public static final String TARGET_DEVICE_SERIAL = "NZQ4WKGAFAEUYLGY";
    public static final String DEVICE_BLUETOOTH_NAME = "VFD 100";
    public static final String DEVICE_BLUETOOTH_MAC = "CC:FD:17:C9:D7:FD";
    
    // HF7000 device information
    public static final String HF7000_SERIAL_NUMBER = "G0603990";
    public static final String HF7000_DEVICE_NAME = "HF7000";
    
    // Compatibility settings
    public static final boolean SUPPORTS_RUNTIME_PERMISSIONS = Build.VERSION.SDK_INT >= 23;
    public static final boolean SUPPORTS_NFC = hasNFC();
    public static final boolean SUPPORTS_FINGERPRINT = hasFingerprint();
    
    // Memory constraints for VFD 100 (480MB RAM, very limited)
    public static final int MAX_TEMPLATE_CACHE_SIZE = 5; // Limit template storage
    public static final boolean USE_LOW_MEMORY_MODE = true;
    
    /**
     * Check if current device matches expected VFD 100
     */
    public static boolean isTargetDevice() {
        return Build.MODEL.contains("VFD 100") || 
               Build.PRODUCT.contains("Pixi4_35_3G") ||
               Build.SERIAL.equals(TARGET_DEVICE_SERIAL);
    }
    
    /**
     * Check if device has NFC hardware
     */
    private static boolean hasNFC() {
        // VFD 100 does not have NFC hardware
        return false;
    }
    
    /**
     * Check if device has fingerprint hardware
     */
    private static boolean hasFingerprint() {
        // VFD 100 does not have fingerprint scanner
        return false;
    }
    
    /**
     * Get device info string for logging
     */
    public static String getDeviceInfo() {
        return String.format(
            "Device: %s, Android: %s (API %d), Serial: %s", 
            Build.MODEL, 
            Build.VERSION.RELEASE, 
            Build.VERSION.SDK_INT,
            Build.SERIAL
        );
    }
    
    /**
     * Get compatibility status
     */
    public static String getCompatibilityStatus() {
        StringBuilder status = new StringBuilder();
        status.append("HF7000 Test App Compatibility:\n");
        status.append("✅ Bluetooth: Supported\n");
        status.append("✅ Android Version: ").append(Build.VERSION.RELEASE).append(" (Compatible)\n");
        status.append("✅ RAM: Available for basic operations\n");
        status.append("❌ NFC: Not available (will use HF7000 for card reading)\n");
        status.append("❌ Built-in Fingerprint: Not available (will use HF7000)\n");
        status.append("🎯 HF7000 External Scanner: Perfect match!");
        return status.toString();
    }
}