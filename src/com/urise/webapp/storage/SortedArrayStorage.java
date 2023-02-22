package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSave(Resume r, int index) {
        int number = -index - 1;
        System.arraycopy(storage, number, storage, index + 1, size - number);
    }

    @Override
    protected void doDelete(int index) {
        int number = size - index - 1;
        if (number > 0) {
            System.arraycopy(storage, index + 1, storage, index, number);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}