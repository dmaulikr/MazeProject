package falstad;

public class BasicRobot implements Robot {

	Maze maze;
	float energy;
	
	public BasicRobot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Turn robot on the spot. If robot runs out of energy, it stops and throws an Exception, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * @param direction to turn to relative to current forward direction 
	 * @throws Exception if the robot stops for lack of energy. 
	 */
	@Override
	public void rotate(Turn turn) throws Exception {
		// TODO Auto-generated method stub
		switch(turn){
			case LEFT: maze.rotate(1); setBatteryLevel(getBatteryLevel() - 3);
				break;
			case RIGHT: maze.rotate(-1); setBatteryLevel(getBatteryLevel() - 3);
				break;
			case AROUND: maze.rotate(2); setBatteryLevel(getBatteryLevel() - 6);
				break;
		}
		System.out.println(directionName());
		if(energy <= 0){
			System.out.println("Rotate Failure");
			throw new Exception();
		}
	}
	
	public String directionName(){
		Direction d = getDirection();
		if(d == Direction.FORWARD){
			return "Forward";
		}
		else if(d == Direction.LEFT){
			return "Left";
		}
		else if(d == Direction.RIGHT){
			return "Right";
		}
		else
			return "Downward";
	}
	
	/**
	 * Moves robot forward a given number of steps. A step matches a single cell.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * If the robot hits an obstacle like a wall, it remains at the position in front 
	 * of the obstacle but hasStopped() == false.
	 * @param distance is the number of cells to move in the robot's current forward direction
	 * @throws Exception if robot hits an obstacle like a wall or border, 
	 * which indicates that current position is not as expected. 
	 * Also thrown if robot runs out of energy. 
	 * @precondition distance >= 0
	 */
	@Override
	public void move(int distance) throws Exception {
		assert (distance >= 0);
		for(int i = 0; i < distance; i++){
			maze.walk(1);
			setBatteryLevel(getBatteryLevel() - 5);
			if(energy <= 0){
				System.out.println("Move Failed");
				throw new Exception();
			}
		}
	}
	
	/**
	 * Provides the current position as (x,y) coordinates for the maze cell as an array of length 2 with [x,y].
	 * @postcondition 0 <= x < width, 0 <= y < height of the maze. 
	 * @return array of length 2, x = array[0], y=array[1]
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		int[] positionArray = {maze.px, maze.py};
		if(positionArray[0] >= maze.mazew || positionArray[0] < 0 
				|| positionArray[1] >= maze.mazeh || positionArray[1] < 0){
			throw new Exception();
		}
			
		return positionArray;
		
	}
	
	/**
	 * Provides the robot with a reference to the maze it is currently in.
	 * The robot memorizes the maze such that this method is most likely called only once
	 * and for initialization purposes. The maze serves as the main source of information
	 * about the current location, the presence of walls, the reaching of an exit.
	 * @param maze is the current maze
	 * @precondition maze != null, maze refers to a fully operational, configured maze object
	 */
	@Override
	public void setMaze(Maze maze) {
		assert(maze != null);
		this.maze = maze;
	}
	
	/**
	 * Tells if current position is at the goal (the exit). Used to recognize termination of a search.
	 * @return true if robot is at the goal, false otherwise
	 */
	@Override
	public boolean isAtGoal() {
		int[] posArray = new int[2];
		try {
			posArray = getCurrentPosition();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return maze.mazecells.isExitPosition(posArray[0], posArray[1]);
	}
	
	/**
	 * Tells if a sensor can identify the goal in given direction relative to 
	 * the robot's current forward direction from the current position.
	 * @return true if the goal (here: exit of the maze) is visible in a straight line of sight
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 */
	@Override
	public boolean canSeeGoal(Direction direction) throws UnsupportedOperationException {

		if(!hasDistanceSensor(direction)){
			throw new UnsupportedOperationException();
		}
		if(distanceToObstacle(direction) == Integer.MAX_VALUE){
			return true;
		}
		return false;
	}
	
	/**
	 * Tells if current position is inside a room. 
	 * @return true if robot is inside a room, false otherwise
	 * @throws UnsupportedOperationException if not supported by robot
	 */	
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
	
		int[] posArray = new int[2];
		try {
			posArray = getCurrentPosition();
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}	
		return maze.mazecells.isInRoom(posArray[0], posArray[1]);
	}

	/**
	 * Tells if the robot has a room sensor.
	 */
	@Override
	public boolean hasRoomSensor() {
		return true;
	}

