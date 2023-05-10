package com.urise.webapp.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.urise.webapp.util.JsonSectionAdapter;
import com.urise.webapp.util.LocalDateAdapter;
import com.urise.webapp.util.LocalDateJsonAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Period EMPTY = new Period();
    private String title;
    private String description;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(LocalDateJsonAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(LocalDateJsonAdapter.class)
    private LocalDate endDate;

    public Period() {
    }

    public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "dateStart must be not null");
        Objects.requireNonNull(endDate, "dateFinish must be not null");
        Objects.requireNonNull(title, "post must be not null");
        Objects.requireNonNull(description, "post must be not null");
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!title.equals(period.title)) return false;
        if (!description.equals(period.description)) return false;
        if (!startDate.equals(period.startDate)) return false;
        return endDate.equals(period.endDate);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
