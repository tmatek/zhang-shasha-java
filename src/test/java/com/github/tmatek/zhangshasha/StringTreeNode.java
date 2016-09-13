package com.github.tmatek.zhangshasha;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple tree node holding a string as its label.
 */
public class StringTreeNode implements TreeNode {

    private static class StringHolder {
        String str;

        public StringHolder(String str) {
            this.str = str;
        }
    }

    /**
     * Creates a string tree from a string representation of the tree. The string representation format is the
     * following: there is only one root node, the next level of the tree is denoted using brackets (, ).
     * Siblings are separated using a comma.
     * <br><br>
     * Valid examples: <code>A(B,C,D(E))</code>, <code>A(B(D(E)))</code>
     * <br><br>
     * Invalid examples: <code>A,B,C(D)</code>
     * @param tree the string used to construct the tree
     * @return a string tree from a string representation.
     */
    public static StringTreeNode fromStringRepresentation(String tree) {
        return fromStringRepresentationRec(new StringHolder(tree), null);
    }

    private static StringTreeNode fromStringRepresentationRec(StringHolder tree, StringTreeNode parent) {
        String node = "";
        while (tree.str.length() > 0) {
            char c = tree.str.charAt(0);
            tree.str = tree.str.substring(1);

            if (c == '(') {

                StringTreeNode cur = new StringTreeNode(node);
                node = "";

                fromStringRepresentationRec(tree, cur);

                if (parent == null)
                    return cur;
                else
                    parent.addChild(cur);

            } else if (c == ',' || c == ')') {
                if (!node.equals("")) {
                    StringTreeNode cur = new StringTreeNode(node);
                    node = "";
                    parent.addChild(cur);
                }

                if (c == ')')
                    break;

            } else {
                node += c;
            }
        }

        return null;
    }

    private List<StringTreeNode> children = new ArrayList<>();

    private StringTreeNode parent;

    private String label;

    public StringTreeNode(String label) {
        this.label = label;
    }

    public void addChild(StringTreeNode child) {
        this.children.add(child);
        child.parent = this;
    }

    @Override
    public Collection<? extends TreeNode> getChildren() {
        return this.children;
    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public int getTransformationCost(TreeOperation operation, TreeNode other) {
        switch (operation) {
            case OP_DELETE_NODE:
                return 1;

            case OP_INSERT_NODE:
                return 1;

            default:
                return this.label.equals(((StringTreeNode) other).label) ? 0 : 1;
        }
    }

    @Override
    public int positionOfChild(TreeNode child) {
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i) == child)
                return i;
        }

        return -1;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
