package com.github.tmatek.zhangshasha;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple tree node holding a string as its label.
 */
public class StringTreeNode implements TreeNode {

    private List<StringTreeNode> children;

    private StringTreeNode parent;

    private String label;

    @Override
    public Collection<TreeNode> getChildren() {
        return new ArrayList<>(this.children);
    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public int getTransformationCost(TreeOperation operation, TreeNode other) {
        return 0;
    }
}
