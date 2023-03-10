package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void doSave(Resume r, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, index);
            size++;
        }
    }

    @Override
    public final void doUpdate(Resume r, Integer index) {
        storage[index] = r;

    }

    @Override
    public final void doDelete(Integer index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;

    }
    @Override
    public final List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public final Resume doGet(Integer index) {
        return storage[index];
    }


    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
    protected abstract Integer getSearchKey(String uuid);
}