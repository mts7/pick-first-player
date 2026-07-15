# pick-first-player
Picks the first player relative to the person holding the phone

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/mts7/pick-first-player/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/mts7/pick-first-player/tree/master)

## Active Investigation: Slow App Startup (Pixel 10)

The app experiences a significant delay (~46s) when opening on Pixel 10 devices.

### Root Cause Analysis
The logs show `AconfigStorageReadException: ERROR_PACKAGE_NOT_FOUND: package android.xr`. This suggests that targeting **SDK 37** (very futuristic) is causing the system to attempt to load XR-related components that do not exist on the current Pixel 10 image.

### Proposed Implementation Plan

#### 1. Build Configuration Adjustments
- **Downgrade SDK**: Lower `compileSdk` and `targetSdk` to **35** (Android 15) to ensure compatibility with current devices and emulators.
- **Splash Screen**: Integrate `androidx.core:core-splashscreen` to handle the startup transition correctly on Android 12+.

#### 2. MainActivity Optimizations
- **Fix BackHandler Logic**: The current `BackHandler` is always enabled, which intercepts the back gesture even at the root state. This should be changed to `enabled = numberOfPlayers.intValue > 0` so that the system handles the exit gesture naturally.
- **Cleanup Exit Logic**: Remove `exitProcess(0)` and rely on `finish()` or system defaults.
- **Scaffold Padding**: Ensure `contentPadding` is properly applied to child views.
- **Startup Logging**: Add `STARTUP_TIMING` logs to track exactly where the delay occurs.

### Verification Plan
- Deploy to Pixel 10 emulator.
- Verify `android.xr` error is resolved in Logcat.
- Confirm startup time is within normal ranges (< 2 seconds).
