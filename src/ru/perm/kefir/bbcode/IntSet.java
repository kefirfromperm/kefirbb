package ru.perm.kefir.bbcode;

/**
 * Best performance set of primitive type int
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
class IntSet {
    private static final int TABLE_SIZE = 256;
    private static final int MASK = 255;
    private static final int INITIAL_CAPACITY = 16;

    private final int[][] table = new int[TABLE_SIZE][];      // Init null values by default
    private final int lengths[] = new int[TABLE_SIZE];      // Init 0 values by default

    IntSet() {
    }

    public void add(int value) {
        int rowIndex = rowIndex(value);

        int[] row = table[rowIndex];
        if (row == null) {
            row = new int[INITIAL_CAPACITY];
            table[rowIndex] = row;
            row[0] = value;
            lengths[rowIndex]++;
        } else {
            int length = lengths[rowIndex];

            // Enlarge array if necessary
            if (length >= row.length) {
                int newLength = 2 * row.length;
                int[] copyRow = new int[newLength];
                System.arraycopy(row, 0, copyRow, 0, row.length);
                row = copyRow;
                table[rowIndex] = copyRow;
            }

            int index = binarySearch(row, length, value);

            // If can't find value
            if (index < 0) {
                // Insert value
                int temp = value;
                for (int i = -index - 1; i < length; i++) {
                    int temp1 = row[i];
                    row[i] = temp;
                    temp = temp1;
                }
                row[length] = temp;
                lengths[rowIndex]++;
            }
        }
    }

    /**
     * Realisation of binary search algorithm. It is in JDK 1.6.0 but for
     * JDK 1.5.0 compatibility I added it there.
     *
     * @param array   array of integers values ordered by ascending
     * @param toIndex top break of array
     * @param key     searched value
     * @return value index or -(index of position)
     * @see java.util.Arrays#binarySearch(int[], int, int, int)
     */
    private static int binarySearch(int[] array, int toIndex, int key) {
        int low = 0;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = array[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }

    public boolean contains(int value) {
        int rowIndex = rowIndex(value);
        int length = lengths[rowIndex];
        return length > 0 && binarySearch(table[rowIndex], length, value) >= 0;
    }

    private int rowIndex(int value) {
        return value & MASK;
    }
}
