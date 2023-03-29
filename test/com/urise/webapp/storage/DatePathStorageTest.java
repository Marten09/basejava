package com.urise.webapp.storage;

import com.urise.webapp.storage.Serializer.DataStreamSerializer;

public class DatePathStorageTest extends AbstractStorageTest{

    public DatePathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
