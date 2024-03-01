# Mobile Application Development Coursework 

## Description
Create an application which requests the user to guess an country based on a flag or vice versa.

## Conditions
* **Must** use Kotlin and Jetpack Compose.
* **Must NOT** use third-party libraries.
* **Must** only use libraries found in [Android Developer Reference](https://developer.android.com/reference/).
* **Must NOT** disable `Activity` recreation. (When screen orientation changes etc.)

## Resources
* Country flag images, and country codes can be sourced from [this repository](https://github.com/hjnilsson/country-flags).

## Requirements
- [ ] **[Home](#home-page)** Page.
- [ ] **[Guess the Country](#country-page)** Page.
- [ ] **[Guess-Hints](#hints-page)** Page.
- [ ] **[Guess the Flag](#flag-page)** Page.
- [ ] **[Advanced Level](#advanced-page)** Page.
- [ ] **[Switch](#switch-button)** Button.

## Instructions

### <a name="home-page"></a>Home page
* Guess the Country Button.
* Guess-Hints Button.
* Guess the Flag Button.
* Advanced Level Button.
* Switch Button.

### <a name="country-page"></a>Guess the Country page
1. A **Random** country flag.
2. A list containing **ALL** names of the 256 countries from the resource. User must be able to select a country from the list.
3. A `Submit` button to confirm his choice.
4. When guessed correctly, must display `CORRECT!` in **Green** color.
5. Otherwise, must display `WRONG!` in **Red** and display the correct country name in **Blue**.
6. Finally, `Submit` buttons text must change to '`Next`' and pressing this button must advance the game to the next level where a new random flag is chosen for the user to guess.

### <a name="hints-page"></a>Guess-Hints page
1. A **Random** country flag.
2. **Dashes** representing **Each** character of the country's name.
3. A **Text Box** for the user to enter a **Character**.
4. A **Submit** button
5. User must guess a single character of the country's name and input it using the **Text Box**, this input must be **Case-Insensitive**.
6. When a character is correctly guessed it must replace **ALL** dashes representing **ALL** occurrences of that character.
7. An incorrect guess must not cause change the contents.
8. After **Three** incorrect guesses, the message `WRONG!` must appear in **Red** color. And correct name of the country must appear in **Blue** color.
9. If user guesses all characters correctly the message `CORRECT!` must appear in **Green** color.
10. Finally, `Submit` buttons text must change to '`Next`' and pressing this button must advance the game to the next level where a new random flag is chosen for the user to guess.

### <a name="flag-page"></a>Guess the Flag page
1. **Name** of a country.
2. Three different country flags, where one is the flag of the country displayed.
3. User must aim to click on the flag belonging to the country mentioned.
4. Upon a correct click, the message `CORRECT!` must appear in **Green** color.
5. An incorrect click must be followed up with a `WRONG!` message.
6. Finally user must click on a `Next` button to go the next level.

### <a name="advanced-page"></a>Advanced Level page
1. Must display **Three** unique country flags with **Three Text Boxes** and a `Submit` button.
2. User must guess the country names of all three displayed flags and type it into the corresponding **Text Boxes**.
3. Then he must click `Submit` button and verify his answers.
4. Once the `Submit` button is clicked, **Text Boxes** with correct answers would be **Greyed out** and **Uneditable**, and either the backgrounds or the text color must change colors into **Green**.
5. If any wrong answers were found, **Text Boxes** must remain editable and either the background or the text color must change into **Red**.
6. If the User gets all three countries correct, then the message `CORRECT!` must appear in **Green** color.
7. After three **Incorrect** attempts (Three `Submit` button clicks and at least **1 answer** is incorrect), `WRONG!` must appear in **Red** color. And the correct country names must appear in **Blue** under the incorrect answers.
8. Finally user must click on a `Next` button to go the next level.
9. For each **Correct** guess, the User receives 1 point. This score must be displayed on the **Top Right** of the screen.

### <a name="switch-button"></a>Switch Button
1. User must be able to turn the switch on/off.
2. When it is turned on, all game modes must include a **Countdown Timer**, which counts down from **10 seconds**.
3. Timer must be displayed in every game level screen.
4. As soon as the value reached 0, the game must react as if the `Submit` button was clicked. (All answers will be checked, `CORRECT!` OR `WRONG!` messages displayed. And allow user to continue to the next level.)
5. Levels that require multiple `Submit` button clicks, must start a new timer until all attempts are exhausted.
6. Timer reaching 0 is functionally **EXACTLY** like pressing `Submit` button once.

### Activity Recreation
* For all tasks, the application must save the state and resume from the same point if the user changes the orientation.

## Addendum
* In the `Guess the Flag` page, Specifications has not provided a way to show the correct flag after an incorrect guess. This may be fixed to improve UX.