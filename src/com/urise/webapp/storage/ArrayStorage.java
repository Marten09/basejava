package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("There is no place in the storage for a new resume." + r.getUuid());
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        if (getIndex(r.getUuid()) == -1) {
            System.out.println("Resume is already in the storage " + r.getUuid());
        } else {
            storage[getIndex(r.getUuid())] = r;
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) == -1) {
            System.out.println("Resume is already in the storage" + uuid);
        } else {
            return storage[getIndex(uuid)];
        }
        System.out.println("There is no resume with uuid = " + uuid);
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) == -1) {
            System.out.println("Resume is already in the storage" + uuid);
        } else {
            storage[getIndex(uuid)] = storage[size - 1];
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

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}