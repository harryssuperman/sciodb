package org.sciodb.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jesus.navarrete  (08/06/16)
 */
public class ByteUtilsTest {

    @Test
    public void split() throws Exception {
        byte[] i = new byte[]{100, 101, 102};

        assertEquals(2, ByteUtils.split(i, 1, 2).length);
        assertTrue(Arrays.equals(new byte[]{101, 102}, ByteUtils.split(i, 1, 2)));
    }
}