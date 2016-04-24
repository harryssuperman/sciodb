package org.sciodb.messages;

/**
 * @author jenaiz on 23/04/16.
 */
public class Header implements Message {

    private int id;
    private int length;

    private int operationId;

    public static void main(String[] args) {
        final Header h = new Header(123, 100, 2);

        byte[] headerBytes = h.encode();

        final Header h2 = new Header();
        h2.decode(headerBytes);

        System.out.println(" h2 - id -" + h2.getId());
        System.out.println(" h2 - length -" + h2.getLength());
        System.out.println(" h2 - op. id -" + h2.getOperationId());
    }

    public Header() {
    }

//    public Header(final byte[] input) {
//        final Decoder decoder = new Decoder(input);
//        this.id = decoder.outInt();
//        this.length = decoder.outInt();
//        this.operationId = decoder.outInt();
//    }

    public Header(int id, int length, int operationId) {
        this.id = id;
        this.length = length;
        this.operationId = operationId;
    }

    @Override
    public byte[] encode() {
        final Encoder encoder = new Encoder();

        encoder.in(id);
        encoder.in(length);
        encoder.in(operationId);

        return encoder.container();
    }

    @Override
    public void decode(byte[] input) {
        final Decoder decoder = new Decoder(input);
        this.id = decoder.outInt();
        this.length = decoder.outInt();
        this.operationId = decoder.outInt();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }
}