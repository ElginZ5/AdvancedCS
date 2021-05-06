package MazeSolver;

import java.awt.Color;

public class SolverBot extends Bot{
	
	int mode = 0;
	int leftTurnCount = 0;
	
	public SolverBot (MazeRunner mr, Color c) {
		
		super(mr, c);
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (mode == 0) {
			
			if (maze.move(this)) {
				
				mode = 1;
				
			} else {
				
				mode = 2;
				
			}
			
		} else if (mode == 1) {
			
			maze.turnLeft(this);
			mode = 0;
			
		} else if (mode == 2) {
			
			maze.turnLeft(this);
			
			if (leftTurnCount == 2) {
				
				leftTurnCount = 0;
				mode = 0;
				
			} else {
				
				leftTurnCount++;
				
			}
			
		}
		
	}
	
}
