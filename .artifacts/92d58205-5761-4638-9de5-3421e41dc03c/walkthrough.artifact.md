# Walkthrough: UI Test Optimizations

I have optimized the `ui-tests` job in your GitHub Actions workflow to run faster and on a more modern Android version.

## Changes Made

### GitHub Actions Workflow
#### [MODIFY] [android.yml](file:///Users/mts7/Repositories/pick-first-player/.github/workflows/android.yml)
- **Updated API Level**: Switched from API 29 to **API 34** (Android 14) to better align with your app's target SDK.
- **Enabled Google ATD**: Changed the emulator target to `google_atd` (Android Test Device).
    > [!TIP]
    > ATD images are headless and optimized for CI. They lack background services like Google Play, which significantly reduces boot time and resource usage.
- **Disabled Animations**: Added `disable-animations: true` to the emulator runner. This prevents the emulator from waiting for UI transitions to finish, speeding up test execution.
- **Architecture**: Explicitly set `arch: x86_64` for optimal performance on GitHub's Linux runners.

## Expected Results
- **Faster Boot**: The `google_atd` image should boot much faster than the standard image.
- **Reduced Test Time**: Disabling animations and using a more efficient image should noticeably reduce the "wall-clock" time of your UI tests.

## How to Verify
1. **Push Changes**: Push this update to your `master` branch.
2. **Monitor Actions**: Observe the `ui-tests` job in the GitHub Actions tab. You should see a decrease in the total runtime compared to the previous 8-minute baseline.
