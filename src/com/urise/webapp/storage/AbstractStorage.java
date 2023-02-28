package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int getKey(String uuid);

    protected abstract void doClear();

    protected abstract void doSave(Resume r, int key);

    protected abstract void doUpdate(Resume r, int key);

    protected abstract Resume doGet(int key);

    protected abstract void doDelete(int key);


    public void clear() {
        doClear();
    }

    public void save(Resume r) {
        int key = getKey(r.getUuid());
        if (key >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r, key);
        }
    }


    public void update(Resume r) {
        int key = getKey(r.getUuid());
        if (key == -1) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            doUpdate(r, key);
        }
    }


    public Resume get(String uuid) {
        int key = getKey(uuid);
        if (key < 0) {
            throw new NotExistStorageException(uuid);
        } else{
            return doGet(key);
        }
    }


    public void delete(String uuid) {
        int key = getKey(uuid);
        if(key == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(key);
        }
    }
}
