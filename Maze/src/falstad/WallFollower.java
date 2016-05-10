package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;

public class WallFollower implements RobotDriver {
	
	Robot r;
	int width;
	int height;
	Distance distance;
	int pathLength;
	
	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) {
		this.r = r;
	}

	/**
	 * Provides the robot driver with information on the dimensions of the 2D maze
	 * measured in the number of cells in each direction.
	 * @param width of the maze
	 * @param height of the maze
	 * @precondition 0 <= width, 0 <= height of the maze.
	 */
	@Override
	public void setDimensions(int width, int height) {
		assert (width >= 0 && height >= 0);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Provides the robot driver with information on the distance to the exit.
	 * Only some drivers such as the wizard rely on this information to find the exit.
	 * @param distance gives the length of path from current position to the exit.
	 * @precondition null != distance, a full functional distance object for the current maze.
	 */
	@Override
	public void setDistance(Distance distance) {
		assert (distance != null);
		this.distance = distance;
	}
	
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		
		while(r.isAtGoal() == false){
			if(hasLeftWall() && !hasFrontWall()){
				r.move(1);
				pathLength++;
				if(!hasEnergy()){
					System.out.println("Has Left Wall and No Front Wall and moved");
					throw new Exception();
				}
			
			}
			else if(hasLeftWall() && hasFrontWall()){
				while(hasFrontWall()){
					r.rotate(Turn.RIGHT);
					if(!hasEnergy()){
						System.out.println("Has Left Wall and Front Wall and didn't move");
						throw new Exception();
					}
				}
				r.move(1);
				pathLength++;
				if(!hasEnergy()){
					System.out.println("Has Left Wall and Front Wall and moved");
					throw new Exception();
				}
			}
			else if(!hasLeftWall()){
				r.rotate(Turn.LEFT);
				r.move(1);
				pathLength++;
				if(!hasEnergy()){
					System.out.println("No Left Wall");
					throw new Exception();
				}
			}
		}
		System.out.println("At Goal");
		if(!r.canSeeGoal(((BasicRobot) r).getDirection())){
			if(!hasLeftWall()){
				r.rotate(Turn.LEFT);
				if(!hasEnergy()){
					throw new Exception();
				}
			}
			else if(hasLeftWall() && hasFrontWall()){
				r.rotate(Turn.RIGHT);
				if(!hasEnergy()){
					throw new Exception();
				}
			}
		}
		r.move(1);
		pathLength++;
		return true;
	}

	public boolean hasEnergy(){
		System.out.println(r.getBatteryLevel());
		if(r.getBatteryLevel() > 0){
			return true;
		}
		return false;
	}
	
	public boolean hasLeftWall(){
		int[] posArray = new int[2];
		try {
			posArray = r.getCurrentPosition();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		int x = posArray[0];
		int y = posArray[1];
		Direction direction = ((BasicRobot) r).getDirection();
		switch (direction){
			case FORWARD: return ((BasicRobot) r).maze.mazecells.hasWallOnLeft(x, y);
			case BACKWARD: return ((BasicRobot) r).maze.mazecells.hasWallOnRight(x, y);
			case LEFT: return ((BasicRobot) r).maze.mazecells.hasWallOnTop(x, y);
			case RIGHT: return ((BasicRobot) r).maze.mazecells.hasWallOnBottom(x, y);
		}
		return true;
	}
	
	public boolean hasFrontWall(){
		int[] posArray = new int[2];
		try {
			posArray = r.getCurrentPosition();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		int x = posArray[0];
		int y = posArray[1];
		Direction direction = ((BasicRobot) r).getDirection();
		switch (direction){
			case FORWARD: return ((BasicRobot) r).maze.mazecells.hasWallOnBottom(x, y);
			case BACKWARD: return ((BasicRobot) r).maze.mazecells.hasWallOnTop(x, y);
			case LEFT: return ((BasicRobot) r).maze.mazecells.hasWallOnLeft(x, y);
			case RIGHT: return ((BasicRobot) r).maze.mazecells.hasWallOnRight(x, y);
		}
		return true;
	}
	
	/**
	 * Returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for a robot driver.
	 */
	@Override
	public float getEnergyConsumption() {
		return (2500 - r.getBatteryLevel());
	}

	/**
	 * Returns the total length of the journey in number of cells traversed. 
	 * Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 */
	@Override
	public int getPathLength() {
		return pathLength;
	}

}
