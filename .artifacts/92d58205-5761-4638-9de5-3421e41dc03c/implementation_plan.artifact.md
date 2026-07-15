# Fix Manifest Lint and Test Deprecation Warnings

This plan addresses specific warnings identified by the user regarding API compatibility in the Manifest and deprecated test APIs in Jetpack Compose.

## Proposed Changes

### Android Manifest

#### [MODIFY] [AndroidManifest.xml](file:///Users/mts7/Repositories/pick-first-player/app/src/main/AndroidManifest.xml)
- Update `tools:targetApi` from `31` to `33` to suppress the warning for `android:enableOnBackInvokedCallback`, which was introduced in API 33.

### Instrumented Tests

#### [MODIFY] [MainActivityBackPressInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityBackPressInstrumentedTest.kt)
- Migrate `createAndroidComposeRule` to `androidx.compose.ui.test.junit4.v2.createAndroidComposeRule`.

#### [MODIFY] [MainActivityLayoutInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityLayoutInstrumentedTest.kt)
- Migrate `createAndroidComposeRule` to `androidx.compose.ui.test.junit4.v2.createAndroidComposeRule`.

#### [MODIFY] [MainActivityTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainActivityTest.kt)
- Migrate `createComposeRule` to `androidx.compose.ui.test.junit4.v2.createComposeRule`.

#### [MODIFY] [MainScreenLayoutInstrumentedTest.kt](file:///Users/mts7/Repositories/pick-first-player/app/src/androidTest/java/com/mts7/pickfirstplayer/MainScreenLayoutInstrumentedTest.kt)
- Migrate `createComposeRule` to `androidx.compose.ui.test.junit4.v2.createComposeRule`.

## Verification Plan

### Automated Tests
- Run the instrumented tests to ensure the migration to `v2` APIs doesn't break synchronization or execution logic:
  - `./gradlew connectedAndroidTest`
- Run lint to verify the Manifest warning is gone:
  - `./gradlew lintDebug`
