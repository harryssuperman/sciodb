package org.sciodb.messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jenaiz on 23/04/16.
 */
public class Encoder {

    public final static int INT_BYTES = 4;
    public final static int LONG_BYTES = 8;

    private int length = 0;

    private List<ByteBuffer> container;

    public Encoder() {
        this.container = new ArrayList<>();
    }

    public void in(final long l) {
        final ByteBuffer bb = ByteBuffer.allocate(LONG_BYTES)
                                        .order(ByteOrder.BIG_ENDIAN)
                                        .putLong(l);
//        container.add(ByteBuffer.allocate(1).putInt(1)); // idea to mark the type
        length += LONG_BYTES;
        container.add(bb);
    }

    public void in(final int i) {
        final ByteBuffer bb = ByteBuffer.allocate(INT_BYTES)
                                        .order(ByteOrder.BIG_ENDIAN)
                                        .putInt(i);
        length += INT_BYTES;
        container.add(bb);
    }

    public void in(final String s) {
        final ByteBuffer bb = ByteBuffer.allocate(s.length())
                                        .order(ByteOrder.BIG_ENDIAN)
                                        .put(s.getBytes());
        in(s.length());
        length += s.length();
        container.add(bb);
    }

    public void in(final byte[] b) {
        final ByteBuffer bb = ByteBuffer.allocate(b.length)
                .order(ByteOrder.BIG_ENDIAN)
                .put(b);
        in(b.length);
        length += b.length;
        container.add(bb);
    }

    public void add(final byte[] input) {
        in(input.length);
        container.add(ByteBuffer.wrap(input));
    }

    public byte[] container() {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (container != null) {
                for (ByteBuffer bb : container) {
                    outputStream.write(bb.array());
                }
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

}
