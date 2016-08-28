package com.github.tmatek.zhangshasha;

import java.util.Collection;
import java.util.List;

/**
 * A simple tree node holding a string as its label.
 */
public class StringTreeNode implements TreeNode {

    private List<StringTreeNode> children;

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
}
