# Migration from CircleCI to GitHub Actions

This plan outlines the steps to migrate the existing CI/CD pipeline from CircleCI to GitHub Actions, maintaining the same levels of verification (Lint, Unit Tests, Build, and UI Tests).

## User Review Required

> [!IMPORTANT]
> GitHub Actions' free tier for macOS runners (recommended for hardware-accelerated emulators) is more limited than Linux. However, we can use `reactivecircus/android-emulator-runner` on a Linux runner with some caveats, or a macOS runner if preferred. I will start with a Linux runner for cost-efficiency.

## Proposed Changes

### GitHub Actions Workflow

#### [NEW] [android.yml](file:///Users/mts7/Repositories/pick-first-player/.github/workflows/android.yml)
Create a new workflow file that replicates the CircleCI logic:
- **Triggers**: On push to `master` and all pull requests.
- **Environment**: Ubuntu-latest (Linux).
- **Setup**: Java 17 and Gradle.
- **Job: verification**:
    - Runs `./gradlew lint`
    - Runs `./gradlew test`
- **Job: ui-tests**:
    - Use `reactivecircus/android-emulator-runner` to run `./gradlew connectedAndroidTest`.
- **Job: build**:
    - **Depends on**: `verification` and `ui-tests` (using `needs`).
    - Run `./gradlew assembleDebug`
    - Upload the APK as an artifact.

## Verification Plan

### Automated Tests
- I will verify the syntax of the new YAML file.
- I will attempt to trigger the workflow (manually if possible, or by suggesting a commit).

### Manual Verification
- Verify that the APK artifact is correctly uploaded in the GitHub Actions "Summary" tab.
- Verify that unit tests and lint reports are accessible.
