package ajbc.examples.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {

	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void run() throws IOException {
		nonblocking();
	}

	
	private static void nonblocking() throws IOException {
		
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.configureBlocking(false);
		serverSocket.bind(new InetSocketAddress(PORT));

		while (true) {
			//instead waiting to a client connecting code will run in non blocking
			
			SocketChannel socketChannel = serverSocket.accept();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			
			while (buffer.hasRemaining() && socketChannel.read(buffer) != -1) {
				buffer.flip();
				socketChannel.write(buffer);
				buffer.clear();
			}
			buffer.flip();
			
			while(buffer.hasRemaining()) {
				System.out.print((char)buffer.get());
			}
			buffer.clear();
		}

//		serverSocket.close();

	}
	
	
	
	
	
	
	
	
	
	private static void blocking() throws IOException {

		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress(PORT));

		while (true) {
			SocketChannel socketChannel = serverSocket.accept();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			
			while (buffer.hasRemaining() && socketChannel.read(buffer) != -1) {
				buffer.flip();
				socketChannel.write(buffer);
				buffer.clear();
			}
			buffer.flip();
			
			while(buffer.hasRemaining()) {
				System.out.print((char)buffer.get());
			}
			buffer.clear();
		}

//		serverSocket.close();

	}
}
