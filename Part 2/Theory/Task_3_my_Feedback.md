# Übung 3 – Teststrategie Bank-Software

At start the program says: *"Es gibt 5 Konten mit den Nummern 1–5."*

Accounts: Nr. 1 Rockefeller (USD, 1500), Nr. 2 Gates (EUR, 2000), Nr. 3 Musk (CHF, 23500), Nr. 4 Bezos (EUR, 100.50), Nr. 5 Branson (USD, 1500000).

Approach: first I tried out the software as a user (without looking at the code) and noted the black-box test cases. Then I thought about white-box test cases, looked into the code (`Account`, `Bank`, `Counter`, `ExchangeRateOkhttp`, `Main`) and added more. At the end I noted what could be done better in the code.

Legend for the column "Status":
- **OK (tested)** / **Bug (tested)**: I tried it myself in the running program.
- **Code**: expected behaviour read from the code, still to be verified by running it.

---

## 1. Black-Box test cases (as a user)

### 1.1 Main menu (Schalter 1)

| ID | Input | Expected result | Observed / result | Status |
|----|-------|-----------------|-------------------|--------|
| BB-01 | Program start | Welcome message and menu are shown | "Willkommen am Schalter 1!" + menu shown | OK (tested) |
| BB-02 | `a` | All accounts are listed with number, name and currency | Nr. 1–5 listed (Rockefeller USD, Gates EUR, Musk CHF, Bezos EUR, Branson USD) | OK (tested) |
| BB-03 | `2` (existing account) | Account 2 opens, shows number, last name, balance | Nr. 2, Gates, 2000.00 EUR | OK (tested) |
| BB-04 | `9`, `0`, `-1` (number of a non-existing account) | "Ein Konto mit dieser Nummer ist nicht vorhanden!", menu again | – | Code |
| BB-05 | `x` or `?` (not in menu) | Error message, menu again | Error text lists "a", "e", **"u"** and "q" – the "u" is wrong, it should be "w" | Bug (message, Code) |
| BB-06 | `e` + name + `CHF` / `EUR` / `USD` | New account is created with balance 0.00 and its details are shown | – | Code |
| BB-07 | `e` + name + unknown 3-letter currency (e.g. `ABC`) | Message that the currency is unknown, USD is used | – | Code |
| BB-08 | `e` + name + invalid currency (e.g. `US`, `12`) | "! Ungültige Eingabe !", asks again | – | Code |
| BB-09 | `w` + `CHF USD` | Rate is shown ("1 CHF = x USD"), needs internet | – | Code |
| BB-10 | `w` + `XYZ USD` or `CHF` | "! Ungültige Eingabe oder unbekannte Währung !", asks again | – | Code |
| BB-11 | `q` | "Auf Wiedersehen!", program ends | – | Code |
| BB-12 | `ae`, `xa`, `3abc` (several characters) | Error message | `ae` → only the first letter counts (list of accounts is shown); `xa` / `3abc` → no error message at all, the menu is just shown again | Bug (Code) |

### 1.2 Account menu (after choosing an account)

