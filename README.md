# Group 16

## Which APK's are used during testing

Både _app-debug-androidTest.apk_ og _app-debug.apk_ blir installert under testing. androidTest versionen er apk-filen som kjører testene, mens den andre er appen som kjører.

## imageCountDecreaseAfterDeletion

This test runs on _GalleryActivity_, but also uses _AddQUestionActivity_

- get the initial number of questions
- click on the first question
- check if the final score is 1 less.

### Expected result

The expected result is that the count should be 1 less than previously.

## QuestionCountIncreasesAfterAdding

This test runs on _GalleryActivity_, but also uses _AddQUestionActivity_

- get the initial number of questions
- click on add new question
- create mock data (the image)
- write text in a text field, and use mock-data
- click on save, to save the new question

### Expected result

The expected result is that the count should be 1 more than previously

## navigateToQuizActivity

This test runs on _MainActivity_, but also uses _QuizActivity_

- click on the quiz-button

### Expected result

The expected result is that the new screen should be `QuizActivity`

## scoreUpdatesCorrectlyOnCorrectAnswer

This test runs on _QuizActivity_

- Start the Quiz-page
- Wait 500ms to make sure the page has loaded
- Check if the old score matches
- Click on the correct-answer
- Check if the new score matches

### Expected result

The expected result is that the old score should match `Score: 0`, and that the new score should match `Score: 1`

## ADB

`adb install` blir brukt for å installere APK-filen som blir generert av Gradle. `adb shell am instrument` blir brukt for å starte testene. `adb shell am start` blir brukt for å starte hoved-aktiviteten i appen. `adb shell am force-stop` blir brukt for å stoppe appen. `adb logcat` blir brukt for å vise loggen generert av appen.
