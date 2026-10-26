# Project Report - Calculator App (Minor Project)

Student: Garvit Sharma | Roll Number: AD-JV-A2/JY-11199 | Program: Android App Development + Java Training Program

## Project Overview
A basic calculator app for Android that performs addition, subtraction, multiplication and division.

## Objective
Practise Android UI, event handling and input validation in Java by building a small, complete app.

## Features
Four operations, decimals, backspace, sign toggle, Clear, chained operations, division-by-zero message, 15 digit limit, one decimal point only, exact decimal maths with BigDecimal, Material 3 light/dark theme with Material You colours.

## Technologies Used
Java, Android Studio, XML layouts, Material Components, Gradle, JUnit 4.

## Android Components Used
Activity, Application class, MaterialButton, TextView, Toast, LinearLayout, string/style resources, Intent filter (launcher).

## Setup Instructions
Open the folder in Android Studio, sync Gradle, run on an emulator or phone. Or run `./gradlew assembleDebug` and install `app/build/outputs/apk/debug/app-debug.apk`.

## Screenshots
Real screenshots from an emulator/phone: **pending, to be added by the student after running the app.**
Layout previews (rendered on a computer from the real XML with sample values, not captured from a running app) are in `docs/layout-previews/`.

## Challenges Faced
- Key size and weight were first set only in a style, which Android ignores for layout attributes. A layout preview showed the keypad missing, so the attributes were moved into the XML of each key.
- Keeping the logic testable: it was separated into `CalculatorEngine` with no Android code.

## Key Learning Outcomes
Separating logic from UI, unit testing, BigDecimal for exact decimals, Material 3 theming and dynamic colour.

## Project Structure
See README.md. `CalculatorEngine` holds logic, `MainActivity` binds the keypad, `Operator` lists the operations.

## Testing
17 unit tests pass. Layout previews inspected in light and dark mode. Not yet tested on an emulator or phone.

## Development note
This code was written and built in one session on 26 Oct 2026 with AI assistance and committed afterwards as a single honest snapshot; the commit history does not show gradual development.
