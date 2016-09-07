package com.github.tmatek.zhangshasha;


import junit.framework.TestCase;

import java.util.Map;

public class TreeDistanceTest extends TestCase {

    public void testGetPostorderIdentifiers() {
        StringTreeNode first = StringTreeNode.fromStringRepresentation("A(B(C,D,E(F)),G)");
        Map<TreeNode, Integer> postorderIds = TreeDistance.getPostorderIdentifiers(first);
        assertEquals(6, (int) postorderIds.get(first));

        StringTreeNode second = StringTreeNode.fromStringRepresentation("A(B(C))");
        postorderIds = TreeDistance.getPostorderIdentifiers(second);
        assertEquals(2, (int) postorderIds.get(second));
    }

}
