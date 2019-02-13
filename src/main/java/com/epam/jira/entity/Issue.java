package com.epam.jira.entity;

import org.apache.commons.text.StringEscapeUtils;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {

    @XmlElement(name = "key", required = true)
    private String issueKey;

    @XmlElement(name = "status", required = true)
    private String status;

    @XmlElement(name = "summary")
    private String summary;

    @XmlElement(name = "time")
    private String time;

    @XmlElementWrapper(name = "attachments")
    @XmlElement(name = "attachment")
    private List<String> attachments;

    @XmlElementWrapper(name = "parameters")
    @XmlElement(name = "parameter")
    private List<Parameter> parameters;

    public Issue(String issueKey, TestResult status) {
       setIssueKey(issueKey);
       setStatus(status.toString());
    }

    public Issue(String issueKey, TestResult status, String time) {
        setIssueKey(issueKey);
        setStatus(status.toString());
        setTime(time);
    }

    public Issue() {
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = StringEscapeUtils.escapeJson(issueKey);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = StringEscapeUtils.escapeJson(status);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = StringEscapeUtils.escapeJson(summary);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments.stream().map(StringEscapeUtils::escapeJson).collect(Collectors.toList());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
