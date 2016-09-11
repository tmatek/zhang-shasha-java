package com.github.tmatek.zhangshasha;


import java.util.Collection;
import java.util.List;

/**
 * A node belonging to a tree structure, with corresponding collection of its children, the pointer to its parent and
 * a transformation cost function for comparison with other tree nodes.
 */
public interface TreeNode {

    /**
     * Returns a collection of children of this node, each children having this node as its parent.
     * @return a collection of children of this node
     */
    Collection<? extends TreeNode> getChildren();

    /**
     * Returns the parent node of this tree node or null if this node is the root of the tree structure.
     * @return the parent node of this tree node or null if this tree node is the root of the tree
     */
    TreeNode getParent();

    /**
     * Returns the cost of transforming this tree node using operation {@code operation};
     * <br><br>
     * If {@code operation} equals {@link TreeOperation#OP_DELETE_NODE} then {@code other} is <code>null</code> and the
     * cost of removing this tree node should be returned.
     * <br><br>
     * If {@code operation} equals {@link TreeOperation#OP_INSERT_NODE} then this object represents the tree node
     * which will be inserted as a child of {@code other} tree node - the insert cost should be returned. It is possible
     * that {@code other} is <code>null</code>; in those cases, the cost of inserting a new root node should be returned.
     * <br><br>
     * If {@code operation} equals {@link TreeOperation#OP_RENAME_NODE} then the cost of renaming this tree node to
     * {@code other} tree node should be returned.
     * @param operation the type of tree operation being performed, {@see TreeOperation}
     * @param other the tree node into which this node is being transformed
     * @return the cost of transforming this tree node using specified operation
     */
    int getTransformationCost(TreeOperation operation, TreeNode other);

}
