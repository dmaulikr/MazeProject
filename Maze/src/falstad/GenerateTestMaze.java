package falstad;

public class GenerateTestMaze extends Maze{ 
	// Provides appropriate newMaze inputs and creates a new maze
	// in a blank environment in order to compare to the 
	// maze that was actually produced
		public void newMaze(BSPNode root, Cells c, Distance dists, int startx, int starty) {
			// Establish variables the same way newMaze did
			mazecells = c ;
			mazedists = dists;
			seencells = new Cells(mazew+1,mazeh+1) ;
			rootnode = root ;
			walkStep = 0;
			angle = 0;
		}
	}