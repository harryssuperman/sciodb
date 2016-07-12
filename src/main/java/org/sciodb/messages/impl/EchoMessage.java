package org.sciodb.messages.impl;

import org.sciodb.messages.Decoder;
import org.sciodb.messages.Encoder;
import org.sciodb.messages.Message;
import org.sciodb.messages.Operations;

/**
 * @author jenaiz on 23/04/16.
 */
public class EchoMessage implements Message {

    private Header header;

    private String msg;

    public EchoMessage() {
        header = new Header();
        header.setOperationId(Operations.ECHO.getValue());
    }

    public Header getHeader() {
        return header;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public byte[] encode() {
        final Encoder encoder = new Encoder();

        encoder.in(header.encode());
        encoder.in(msg);

        return encoder.container();
    }

    @Override
    public void decode(byte[] input) {
        final Decoder d = new Decoder(input);

        header.decode(d.getByteArray());
        msg = new String(d.getByteArray());
    }

}
