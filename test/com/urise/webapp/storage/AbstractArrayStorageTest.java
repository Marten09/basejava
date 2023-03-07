package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }
    @Test(expected = StorageException.class)
    public void overflowStorage() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name"));
            }
        } catch (StorageException e) {
            Assert.fail("overflow occurred ahead of time");
        }
        storage.save(new Resume("Overflow"));
    }
}