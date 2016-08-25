package com.github.tmatek.zhangshasha;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Utility class for calculating the tree distance between two tree structures.
 */
public final class TreeDistance {

    private TreeDistance() {}

    private static class IntHolder {
        public int value;
    }

    /**
     * Assigns a unique identifier to each tree node according to the postorder traversal of the tree structure.
     * Assumes that {@code node} is the root of the tree structure. All identifiers are in the range [0, number of
     * nodes in the tree).
     * @param node - the node from which to identify postorder numbering.
     * @return - a mapping of tree nodes to postorder sequence ids
     */
    public static Map<TreeNode, Integer> getPostorderIdentifiers(TreeNode node) {
        Map<TreeNode, Integer> postorderMap = new IdentityHashMap<>();
        getPostorderIdentifiersRec(node, postorderMap, new IntHolder());
        return postorderMap;
    }

    /**
     * Recursively assign postorder identifiers to tree nodes.
     * @param current - the current tree node being processed
     * @param map - a mapping of tree nodes to postorder sequence ids
     * @param idSequence - the counter of postorder sequence ids
     */
    private static void getPostorderIdentifiersRec(TreeNode current, Map<TreeNode, Integer> map,
                                                   IntHolder idSequence) {

        for (TreeNode child : current.getChildren())
            getPostorderIdentifiersRec(child, map, idSequence);

        map.put(current, idSequence.value++);
    }

}
