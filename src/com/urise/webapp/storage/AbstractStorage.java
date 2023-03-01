package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object getKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doClear();

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);


    public void clear() {
        doClear();
    }

    public void save(Resume r) {
        Object searchKey = getKey(r.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r, searchKey);
        }
    }


    public void update(Resume r) {
        Object searchKey = ExistSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }


    public Resume get(String uuid) {
        Object searchKey = ExistSearchKey(uuid);
        doGet(searchKey);
    }


    public void delete(String uuid) {
        Object searchKey = ExistSearchKey(uuid);
        doDelete(searchKey);
    }

    protected Object ExistSearchKey(String uuid) {
        Object searchKey = getKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

}
