import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        for (Resume r : getAll()) {
            r = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size < storage.length) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("There is no place in the storage for a new resume.");
        }
    }

    Resume get(String uuid) {
        Resume[] all = getAll();
        for (Resume r : all) {
            return r;
        }
        System.out.println("There is no resume with uuid = " + uuid);
        return null;
    }

    void delete(String uuid) {
        Resume[] all = getAll();
        for (int i = 0; i < size; i++) {
            if (all[i] != null) {
                storage[i] = storage[size - 1];
                size--;
            } else {
                System.out.println("Invalid uuid in method 'delete': " + uuid);
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size];
        for (int i = 0; i < size; i++) {
            all[i] = storage[i];
        }
        return all;
    }

    int size() {
        return size;
    }
}