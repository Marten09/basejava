package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();
    protected int key;

    @Override
    protected int getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                key = i;
                return key;
            }
        }
        key = -1;
        return key;
    }

    @Override
    public void doClear() {
        list.clear();
    }

    @Override
    public void doSave(Resume r, int key) {
        list.add(key, r);
    }

    @Override
    public void doUpdate(Resume r, int key) {
        list.set(key, r);
    }

    @Override
    public Resume doGet(int key) {
        return list.get(key);
    }

    @Override
    public void doDelete(int key) {
        list.remove(key);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
