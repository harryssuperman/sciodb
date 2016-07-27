package org.sciodb.utils;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

import static org.sciodb.utils.ScioDBConstants.MAX_SIZE;

/**
 * @author jesus.navarrete  (08/06/16)
 */
public class ByteUtils {

    final static private Logger logger = Logger.getLogger(ByteUtils.class);

    public static byte[] split(final byte[] source, final int from, final int to) {

        final ByteBuffer bb = ByteBuffer.wrap(source);

        byte[] result = new byte[(to - from) + 1];
        bb.position(from);

        bb.get(result).array();

        return result;
    }

    public static byte[] newArray(final int length) { // throws Exception {
        int dst;
        if (length < MAX_SIZE) {
            dst = length;
        } else {
            dst = MAX_SIZE;
            logger.warn("Maximum value exceeded, current " + length + ", allowed 16Mb.");

        }
//        else {
//            throw new Exception("Maximum value exceeded"); // TODO This is a better idea, maybe...
//        }
        return new byte[dst];

    }
}
