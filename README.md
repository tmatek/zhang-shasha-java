## Zhang-Shasha tree distance algorithm
A Java implementation of Zhang-Shasha algorithm for *ordered* tree distance calculation.
Calculates a series of transformations required to transform one tree into another. Every
transformation has an associated cost. The sum of costs of all transformations is minimal - the tree distance.

For further information see paper by K. Zhang et al.:
http://grantjenks.com/wiki/_media/ideas/simple_fast_algorithms_for_the_editing_distance_between_tree_and_related_problems.pdf

Or the lecture slides by Nikolaus Augsten:
http://www.inf.unibz.it/dis/teaching/ATA/ata6-handout-1x1.pdf
http://www.inf.unibz.it/dis/teaching/ATA/ata7-handout-1x1.pdf



### Usage

The usage depends on the desired result. If you only want to retrieve the tree distance
between two trees, then your tree node object should implement `TreeNode` interface.
For example:

```Java
class MyTreeNode implements TreeNode {

    private String label;
    
    private List<MyTreeNode> children;
    
    private MyTreeNode parent;
    
    public MyTreeNode(String label) {
        this.label = label;
    }
    
    @Override
    public List<? extends TreeNode> getChildren() {
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
                return this.label.equals(((MyTreeNode) other).label) ? 0 : 1;
        }
    }

}
```
Afterwards a call to

```Java
TreeNode t1 = ...
TreeNode t2 = ...
int dist = TreeDistance.treeDistanceZhangShasha(t1, t2);
```

will return the tree distance between ```t1``` and ```t2```.

If you want to actually transform one tree into another, then your tree node object
should implement the ```EditableTreeNode``` interface:

```Java
class MyTreeNode implements EditableTreeNode {

    private String label;
    
    private List<MyTreeNode> children;
    
    private MyTreeNode parent;

    ...
    
    @Override
    public TreeNode cloneNode() {
        return new MyTreeNode(this.label);
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
    public void setParent(TreeNode newParent) {
        this.parent = (MyTreeNode) newParent;
    }    
    
    @Override
    public void addChildAt(TreeNode child, int position) {
        this.children.add(position, (MyTreeNode) child);
    }  
    
    @Override
    public void renameNodeTo(TreeNode other) {
        this.label = ((MyTreeNode) other).label;
    }    
    
    @Override
    public void deleteChild(TreeNode child) {
        this.children.remove(child);
    }    
}

```

Afterwards a list of ```TreeTransformation``` objects can be obtained and used
to transform the tree in-place:

```Java
EditableTreeNode t1 = ...
EditableTreeNode t2 = ...
List<TreeTransformation> tr = TreeDistance.treeDistanceZhangShasha(t1, t2);

t1 = TreeDistance.transformTree(t1, tr); // t1 is now equal to t2
```

Note: all tree operations are based on memory references. If your tree node object
implements ```equals()``` and/or ```hashCode()``` methods, make sure that 
```deleteChild(TreeNode child)``` method removes children by reference.