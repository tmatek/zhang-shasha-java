package com.github.tmatek.zhangshasha;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
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
     * @param node the node from which to identify postorder numbering.
     * @return a mapping of tree nodes to postorder sequence ids
     */
    public static Map<TreeNode, Integer> getPostorderIdentifiers(TreeNode node) {
        Map<TreeNode, Integer> postorderMap = new IdentityHashMap<>();
        getPostorderIdentifiersRec(node, postorderMap, new IntHolder());
        return postorderMap;
    }

    /**
     * Recursively assign postorder identifiers to tree nodes.
     * @param current the current tree node being processed
     * @param map a mapping of tree nodes to postorder sequence ids
     * @param idSequence the counter of postorder sequence ids
     */
    private static void getPostorderIdentifiersRec(TreeNode current, Map<TreeNode, Integer> map,
                                                   IntHolder idSequence) {

        for (TreeNode child : current.getChildren())
            getPostorderIdentifiersRec(child, map, idSequence);

        map.put(current, idSequence.value++);
    }

    /**
     * Returns a list of leftmost leaf descendants (using postorder IDs) for every postorder ID node.
     * @param root the root node to start the search from
     * @param postorderIDs a mapping of tree nodes to postorder IDs
     * @return a list of leftmost leaf descendants for every node
     */
    public static int[] leftmostLeafDescendants(TreeNode root, Map<TreeNode, Integer> postorderIDs) {
        int[] lmld = new int[postorderIDs.get(root) + 1];
        leftmostLeafDescendantsRec(root, lmld, new ArrayList<>(), postorderIDs);
        return lmld;
    }

    /**
     * Recursively find the leftmost leaf descendants for all nodes in the tree.
     * @param current the current node being processed
     * @param ref the list reference in which to store leftmost descendants
     * @param chain the current path in the tree
     * @param postorderIDs a mapping of tree nodes to postorder IDs
     */
    private static void leftmostLeafDescendantsRec(TreeNode current, int[] ref, List<TreeNode> chain,
                                                   Map<TreeNode, Integer> postorderIDs) {


        if (current.getChildren().size() == 0) {
            // leftmost descendant of a leaf is the leaf itself
            ref[postorderIDs.get(current)] = postorderIDs.get(current);

            // assign the rest of nodes in the chain the same leftmost leaf descendant - this leaf
            for (TreeNode ancestor : chain)
                ref[postorderIDs.get(ancestor)] = postorderIDs.get(current);

        } else {
            chain.add(current);

            int i = 0;
            for (TreeNode child : current.getChildren())
                leftmostLeafDescendantsRec(child, ref, i++ == 0 ? chain : new ArrayList<>(), postorderIDs);
        }
    }



}