| ID | Input | Expected result | Observed / result | Status |
|----|-------|-----------------|-------------------|--------|
| BB-20 | `9` | Error message | "Ungültige Eingabe: Bitte eine der Aktionen auswählen!" | OK (tested) |
| BB-21 | `x` | Error message | Same error message | OK (tested) |
| BB-22 | `?` | Error message | Same error message | OK (tested) |
| BB-23 | `ae` (two letters) | Error message (not a valid single action) | Accepted: the withdraw flow started ("Welchen Betrag möchten Sie abheben (EUR)?"). Only the **first** letter is evaluated (`a` = abheben). The same applies to every longer input like `abc`. | Bug (tested) |
| BB-24 | `a`, then `ake` as amount | Error message, asks again | "Ungültige Eingabe, bitte nochmals!" | OK (tested) |
| BB-25 | Empty input (only Enter) | Error message | Program crashes with an exception | Bug (Code) |
| BB-26 | `e` + valid amount (e.g. `100`) | Balance increases by the amount | – | Code |
| BB-27 | `a` + valid amount smaller than balance | Balance decreases by the amount | – | Code |
| BB-28 | `a` + amount larger than balance | "Kontostand zu niedrig (momentan …)", asks again, balance unchanged | – | Code |
| BB-29 | `a` + exactly the balance | Balance is 0.00 | – | Code |
| BB-30 | `e` or `a` + negative amount (`-50`) | Error message | Accepted: einzahlen of `-50` lowers the balance, abheben of `-50` **increases** it | Bug (Code) |
| BB-31 | Amount with comma (`10,50`) | Accepted or clear hint to use a dot | "Ungültige Eingabe" (only `10.50` works) | Weakness (Code) |
| BB-32 | `k` | Balance is shown | – | Code |
| BB-33 | `ü` + other account + amount (same currency, e.g. 4 → 2) | Source account has less, target account has more money | – | Code |
| BB-34 | `ü` between different currencies with a defined rate (USD→CHF, USD→EUR, CHF→USD) | Amount is converted (×1.11, ×0.91, ×0.9) | – | Code |
| BB-35 | `ü` between currencies without a defined rate (e.g. EUR→USD, CHF→EUR) | Amount is converted | Message "Es wurde keine Umrechnung vorgenommen." and the amount is transferred 1:1 | Bug (Code) |
| BB-36 | `ü` + the same account / non-existing account | Error message | Error message, back to the action menu | Code |
| BB-37 | `ü` + letter as target account | Error message, asks again | "Ungültige Eingabe ! Bitte eine Zahl eingeben!" | Code |
| BB-38 | `ü` + amount larger than balance | Error message | Message, asks for the amount again (no way to cancel) | Code |
| BB-39 | `l`, then `j` | Account is deleted, back to main menu, account is not in list `a` anymore | – | Code |
| BB-40 | `l`, then `n` | "Aktion abgebrochen.", account still exists | – | Code |
| BB-41 | `l`, then empty input | Treated as "no" | Program crashes with an exception | Bug (Code) |
| BB-42 | `w` | Back to account selection (main menu) | – | Code |
| BB-43 | `q` | "Auf Wiedersehen!", program ends | – | Code |
| BB-44 | Wanting to cancel at an amount prompt (after BB-23 for example) | A way to cancel or show the options again | No way to cancel, the program keeps asking for an amount | Weakness (tested) |

### 1.3 Usability findings (as a user)

| ID | Finding |
|----|---------|
| UX-01 | The program is only in German. Someone who does not speak German cannot use it. A language selection would be better. |
| UX-02 | The prompt "Was möchten Sie tun? Tippen Sie ..." is misleading: first it says to type an account number, then the letters a, e, w, q are listed. As a user I am not sure whether to type a number or a letter. |
| UX-03 | After `a` the whole main menu is shown again (identical text). It would be enough to show "Was möchten Sie tun?" and the options. |
| UX-04 | The letters change meaning between menus: in the main menu `a` = alle Konten, `e` = erstellen, `w` = Wechselkurs; in the account menu `e` = einzahlen, `a` = abheben, `w` = Konto wechseln, and even the order is reversed. The letters should stay the same or be clearly different. |
| UX-05 | The account menu is only shown once. After an action there is no way to show the options again; the user only sees "Gewünschte Aktion:". `w` brings me back to the main menu, but this is not obvious from the wording ("Konto wechseln"). |
| UX-06 | The error message in the main menu names the letter "u", which does not exist (see BB-05). |
| UX-07 | Amounts have to be entered with a dot, a comma is rejected (BB-31). |

---

## 2. White-Box test cases (methods in the code)

Note: `Counter` reads from a `Scanner(System.in)` that is created in the constructor. So its methods can be tested by calling `System.setIn(...)` with the input before creating the `Counter`, and the output can be checked by redirecting `System.out`.

### 2.1 Class `Account`

| ID | Method | Test idea | Expected |
|----|--------|-----------|----------|
| WB-01 | `deposit` | Deposit a positive amount | New balance = old balance + amount |
| WB-02 | `deposit` | Deposit `0` / negative amount | Should be refused; currently the balance is changed (bug) |
| WB-03 | `withdraw` | Withdraw an amount smaller than the balance | Returns `true`, new balance = old balance − amount |
| WB-04 | `withdraw` | Withdraw more than the balance | Returns `false`, balance unchanged |
| WB-05 | `withdraw` | Boundary values: exactly the balance, `0`, negative amount | Exactly the balance → `true`, balance 0. Negative amount should return `false`; currently it returns `true` and increases the balance (bug) |
| WB-06 | constructor / getters | Create several accounts | IDs count up (1, 2, 3, …), getters return the given name, currency and start balance |

