package falstad;

public class KruskalSet
{
  int[] array;
  
  public KruskalSet(int dimension)
  {
    this.array = new int[dimension];
    for (int i = 0; i < this.array.length; i++) {
      this.array[i] = -1;
    }
  }
  
  public void union(int root1, int root2)
  {
    if (this.array[root2] < this.array[root1])
    {
      this.array[root1] = root2;
    }
    else
    {
      if (this.array[root1] == this.array[root2]) {
        this.array[root1] -= 1;
      }
      this.array[root2] = root1;
    }
  }
  
  public int find(int x)
  {
    if (this.array[x] < 0) {
      return x;
    }
    this.array[x] = find(this.array[x]);
    return this.array[x];
  }
}
