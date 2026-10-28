# Acceptance and screenshot register — NOT executed
Record your actual device/API, build version, date, result and screenshot after each check.
Expected results below are specifications, not observed results.

| ID | Action | Expected result / screenshot |
|---|---|---|
| A01 | Fresh install; open Pantry | Empty pantry message; no crash |
| A02 | Save blank name, then zero quantity | Visible field error; no row inserted |
| A03 | Enter 2026-02-30 | Invalid date rejected |
| A04 | Add eggs 2 pcs | Pantry lists canonical egg, 2 pcs |
| A05 | Open Suggestions | No recipes: even Simple Omelette needs oil |
| A06 | Add oil 5 ml | Simple Omelette now appears |
| A07 | Add tomato 1 pcs | Tomato Scrambled Eggs appears |
| A08 | Open recipe | All three ingredients and steps visible |
| A09 | Edit eggs from 2 to 1 | Both two-egg recipes disappear |
| A10 | Edit eggs back to 2 | Both return |
| A11 | Long-press tomato; cancel | Tomato remains |
| A12 | Long-press tomato; confirm delete | Tomato absent; Tomato Scrambled Eggs absent |
| A13 | Close and reopen / force stop | Egg and oil rows persist |
| A14 | Add milk 0.25 l and oats 50 g | Milk Oats appears |
| A15 | Change milk to 0.249999 l | Milk Oats disappears |
| A16 | Change milk to 250 g | Milk Oats stays absent (wrong dimension) |
| A17 | Two milk rows 100 ml and 150 ml | Milk Oats appears when oats available |
| A18 | Set milk expiry to yesterday | Excluded with default setting; expired label |
| A19 | Disable expired-entry exclusion | Expired stock participates; warning in Suggestions |
| A20 | Disable alerts; restart | Preference persists; labels hidden |
| A21 | Rotate partially completed add/edit form | Typed values and selected unit restored |
| A22 | Navigate all five screens/back | No crash; settings and list refresh on resume |
| A23 | Run testDebugUnitTest | Save actual test result; no result assumed here |

Capture actual screenshots of: empty pantry, pantry list, add form, saved record, edit form,
updated quantity, delete confirmation, post-delete list, validation error, zero matches,
strict matches, recipe detail, settings, unit-conversion proof and reopened persistent data.
Include captions in the report with the exact input and observed outcome. Do not claim tests
passed before running them. Test on small-screen portrait and landscape if possible.
