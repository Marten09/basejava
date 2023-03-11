package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public void doClear() {
        list.clear();
    }

    @Override
    public void doSave(Resume r, Integer searchKey) {
        list.add(r);
    }

    @Override
    public void doUpdate(Resume r, Integer searchKey) {
        list.set(searchKey, r);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public void doDelete(Integer searchKey) {
        list.remove(searchKey.intValue());
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}
