# Fix Hardware Acceleration and Modernize Build Configuration

This plan addresses the critical `x86_64 emulation currently requires hardware acceleration!` error by enabling KVM on the GitHub Actions runner. It also modernizes the build configuration to resolve deprecation warnings and align with Android Gradle Plugin (AGP) 9 standards.

## Proposed Changes

### GitHub Actions Workflow

#### [MODIFY] [android.yml](file:///Users/mts7/Repositories/pick-first-player/.github/workflows/android.yml)
- **Enable KVM**: Add a step to grant permissions to `/dev/kvm`.
- **Force Software Rendering**: Pass `-Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect` to the GMD task.
- **Gradle Caching**: Ensure the GMD setup and system images are cached to avoid re-downloading them every time (though GMD images are quite large, this helps with the setup task).

### Build Configuration

#### [MODIFY] [app/build.gradle](file:///Users/mts7/Repositories/pick-first-player/app/build.gradle)
- **Migrate `packagingOptions`**: Rename to `packaging` to resolve the deprecation warning.
- **Refine `testOptions`**: Ensure the DSL is as clean as possible for AGP 9.

#### [MODIFY] [build.gradle](file:///Users/mts7/Repositories/pick-first-player/build.gradle)
- **Cleanup `buildscript`**: Remove the redundant Kotlin plugin dependency in the top-level `buildscript` block, as it is now managed via the `plugins` block and AGP's built-in support.

## Verification Plan

### Automated Tests
- Run `:app:lintDebug` to ensure no new deprecation warnings are introduced.
- The `ui-tests` job in GitHub Actions must pass on the `pixel6Api34` device.

### Manual Verification
- Check the GitHub Actions logs for the "Enable KVM" step output.
- Verify that the total runtime for `ui-tests` is reasonable (aiming for < 10 minutes with ATD and KVM).