### 2.2 Class `Bank`

| ID | Method | Test idea | Expected |
|----|--------|-----------|----------|
| WB-07 | `createAccount` | Create an account, then go through the list of accounts | The account is in the list, `getNumberOfAccounts()` is +1 |
| WB-08 | `deleteAccount` | Delete an account, then go through the whole list of accounts | The account is not in the list anymore, `getNumberOfAccounts()` is −1 |
| WB-09 | `deleteAccount` | Delete an account that is not in the list | Nothing is removed; currently the message "wurde gelöscht" is still printed (bug) |
| WB-10 | `getAccount` | Existing number / non-existing number / number of a deleted account | Account / `null` / `null` |
| WB-11 | `printAccountDetails` | Capture the output for an existing account | Contains number, last name, balance with 2 decimals and currency |
| WB-12 | `printAccountDetails` | Account that was deleted | "Das Konto … existiert nicht mehr!" |
| WB-13 | `printAccountsList` / `printOtherAccounts` | Capture the output | All accounts / all accounts except the given one |
| WB-14 | `printBalance` (`Bank` and `Account`) | Only prints to the console, no return value | Low value; only possible by redirecting `System.out` (not tested directly) |

Note: I would not test whether the account data itself (e.g. the name) is "correct", because there is no absolute source of truth to compare it with.

### 2.3 Class `Counter`

| ID | Method | Test idea | Expected |
|----|--------|-----------|----------|
| WB-15 | `chooseAccount` | Parameterized test with regex: input → output. `1`–`5` return the number, `q` returns `0`, `a` / `e` / `w` run their action and ask again | Correct action per input |
| WB-16 | `chooseAccount` | Invalid inputs: `x`, `?`, empty, `9`, `0`, `-1`, a very long number, `ae`, `xa`, `3abc` | Error message for every one (currently `xa` / `3abc` give no message, bug BB-12) |
| WB-17 | `editAccount` | Parameterized test for `e`, `a`, `k`, `ü`, `l`, `w`, `q` | Correct action; return value `false` for `q`, `true` for `l` and `w` |
| WB-18 | `editAccount` | Invalid inputs: `9`, `x`, `?`, `ae`, empty | Error message for every one (currently `ae` is accepted and empty input throws an exception) |
| WB-19 | `transfer` | Valid target, same account, non-existing account, letters | Correct transfer / error messages |
| WB-20 | `transferAmount` | Value transferred between two accounts | Source account has less money, target account has more |
| WB-21 | `transferAmount` | Amount larger than balance, text, negative amount | Error message, asks again, balances unchanged (negative amount is currently accepted, bug) |
| WB-22 | `convertCurrency` (private, via `transferAmount`) | All 9 currency pairs | USD→CHF ×1.11, USD→EUR ×0.91, CHF→USD ×0.9; all other pairs (also EUR→USD, CHF→EUR) are not converted |
| WB-23 | `getConfirmation` (private, via `editAccount` + `l`) | `j`, `J`, `n`, other letter, empty | `j`/`J` → deleted; others → "Aktion abgebrochen" (empty currently throws an exception) |
| WB-24 | `deposit` / `withdraw` (private, via `editAccount`) | Valid amount, text, amount larger than balance | Balance correct, error messages for invalid input |
| WB-25 | `createAccount` | `CHF`, lower case `chf`, unknown 3-letter currency, invalid text, empty name | Account with balance 0.00; unknown currency → USD; invalid text → asks again |
| WB-26 | `getExchangeRate` (private, via `chooseAccount` + `w`) | `CHF USD`, `CHF,USD`, `CHF>USD`, `CHF\|USD`, `chf usd` as valid; `CHF-USD`, `XYZ USD`, `CHF` as invalid | Valid → API is called; invalid → error message and asks again |

### 2.4 Class `ExchangeRateOkhttp`

