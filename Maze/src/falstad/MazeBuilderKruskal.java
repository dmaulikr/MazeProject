package falstad;

import java.util.ArrayList;

public class MazeBuilderKruskal extends MazeBuilder implements Runnable
{
	//Override Prim Generator
  public void generatePathways()
  {
	// Initialize counter to track distance
    int Kruscounter = 0;
    KruskalSet set = new KruskalSet(this.width * this.height);
    ArrayList<Wall> walls = new ArrayList();
    // Increment q up to the width
    for (int q = 0; q < this.width; q++) {
    	// Increment w up to the height
      for (int w = 0; w < this.height; w++)
      {
    	// Given the current location, proceed adding values
    	// To the the list of walls
        if (this.cells.canGo(q, w, 0, 1)) 
        {
          walls.add(new Wall(q, w, 0, 1));
        }
        if (this.cells.canGo(q, w, 0, -1)) 
        {
          walls.add(new Wall(q, w, 0, -1));
        }
        if (this.cells.canGo(q, w, 1, 0)) 
        {
          walls.add(new Wall(q, w, 1, 0));
        }
        if (this.cells.canGo(q, w, -1, 0)) 
        {
          walls.add(new Wall(q, w, -1, 0));
        }
      }
    }
    // While there are not no walls left
    while (Kruscounter < this.width * this.height - 1)
    {
    // 
      int r = randomRange(0, walls.size() - 1);
      Wall edge = (Wall)walls.get(r);
      // Height and width aspects of edges 
      int current = edge.y + edge.x * this.height;
      int current2 = edge.y + edge.dy + (edge.x + edge.dx) * this.height;
      int compare = set.find(current);
      int compared_to = set.find(current2);
      if (compare != compared_to)
      {
        set.union(compare, compared_to);
        this.cells.deleteWall(edge.x, edge.y, edge.dx, edge.dy);
        int x = edge.x + edge.dx;
        int y = edge.y + edge.dy;
        this.cells.deleteWall(x, y, -edge.dx, -edge.dy);
        // Increment Kruscounter
        Kruscounter++;
      }
      // Remove wall from board
      walls.remove(r);
    }
  }
}