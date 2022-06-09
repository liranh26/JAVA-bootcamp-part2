package reader_writer;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

import java.io.FileWriter;
import java.io.IOException;

import boards.Guess;
import player.Player;

/**
 * @author Liran Hadad
 */
public class FileReaderWriter {

	public static void writePlayerToFile(Player player) {
		try (FileWriter output = new FileWriter("myFile/guesses.txt")) {
			String str = player.toString() + "\n";
			output.write(str);

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void writeGuessesToFile(Guess[] guesses, int index, char[] hitOrMiss) {
		try (FileWriter output = new FileWriter("myFile/guesses.txt", true)) {

			for (int i = 0; i < index; i++) {
				String x = Integer.toString(guesses[i].getxCord()) + " ";
				output.write(x);
				String y = Integer.toString(guesses[i].getyCord()) + " ";
				output.write(y);
				output.append(hitOrMiss[i]);
				output.write('\n');
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void writeStatsToFile(int points, int misses, int hits) {
		try (FileWriter output = new FileWriter("myFile/guesses.txt", true)) {

			String totalPoints = "Your points: " + Integer.toString(points) + ", " ;
			output.write(totalPoints);

			String totalMisses = "Your misses: " + Integer.toString(misses) + ", ";
			output.write(totalMisses);

			String totalHits = "Your hits: " + Integer.toString(hits) + ".\n";
			output.write(totalHits);

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	

}
