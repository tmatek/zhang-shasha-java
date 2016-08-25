package com.github.tmatek.zhangshasha;

/**
 * Possible tree operations which transform one tree to another
 * <li>{@link #OP_INSERT_NODE}</li>
 * <li>{@link #OP_DELETE_NODE}</li>
 * <li>{@link #OP_RENAME_NODE}</li>
 */
public enum TreeOperation {

    /**
     * Insert a new node into the tree structure.
     */
    OP_INSERT_NODE,

    /**
     * Delete an existing node from the tree structure.
     */
    OP_DELETE_NODE,

    /**
     * Rename an existing node in the tree structure to a new label.
     */
    OP_RENAME_NODE

}
