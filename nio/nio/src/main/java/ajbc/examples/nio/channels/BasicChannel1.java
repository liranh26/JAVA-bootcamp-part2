package ajbc.examples.nio.channels;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;

public class BasicChannel1 {

	public static void run() throws IOException {

		// working with files with file channel
		String fileName = "data/nio-data.txt";

		try(FileInputStream fileInputStream = new FileInputStream(fileName);
				FileChannel fileChannel = fileInputStream.getChannel();
				WritableByteChannel outChannel = Channels.newChannel(System.out);
				){
		
			MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
			while(buffer.hasRemaining()) {
				outChannel.write(buffer); //write to the channel what is in the buffer
			}
			
		}
		
		/*** read from fileChannel to a buffer ***/

//		// channel can take a stream (it wraps a steam), still blocking
//		FileInputStream fileInputStream = new FileInputStream(fileName);
//
//		// getChannel builds new channel and warps it with channel
//		FileChannel fileChannel = fileInputStream.getChannel();
//
//		// we need a buffer to write & read the data from
//		// TODO read about this map buffer
//		// mode - , position - from where start the reading , size -
//		MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());

		
		
		
//		String text = "";
//		while (buffer.hasRemaining()) {
////			char ch = buffer.getChar();  // not working because we read 2 bytes 
//			char ch = (char) buffer.get();
//
//			text += ch;
//		}
//
//		System.out.println(text);

	
	}

}
