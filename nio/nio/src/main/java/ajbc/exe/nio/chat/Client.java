package ajbc.exe.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {
	
	private static final String STOP = "STOP";
	private static final int PORT = 9090, BUFFER_SIZE = 256;
	
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		Selector selector = Selector.open();
		SocketChannel clientSocket = SocketChannel.open();
		clientSocket.connect(new InetSocketAddress("localhost", PORT));
		clientSocket.configureBlocking(false);
		
		clientSocket.register(selector, SelectionKey.OP_WRITE);
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

		Thread read = new Thread(new readIncomeMsg(clientSocket));
		
		read.start();
		
		
		mainLoop:
		while (true) {
			
			selector.select(); // this blocks until a ready channel registers
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			
			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				if (key.isWritable()) {
					System.out.println("Say something");
					String txt = sc.nextLine();
					if(txt.equals(STOP))
						break mainLoop;
					write(buffer, txt, key);
				}

				else if (key.isReadable()) {
					read(buffer, key);
				}
				
				iter.remove();
			}
		}
		sc.close();
		clientSocket.close();
		selector.close();
		try {
			read.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void write(ByteBuffer buffer, String txt, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		buffer.clear();
		buffer.put(txt.getBytes());
		buffer.flip();
		client.write(buffer);
		buffer.clear();

		client.register(key.selector(), SelectionKey.OP_WRITE);
	}

	private static void read(ByteBuffer buffer, SelectionKey key) throws IOException {
 
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		buffer.flip();
		String txt = "";
				
		while(buffer.hasRemaining())
			txt+=(char)buffer.get();

		buffer.clear();
		System.out.println("Server replied:");
		System.out.println(txt.trim());
		client.register(key.selector(), SelectionKey.OP_WRITE);
	}

	
	static class readIncomeMsg implements Runnable {
		
		SocketChannel clientSocket;
		
		readIncomeMsg(SocketChannel clientSocket){
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			
			try {
				while(clientSocket.read(buffer) != -1 ) { // writing from channel to buffer
					if(!buffer.hasRemaining())
						break;
					buffer.flip(); 
					while(buffer.hasRemaining()) { //reading from buffer
						System.out.print((char)buffer.get()); 
					}
					buffer.clear();
					buffer.flip(); 
					System.out.println();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}

