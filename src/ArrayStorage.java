import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
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
        int indexOfResumeToDelete = -1;
        for (int i = 0; i < size; i++) {
            if (indexOfResumeToDelete < 0) {
                if (all[i].toString().equals(uuid)) {
                    indexOfResumeToDelete = i;
                }
            } else {
                all[i] = storage[i + 1];
            }
        }
        if (indexOfResumeToDelete < 0) {
            System.out.println("There is no resume with uuid = " + uuid);
        } else {
            all[size - 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = Arrays.copyOf(storage, size);
        return all;
    }

    int size() {
        return size;
    }
}