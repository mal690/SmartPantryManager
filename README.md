# Smart Pantry Manager
Java Android coursework implementation for Malcolm Mashinya (402110146).

## Delivery status — read first
Generated implementation and documentation scaffold, not a certified finished submission.
No Android SDK/JDK/emulator is available in the generation environment: no APK was built,
no Android screen was captured and the included JUnit tests have NOT been executed.
The student's narrated video, genuine GitHub development history, accessible repository URL,
actual screenshots, signed originality declaration and personal reflection remain outstanding.
Do not claim this generated implementation is entirely unaided original work. Follow your
institution's AI assistance policy and acknowledge assistance as required.

## App
Five Activities: Pantry, Add/Edit Ingredient, Suggested Recipes, Recipe Detail, Settings.
Local SQLiteOpenHelper database: full pantry CRUD; 20 recipes seeded transactionally during
first creation. Custom ListView BaseAdapter; explicit Intent extras for record identifiers.
Persistent in-app expiry labels and expired-entry matching preference use SharedPreferences.
No network, GPS, maps, payments, accounts or mandatory shopping ingredients.

## Why SQLite
Single-device, offline pantry use does not require a server or login. SQLite persists all pantry
and recipe records locally, with foreign keys for recipe requirements. Database operations
are serialized on a background executor, and UI updates return to the main thread.

## Build / run
1. Install Android Studio, Android SDK Platform 35 and JDK 17. Install Gradle 8.9.
2. In the project root run `gradle wrapper --gradle-version 8.9` to create the standard wrapper.
   The wrapper JAR/binaries are not bundled. Gradle and Android dependencies need internet on first use.
3. Open this folder in Android Studio, select JDK 17, allow Gradle sync, and install requested SDK tools.
4. Start an API 26-or-newer emulator or connect an Android device with USB debugging enabled.
5. Run the `app` configuration. Alternatively use `./gradlew assembleDebug` (Windows: `gradlew.bat assembleDebug`).
6. Run unit tests with `./gradlew testDebugUnitTest`. These are authored tests, not a test-pass claim.
7. For local SDK discovery allow Android Studio to generate local.properties; never commit that file.

Compile SDK 35, minimum SDK 26, target SDK 28. This deliberate compatibility target keeps the
platform Views implementation simple; it is NOT suitable for a Play Store release without
updating the target and reviewing current platform behavior. Store publication is out of scope.

## Strict matching contract
Canonical lower-case ingredient name + compatible unit dimension identifies a requirement.
Explicit singular/plural aliases avoid dangerous blanket stemming. Pantry duplicates and recipe
requirements are summed using BigDecimal. Every requirement must be fully met. Empty recipes
never qualify. kg/g and l/ml convert by 1000; pcs never converts to mass or volume.
No oil, salt, water, seasoning or other ingredient is implicitly assumed. Recipe steps use only
listed ingredients. Ingredient naming is not a full food ontology: arbitrary synonyms are not supported.
Recipes expressed in pcs require pcs pantry entries. Bread pcs means slices, as stated in the form.
Expiry exclusion defaults ON; today counts as available; a missing expiry remains eligible.
Cooking does not consume stock automatically. Update quantities after preparing food.

## Source map
- Matching.java: platform-independent canonicalisation, dimensions, quantities and strict predicate.
- PantryDb.java: schema, seeding, CRUD, queries and expiry-aware recipe suggestions.
- BaseActivity.java: consistent UI, navigation, background database work and errors.
- PantryAdapter.java: reusable list rows, database identity and in-app date labels.
- Five *Activity.java files: the required screens.
- app/src/main/assets/recipes.json: 20 original simple recipes.
- app/src/test/.../MatchingTest.java: 17 authored JUnit tests.

## Evidence / limitations
See evidence/ACCEPTANCE.md and evidence/VIDEO_PLAN.md. Actual screenshots and video are not
included and must not be replaced with mockups. See the report for the same honest status.
No public GitHub repository has been created. Git history cannot be retroactively manufactured.
No version migration exists beyond initial database version 1; later schema changes need a migration.
UI uses Java-created LinearLayouts and native platform widgets, not XML layouts or Kotlin.

## Documentation consulted (Harvard)
Google (n.d.) Save data using SQLite. Available at: https://developer.android.com/training/data-storage/sqlite (Accessed: 23 October 2026).
Google (n.d.) SQLiteOpenHelper. Available at: https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper (Accessed: 23 October 2026).
Google (n.d.) Intents and intent filters. Available at: https://developer.android.com/guide/components/intents-filters (Accessed: 23 October 2026).
Google (n.d.) The activity lifecycle. Available at: https://developer.android.com/guide/components/activities/activity-lifecycle (Accessed: 23 October 2026).
