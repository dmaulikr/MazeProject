package falstad;

import static org.junit.Assert.*;

import org.junit.Test;


public class MazeBuilderKruskalTest {
	final int height = 15;
	final int width = 15;
	final int roomCt = 20;
	final int pc = 15;
	
	// Return a new maze object for testing
	private Maze GenerateTestMaze() {
		return new GenerateTestMaze();
	}
	
	//Make sure the MazeBuilderKruskal is structuring a maze
	//That has the same specifications of the input
	@Test
	public void testWidthAndHeight() {
		Maze testMaze = GenerateTestMaze();
		MazeBuilderKruskal MazeBuilderKruskal = new MazeBuilderKruskal();
		MazeBuilderKruskal.build(testMaze, width, height, roomCt, pc);
		assertTrue(MazeBuilderKruskal.width == width);
		assertTrue(MazeBuilderKruskal.height == height);
		assertTrue(MazeBuilderKruskal.expectedPartiters == pc);
	}
		
	@Test
	// Distance must be less than the total size of the maze
	public void TheSolutionExists() {
		int disMoved;
		Maze testMaze = GenerateTestMaze();
		MazeBuilderKruskal MazeBuilderKruskal = new MazeBuilderKruskal();
		MazeBuilderKruskal.build(testMaze, width, height, roomCt, pc);
		// Use getMaxDistance in collaboration with dists to
		// determine what the distance could possibly be from there
		disMoved = MazeBuilderKruskal.dists.getMaxDistance();
		//Assume that the distance (in moves) to the exit is less than
		//the total size of the maze. If this isn't true, there is an
		//issue with the maze and it has no exit.
		assertTrue(disMoved <= (height * width));
		
	}
	
	@Test
	//Test the getSign method in mazeBuilder
	public void SignTest(){
		
		for(int n=1; n<100; n++)
		{
			assertTrue(MazeBuilderKruskal.getSign(n)==1);
		}	
			for(int n=-1; n>-100; n--)
			{
			assertTrue(MazeBuilderKruskal.getSign(n)==-1);
	}
			assertTrue(MazeBuilderKruskal.getSign(0)==0);
	}

}