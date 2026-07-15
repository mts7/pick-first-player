# Optimize UI Tests in GitHub Actions

This plan optimizes the `ui-tests` job in your GitHub Actions workflow to reduce execution time and update the Android API level to a more modern version.

## User Review Required

> [!NOTE]
> I am proposing to use **API 34** (Android 14) with the **Google ATD (Android Test Device)** image.
> - **Why not API 36/37?** These are currently in Preview/Beta. While you target them, testing on the latest *stable* (API 34 or 35) is generally more reliable for CI to avoid emulator-level bugs.
> - **Why Google ATD?** ATD images are optimized for headless testing. They lack Google Play Services and other "bloat," making them significantly faster to boot and run on Linux runners.

## Proposed Changes

### GitHub Actions Workflow

#### [MODIFY] [android.yml](file:///Users/mts7/Repositories/pick-first-player/.github/workflows/android.yml)
- Update the `ui-tests` job:
    - Set `api-level: 34`.
    - Set `target: google_atd` for high-performance testing.
    - Set `arch: x86_64`.
    - Enable `disable-animations: true`.
    - (Optional) Use `reactivecircus/android-emulator-runner@v2` (ensure latest minor version).

## Verification Plan

### Automated Tests
- I will check the syntax of the updated YAML file.
- The user will need to push the changes to verify the actual "wall-clock" time reduction on GitHub.

### Manual Verification
- Monitor the GitHub Actions `ui-tests` job to ensure it completes successfully and faster than the previous 8-minute baseline.
