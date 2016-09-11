package com.github.tmatek.zhangshasha;

import java.util.*;

/**
 * Utility class for calculating the tree distance between two tree structures.
 */
public final class TreeDistance {

    private TreeDistance() {
    }

    private static class IntHolder {
        public int value;
    }

    /**
     * Assigns a unique identifier to each tree node according to the postorder traversal of the tree structure.
     * Assumes that {@code node} is the root of the tree structure. All identifiers are in the range [0, number of
     * nodes in the tree).
     *
     * @param node the node from which to identify postorder numbering.
     * @return a mapping of tree nodes to postorder sequence ids
     */
    public static ReversibleIdentityMap<TreeNode, Integer> getPostorderIdentifiers(TreeNode node) {
        ReversibleIdentityMap<TreeNode, Integer> postorderMap = new ReversibleIdentityMap<>();
        getPostorderIdentifiersRec(node, postorderMap, new IntHolder());
        return postorderMap;
    }

    /**
     * Recursively assign postorder identifiers to tree nodes.
     *
     * @param current    the current tree node being processed
     * @param map        a mapping of tree nodes to postorder sequence ids
     * @param idSequence the counter of postorder sequence ids
     */
    private static void getPostorderIdentifiersRec(TreeNode current, Map<TreeNode, Integer> map,
                                                   IntHolder idSequence) {

        for (TreeNode child : current.getChildren())
            getPostorderIdentifiersRec(child, map, idSequence);

        map.put(current, idSequence.value++);
    }

    /**
     * Returns an array of leftmost leaf descendants for every node in the tree given by <code>root</code>. The
     * indexing of the returned array follows postorder IDs given in <code>postorderIDs</code>; <code>result[0]</code>
     * returns the leftmost leaf descendant of node with postorder ID zero.
     * <br><br>
     * A leftmost leaf descendant of a node is found by following the leftmost branch from the node to a leaf.
     *
     * @param root         the root node to start the search from
     * @param postorderIDs a mapping of tree nodes to postorder IDs
     * @return a list of leftmost leaf descendants for every node
     */
    public static TreeNode[] leftmostLeafDescendants(TreeNode root, Map<TreeNode, Integer> postorderIDs) {
        TreeNode[] lmld = new TreeNode[postorderIDs.get(root) + 1];
        leftmostLeafDescendantsRec(root, lmld, new ArrayList<>(), postorderIDs);
        return lmld;
    }

    /**
     * Recursively find the leftmost leaf descendants for all nodes in the tree.
     *
     * @param current      the current node being processed
     * @param ref          the list reference in which to store leftmost descendants
     * @param chain        the current path in the tree
     * @param postorderIDs a mapping of tree nodes to postorder IDs
     */
    private static void leftmostLeafDescendantsRec(TreeNode current, TreeNode[] ref, List<TreeNode> chain,
                                                   Map<TreeNode, Integer> postorderIDs) {

        if (current.getChildren().size() == 0) {
            // leftmost descendant of a leaf is the leaf itself
            ref[postorderIDs.get(current)] = current;

            // assign the rest of nodes in the chain the same leftmost leaf descendant - this leaf
            for (TreeNode ancestor : chain)
                ref[postorderIDs.get(ancestor)] = current;

        } else {
            chain.add(current);

            int i = 0;
            for (TreeNode child : current.getChildren())
                leftmostLeafDescendantsRec(child, ref, i++ == 0 ? chain : new ArrayList<>(), postorderIDs);
        }
    }

    /**
     * Returns an ordered list of keyroot nodes in the tree. A keyroot node is a node which either has a left sibling
     * or is the root of the tree. The keyroot nodes are ordered according to their postorder IDs.
     *
     * @param root         the root node to start the search from
     * @param postorderIDs a mapping of tree nodes to postorder IDs
     * @return an ordered list of keyroot nodes, ordered according to postorder IDs
     */
    public static List<TreeNode> getKeyroots(TreeNode root, Map<TreeNode, Integer> postorderIDs) {
        List<TreeNode> keyroots = new ArrayList<>();
        keyrootsRec(root, keyroots, new ArrayList<>());
        Collections.sort(keyroots, new PostorderComparator(postorderIDs));
        return keyroots;
    }

    /**
     * A comparator which sorts {@link TreeNode} objects according to their postorder IDs
     * given by a mapping.
     */
    private static class PostorderComparator implements Comparator<TreeNode> {

        private Map<TreeNode, Integer> postorderIDs;

        public PostorderComparator(Map<TreeNode, Integer> postorderIDs) {
            this.postorderIDs = postorderIDs;
        }

        @Override
        public int compare(TreeNode t1, TreeNode t2) {
            return this.postorderIDs.get(t1) - this.postorderIDs.get(t2);
        }
    }

