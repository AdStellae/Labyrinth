import java.util.Arrays;

public class DisjointSets {
    private int[] S; // an array will represent the tree

    public DisjointSets(int elements) {
        S = new int[elements];
        //set all roots to -1 initially
        Arrays.fill(S, -1);
        for (int i : S) {
            System.out.println(i);
        }
    }

    /**
     * Returns the set S
     * @return
     */
    public int[] getS() {
        return S;
    }

    /**
     * Union is performed on the roots of the trees
     * @param x root x
     * @param y root y
     */
    public void union(int x, int y) {

        // if the tree with root y is larger or if the trees are of equal size (the element of y is more negative), y will be the parent of x
        if (S[y] <= S[x]) {
            S[y] = S[y] + S[x];
            S[x] = y;
            System.out.println("after union: y:" + y + "  S[y] (value of index y in S): " + S[y] );
            System.out.println("after union: x:" + x + "  S[x] (value of index x in S): " + S[x] );

        }
        else {
            S[x] = S[x] + S[y]; //otherwise x has a larger tree and will be the root
            S[y] = x;
        }
    }

    /**
     * Finds S[x] and compares it to 0 to determine the root of the tree.
     * @param x
     * @return
     */
    public int find(int x) {

        if(S[x] < 0) {
            return x; //if x is negative it is the root, return x
        }
        else {
            return find(S[x]); //traverse recursively up the tree if x is not the root
        }
    }
}

