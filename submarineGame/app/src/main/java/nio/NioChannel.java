package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import boards.Board;
import boards.Guess;
import boards.TrackerBoard;

public class NioChannel {

	private static final int BUFFER_SIZE = 512;
	private static ByteBuffer buffer;
	private static Path path;

	private String playerInfo = "";
	private String playerStats = "";
	protected TrackerBoard replayBoard;
	private int[] xPlayerGuesses;
	private int[] yPlayerGuesses;
	private char[] hitOrMissGuess;
	private int numOfGuesses;
	private final char HIT = 'H';

	public NioChannel() {
		path = Paths.get("myFile", "guesses.txt");
		try {
			resetFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		replayBoard = new TrackerBoard();
		xPlayerGuesses = new int[Board.BOARD_ROWS * Board.BOARD_COLS];
		yPlayerGuesses = new int[Board.BOARD_ROWS * Board.BOARD_COLS];
		hitOrMissGuess = new char[Board.BOARD_ROWS * Board.BOARD_COLS];
		
	}

	public void writeString(String input) {

		try {
			Files.createFile(path);
		} catch (IOException e) {
			System.out.println("File already exists!");
		}

		Set<StandardOpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);
		
		try(RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
				FileChannel fileChannel = FileChannel.open(path, options);){
			ByteBuffer buffer = ByteBuffer.allocate(input.length());
			
			buffer.put(input.getBytes());
			
			buffer.flip();
			
			fileChannel.write(buffer);
			
			buffer.clear();
		} catch (IOException e) {
			System.out.println("Failed open a channel");
			e.printStackTrace();
		}

	}

	public void writePlayerGameResult(int points, int misses, int hits) {
		String playerResult = "Your points: " + Integer.toString(points) + ", ";

		playerResult += "Your misses: " + Integer.toString(misses) + ", ";

		playerResult += "Your hits: " + Integer.toString(hits) + ".\n";

		writeString(playerResult);
	}

	public void writeGuesses(Guess[] guesses, int index, char[] hitOrMiss) {

		for (int i = 0; i < index; i++) {
			String guess = new String();
			guess = Integer.toString(guesses[i].getxCord()) + " ";
			guess += Integer.toString(guesses[i].getyCord()) + " ";
			guess += hitOrMiss[i] + "\n";

			writeString(guess);
		}
	}
	
	public void startReplay() {
		
		//TODO - COMPLETE exctract data from the buffer
//		Set<StandardOpenOption> options = new HashSet<>();
//
//		options.add(StandardOpenOption.READ);
//		
//		// access a file
//		try (RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
//				FileChannel fileChannel = FileChannel.open(path, options)) {
//			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
//
//			int bytesRead = fileChannel.read(buffer);
//			
//			String txt = "";
// 			while(bytesRead != -1) {
//				buffer.flip(); // switch from reading to writing from buffer
//				
//				while (buffer.hasRemaining()) {
//					char ch = (char) buffer.get();
//					txt += ch;	
//				}
//		
//				buffer.clear(); // end of the switch
//				bytesRead = fileChannel.read(buffer);
//			}
//			
//			String[] data = txt.split("\\r?\\n");
//			
//			for(String str:data) {
//				System.out.println(str);
//			}
//			
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		System.out.println();
//		System.out.println("-----------------------");
//		System.out.println(playerInfo);
//		System.out.println(playerStats);
//		
//		for (int i = 0; i < numOfGuesses; i++) {
//			if(hitOrMissGuess[i] == HIT)
//				replayBoard.markHit(xPlayerGuesses[i], yPlayerGuesses[i]);
//			else
//				replayBoard.markMiss(xPlayerGuesses[i], yPlayerGuesses[i]);
//
//			replayBoard.printBoard();
//			 try {
//				TimeUnit.SECONDS.sleep(2);
//			} catch (InterruptedException e) {
//				System.out.println(e + ", Problem delaying the print!");
//			} 
//		}
//		System.out.println(playerInfo);
//		System.out.println(playerStats);
	
	}
	
	
	
	private static void resetFile(Path path) throws IOException {
		String delete = "";
		Set<StandardOpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.TRUNCATE_EXISTING);
		options.add(StandardOpenOption.WRITE);
		try (RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
				FileChannel fileChannel = FileChannel.open(path, options)) {
			ByteBuffer buffer = ByteBuffer.allocate(delete.length());

			buffer.put(delete.getBytes());
			
			buffer.flip();
	
			fileChannel.write(buffer);
		}
	}

}
