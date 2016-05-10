package edu.wm.cs.cs301.tomvdh44.falstad;

import android.graphics.*;

public class MazeView extends DefaultViewer {

	Maze maze ; // need to know the maze model to check the state 
	// and to provide progress information in the generating state
	
	public MazeView(Maze m) {
		super() ;
		maze = m;
	}

	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
	
	// 
	
	/**
	 * Helper method for redraw to draw the title screen, screen is hardcoded
	 * @param  gc graphics is the off screen image
	 */

	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off screen image
	 */


	/**
	 * Helper method for redraw to draw screen during phase of maze generation, screen is hard coded
	 * only attribute percentdone is dynamic
	 * @param gc graphics is the off screen image
	 */


}
