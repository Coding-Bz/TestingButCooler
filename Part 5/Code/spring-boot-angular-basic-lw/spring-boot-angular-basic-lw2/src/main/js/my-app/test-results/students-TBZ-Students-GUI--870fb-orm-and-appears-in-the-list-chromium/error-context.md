# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: students.spec.ts >> TBZ Students GUI (end-to-end) >> a new student can be added with the form and appears in the list
- Location: e2e\students.spec.ts:25:7

# Error details

```
Error: expect(locator).toBeVisible() failed

Locator: getByRole('row', { name: /e2e1792962002011@tbz.ch/ })
Expected: visible
Timeout: 5000ms
Error: element(s) not found

Call log:
  - Expect "toBeVisible" getByRole('row', { name: /e2e1792962002011@tbz.ch/ }) with timeout 5000ms
  - waiting for getByRole('row', { name: /e2e1792962002011@tbz.ch/ })

```

```yaml
- img "Responsive image"
- list:
  - listitem:
    - link "List Students":
      - /url: /students
  - listitem:
    - link "Add Students":
      - /url: /addstudents
- table:
  - rowgroup:
    - row "# Name Email":
      - columnheader "#"
      - columnheader "Name"
      - columnheader "Email"
  - rowgroup
```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | 
  3  | test.describe('TBZ Students GUI (end-to-end)', () => {
  4  | 
  5  |   test('home page shows title, logo and navigation', async ({ page }) => {
  6  |     await page.goto('/');
  7  | 
  8  |     await expect(page).toHaveTitle('TBZ Students');
  9  |     await expect(page.getByAltText('Responsive image')).toBeVisible();
  10 |     await expect(page.getByRole('link', { name: 'List Students' })).toBeVisible();
  11 |     await expect(page.getByRole('link', { name: 'Add Students' })).toBeVisible();
  12 |   });
  13 | 
  14 |   test('list page shows the students loaded from the backend', async ({ page }) => {
  15 |     await page.goto('/');
  16 |     await page.getByRole('link', { name: 'List Students' }).click();
  17 | 
  18 |     await expect(page).toHaveURL(/\/students$/);
  19 |     // These 5 students are created by the CommandLineRunner in StudentApplication
  20 |     await expect(page.getByRole('row', { name: /jonas@tbz\.ch/ })).toBeVisible();
  21 |     await expect(page.getByRole('row', { name: /patrick@tbz\.ch/ })).toBeVisible();
  22 |     await expect(page.getByRole('row', { name: /ann@tbz\.ch/ })).toBeVisible();
  23 |   });
  24 | 
  25 |   test('a new student can be added with the form and appears in the list', async ({ page }) => {
  26 |     // unique values so the test can be run many times against the same backend
  27 |     const unique = Date.now();
  28 |     const name = `E2E Student ${unique}`;
  29 |     const email = `e2e${unique}@tbz.ch`;
  30 | 
  31 |     await page.goto('/');
  32 |     await page.getByRole('link', { name: 'Add Students' }).click();
  33 |     await expect(page).toHaveURL(/\/addstudents$/);
  34 | 
  35 |     // form validation: Submit is disabled as long as the form is empty
  36 |     const submit = page.getByRole('button', { name: 'Submit' });
  37 |     await expect(submit).toBeDisabled();
  38 | 
  39 |     await page.getByLabel('Name').fill(name);
  40 |     await page.getByLabel('Email').fill(email);
  41 |     await expect(submit).toBeEnabled();
  42 |     await submit.click();
  43 | 
  44 |     // after saving, the app navigates to the list and the new student is shown
  45 |     await expect(page).toHaveURL(/\/students$/);
> 46 |     await expect(page.getByRole('row', { name: new RegExp(email) })).toBeVisible();
     |                                                                      ^ Error: expect(locator).toBeVisible() failed
  47 |   });
  48 | });
  49 | 
```