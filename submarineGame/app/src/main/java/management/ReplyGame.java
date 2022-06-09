package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import boards.Board;
import boards.TrackerBoard;

//import player.Player;
//import reader_writer.ReaderWriterObject;

/**
 * @author Liran Hadad
 */
public class ReplyGame {

	private String playerInfo = "";
	private String playerStats = "";
	protected TrackerBoard replayBoard;
	private int[] xPlayerGuesses;
	private int[] yPlayerGuesses;
	private char[] hitOrMissGuess;
	private int numOfGuesses;
	private final char HIT='H';

	public ReplyGame() {
		replayBoard = new TrackerBoard();
		getPlayerInfo();
		xPlayerGuesses = new int[Board.BOARD_ROWS * Board.BOARD_COLS];
		yPlayerGuesses = new int[Board.BOARD_ROWS * Board.BOARD_COLS];
		hitOrMissGuess = new char[Board.BOARD_ROWS * Board.BOARD_COLS];
		getGuessesFromFile();
	}

	public void startReplay() {
		System.out.println();
		System.out.println("-----------------------");
		System.out.println(playerInfo);
		System.out.println(playerStats);
		
		for (int i = 0; i < numOfGuesses; i++) {
			if(hitOrMissGuess[i] == HIT)
				replayBoard.markHit(xPlayerGuesses[i], yPlayerGuesses[i]);
			else
				replayBoard.markMiss(xPlayerGuesses[i], yPlayerGuesses[i]);

			replayBoard.printBoard();
			 try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				System.out.println(e + ", Problem delaying the print!");
			} 
		}
		System.out.println(playerInfo);
		System.out.println(playerStats);
	
	}

	private void getPlayerInfo() {
		File file = new File("myFile/guesses.txt");
		try (Scanner sc = new Scanner(file)) {
			playerInfo = sc.nextLine();
			playerStats = sc.nextLine();
		} catch (FileNotFoundException e) {
			System.out.println(e + ", File not found check the path!");

		}
	}

	private void getGuessesFromFile() {
		int index = 0;
		File file = new File("myFile/guesses.txt");
		try (Scanner sc = new Scanner(file)) {
			// skip the player info
			sc.nextLine();
			sc.nextLine();
			// pull the guesses from the file
			while (sc.hasNext()) {
				xPlayerGuesses[index] = Integer.parseInt(sc.next());
				yPlayerGuesses[index] = Integer.parseInt(sc.next());
				hitOrMissGuess[index] = sc.next().charAt(0);
				index++;
			}
			
			setNumOfGuesses(index);
			
		} catch (FileNotFoundException e) {
			System.out.println(e + ", File not found check the path!");

		}
	}

	private void setNumOfGuesses(int numOfGuesses) {
		this.numOfGuesses = numOfGuesses;
	}
	
	

}
