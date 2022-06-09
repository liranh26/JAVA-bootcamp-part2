package ajbc.examples.nio.channels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class BasicChannel2 {

	private static final int BUFFER_SIZE = 50;

	
	public static void read() throws IOException {
		String fileName = "data/nio-data.txt";
		Path path = Paths.get(fileName);
		
		Set<StandardOpenOption> options = new HashSet<>();

		options.add(StandardOpenOption.READ);
		
		// access a file
		try (RandomAccessFile file = new RandomAccessFile(fileName, "rw");
				FileChannel fileChannel = FileChannel.open(path, options)) {
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

			int bytesRead = fileChannel.read(buffer);
			
			
			String txt = "";
 			while(bytesRead != -1) {
				buffer.flip(); // switch from reading to writing from buffer
				
				while (buffer.hasRemaining()) {
					char ch = (char) buffer.get();
					txt += ch;	
				}
//				System.out.print(txt);
				
	
				
				buffer.clear(); // end of the switch
				bytesRead = fileChannel.read(buffer);
			}
			
			String[] data = txt.split("\\r?\\n");
			
			for(String str:data) {
				System.out.println(str);
			}
			
		}

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
	
			int bytesRead = fileChannel.write(buffer);
		}
	}
	
	public static void write(String input) throws IOException {
		String fileName = "data/nio-data.txt";
		Path path = Paths.get(fileName);
		
		resetFile(path);
		
		Set<StandardOpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);
//		options.add(StandardOpenOption.READ);
		
		// access a file
		try (RandomAccessFile file = new RandomAccessFile(fileName, "rw");
				FileChannel fileChannel = FileChannel.open(path, options)) {
			ByteBuffer buffer = ByteBuffer.allocate(input.length());

			buffer.put(input.getBytes());

//			int bytesRead = fileChannel.read(buffer);
//			
//			while(bytesRead != -1) {
//				bytesRead = fileChannel.read(buffer);
//			}
			
			buffer.flip();
			
		
			
			int bytesRead = fileChannel.write(buffer);
			
//			while(bytesRead != -1) {
//
//				bytesRead = fileChannel.write(buffer);
//				
//				buffer.clear(); // end of the switch
//				bytesRead = fileChannel.read(buffer);
//			}
//			writeFileChannel(buffer, Paths.get(fileName));

//			buffer.clear();
		}

	}

	public static void writeFileChannel(ByteBuffer byteBuffer, Path path) throws IOException {
		Set<StandardOpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);
//		Path path = Paths.get("C:/Test/temp.txt");
		FileChannel fileChannel = FileChannel.open(path, options);
		fileChannel.write(byteBuffer);
		fileChannel.close();
	}

	public static void run() throws IOException {
		String fileName = "data/nio-data.txt";
		// access a file
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");

		// get file's channel
		FileChannel fileChannel = file.getChannel();

		// create a buffer to hold and manipulate the file data
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

		// read text from file channel into the buffer
		int bytesRead = 0; // number of bytes we read to the channel
		bytesRead = fileChannel.read(buffer);

		while (bytesRead != -1) {

			buffer.flip(); // switch from reading to writing from buffer
			while (buffer.hasRemaining())
				System.out.print((char) buffer.get());
			buffer.clear(); // end of the switch
			bytesRead = fileChannel.read(buffer);

//			System.out.print( "<->");
		}
	}

}
