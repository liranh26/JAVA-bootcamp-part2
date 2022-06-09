package management;

import boards.Board;
import boards.BoardGame;
import boards.Guess;
import boards.TrackerBoard;
import exceptions.OutOfTargetsException;
import nio.NioChannel;
import player.Player;
import player.RecordInfo;
import reader_writer.FileReaderWriter;
import userInput.ScannerInput;

public class GameManager {

	public static final int SUBMARINES_NUMBER = 5;

	private int points, guesses, hits, replayIndex;
	private final int startGueses = 100;
	protected BoardGame boardGame;
	protected TrackerBoard guessBoard;
	protected Guess[] gameReply;
	protected char[] hitsOrMissReply;
	protected Player player;
	private ReplyGame replay;
	private RecordInfo recordPlayerMove;
	
	private NioChannel nio;

	public GameManager() {
		points = 1000;
		guesses = startGueses;
		hits = 0;
		boardGame = new BoardGame();
		boardGame.printBoard();
		guessBoard = new TrackerBoard();
		initializeReplyArray();
		hitsOrMissReply = new char[Board.BOARD_ROWS * Board.BOARD_COLS];
		nio = new NioChannel();
	}

	public void play(){
		char hitOrMiss = 'm';
		int currentPoints, x, y;
		String input = "";
		boolean lastGuess = false;
		
		getPlayerInfo();
		
		while (guesses > 0 && points > 0 && input != "q") {
			
			try{
				isAllSubsDetected();
			}catch(OutOfTargetsException e) {
				System.out.println(e.getMessage());
				break;
			}
			
			printBoardAndScore();

			x = getCoordinate('X');
			if (x == 0)
				break;
			gameReply[replayIndex].xRecordCoord(x);
			
			y = getCoordinate('Y');
			if (y == 0)
				break;
			gameReply[replayIndex].yRecordCoord(y);
			
			
			if (!guessBoard.checkValidInput(x, y))
				continue;

			if (checkGuess(x, y)) {
				guessBoard.markHit(x, y);
				hitsOrMissReply[replayIndex] = 'H';
				currentPoints = 200;
				if (lastGuess)
					currentPoints = 1000;
				points += currentPoints;
				lastGuess = true;
				hits++;
				hitOrMiss = 'H';
			} else {
				guessBoard.markMiss(x, y);
				hitsOrMissReply[replayIndex] = 'm';
				lastGuess = false;
				points -= 10;
			}
			recordPlayerMove = new RecordInfo(player.toString(), getMisses(), points, hits, new Guess(x,y), 'm');
			guesses--;
			replayIndex++;
		}
		
//		recordMovesToFile();

		ScannerInput.closeScanner();

		System.out.println("Bye Bye!");
	}

//	private void recordMovesToFile() {
//		
//		nio.writeString(player.toString() + "\n");
//		
//		nio.writePlayerGameResult(points, getMisses(), hits);
//		
//		nio.writeGuesses(gameReply, replayIndex, hitsOrMissReply);
//		
////		FileReaderWriter.writePlayerToFile(player);
////		FileReaderWriter.writeStatsToFile(points, getMisses(), hits);
////		FileReaderWriter.writeGuessesToFile(gameReply, replayIndex, hitsOrMissReply);
//	}
	
	public void reply() {

//		NioChannel.startGame();
		replay = new ReplyGame();
		replay.startReplay();
	}

	private void printBoardAndScore() {
		guessBoard.printBoard();
		System.out.println(
				"Your score is: " + points + ", number of hits is: " + hits + ", number of misses: " + getMisses());
		System.out.println("Enter X and Y coordinate, Or press q to quit.");
	}

	public int getMisses() {
		return startGueses - guesses - hits;
	}

	protected boolean checkGuess(int x, int y) {
		if (boardGame.getCellValue(x, y) == 'S')
			return true;

		return false;
	}

	protected int getCoordinate(char coordinate) {

		System.out.println("Enter " + coordinate + " coordinate");

		int input = getValidInput();

		return input;
	}

	private int getValidInput() {
		
		boolean valid = false;
		int coordinate = 0;
		String input = "";
		
		while (!valid) {
			
			input = ScannerInput.getStringFromUser();
			if (input.equals("q"))
				return coordinate;
//			else {
//				coordinate = Integer.parseInt(input);
//				valid = true;
//			}
			else
				try {
					coordinate = Integer.parseInt(input);
					valid = true;
				} catch (NumberFormatException e) {
					System.out.println(e + ". Your input is not valid!");
				} 
		}

		return coordinate;
	}
	
	private void initializeReplyArray() {
		gameReply = new Guess[Board.BOARD_ROWS * Board.BOARD_COLS];
		replayIndex=0;
		for (int i = 0; i < gameReply.length; i++) {
			gameReply[i] = new Guess();
		}
	}
	
	private void isAllSubsDetected() throws OutOfTargetsException {
		if(hits == boardGame.getTotalSubsCells()) {
			throw new OutOfTargetsException("All subs detected! well done!");
		}
	}
	
	public void getPlayerInfo() {
		System.out.println("Enter name: ");
		String name = ScannerInput.getStringFromUser();
		System.out.println("Enter email: ");
		String email = ScannerInput.getStringFromUser();
		System.out.println("Enter phone number: ");
		String phone = ScannerInput.getStringFromUser();
		
		player = new Player(name, email, phone);
	}
	
	
}
