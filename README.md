# LogMeasureApp

The simple app to measure log diameter and length.

## Features

### Measurements Screen

- Shows all measurements screen
- Shows empty view if there are no measurements

### AddLogMeasurement Screen

- Allows to pick an image from the gallery 
- Add new measurement
  - Measure diameter
  - Measure length (not implemented)

## How to run app

To run the app you need latest stable [Android Studio](https://developer.android.com/studio).
Open the app folder in Terminal, attach device and run
```shell
./gradlew app:assembleDebug

./gradlew app:installDebug

./adb shell am start -n "dev.l3m4rk.logmeasureapp/dev.l3m4rk.logmeasureapp.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
```

## Technologies

- Kotlin
- Jetpack Compose
- Jetpack image picker
- Jetpack Room
- Jetpack Navigation
- Dagger Hilt

## TODOs

- [ ] Implement length measuring
- [ ] Make app work in landscape mode
- [ ] Improve UI/UX
