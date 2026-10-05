# Grundlagen Testing: Aufgabe 1 und 2

## Aufgabe 1

> Welche Formen von Tests kennen Sie aus der Informatik? Erläutern Sie mind. drei Beispiele, die Sie aus der Praxis kennen. Wie werden die Tests durchgeführt?

There are many different kinds of testing.

### Unit testing

First of all there's **unit testing**, where you test specific units, like a single method or class. It's a pretty basic kind of **white-box testing**: I know the code and what the result should be, so the expected values are pretty much hardcoded in the test. The developer writes these tests, and they run automatically in the build.

### Black-box testing (usability testing)

It's not only software based, though. There's also **black-box testing**, where you don't know the code and only look at the behaviour.

In the UX/UI field, for example, people often have UX labs where they let people use the device or software they want to test and ask them specific questions, to make sure the whole thing works as intended and that people understand it as they should. That's **usability testing**, and it's done manually by watching real users.

### Fuzz testing

Then there's **fuzz testing**, where you throw random or unexpected input at a program to see if it crashes or behaves in a way it shouldn't. It's automated and often used to find security problems.

### Performance testing

And there's **performance testing**, for example how fast the system responds when I click a button. I thought Selenium was one of those tools, but it's actually for automating UI tests in the browser, so that's not performance. I don't know the other tools.

### Side note

I know there's a lot of frontend testing too, but I mostly know the backend side, because I've only ever worked on backend and always refused to do frontend.

---

## Aufgabe 2

> Nennen Sie ein Beispiel eines SW-Fehlers und eines SW-Mangels. Nennen Sie ein Beispiel für einen hohen Schaden bei einem SW-Fehler.

### Software bug (Fehler)

A software bug (**Fehler**) is when something should be one way but it's the opposite, or when a calculation is just wrong. The actual behaviour is different from what it should be.

### Software deficiency (Mangel)

A software deficiency (**Mangel**) is when it works, but not adequately. For example, the backend is correct, but the UI is so terrible that the user doesn't even know how to use it.

### Example from my own experience

I've worked with something where I had many bugs and some deficiencies that I had to fix. If you tell an AI to back up what it says with data and to send you the link it used, the link often looked like a link but wasn't. If you clicked on it, you realised it wasn't an actual link. That's a bug, because a working link was the requirement and it wasn't delivered.

### High damage

For high damage, a good example is **Knight Capital in 2012**. A faulty software deployment sent out wrong trades, and the company lost around 440 million dollars in about 45 minutes.
