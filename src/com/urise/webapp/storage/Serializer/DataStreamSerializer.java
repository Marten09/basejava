package com.urise.webapp.storage.Serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements Serialazable {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContact();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
            Map<SectionType, AbstractSection> sections = r.getSection();
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> dos.writeInt(((ListSection) section).getItems().size());
                    case EXPERIENCE, EDUCATION -> {
                        //dos.writeUTF(((OrganizationSection) section).getOrganizations().toString());
                        dos.writeInt(((OrganizationSection) section).getOrganizations().size());
                        for (Organization organizations : ((OrganizationSection) section).getOrganizations()){
                            dos.writeUTF(organizations.getName());
                            //dos.writeUTF(organizations.getPeriods().toString());
                            dos.writeInt(organizations.getPeriods().size());
                            for (Period period : organizations.getPeriods()){
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getTitle());
                                writeLocalDate(dos,period.getStartDate());
                                writeLocalDate(dos,period.getEndDate());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            }
            // TODO implements sections
            return resume;
        }
    }
    public void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }
    public AbstractSection readSection(DataInputStream dis,SectionType sectionType) throws IOException {
        switch (sectionType){
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                int size = dis.readInt();
                List<String> items = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    items.add(dis.readUTF());
                }
                return new ListSection(items);
            }
            case EXPERIENCE, EDUCATION -> {
                return new OrganizationSection(List.of(new Organization(dis.readUTF(),dis.readUTF(),List.of(new Period(dis.readUTF(),dis.readUTF(),readLocalDate(dis),readLocalDate(dis))))));
            }
            default -> throw new IllegalStateException();
        }
    }
    public LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
