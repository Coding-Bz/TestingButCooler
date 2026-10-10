# Banken-Simulation – Funktionsweise

## Klassen & Zusammenhänge
- **Bank** – verwaltet Konten in `TreeMap<String, Account>` (Schlüssel = Kontonummer). Zähler `nextAccountId` startet bei 1000.
  - `createSavingsAccount()` → `S-<nr>`, `createPromoYouthSavingsAccount()` → `Y-<nr>`, `createSalaryAccount(limit)` → `P-<nr>` (gibt `null` zurück, wenn Limite > 0).
  - `deposit/withdraw(id, date, amount)`: Konto suchen → an Konto delegieren; unbekanntes Konto → `false`.
  - `getBalance(id)`: Saldo oder 0 bei unbekanntem Konto. `getBalance()`: **negative Summe** aller Konti (Bank schuldet den Kunden das Geld).
  - `print(id)`, `print(id, year, month)`, `printTop5()`, `printBottom5()` (sortiert mit Comparatoren).
- **Account** (abstrakt) – Kontonummer, `balance` (in **Millirappen**), Liste von `Booking`s.
  - `deposit`/`withdraw`: `false` bei negativem Betrag oder wenn `date` älter als letzte Buchung (`canTransact`). Abhebung wird als negative Buchung gespeichert.
  - `print()` Kontoauszug komplett; `print(year, month)` nur Buchungen des Monats (Saldo inkl. Vormonate).
- **SavingsAccount** extends Account – `withdraw` nur wenn Saldo ≥ Betrag (kein Überziehen).
- **PromoYouthSavingsAccount** extends SavingsAccount – `deposit` schreibt Betrag + 1 % Bonus (`amount/100`, ganzzahlig) gut.
- **SalaryAccount** extends Account – `withdraw` erlaubt, solange Saldo nach Abhebung ≥ `creditLimit` (negativ).
- **Booking** – Datum (Banktage) + Betrag; `print(balance)` gibt eine Zeile aus.
- **BankUtils** – Formatierung: `formatBankDate` (Jahr = 1970 + date/360, Monat = 30 Tage), `formatAmount` (mRp / 100'000 = Franken, 2 Nachkommastellen).
- **AccountBalanceComparator** (absteigend) / **AccountInverseBalanceComparator** (aufsteigend) – für Top5/Bottom5.
- **Main**, **TestBank** – Demo-Programme.

## Beziehungen (Klassendiagramm in Stichworten)
- Bank 1 —— * Account (Komposition via Map)
- Account 1 —— * Booking
- SavingsAccount, SalaryAccount ▷ Account; PromoYouthSavingsAccount ▷ SavingsAccount
- Bank ··> Comparatoren, Account ··> BankUtils (via Booking)

## Rahmenbedingungen
- Zeit: 1 Banktag, 1 Jahr = 360 Tage, 1 Monat = 30 Tage; ab 1.1.1970.
- Beträge: 1 Franken = 100'000 Millirappen.

## Auffälligkeiten im Code
- `Main` ruft `createSalaryAccount(12000)` mit **positiver** Limite auf → liefert `null`, es wird kein Konto erzeugt.
- `Account.booking` und `Bank.account` (Getter/Setter) sind UML-Generator-Reste und werden nicht verwendet.
- `printBottom5()` hat im Javadoc den gleichen Text wie `printTop5()` (Copy/Paste).
- Die Comparatoren sind `Comparator<Object>` (ungenerisch, mit Casts).
- Dateien enthalten teils ISO-8859-1-Umlaute (Encoding in der IDE beachten).
