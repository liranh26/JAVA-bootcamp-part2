package boards;

import java.awt.Point;
import java.util.Random;

import exceptions.OutOfBoardException;
import management.GameManager;
import submarine.Submarine;

public class BoardGame extends Board {

	private Submarine[] subs;
	private int totalSubsCells = 0;

	public BoardGame() {
		super();
		setSubsOnBoard();
	}

	private void getSubmarines() {

		subs = new Submarine[GameManager.SUBMARINES_NUMBER];
		for (int i = 0; i < subs.length; i++) {
			subs[i] = new Submarine();
		}
	}

	private void setSubsOnBoard() {
		int i = 0;
		Point randPoint;

		getSubmarines();

		do {
			randPoint = getRandomPointOnBoard();

			if (checkValidLocation(randPoint, i)) {
				insertSubToBoard(randPoint, i);
				i++;
			}

		} while (i < subs.length);
	}

	private Point getRandomPointOnBoard() {
		Point p = new Point();
		Random rad = new Random();

		p.x = rad.nextInt(BOARD_ROWS) + 1;
		p.y = rad.nextInt(BOARD_COLS) + 1;
		return p;
	}

	private boolean checkValidLocation(Point randomPoint, int subIndex) {
		int xtmp, ytmp;
		for (int i = 0; i < subs[subIndex].getSize(); i++) {
			xtmp = randomPoint.x + subs[subIndex].getXcoord(i);
			ytmp = randomPoint.y + subs[subIndex].getYcoord(i);
			
			if (xtmp >= getRows() - 1 || ytmp >= getCols() - 1 || xtmp < 1 || ytmp < 1)
				return false;
			try {
			if (isSubAround(new Point(xtmp, ytmp)))
				return false;
			}catch(OutOfBoardException e){
				System.out.println(e);
			}
		}

		return true;
	}

	private boolean isSubAround(Point point) throws OutOfBoardException{

		for (int i = -1; i <= 1; i++)
			try {
				if (board[point.x - 1][point.y + i] == 'S' || board[point.x + i][point.y - 1] == 'S'
						|| board[point.x + 1][point.y + i] == 'S' || board[point.x + i][point.y + 1] == 'S')
					return true;
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e+". You are checking an index out of board.");
			}

		return false;
	}

	private void insertSubToBoard(Point p, int subIndex) {
		int xtmp, ytmp;
		for (int i = 0; i < subs[subIndex].getSize(); i++) {
			xtmp = p.x + subs[subIndex].getXcoord(i);
			ytmp = p.y + subs[subIndex].getYcoord(i);
			board[xtmp][ytmp] = 'S';
			totalSubsCells++;
		}
	}

	public int getTotalSubsCells(){
		return totalSubsCells;
	}

	public char getCellValue(int x, int y) {
		return board[x][y];
	}
}
