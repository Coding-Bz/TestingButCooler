# JUnit 5 – Zusammenfassung

Referenz: [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

| Feature | Zweck | Beispiel |
|---|---|---|
| `@Test` | Markiert eine Testmethode | `@Test void add() { assertEquals(5, calc.add(2,3)); }` |
| `@BeforeEach` / `@AfterEach` | Läuft vor/nach **jedem** Test (Setup/Cleanup) | `@BeforeEach void setUp() { calc = new Calculator(); }` |
| `@BeforeAll` / `@AfterAll` | Einmal pro Klasse (Methode `static`) | teure Ressourcen, z.B. DB-Verbindung |
| `@DisplayName` | Lesbarer Testname im Report | `@DisplayName("Division durch 0 wirft Exception")` |
| `@Disabled` | Test temporär ausschalten | `@Disabled("Bug #12")` |
| `@Nested` | Tests gruppieren (innere Klassen) | `@Nested class WennKontoLeerIst { ... }` |
| `@Tag` | Tests kategorisieren/filtern | `@Tag("slow")` → `mvn test -Dgroups=slow` |
| `@Timeout` | Test schlägt bei zu langer Dauer fehl | `@Timeout(2)` |
| `@RepeatedTest` | Test mehrfach ausführen | `@RepeatedTest(5)` |
| `@ParameterizedTest` | Gleicher Test mit mehreren Eingaben (braucht `junit-jupiter-params`) | `@ParameterizedTest @CsvSource({"1,2,3","2,3,5"}) void add(int a,int b,int r){...}` |
| `@ValueSource`, `@CsvSource`, `@MethodSource`, `@EnumSource` | Datenquellen für parametrisierte Tests | `@ValueSource(ints={1,2,3})` |

## Assertions (`org.junit.jupiter.api.Assertions`)

- `assertEquals(expected, actual)` / `assertEquals(e, a, delta)` – Gleichheit (Delta bei `double`)
- `assertNotEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertNotNull`
- `assertSame` / `assertNotSame` – Objektidentität (`==`)
- `assertArrayEquals`, `assertIterableEquals`
- `assertThrows(Exception.class, () -> calc.divide(1,0))` – erwartet Exception
- `assertAll(...)` – mehrere Assertions, alle werden ausgewertet
- `assertTimeout(Duration, executable)`
- `fail("msg")` – Test explizit fehlschlagen lassen (Platzhalter für «toDo»)

## Weiteres

- **Testname/Struktur:** Arrange – Act – Assert (Given/When/Then).
- **Ausführen:** IDE (grüner Pfeil) oder `mvn test` (Surefire; Reports in `target/surefire-reports`).
- **Code Coverage:** JaCoCo (`mvn test` → `target/site/jacoco/index.html`).
- **Lifecycle:** Pro Test wird standardmässig eine **neue** Instanz der Testklasse erzeugt → Tests sind voneinander unabhängig.
- **Mocking** (nicht Teil von JUnit): Mockito, um Abhängigkeiten zu ersetzen.
