# Modernize GitHub Actions with Gradle Managed Devices

This plan replaces the 3rd-party emulator runner with the official **Gradle Managed Devices (GMD)** feature. GMD provides a more stable, built-in way to manage emulators directly from the Android Gradle Plugin (AGP), optimizing for headless CI environments.

## User Review Required

> [!IMPORTANT]
> This change moves the emulator configuration into your `build.gradle` file. This is the recommended practice for modern Android development as it ensures the same environment is used locally and on CI.
>
> We will use **API 34** with the **Google ATD (Android Test Device)** image. ATD images are optimized for headless testing, which will help keep execution time as low as possible on Linux runners.

## Proposed Changes

### Build Configuration

#### [MODIFY] [app/build.gradle](file:///Users/mts7/Repositories/pick-first-player/app/build.gradle)
- Add `testOptions.managedDevices` block to define a `pixel6Api34` device.
- Configure it to use `apiLevel = 34` and `systemImageSource = "google-atd"`.

### GitHub Actions Workflow

#### [MODIFY] [android.yml](file:///Users/mts7/Repositories/pick-first-player/.github/workflows/android.yml)
- Simplify the `ui-tests` job:
    - Remove the `reactivecircus/android-emulator-runner` action.
    - Run the tests using the GMD command: `./gradlew pixel6Api34DebugAndroidTest`.
    - (Optional) Add a step to clean up GMD artifacts if needed, though GHA runners are ephemeral.

### Instrumented Tests

#### [MODIFY] [MainActivityBackPressInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityBackPressInstrumentedTest.kt)
- Replace the legacy Espresso `pressBack()` with the modern Compose-native `performKeyInput { pressKey(Key.Back) }`.
- This avoids the common `RootViewWithoutFocusException` that occurs on headless runners when using system-level back press events.

## Verification Plan

### Automated Tests
- Verify that `app/build.gradle` compiles after adding the GMD block.
- Verify that the GitHub Actions workflow syntax is valid.

### Manual Verification
- After pushing, monitor the GitHub Actions "Summary" tab.
- The `ui-tests` job should now use the native Gradle task and report results directly to the workflow summary.
