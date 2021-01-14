package by.bsu.mmf.fan.iso.utils;

/**
 * A data structure that allows you to merge sets and check whether two items belong to the same set in almost O(1)
 * time.
 */
public class DisjointSetUnion {
    private final int[] parent;
    private final int[] rank;

    /**
     * Creates a disjoint set union with the specified number of elements. Initially each element belongs to its own
     * singleton set.
     *
     * @param length the number of initial elements.
     */
    public DisjointSetUnion(int length) {
        this.parent = new int[length];
        this.rank = new int[length];
        for (int i = 0; i < length; i++) {
            this.parent[i] = i;
        }
    }

    /**
     * Finds the ID of the set that the specified representative belongs to.
     *
     * @param item the representative of the set.
     * @return the set the representative belongs to.
     */
    public int findSet(int item) {
        if (item == this.parent[item])
            return item;
        return this.parent[item] = this.findSet(this.parent[item]);
    }

    /**
     * Checks whether the specified representatives belong to the same set.
     *
     * @param item1 the first representative.
     * @param item2 the second representative.
     * @return {@code true} if the representatives belong to the same set, {@code false} otherwise.
     */
    public boolean areInSameSet(int item1, int item2) {
        return this.findSet(item1) == this.findSet(item2);
    }

    /**
     * Merges the sets represented by the specified elements.
     *
     * @param item1 the first representative.
     * @param item2 the second representative.
     */
    public void mergeSets(int item1, int item2) {
        item1 = this.findSet(item1);
        item2 = this.findSet(item2);
        if (item1 != item2) {
            if (this.rank[item1] < this.rank[item2]) {
                int tmp = item2;
                item2 = item1;
                item1 = tmp;
            }
            this.parent[item2] = item1;
            if (this.rank[item1] == this.rank[item2]) {
                ++this.rank[item1];
            }
        }
    }
}