| ID | Method | Test idea | Expected |
|----|--------|-----------|----------|
| WB-27 | `getExchangeRate` | Valid currency pair with internet connection | Rate > 0 |
| WB-28 | `getExchangeRate` | No internet / wrong API key / invalid answer | Error message and return value `0.0`; nothing crashes |

Because this method calls an external API, a unit test should replace the web service with a mock server (OkHttp MockWebServer) so the test does not depend on the internet. See also improvement 12 (comparing two APIs).

---

## 3. What I would improve in the code / best practices

**What is already good**

| Topic | Comment |
|-------|---------|
| Readability | Extremely good, I understood everything at first sight. At my company we prefer a function that is 50 lines long and understandable for everybody over something short and fast that nobody understands. |
| Naming | Pretty good, self-explaining. |
| Comments / Javadoc | Comments exist (nice); the code is mostly self-explaining. |

**What I would change**

| Nr. | Improvement | Reason |
|-----|-------------|--------|
| 1 | Delete unused code instead of leaving it as a comment: the commented lines and the TODO in `Bank.deleteAccount` / `Bank.getAccount`, and the unused `Account.pseudoDeleteAccount()` | If code is not used it is not needed; it does not need to stay in the file (minor point). |
| 2 | Validate the whole input: `chooseAccount` uses `find()` and `substring(0, 1)`, `editAccount` cuts the input to the first character. Use `matches()` on the complete input and check for empty input | `ae` is accepted as `a` (BB-23), `xa` / `3abc` give no message (BB-12), empty input throws a `StringIndexOutOfBoundsException` (BB-25, BB-41). |
| 3 | Validate amounts (> 0, no `NaN` / `Infinity`) in `Account.deposit` and `Account.withdraw` | A negative amount currently reverses deposit and withdraw (BB-30). |
| 4 | Add a way to cancel / go back and show the options again | No way out at the amount prompts (BB-44, UX-05). |
| 5 | Clearer menu text: "Was möchten Sie tun?" and then the options, not "Tippen Sie ..." followed by a different list; fix the letter "u" in the error message | UX-02, UX-06. |
| 6 | Do not repeat the whole main menu after `a`, `e` or `w`: after the `switch` the code continues with `Integer.parseInt(str)`, and the resulting exception is silently swallowed. Add `continue` / `return` after each case | UX-03. |
| 7 | Use consistent letters for the same actions in all menus | UX-04. |
| 8 | Support multiple languages and accept a comma as decimal separator | UX-01, UX-07. |
| 9 | Complete the currency conversion: `convertCurrency` only knows 3 hard-coded pairs, all others are transferred 1:1. Use the exchange-rate service or a complete rate table | BB-35. |
| 10 | Use `BigDecimal` instead of `double` for money | Rounding errors with `double`. |
| 11 | Catch specific exceptions instead of `catch (Exception e)` + `instanceof`; remove the `InputMismatchException` branch (`nextLine()` never throws it); rename `AccountExeption` → `AccountException` and move it to its own file | Cleaner error handling, dead code. |
| 12 | `ExchangeRateOkhttp`: do not store the API key in the source code (use an environment variable or config file); close the `Response` (try-with-resources); check `isSuccessful()` and `body() != null`; do not use `0.0` as error value; set timeouts; compare two APIs and only show a rate if they agree, otherwise "we can't give you that data" | Security, resource leak, an API failure should not look like a rate of 0. |
| 13 | Separate logic from console input/output (inject the `Scanner` / `PrintStream`, return values instead of only printing) | Makes methods like `printBalance` and the `Counter` methods much easier to test. |
| 14 | `Bank.deleteAccount`: only print "gelöscht" if the account was really removed; `getAccount` with a stream or `Optional` (the TODO in the code) | WB-09. |
| 15 | `Account.counter` should be `private`; `Main` should not use `System.exit(0)` and `throws IOException` is not needed; move the `Currency` enum into its own file; move the exercise description at the top of `Account.java` into a README | Cleaner structure. |
| 16 | `Account.printBalance` ("Aktueller Kontostand") and `Bank.printBalance` ("Neuer Kontostand") do nearly the same: keep only one | Duplicated code. |
