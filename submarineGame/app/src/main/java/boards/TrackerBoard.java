package boards;

public class TrackerBoard extends Board {

	
	public TrackerBoard() {
		super();
	}

	public boolean checkValidInput(int x, int y) {
		if (x > BOARD_ROWS || x < 1 || y > BOARD_COLS|| y < 1) {
			System.out.println("Your input is not valid, Try again.");
			return false;
		}

		return true;
	}

	public void markHit(int x, int y) {
		board[x][y] = 'H';
	}
	
	public void markMiss(int x, int y) {
		board[x][y] = 'm';
	}
	
}
