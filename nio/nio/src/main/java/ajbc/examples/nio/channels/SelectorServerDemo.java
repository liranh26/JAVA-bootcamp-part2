package ajbc.examples.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorServerDemo {
	
	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void main(String[] args) throws IOException {
		
		Selector selector = Selector.open();
		
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		
		serverChannel.socket().bind(new InetSocketAddress(PORT));
		serverChannel.configureBlocking(false);
		
		//writing the server channel to the selector
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);  //SelectionKey 

		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
	
		while(true) {
			selector.select(); //blocking 
//			selector.selectNow(); //Non-blocking 
			
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while(iterator.hasNext()) {
				
				SelectionKey key = iterator.next();
				if(key.isAcceptable()) {
					registerServer(selector, serverChannel);
				}
				else
					if(key.isReadable()) {
						echo(buffer, key);
					}
				//removes current event
				iterator.remove();
			}
			
		}
	}

	private static void echo(ByteBuffer buffer, SelectionKey key) throws IOException {
		//retrive the client via the key after accepting the client in registerServer
		SocketChannel client = (SocketChannel) key.channel();
		// writes from the buffer
		client.read(buffer);
		//switch
		buffer.flip();
		//sends to server
		client.write(buffer);
		buffer.clear();
	}

	private static void registerServer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
		//server channel is non-blocking waiting for client
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		//client registers to the selector -> this is how we sends a client with a key
		client.register(selector, SelectionKey.OP_READ);
	}

}
