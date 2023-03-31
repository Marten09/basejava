package com.urise.webapp.storage.Serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serialazable {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContact();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            // TODO implements sections
            writeCollection(dos,r.getSection().entrySet(),entry -> {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> {
                        writeCollection(dos, ((OrganizationSection) section).getOrganizations(), organizations -> {
                            dos.writeUTF(organizations.getName());
                            dos.writeUTF(organizations.getWebsite());
                            writeCollection(dos, organizations.getPeriods(), period -> {
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getTitle());
                                writeLocalDate(dos, period.getStartDate());
                                writeLocalDate(dos, period.getEndDate());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid,fullName);
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            // TODO implements sections
            return resume;
        }
    }

    public void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    public AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION -> new OrganizationSection(readList(dis, () -> new Organization(dis.readUTF(), dis.readUTF(), readList(dis
                    , () -> new Period(dis.readUTF(), dis.readUTF(), readLocalDate(dis), readLocalDate(dis))))));
        };
    }

    public LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private interface ElementWriter<Key> {
        void write(Key key) throws IOException;

    }

    private interface ElementChecker {
        void check() throws IOException;
    }

    private interface ElementReader<Key> {
        Key read() throws IOException;
    }

    private <Key> void writeCollection(DataOutputStream dos, Collection<Key> collection, ElementWriter<Key> writer) throws IOException {
        dos.writeInt(collection.size());
        for (Key item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream dis, ElementChecker checker) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            checker.check();
        }
    }

    private <Key> List<Key> readList(DataInputStream dis, ElementReader<Key> reader) throws IOException {
        int size = dis.readInt();
        List<Key> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
}
