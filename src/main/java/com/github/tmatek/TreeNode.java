package com.github.tmatek;


import java.util.Collection;

/**
 * A node belonging to a tree structure, with corresponding collection of its children, the pointer to its parent and
 * a comparison function with other tree nodes.
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

}
