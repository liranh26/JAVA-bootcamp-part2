package ajbc.examples.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncFileDemo {

	private static final int NUM_BYTES = 1024;

	public static void run() throws IOException, InterruptedException, ExecutionException {

		
		/******* reading using CompletionHandler ********/
		Path path = Paths.get("data", "test-write.txt");
		
		if(!Files.exists(path))
			Files.createFile(path);
		
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
	
		ByteBuffer buffer = ByteBuffer.allocate(NUM_BYTES);
		
		String text = new String("He hath abandoned his physicians, madam; under whose\r\n"
				+ "practises he hath persecuted time with hope, and\r\n"
				+ "finds no other advantage in the process but only the\r\n"
				+ "losing of hope by time.\r\n"
				+ "COUNTESS");
		
		//fills the buffer
		buffer.put(text.getBytes());

		//read from the buffer
		buffer.flip();
		
		int startLocation = 0;
		Double attachment = new Double(4);
		
		CompletionHandler<Integer, Double> completionHandler = new CompletionHandler<Integer, Double>() {

			@Override
			public void completed(Integer result, Double attachment) {
				System.out.println("Result is: "+result);
				System.out.println("Attachment is: "+attachment);
			}

			@Override
			public void failed(Throwable exc, Double attachment) {
				System.out.println(exc.getMessage() + "Attachment is: "+attachment);
				
			}
		};
		
		fileChannel.write(buffer, startLocation, attachment, completionHandler);
		// ...here more code can run during the writing of the file

		Thread.sleep(1000);
		
		System.out.println("mean while... ");
		
		
		
		
		
//		/******* reading using Future ********/
//		Path path = Paths.get("data", "shakespeare.txt");
//		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
//	
//		ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
//		
//		long start = System.currentTimeMillis();
//		Future<Integer> numBytesRead = fileChannel.read(buffer, 0);
////		while(!numBytesRead.isDone()) {     //--> blocking
////			System.out.println("Reading....");
////		}
//		
//		// here we can do somthing else while the file not finished to read
//		
//		numBytesRead.get();
//		long end = System.currentTimeMillis();
//		
//		System.out.println("Reading took %d [ms]".formatted(end - start));
//		
//		buffer.flip();
//		byte[] data =new byte[buffer.limit()];
//		buffer.get(data);
////		System.out.println(new String(data));
//		buffer.clear();
//		
//		
//		System.out.println("something");
//		
//		fileChannel.close();
	}
}
