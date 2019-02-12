package com.epam.jira.entity;

public enum TestResult {

    FAILED("Failed"),
    PASSED("Passed"),
    BLOCKED("Blocked"),
    SKIPPED("Skipped"),
    PENDING("Pending"),
    UNDEFINED("Undefined"),
    AMBIGUOUS("Ambiguous"),
    UNTESTED("Untested");

    private final String text;

    TestResult(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
