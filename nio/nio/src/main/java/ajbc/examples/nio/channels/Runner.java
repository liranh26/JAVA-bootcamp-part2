package ajbc.examples.nio.channels;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//		BasicChannel1.run();
//		BasicChannel2.run();
//		BasicChannel2.write("bla bla add this!!!");
//		BasicChannel2.read();
//		BufferDemo.run();
//		PathDemo.run();
//		AsyncFileDemo.run();
		ServerSocketChannelDemo.run();
	
	}

}
