package com.epam.jira;

import com.epam.jira.entity.Issue;
import com.epam.jira.entity.Issues;
import com.epam.jira.util.FileUtils;
import cucumber.api.TestCase;
import cucumber.api.event.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.*;

public class QASpaceReporter implements EventListener {

    private Issue issue = null;
    private List<Issue> issues = new ArrayList<>();
    private List<String> summaryList;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, getTestCaseFinishedHandler());
        publisher.registerHandlerFor(TestCaseStarted.class, getTestCaseStartedHandler());
        publisher.registerHandlerFor(TestRunFinished.class, getTestRunFinishedHandler());
    }

    private void handleStartOfTestCase(TestCaseStarted event) {
        issue = new Issue();
        summaryList = new ArrayList<>();
        TestCase testCase = event.testCase;

        if (isJIRATestKeyPresent(testCase)) {
            issue.setIssueKey(getJIRAKey(testCase));
        }
        summaryList.add("Test case: " + testCase.getName());
    }

    private boolean isJIRATestKeyPresent(TestCase testCase) {
        return testCase.getTags().stream().anyMatch(tag -> isStringContainsTag(tag.getName()));
    }

    private boolean isStringContainsTag(String string) {
        return string.contains("JIRATestKey");
    }

    private String durationToString(long duration) {
        return MINUTES.convert(duration, NANOSECONDS) +
                "m " +
                SECONDS.convert(duration, NANOSECONDS) +
                "." +
                MILLISECONDS.convert(duration, NANOSECONDS) +
                "s";
    }

    private EventHandler<TestCaseStarted> getTestCaseStartedHandler() {
        return this::handleStartOfTestCase;
    }

    private EventHandler<TestCaseFinished> getTestCaseFinishedHandler() {
        return event -> {
            issue.setStatus(event.result.getStatus().firstLetterCapitalizedName());
            issue.setTime(durationToString(event.result.getDuration()));
            if (event.result.getError() != null) {
                summaryList.add(event.result.getError().getLocalizedMessage());
                summaryList.add(FileUtils.save(event.result.getError()));
            } else {
                summaryList.add("Scenario passed OK");
            }
            if (!FileUtils.getFiles().isEmpty()) {
                issue.setAttachments(FileUtils.getFiles());
            }
            if (!FileUtils.getParams().isEmpty()) {
                issue.setParameters(FileUtils.getParams());
            }
            issue.setSummary(String.join("\n", summaryList));
            issues.add(issue);
        };
    }

    private EventHandler<TestRunFinished> getTestRunFinishedHandler() {
        return event -> FileUtils.writeXmlJunit(new Issues(issues), "jira-tm-report.xml");
    }

    private String getJIRAKey(TestCase testCase) {
        return testCase.getTags().stream().filter(tag -> isStringContainsTag(tag.getName())).findFirst()
                .map(tag -> tag.getName().substring(13, tag.getName().length() - 1)).orElse("");
    }
}