	/**
	 * Provides the current direction as (dx,dy) values for the robot as an array of length 2 with [dx,dy].
	 * Note that dx,dy are elements of {-1,0,1} and as in bitmasks masks in Cells.java and dirsx,dirsy in MazeBuilder.java.
	 * 
	 * @return array of length 2, dx = array[0], dy=array[1]
	 */	
	@Override
	public int[] getCurrentDirection() {
		int[] directionArray = {maze.dx, maze.dy};
		return directionArray;
	}
	
	public Direction getDirection(){
		int[] directionArray = getCurrentDirection();
		if(directionArray[0] == 0){
			return (directionArray[1] == -1) ? Direction.BACKWARD : Direction.FORWARD;
		}
		return (directionArray[0] == 1) ? Direction.RIGHT : Direction.LEFT;
		
	}
	/**
	 * Returns the current battery level.
	 * The robot has a given battery level (energy level) that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call for distance2Obstacle may use less energy than a move forward operation.
	 * If battery level <= 0 then robot stops to function and hasStopped() is true.
	 * @return current battery level, level is > 0 if operational. 
	 */
	@Override
	public float getBatteryLevel() {
		
		return energy;
	}
	
	/**
	 * Sets the current battery level.
	 * The robot has a given battery level (energy level) that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call for distance2Obstacle may use less energy than a move forward operation.
	 * If battery level <= 0 then robot stops to function and hasStopped() is true.
	 * @param level is the current battery level
	 * @precondition level >= 0 
	 */
	@Override
	public void setBatteryLevel(float level) {
		
		energy = level;
	}
	
	/**
	 * Gives the energy consumption for a full 360 degree rotation.
	 * Scaling by other degrees approximates the corresponding consumption. 
	 * @return energy for a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() {
		return 12;
	}

	/**
	 * Gives the energy consumption for moving forward for a distance of 1 step.
	 * For simplicity, we assume that this equals the energy necessary 
	 * to move 1 step backwards and that scaling by a larger number of moves is 
	 * approximately the corresponding multiple.
	 * @return energy for a single step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		return 5;
	}

	/**
	 * Tells if the robot has stopped for reasons like lack of energy, hitting an obstacle, etc.
	 * @return true if the robot has stopped, false otherwise
	 */
	@Override
	public boolean hasStopped() {
		
		if(energy < 0 || distanceToObstacle(getDirection()) == 0){
			return true;
		}
	
		return false;
	}

	/**
	 * Tells the distance to an obstacle (a wall or border) 
	 * in a direction as given and relative to the robot's current forward direction.
	 * Distance is measured in the number of cells towards that obstacle, 
	 * e.g. 0 if current cell has a wall in this direction
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws UnsupportedOperationException if not supported by robot
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		int[] posArray = new int[2];
		int count = 0;
		int x = posArray[0];
		int y = posArray[1];
		try {
			posArray = getCurrentPosition();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		switch (direction){
			case LEFT: 
				while(maze.mazecells.hasNoWallOnLeft(x, y)){
					count++;
					x--;
					if(x < 0){
						return Integer.MAX_VALUE;
					}
				}
				break;
			case RIGHT: 
				while(maze.mazecells.hasNoWallOnRight(x, y)){
					count++;
					x++;
					if(x >= maze.mazew){
						return Integer.MAX_VALUE;
					}
				}
				break;
			case FORWARD: 
				while(maze.mazecells.hasNoWallOnBottom(x, y)){
					count++;
					y++;
					if(y >= maze.mazeh){
						return Integer.MAX_VALUE;
					}
				}
				break;
			case BACKWARD: 
				while(maze.mazecells.hasNoWallOnTop(x, y)){
					count++;
					y--;
					if(y < 0){
						return Integer.MAX_VALUE;
					}
				}
				break;
		}
		return count;
	}

	public Direction getLeftDirection(){
		Direction currDirection = getDirection();
		switch (currDirection){
			case LEFT:
				return Direction.BACKWARD;
			case RIGHT:
				return Direction.FORWARD;
			case BACKWARD:
				return Direction.RIGHT;
			case FORWARD:
				return Direction.LEFT;
			default: return Direction.LEFT;
		}
		
	}
	/**
	 * Tells if the robot has a distance sensor for the given direction.
	 */
	@Override
	public boolean hasDistanceSensor(Direction direction) {
		return true;
	}

}
