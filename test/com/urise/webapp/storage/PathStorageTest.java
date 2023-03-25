package com.urise.webapp.storage;

import com.urise.webapp.storage.Serializer.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest(){
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
    }
}