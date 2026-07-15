# Walkthrough: Manifest and Test Fixes

I have addressed the specific API compatibility and deprecation warnings you identified.

## Changes Made

### Android Manifest
#### [MODIFY] [AndroidManifest.xml](file:///Users/mts7/Repositories/pick-first-player/app/src/main/AndroidManifest.xml)
- Updated `tools:targetApi` to `33`. This correctly informs the lint tool that the `android:enableOnBackInvokedCallback` attribute is intended for API 33 and higher, resolving the "only used in API level 33 and higher" warning.

### Instrumented Tests
#### [MODIFY] [MainActivityBackPressInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityBackPressInstrumentedTest.kt)
#### [MODIFY] [MainActivityLayoutInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityLayoutInstrumentedTest.kt)
#### [MODIFY] [MainActivityTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityTest.kt)
#### [MODIFY] [MainScreenLayoutInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainScreenLayoutInstrumentedTest.kt)
- Migrated all usages of `createAndroidComposeRule` and `createComposeRule` to the `androidx.compose.ui.test.junit4.v2` package.
- This resolves the deprecation warnings and aligns your tests with the latest Jetpack Compose testing practices, which use `StandardTestDispatcher` for more predictable coroutine behavior.

## Verification Results

### Automated Tests
- Ran `:app:lintDebug` successfully. This confirms that the Manifest warning is resolved and that the code changes are syntactically correct and compatible with the project's configuration.
