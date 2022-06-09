package ajbc.examples.nio.channels;

import java.io.IOException;
import java.nio.CharBuffer;

public class BufferDemo {

	private static final int BUFFER_SIZE = 50;

	public static void run() throws IOException {

		// create a buffer using allocate
		CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);

		// write directly to buffer
		buffer.put('J');
		buffer.put('A');
		buffer.put('V');
		buffer.put('A');

		// append is like a put (adds last)
		buffer.append(" is the best!!!");

		// change a char in the buffer, 'J' -> 'N'
//		buffer.put(0, 'N');

		// to see the change we need to change buffer to read (indexes position)
		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();
		// now the position is at the same location as limit.

		// to read again we use rewind
		buffer.rewind();
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

		buffer.rewind();
		// simulate reading part of the buffer and then continue writing after the part
		// wasnt read
		buffer.get(); // --> 'J'
		buffer.get(); // --> 'A'
		buffer.get(); // --> 'V'
		buffer.get(); // --> 'A'

		// we just read 4 chars - to delete them lets use compact
		buffer.compact();

		buffer.put(" And continue.....");

		// to read back we need to flip
		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}

		// clear sets position at zero and loses the data
		buffer.clear();
		System.out.println();

		// use mark() to hold an index in the buffer
		buffer.put("This is a new text.");
		buffer.flip();
		int counter = 0;
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
			if (counter == 4)
				buffer.mark();
			counter++;
		}
		System.out.println();
		
		// sets position to the mark position
		buffer.reset();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		
		
	}

}
