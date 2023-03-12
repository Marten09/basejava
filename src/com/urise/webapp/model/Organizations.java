package com.urise.webapp.model;

import java.time.LocalDate;

public class Organizations {
    private final String title;
    private final String description;
    private final String post;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Organizations(String title, String description, String post, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.post = post;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organizations that = (Organizations) o;

        if (!title.equals(that.title)) return false;
        if (!description.equals(that.description)) return false;
        if (!post.equals(that.post)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + post.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organizations{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", post='" + post + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
