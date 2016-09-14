package com.github.tmatek.zhangshasha;


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeDistanceTest extends TestCase {

    public void testGetPostorderIdentifiers() {
        StringTreeNode first = StringTreeNode.fromStringRepresentation("A(B(C,D,E(F)),G)");
        Map<TreeNode, Integer> postorderIds = TreeDistance.getPostorderIdentifiers(first);
        assertEquals(6, (int) postorderIds.get(first));

        StringTreeNode second = StringTreeNode.fromStringRepresentation("A(B(C))");
        postorderIds = TreeDistance.getPostorderIdentifiers(second);
        assertEquals(2, (int) postorderIds.get(second));

        StringTreeNode root = new StringTreeNode("A");
        root.addChild(new StringTreeNode("B"));
        StringTreeNode c = new StringTreeNode("C");
        root.addChild(c);
        root.addChild(new StringTreeNode("D"));
        c.addChild(new StringTreeNode("E"));
        postorderIds = TreeDistance.getPostorderIdentifiers(root);
        assertEquals(2, (int) postorderIds.get(c));
    }

    public void testLeftmostLeafDescendants() {
        StringTreeNode a = new StringTreeNode("A");
        StringTreeNode b = new StringTreeNode("B");
        StringTreeNode c = new StringTreeNode("C");
        StringTreeNode d = new StringTreeNode("D");
        StringTreeNode e = new StringTreeNode("E");
        StringTreeNode f = new StringTreeNode("F");

        a.addChild(b);
        b.addChild(c);
        a.addChild(d);
        a.addChild(e);
        e.addChild(f);

        Map<TreeNode, Integer> postorderIds = TreeDistance.getPostorderIdentifiers(a);
        TreeNode[] lmld = TreeDistance.leftmostLeafDescendants(a, postorderIds);

        assertEquals(0, (int) postorderIds.get(lmld[5]));
        assertEquals(2, (int) postorderIds.get(lmld[2]));
        assertEquals(0, (int) postorderIds.get(lmld[1]));
        assertEquals(3, (int) postorderIds.get(lmld[3]));
        assertEquals(3, (int) postorderIds.get(lmld[4]));
    }

    public void testGetKeyroots() {
        StringTreeNode a = new StringTreeNode("A");
        StringTreeNode b = new StringTreeNode("B");
        StringTreeNode c = new StringTreeNode("C");
        StringTreeNode d = new StringTreeNode("D");
        StringTreeNode e = new StringTreeNode("E");
        StringTreeNode f = new StringTreeNode("F");

        a.addChild(b);
        b.addChild(c);
        a.addChild(d);
        a.addChild(e);
        e.addChild(f);

        Map<TreeNode, Integer> postorderIds = TreeDistance.getPostorderIdentifiers(a);
        List<TreeNode> keyRoots = TreeDistance.getKeyroots(a, postorderIds);

        assertEquals(3, keyRoots.size());
        assertEquals(2, (int) postorderIds.get(keyRoots.get(0)));
        assertEquals(4, (int) postorderIds.get(keyRoots.get(1)));
        assertEquals(5, (int) postorderIds.get(keyRoots.get(2)));
    }

    private static void assertTreesMatchAfterTransformation(String a, String b) {
        StringTreeNode t1 = StringTreeNode.fromStringRepresentation(a),
                t2 = StringTreeNode.fromStringRepresentation(b);

        List<TreeTransformation> tr = TreeDistance.treeDistanceZhangShasha(t1, t2);

        t1 = (StringTreeNode) TreeDistance.transformTree(t1, tr);
        assertEquals(b, t1.toTreeString());
    }

    public void testTreeTransformation() {
        assertTreesMatchAfterTransformation("4(1,2,3)", "5(3(1,2),4)");
        assertTreesMatchAfterTransformation("5(3(1,2),4)", "4(1,2,3)");
    }

}
