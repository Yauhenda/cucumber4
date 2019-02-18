# QASpaceReport for Cucumber4

QASpaceReport for Cucumber4 is a Jav library for creating proper artifact for Jenkins plugin.

## Installation

Add dependency in pom.xml file

## Usage

in your Runner add QASpaceReporter as a plugin
```java
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.epam.jira.QASpaceReporter"}
)
public class TestRunner {
```

## Adding a attachment file
To add a screenshot or any other attachment after scenario call embed method.
```java
@After
    public void embedScreenshot(Scenario scenario) {
            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
    }
```
supports MIME types:
```java
"image/bmp"
"image/gif"
"image/jpeg"
"image/png"
"image/svg+xml"
"video/ogg"
"text/plain"
```
To add file during step perfoming:
1. Setup scenario in @Before hook:
```java
@Before
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
    }
```
2. Call embed method in a step:
```java
byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
```

