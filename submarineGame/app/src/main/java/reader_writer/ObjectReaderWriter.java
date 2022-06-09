package reader_writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import player.RecordInfo;

public class ObjectReaderWriter {
	
	
	
	public static void writeMove(RecordInfo moveRecord) throws FileNotFoundException, IOException {
		
		try (FileOutputStream fileOut = new FileOutputStream("myFiles/objFile1.dat");
				// Creates an ObjectOutputStream
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

			objOut.writeObject(moveRecord);
		}

	}

	
	
}
