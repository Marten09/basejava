package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("There is no place in the storage for a new resume. " + r.getUuid());
        } else if (index != -1) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else {
            doSave(r,index);
            size++;
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume is already in the storage " + r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume is already in the storage" + uuid);
        } else {
            doDelete(index);
            storage[size - 1] = null;
            size--;
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume is already in the storage " + uuid);
        } else {
            return storage[index];
        }
        System.out.println("There is no resume with uuid = " + uuid);
        return null;
    }

    protected abstract int getIndex(String uuid);
    protected abstract void doSave(Resume r, int index);
    protected abstract void doDelete(int index);
}
