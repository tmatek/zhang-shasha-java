package com.github.tmatek.zhangshasha;


import java.util.Collection;

/**
 * A node belonging to a tree structure, with corresponding collection of its children, the pointer to its parent and
 * a transformation cost function for comparison with other tree nodes.
 */
public interface TreeNode {

    /**
     * Returns a collection of children of this node, each children having this node as its parent.
     * @return - a collection of children of this node
     */
    Collection<TreeNode> getChildren();

    /**
     * Returns the parent node of this tree node or null if this node is the root of the tree structure.
     * @return - the parent node of this tree node or null if this tree node is the root of the tree
     */
    TreeNode getParent();

    /**
     * Returns the cost of transforming this tree node to {@code other} tree node using operation {@code operation}.
     * @param operation - the type of tree operation being performed, {@see TreeOperation}
     * @param other - the tree node into which this node is being transformed
     * @return - the cost of transforming this tree node to another tree node using specified operation
     */
    int getTransformationCost(TreeOperation operation, TreeNode other);

}
