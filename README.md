# Calculator App

Minor Project - Android App Development + Java Training Program (InternsElite)

**Student:** Garvit Sharma
**Roll Number:** AD-JV-A2/JY-11199 (matched by name in the official InternsElite roll list)

A basic calculator for Android written in Java with a Material 3 (Material You) interface.

## Features

- Addition, subtraction, multiplication and division
- Decimal numbers, backspace, plus/minus toggle and a Clear (C) button
- Chained operations (2 + 3 × 4 is evaluated left to right, like a pocket calculator)
- Division by zero shows a clear message instead of crashing
- Input validation: one decimal point only, no leading zeros, 15 digit limit
- Exact decimal arithmetic with `BigDecimal` (0.1 + 0.2 = 0.3)
- Material You dynamic colours on Android 12+, light and dark theme

## Tech

Java 8 source level, Android Studio, XML layouts, Material Components 1.11, JUnit 4.
minSdk 24, targetSdk 34.

## Project structure

| File | Purpose |
|------|---------|
| `MainActivity.java` | Screen: connects the buttons to the engine and updates the display |
| `CalculatorEngine.java` | All calculator logic (no Android code, so it is unit tested) |
| `Operator.java` | The four operations |
| `CalculatorApp.java` | Application class that turns on dynamic colour |
| `res/layout/activity_main.xml` | Display and keypad layout |
| `CalculatorEngineTest.java` | 17 unit tests for the engine |

## Run it

1. Open this folder in Android Studio (Hedgehog or newer, JDK 17).
2. Let Gradle sync, then press Run on an emulator or a phone.
3. Command line: `./gradlew testDebugUnitTest assembleDebug` - the APK is created at `app/build/outputs/apk/debug/app-debug.apk`.

## Testing done

- 17 unit tests (`./gradlew testDebugUnitTest`): all four operations, decimals, divide by zero, chaining, clear, backspace, sign toggle, digit limit.
- Layout rendered in light and dark mode on a Pixel 5 frame to check the keypad.
- Not yet recorded: a run on an emulator or physical phone. See the report.
