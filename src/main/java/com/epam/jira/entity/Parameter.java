package com.epam.jira.entity;

import org.apache.commons.text.StringEscapeUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "value")
    private String value;

    public Parameter() {
    }

    public Parameter(String title, String value) {
        setTitle(title);
        setValue(value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = StringEscapeUtils.escapeJson(title);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = StringEscapeUtils.escapeJson(value);
    }

}
