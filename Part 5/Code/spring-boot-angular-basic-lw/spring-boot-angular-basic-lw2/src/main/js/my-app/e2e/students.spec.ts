import { test, expect } from '@playwright/test';

test.describe('TBZ Students GUI (end-to-end)', () => {

  test('home page shows title, logo and navigation', async ({ page }) => {
    await page.goto('/');

    await expect(page).toHaveTitle('TBZ Students');
    await expect(page.getByAltText('Responsive image')).toBeVisible();
    await expect(page.getByRole('link', { name: 'List Students' })).toBeVisible();
    await expect(page.getByRole('link', { name: 'Add Students' })).toBeVisible();
  });

  test('list page shows the students loaded from the backend', async ({ page }) => {
    await page.goto('/');
    await page.getByRole('link', { name: 'List Students' }).click();

    await expect(page).toHaveURL(/\/students$/);
    // These 5 students are created by the CommandLineRunner in StudentApplication
    await expect(page.getByRole('row', { name: /jonas@tbz\.ch/ })).toBeVisible();
    await expect(page.getByRole('row', { name: /patrick@tbz\.ch/ })).toBeVisible();
    await expect(page.getByRole('row', { name: /ann@tbz\.ch/ })).toBeVisible();
  });

  test('a new student can be added with the form and appears in the list', async ({ page }) => {
    // unique values so the test can be run many times against the same backend
    const unique = Date.now();
    const name = `E2E Student ${unique}`;
    const email = `e2e${unique}@tbz.ch`;

    await page.goto('/');
    await page.getByRole('link', { name: 'Add Students' }).click();
    await expect(page).toHaveURL(/\/addstudents$/);

    // form validation: Submit is disabled as long as the form is empty
    const submit = page.getByRole('button', { name: 'Submit' });
    await expect(submit).toBeDisabled();

    await page.getByLabel('Name').fill(name);
    await page.getByLabel('Email').fill(email);
    await expect(submit).toBeEnabled();
    await submit.click();

    // after saving, the app navigates to the list and the new student is shown
    await expect(page).toHaveURL(/\/students$/);
    await expect(page.getByRole('row', { name: new RegExp(email) })).toBeVisible();
  });
});
