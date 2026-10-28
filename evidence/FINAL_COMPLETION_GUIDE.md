# Final completion guide

## 1. Build and test (must be done in Android Studio)
- Open the `SmartPantryManager` folder, not the outer ZIP folder.
- Use JDK 17 and install Android SDK Platform 35 / build tools when prompted.
- Allow Gradle sync to finish. Resolve any errors shown in the Build window before running.
- Start an API 26+ emulator or connect an Android phone with USB debugging.
- Run the `app` configuration and exercise the acceptance cases in `ACCEPTANCE.md`.
- Run `testDebugUnitTest` from Android Studio's Gradle tool window (or run `gradle testDebugUnitTest`
  if Gradle is installed). Save the real output. Do not write “passed” unless it did pass.
- The original project does not include the Gradle wrapper JAR, and this environment has no Android SDK
  or `adb`; therefore no APK or device verification is included in this pack.

## 2. Capture genuine screenshots
Capture the running app itself, not diagrams or mockups. Recommended minimum:
1. Empty pantry.
2. Populated pantry list.
3. Add ingredient form.
4. Validation error for blank name/invalid quantity.
5. Saved ingredient.
6. Edit form and updated quantity.
7. Delete confirmation and result after deletion.
8. Suggested Recipes with no qualifying recipes.
9. Suggested Recipes with a complete match.
10. Recipe detail with ingredients and method.
11. Settings, including expiry preference.
12. Strict-match proof: show a recipe disappear when one required ingredient/quantity is missing and return when corrected.
13. Persistence proof after closing and reopening the app.

Copy originals to `evidence/screenshots/`. Add each to the report with a figure number, a concise caption,
and the exact input/state that was visible. Never use the design SVGs as runtime screenshots.

## 3. GitHub
- Create a public repository (or add the marker as collaborator if private).
- Push the source and README. Add the actual repository URL to the report.
- Make descriptive commits for genuine work you do from now on, for example: configure build, resolve a
  reproducible build issue, add/repair validation, verify CRUD, fix a matching edge case, add test coverage,
  document real device results, and update report evidence. Only use a commit message when that change
  actually happened. Do not backdate or manufacture a development history; the brief specifically checks
  incremental history over time and a minimum of 10 meaningful commits.

## 4. Report truthfulness
Before submission, update:
- Screenshot section with actual screenshots and captions.
- Challenges section with 2–3 problems you personally encountered, how you diagnosed them, and the fix.
- Reflection with what you learned and what you would improve.
- References with sources you actually used and consistent Harvard style.
- Repository URL with a working URL.
- Declaration/signature only after checking the declaration is accurate and allowed by the institution's policy.

## 5. Final ZIP
The brief requires one ZIP under 50 MB containing the complete Android Studio source, a 5–7 minute narrated
MP4, and one report (Word or PDF). The current package intentionally does not include a fabricated video,
screenshots, repository URL, or signature. Add your real evidence before uploading to Moodle.
