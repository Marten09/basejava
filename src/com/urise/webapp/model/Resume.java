package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final Resume NEW_RESUME = new Resume();
    static {
//        NEW_RESUME.setFullName("");
//        NEW_RESUME.setUuid("");
        NEW_RESUME.setSection(SectionType.OBJECTIVE, new TextSection(""));
        NEW_RESUME.setSection(SectionType.PERSONAL, new TextSection(""));
        NEW_RESUME.setSection(SectionType.ACHIEVEMENT, new ListSection(List.of("")));
        NEW_RESUME.setSection(SectionType.QUALIFICATIONS, new ListSection(List.of("")));
        NEW_RESUME.setSection(SectionType.EXPERIENCE, new OrganizationSection(List.of(new Organization("", "", List.of(new Period("", "", LocalDate.of(1, 1, 1), LocalDate.of(1, 1, 1)))))));
        NEW_RESUME.setSection(SectionType.EDUCATION, new OrganizationSection(List.of(new Organization("", "", List.of(new Period("", "", LocalDate.of(1, 1, 1), LocalDate.of(1, 1, 1)))))));
    }

    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> section = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contact = new EnumMap<>(ContactType.class);

    public Map<ContactType, String> getContact() {
        return contact;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<SectionType, AbstractSection> getSection() {
        return section;
    }

    public void setContact(ContactType type, String value) {
       contact.put(type,value);
    }

    public void setSection(SectionType type, AbstractSection sections) {
       section.put(type,sections);
    }

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }
    public String getContact(ContactType type) {
        return contact.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return section.get(type);
    }

    public void addContact(ContactType type, String value) {
        contact.put(type, value);
    }

    public void addSection(SectionType type, AbstractSection sections) {
        section.put(type, sections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && section.equals(resume.section) && contact.equals(resume.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, section, contact);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "fullname='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName);
    }
}