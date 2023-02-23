package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume("uuid3");
    private static final Resume RESUME_4 = new Resume("uuid4");

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = StorageException.class)
    public void overflowStorage() throws Exception {
        try {
            for (int i = 4; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("overflow occurred ahead of time");
        }
        storage.save(new Resume());
    }
        @Test
        public void save () throws Exception {
            storage.save(RESUME_4);
            Assert.assertEquals(4, storage.size());
        }

        @Test(expected = ExistStorageException.class)
        public void ExistSave () throws Exception {
            storage.save(RESUME_1);
        }

        @Test
        public void update () throws Exception {
            Resume new_Resume = new Resume(UUID_1);
            storage.update(new_Resume);
            Assert.assertTrue(new_Resume == storage.get(UUID_1));
        }

        @Test(expected = NotExistStorageException.class)
        public void NotExistUpdate () throws Exception {
            storage.get("dummy");
        }

        @Test
        public void delete () throws Exception {
            Assert.assertEquals(3, storage.size());
            storage.delete(UUID_1);
            Assert.assertEquals(2, storage.size());
        }

        @Test(expected = NotExistStorageException.class)
        public void notExistdelete () throws Exception {
            Assert.assertEquals(3, storage.size());
            storage.delete(UUID_1);
            storage.get(UUID_1);
        }

        @Test
        public void getAll () throws Exception {
            Resume[] array = storage.getAll();
            Assert.assertEquals(3, storage.size());
            Assert.assertEquals(array[0], RESUME_1);
            Assert.assertEquals(array[1], RESUME_2);
            Assert.assertEquals(array[2], RESUME_3);
        }

        @Test
        public void get () throws Exception {
            Assert.assertTrue(RESUME_1 == storage.get(UUID_1));
            Assert.assertTrue(RESUME_2 == storage.get(UUID_2));
            Assert.assertTrue(RESUME_3 == storage.get(UUID_3));
        }

        @Test(expected = NotExistStorageException.class)
        public void getNotExist () throws Exception {
            storage.get("dummy");
        }
    }