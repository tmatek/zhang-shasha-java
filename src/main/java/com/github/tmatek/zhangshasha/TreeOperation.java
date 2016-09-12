package com.github.tmatek.zhangshasha;

/**
 * Possible tree operations which transform one tree to another
 * <li>{@link #OP_INSERT_NODE}</li>
 * <li>{@link #OP_NESTED_INSERT_NODE}</li>
 * <li>{@link #OP_DELETE_NODE}</li>
 * <li>{@link #OP_RENAME_NODE}</li>
 */
public enum TreeOperation {

    /**
     * Insert a new node into the tree structure at specified position.
     */
    OP_INSERT_NODE,

    /**
     * Insert a new node into the tree structure at specified position p; make all siblings
     * whose position is higher than p, children of the inserted node.
     */
    OP_NESTED_INSERT_NODE,

    /**
     * Delete an existing node from the tree structure, promoting its children one level up.
     */
    OP_DELETE_NODE,

    /**
     * Rename an existing node in the tree structure to a new label.
     */
    OP_RENAME_NODE

}
