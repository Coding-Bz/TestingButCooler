# Black-Box Test Cases: Car Rental

**Tested website:** <(https://www.check24.de/)>
**Test type:** Functional black-box test (inputs and outputs only, no knowledge of the code)

| ID | Description | Expected Result | Actual Result | Status | Possible Cause |
|----|-------------|-----------------|---------------|--------|----------------|
| 1 | Try to rent something in the past. | A notification that I am not able to rent things for the past. |<img width="1270" height="434" alt="image" src="https://github.com/user-attachments/assets/31d0fde1-e710-4bc4-9b25-de89ced265b6" />
 | Determined | - |
| 2 | Try to rent a car for one day, but set the time we take the car to later than when we give it back. | A notification that the renting should start before the end of the rent. | | Not determined | |
| 3 | Try to add as many add-ons as possible and make sure the price is calculated correctly. | The price is calculated correctly. | | Not determined | |
| 4 | Try to rent a car and check if I receive the mail with the correct data. | The booking is shown on the website and I also receive the mail with correct information. | | Not determined | |
| 5 | Try to cancel my booking and check if I am able to cancel it as it is mentioned. | I can cancel it via the website, the cancellation is shown on the site and I receive a mail that it is cancelled. | | Not determined | |
