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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSiblingCount(int siblingCount) {
        this.siblingCount = siblingCount;
    }

    /**
     * Return the {@link TreeOperation} associated with this tree transformation.
     * @return the {@link TreeOperation} associated with this tree transformation.
     */
    public TreeOperation getOperation() {
        return operation;
    }

    /**
     * Return the cost of this tree transformation.
     * @return the cost of this tree transformation
     */
    public int getCost() {
        return cost;
    }

    /**
     * The first node involved in this tree transformation.
     * @return the first node involved in this tree transformation
     */
    public TreeNode getFirstNode() {
        return firstNode;
    }

    /**
     * The second node involved in this tree transformation.
     * @return the second node involved in this tree transformation
     */
    public TreeNode getSecondNode() {
        return secondNode;
    }

    /**
     * Returns the position of tree node insertion for {@link TreeOperation#OP_INSERT_NODE} operation.
     * @return the position of tree node insertion for {@link TreeOperation#OP_INSERT_NODE} operation
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the number of siblings for {@link TreeOperation#OP_INSERT_NODE} operation, whose position is higher
     * than the position of inserted node and who should become children of the inserted node.
     * @return the number of siblings which should become children of the inserted node
     */
    public int getSiblingCount() {
        return siblingCount;
    }
}
