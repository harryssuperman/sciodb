package org.sciodb.messages;

/**
 * @author jenaiz on 23/04/16.
 */
public enum Operations {
    ECHO(1), STATUS(2),
    MASTER_SLAVE_TOPOLOGY(20), MASTER_SLAVE_TOPOLOGY_STATUS(21),
    CHECK_NODE_STATUS(30), ADD_SLAVE_NODE(31)
    ;

    private final int value;

    Operations(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public class STATUS {
    }
}
