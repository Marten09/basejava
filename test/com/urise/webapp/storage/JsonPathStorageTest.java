package com.urise.webapp.storage;

import com.urise.webapp.storage.Serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest{
    public JsonPathStorageTest(){
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),new JsonStreamSerializer()));
    }
}
