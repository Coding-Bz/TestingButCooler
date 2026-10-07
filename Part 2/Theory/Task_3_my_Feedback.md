# Übung 3 – Teststrategie Bank-Software

**How I worked:**
1. I tried out the whole program as a user, without looking at the code → black-box test cases.
2. I thought about which test cases come to my mind immediately, then looked at the code and added more → white-box test cases.
3. I looked more deeply at the code and noted what could be done in a better way → improvements.

---

## 1. Black-Box test cases (as a user)

The user can only type letters and numbers. So the most important thing to test is what the user is able to type in, what the user should not be able to type in, and that the program answers in the right way.

### 1.1 Main menu

| Nr. | Input | Expected result |
|-----|-------|-----------------|
| BB-01 | Start the program | Welcome message and menu are shown |
| BB-02 | `a` | All accounts are shown (number, name, currency) |
| BB-03 | `2` (account number from 1–5) | The account opens: account number, last name, balance |
| BB-04 | `e` | An account is created |
| BB-05 | `w` | The exchange rate can be asked |
| BB-06 | `q` | The program ends |

### 1.2 Invalid input

| Nr. | Input | Expected result |
|-----|-------|-----------------|
| BB-07 | `9` (number outside 1–5) | Error message |
| BB-08 | `x` (letter that is not in the menu) | Error message |
| BB-09 | `?` (special character) | Error message |
| BB-10 | `ae` (two letters) | Error message, because it is not a valid action. Currently the program only uses one letter of the input and starts withdrawing |
| BB-11 | `ake` as an amount | Error message, the program asks again |

### 1.3 Account menu: deposit, withdraw, balance

| Nr. | Input | Expected result |
|-----|-------|-----------------|
| BB-12 | `e` + amount | The money is added correctly to the balance |
| BB-13 | `a` + amount | The money is taken correctly from the balance |
| BB-14 | `a` + more than the balance | Not possible, the user cannot take more than the account has |
| BB-15 | `k` | The balance is shown |

### 1.4 Account menu: transfer, delete, switch, quit

| Nr. | Input | Expected result |
|-----|-------|-----------------|
| BB-16 | `ü` + other account + amount | This account has less money, the other account has more |
| BB-17 | `l` | The account is deleted |
| BB-18 | `w` | Switch to another account |
| BB-19 | `q` | The program ends |

### 1.5 Going back

| Nr. | Input | Expected result |
|-----|-------|-----------------|
| BB-20 | Wanting to go back, cancel, or see the options again (for example after typing `ae`) | There is a way to do this |

---

## 2. White-Box test cases (methods in the code)

### 2.1 Class `Account`

| Method | Test idea | Expected result |
|--------|-----------|-----------------|
| `deposit` | Deposit an amount | The balance is calculated correctly |
| `withdraw` | Withdraw an amount | The balance is calculated correctly |
| `withdraw` | Withdraw more than the balance | Not possible, the user cannot take more than it has |

### 2.2 Class `Bank`

| Method | Test idea | Expected result |
|--------|-----------|-----------------|
| `deleteAccount` | Delete an account, then go through the whole list of accounts | The account is not in the list anymore |
| `createAccount` | Create an account, then go through the list of accounts | The account is in the list |
| `printAccountDetails` | The accounts are stored in an `ArrayList` of objects, so the output can be checked | It contains all the needed information |
| `printAccountsList` | Check the list | All accounts are in it |
| `printOtherAccounts` | Check the list | All accounts except the current one are in it |
| `printBalance` | Not tested: it only uses `System.out.print`, so there is nothing to compare | – |

I would not test whether the account information itself (for example the name) is correct, because I do not have an absolute source of truth to compare it with.

### 2.3 Class `Counter`

| Method | Test idea | Expected result |
|--------|-----------|-----------------|
| `chooseAccount` | Parameterized test: this input should produce this output | Every valid input gives the right action |
| `chooseAccount` | Test with regex: not every input is possible | Invalid input gives an error message |
| `chooseAccount` | Several letters typed together (for example `ae`) | Error message, not only one letter of the input is used |
| `editAccount` | Same tests as `chooseAccount` (regex) | Same as above |
| `transfer` | Same tests as `chooseAccount` (regex) | Same as above |
| `transferAmount` | Transfer a value | This account has less money, the other account has more |

### 2.4 Class `ExchangeRateOkhttp`

| Method | Test idea | Expected result |
|--------|-----------|-----------------|
| `getExchangeRate` | The rate comes from an API. Use two APIs and compare them | If both are the same, the rate is shown. If not (for example because one API is failing), the message is "we can't give you that data" |

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
