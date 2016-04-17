package org.sciodb.server;

import org.apache.log4j.Logger;
import org.sciodb.server.services.Dispatcher;
import org.sciodb.utils.CommandEncoder;
import org.sciodb.utils.models.Command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jesus.navarrete  (07/04/16)
 */
public class SocketsThreadPool {

    final static private Logger logger = Logger.getLogger(SocketsThreadPool.class);

    private ExecutorService service;
    private Dispatcher dispatcher;
    private static SocketsThreadPool instance;

    private SocketsThreadPool() {
        this.service = Executors.newFixedThreadPool(10);
        this.dispatcher = new Dispatcher();
    }

    public static SocketsThreadPool getInstance() {
        if (instance == null) {
            instance = new SocketsThreadPool();
        }
        return instance;
    }

    public void run(final SelectionKey key) {

        final MessageReader message = new MessageReader(key);
        logger.debug(" -- " + message.getContent());

//        final Runnable runnable = new ConnectionHandler(key);
//        logger.error("runnable hashcode -- " + runnable.hashCode());
//        service.execute(runnable);

//        service.execute(() -> execute(key, message.getContent()) );
    }

    private void execute(SelectionKey key, String message) {
        logger.debug(" message --> " + message);
        //
        if (message != null) {
            final Command command = CommandEncoder.decode(message);

            // do it a?-synchronously
            if (command != null && command.getOperationID() != null) {
                final byte [] response = dispatcher.getService(command);
    //                        String address = (new StringBuilder(channel.socket().getInetAddress().toString() )).append(":").append(channel.socket().getPort() ).toString();
    //
    //                        channel.configureBlocking(false);
    //                        channel.register(selector, SelectionKey.OP_READ, address);
    //                        final ByteBuffer bbResponse = ByteBuffer.wrap(response);
    //                        channel.write(bbResponse);
    //                        bbResponse.rewind();
    //                        System.out.println("accepted connection from: "+address);

    //                        channel.register(selector, SelectionKey.OP_WRITE);

                if (key.isWritable() && response != null) {
                    final ByteBuffer bbResponse = ByteBuffer.wrap(response);
                    final SocketChannel channel = (SocketChannel) key.channel();

                    try {
                        channel.write(ByteBuffer.wrap((response.length + "").getBytes()));
                        channel.write(bbResponse);
                    } catch (IOException e) {
                        logger.error("Not possible to write in the socket", e);
                    }
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        service.shutdown();
    }

    class ConnectionHandler implements Runnable {
        final SelectionKey key;

        public ConnectionHandler(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            final MessageReader message = new MessageReader(key);
            logger.debug(" -- " + message.getContent());
        }
    }
}