package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != -1;
    }

    @Override
    public void doClear() {
        list.clear();
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    public void doUpdate(Resume r, Object searchKey) {
        list.set((Integer) searchKey, r);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        list.remove(searchKey);
    }

    @Override
    public List<Resume> doCopyAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