    /**
     * Recursively find the keyroots starting from <code>current</code>.
     *
     * @param current the current node being processed
     * @param ref     the list reference in which to store keyroot nodes
     * @param chain   the current path in the tree
     */
    private static void keyrootsRec(TreeNode current, List<TreeNode> ref, List<TreeNode> chain) {
        if (current.getChildren().size() == 0) {

            if (chain.size() > 0) {
                // the first node in the chain is the keyroot node
                ref.add(chain.get(0));
            } else
                ref.add(current);

        } else {
            chain.add(current);

            int i = 0;
            for (TreeNode child : current.getChildren())
                keyrootsRec(child, ref, i++ == 0 ? chain : new ArrayList<>());
        }

    }

    /**
     * Calculates the tree distance between tree <code>t1</code> and <code>t2</code>.
     * Returns a list of tree transformations required to transform tree <code>t1</code> to <code>t2</code>.
     * Every transformation has an associated cost. The sum of costs of all transformations is the tree distance
     * between <code>t1</code> and <code>t2</code>. The sum of costs is minimal.
     * <br><br>
     * For further information see paper by K. Zhang et al.:
     * <a href="http://grantjenks.com/wiki/_media/ideas/simple_fast_algorithms_for_the_editing_distance_between_tree_and_related_problems.pdf">Simple fast algorithms for the editing distance between trees and related problems</a>
     *
     * @param t1 the first tree structure
     * @param t2 the second tree structure
     * @return a list of tree transformations required to transform first tree into the second
     */
    public static List<TreeTransformation> treeDistanceZhangShasha(TreeNode t1, TreeNode t2) {

        // prepare postorder numbering
        ReversibleIdentityMap<TreeNode, Integer> postorder1 = getPostorderIdentifiers(t1),
                postorder2 = getPostorderIdentifiers(t2);

        // prepare leftmost leaf descendants
        TreeNode[] lmld1 = leftmostLeafDescendants(t1, postorder1),
                lmld2 = leftmostLeafDescendants(t2, postorder2);

        // prepare keyroots
        List<TreeNode> keyRoots1 = getKeyroots(t1, postorder1),
                keyRoots2 = getKeyroots(t2, postorder2);

        // prepare tree distance table and transformation list
        int[][] treeDistance = new int[postorder1.get(t1) + 1][postorder2.get(t2) + 1];
        List<TreeTransformation> transformations = new ArrayList<>();

        // calculate tree distance
        for (TreeNode keyRoot1 : keyRoots1) {
            for (TreeNode keyRoot2 : keyRoots2) {
                forestDistance(keyRoot1, keyRoot2, lmld1, lmld2, postorder1, postorder2, treeDistance);
            }
        }

        return transformations;
    }

    /**
     * A class which encodes a single transformation in the forest distance table. Used for backtracking to produce a
     * series of transformations needed to transform one tree into another.
     */
    private static class ForestTrail {

        private TreeOperation operation;

        private int cost;

        private ForestTrail nextState;

        private TreeNode first, second;

        // if either first or second tree node is a forest
        private boolean wasForest;

        /**
         * A constructor which initializes the final forest trail state - state where both trees are empty.
         */
        public ForestTrail() {
            this.cost = 0;
        }

        public ForestTrail(TreeOperation operation, TreeNode first) {
            this.operation = operation;
            this.first = first;
            this.cost = first.getTransformationCost(operation, null);
        }

        public ForestTrail(TreeOperation operation, TreeNode first, TreeNode second) {
            this.operation = operation;
            this.first = first;
            this.second = second;
            this.cost = first.getTransformationCost(operation, second);
        }

        public int getTotalCost() {
            return this.cost + (this.nextState == null ? 0 : this.nextState.cost);
        }

        public int getOperationCost() {
            return this.cost;
        }
    }

    private static ForestTrail[][] forestDistance(TreeNode keyRoot1, TreeNode keyRoot2, TreeNode[] lmld1,
                                                  TreeNode[] lmld2, ReversibleIdentityMap<TreeNode, Integer> postorder1,
                                                  ReversibleIdentityMap<TreeNode, Integer> postorder2, int[][] treeDist) {

        int kr1 = postorder1.get(keyRoot1),
                kr2 = postorder2.get(keyRoot2);

        int lm1 = postorder1.get(lmld1[kr1]),
                lm2 = postorder2.get(lmld2[kr2]);

        int bound1 = kr1 - lm1 + 2;
        int bound2 = kr2 - lm2 + 2;

        // initialize forest distance table
        ForestTrail[][] forestDistance = new ForestTrail[bound2][bound1];
        forestDistance[0][0] = new ForestTrail();

        for (int i = 1, k = lm2; i < bound2; i++, k++) {
            TreeNode t = postorder2.getInverse(k);
            TreeNode parent = postorder1.getInverse(postorder2.get(t.getParent()));

            if (i == bound2 - 1)
                forestDistance[i][0] = new ForestTrail(TreeOperation.OP_INSERT_NODE, t);
            else
                forestDistance[i][0] = new ForestTrail(TreeOperation.OP_INSERT_NODE, t, parent);

            forestDistance[i][0].nextState = forestDistance[i - 1][0];
        }


        return forestDistance;
    }

}

