package com.github.tmatek.zhangshasha;

/**
 * A single tree transformation is a part of a series of tree structure edits, which transform one tree structure into
 * another. Each tree transformation is uniquely identified by the {@link TreeOperation}, the cost of the operation and
 * corresponding {@link TreeNode}s.
 */
public class TreeTransformation {

    private TreeOperation operation;

    // nodes involved in operation
    private TreeNode firstNode, secondNode;

    private int position;

    private int siblingCount;

    // the cost of performing this operation
    private int cost;

    public TreeTransformation(TreeOperation operation, int cost, TreeNode firstNode) {
        this.operation = operation;
        this.cost = cost;
        this.firstNode = firstNode;
    }

    public TreeTransformation(TreeOperation operation, int cost, TreeNode firstNode, TreeNode secondNode) {
        this.operation = operation;
        this.cost = cost;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    /**
     * Sets the position of tree node insertion for {@link TreeOperation#OP_INSERT_NODE} operation.
     * @param position - the position of tree node insertion for {@link TreeOperation#OP_INSERT_NODE} operation
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the number of siblings for {@link TreeOperation#OP_INSERT_NODE} operation, whose position is higher than
     * that of inserted node, to make children of the inserted node.
     * @param siblingCount - the number of siblings to make children of the inserted node
     */
    public void setSiblingCount(int siblingCount) {
        this.siblingCount = siblingCount;
    }

    public TreeOperation getOperation() {
        return operation;
    }

    public TreeNode getFirstNode() {
        return firstNode;
    }

    public TreeNode getSecondNode() {
        return secondNode;
    }
}
