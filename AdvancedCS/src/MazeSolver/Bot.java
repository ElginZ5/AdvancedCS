package MazeSolver;

import java.awt.Color;

public abstract class Bot {
	
	public MazeRunner maze;
	public Color color;
	
	public Bot(MazeRunner mr, Color c) {
		maze = mr;
		color = c;
	}
	
	// use maze.move(this) and maze.turnLeft(this)
	// to move through the maze.
	// You are only allowed to call move/turnLeft once per 
	// move.
	public abstract void move();
}