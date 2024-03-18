# Group 16

## Which APK's are used during testing

Både _app-debug-androidTest.apk_ og _app-debug.apk_ blir installert under testing. androidTest versionen er apk-filen som kjører testene, mens den andre er appen som kjører.

## Detailed descript of the steps in the test

### The expected Result

### Which class/metods implements the test

## ADB

`adb install` blir brukt for å installere APK-filen som blir generert av Gradle. `adb shell am instrument` blir brukt for å starte testene. `adb shell am start` blir brukt for å starte hoved-aktiviteten i appen. `adb shell am force-stop` blir brukt for å stoppe appen. `adb logcat` blir brukt for å vise loggen generert av appen.
