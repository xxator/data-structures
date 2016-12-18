
// UnionFind/Disjoint Set Data-structure.
// 
// This code is an inspired modification of the union find implementation found in 
// 'Algorithms Fourth Edition' by Robert Sedgewick and Kevin Wayne.

interface IDisjointSet {

  public void unify(int p, int q);
  public boolean connected(int p, int q);
  public int size();
  public int components();
  public int componentSize(int p);

}

public class UnionFind implements IDisjointSet {

  private int size;
  private int[] sz;
  private int[] id;
  private int numComponents;

  public UnionFind(int size) {

    if (size <= 0)
      throw new IllegalArgumentException("Size <= 0 is not allowed");

    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];
    for(int i = 0; i < size; i++) {
      id[i] = i; // Link to itself (self root)
      sz[i] = 1; // Each component is of size one originally
    }

  }

  public int find(int p) {
    
    // Find the root of the component/set
    int root = p;
    while( root != id[root] ) 
      root = id[root];

    // Path compression
    while(p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;

  }

  // Return whether or not the elements 'p' and
  // 'q' are in the same components/set.
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  // Return the size of the components/set 'p' belongs to
  public int componentSize(int p) {
    return sz[find(p)];  
  }

  // Return the number of elements in this UnionFind/Disjoint set
  public int size() {
    return size;
  }

  // Returns the number of remaining components/sets 
  public int components() {
    return numComponents;
  }

  // Unify the components/sets containing elements 'p' and 'q'
  public void unify(int p, int q) {

    int root1 = find(p);
    int root2 = find(q);

    // These elements are already in the same group!
    if (root1 == root2) return;

    // Merge two components/sets together
    if (sz[root1] < sz[root2]) {
      sz[root2] += sz[root1];
      id[root1] = root2;
    } else {
      sz[root1] += sz[root2];
      id[root2] = root1;
    }

    // Since the roots found are different we know that the
    // number of components/sets has decreased by one
    numComponents--;

  }

}
