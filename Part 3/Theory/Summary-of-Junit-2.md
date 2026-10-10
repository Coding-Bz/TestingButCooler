# JUnit 5 Summary: The Most Common Features

Today we are going to figure out the different annotations and assertions in JUnit. Every feature comes with a short use case and a tiny example.

> **Note:** All examples use JUnit 5 (Jupiter).

---

## 1. Lifecycle Annotations

These annotations control what happens **before** and **after** the tests.

### `@BeforeEach`

Does something before each test. For example, if I see the need to create a new object, so that every test has a clean new object, I can use this.

```java
@BeforeEach
void setUp() { calculator = new Calculator(); }
```

### `@BeforeAll`

Runs only once before all tests. For example, if I need to create a database which only has to be created once and every test case can just use it, without having to create it again. The method has to be `static`.

```java
@BeforeAll
static void createDb() { db = new Database(); }
```

### `@AfterEach`

A cleanup after each test case. This is useful if a test does something which has an influence on the next test.

```java
@AfterEach
void cleanUp() { calculator.reset(); }
```

### `@AfterAll`

Runs once after all tests are done. For example, to close a database connection or stop a server. Also has to be `static`.

```java
@AfterAll
static void closeDb() { db.close(); }
```

> **Side note:** Java has a garbage collector, so there is no need to free memory manually. `@AfterAll` is more about external stuff like connections, files or servers.

### Execution order

```
@BeforeAll
  |- @BeforeEach -> @Test -> @AfterEach   (test 1)
  |- @BeforeEach -> @Test -> @AfterEach   (test 2)
@AfterAll
```

---

## 2. Test Annotations

### `@Test`

Clarifies that a method is a test. Kind of obvious, but without it JUnit just ignores the method.

```java
@Test
void myFirstTest() { }
```

### `@DisplayName`

Gives a test a readable name in the test report.

```java
@DisplayName("Dividing by zero throws an exception")
```

### `@Tag`

Here you can define a string as a label for a test. It is mostly about readability and organization, but you can also use it to filter, for example to run only the fast tests locally.

```java
@Tag("integration")
```

### `@Disabled`

With this you can disable tests, so they are skipped. It is reported as skipped, not as failed. It can be useful for a test that is temporarily broken because of a known bug or a feature which is not finished yet. It is a good idea to add a reason, and these tests should not stay disabled forever.

```java
@Disabled("Waiting for bugfix #123")
```

### `@RepeatedTest`

This one is really interesting: you can repeat a test as many times as you want, for example to check it for flakiness (a test that sometimes passes and sometimes fails).

```java
@RepeatedTest(10)
void shouldAlwaysWork() { }
```

---

## 3. Assertions

Assertions check if the result is what we expect. If an assertion fails, the test fails.

### `assertEquals(expected, actual)`

Compares two values and checks if they are equal. The expected value comes first.

```java
assertEquals(5, calculator.add(2, 3));
```

### `assertTrue(condition)`

We test a function and we want the result to be true.

```java
assertTrue(user.isAdult());
```

### `assertFalse(condition)`

We test a function and we want the result to be false.

```java
assertFalse(list.isEmpty());
```

### `assertNotNull(value)`

Checks that a value is not `null`. There is also `assertNull`.

```java
assertNotNull(repo.findById(1));
```

### `assertThrows(ExceptionType.class, executable)`

This is the one to use if we are checking for an exception, for example when a function should throw an exception in a certain case.

```java
assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
```

---

## 4. Assumptions

### `assumeTrue(condition)`

Similar to `assertTrue`, but with a difference. `assertTrue` really expects the condition to be true, and if it is not, you get an error. `assumeTrue` is just an assumption: if it is wrong, the test is aborted and shown as skipped, not as failed.

| | `assertTrue` | `assumeTrue` |
|---|---|---|
| Condition is true | Test continues | Test continues |
| Condition is false | Test fails | Test is skipped (no failure) |

A typical use case is a test that should only run in a certain environment, for example only on Linux.

```java
assumeTrue(System.getProperty("os.name").contains("Linux"));
```

---

## 5. Quick Overview

| Feature | Purpose |
|---|---|
| `@BeforeEach` | Setup before every test |
| `@BeforeAll` | One-time setup before all tests (static) |
| `@AfterEach` | Cleanup after every test |
| `@AfterAll` | One-time cleanup after all tests (static) |
| `@Test` | Marks a test method |
| `@DisplayName` | Readable test name |
| `@Tag` | Label tests for filtering |
| `@Disabled` | Skip a test |
| `@RepeatedTest` | Repeat a test, e.g. to find flaky tests |
| `assertEquals` | Values are equal |
| `assertTrue` / `assertFalse` | Result is true / false |
| `assertNotNull` | Value is not null |
| `assertThrows` | Expect an exception |
| `assumeTrue` | Skip the test if the assumption is false |

---

## 6. Reference

**Official JUnit 5 User Guide:** <https://junit.org/junit5/docs/current/user-guide/>

I picked this one because it is the official documentation, always up to date, and it covers every annotation and assertion with examples.
