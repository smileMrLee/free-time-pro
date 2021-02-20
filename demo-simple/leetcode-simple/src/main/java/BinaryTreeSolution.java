import java.util.Objects;

/**
 * @author lee
 * 给定一个二叉搜索树(BST)，找到树中第 K 小的节点。
 *
 * 说明：
 *
 * 如果按照中序遍历的顺序遍历一棵二叉搜索树，遍历序列的数值是递增排序的。上图中的二叉搜索树的中序遍历序列为{2,3,4,5,6,7,8}，因此，只需要用中序遍历算法遍历一棵二叉搜索树，就很容易找出它的第k大结点。
 */
public class BinaryTreeSolution {
    class BinaryTreeNode{
        int val;
        BinaryTreeNode left;
        BinaryTreeNode right;
        public BinaryTreeNode(int val) {
            this.val = val;
        }
        
    }
    public static void main(String[] args) {
        BinaryTreeSolution demo = new BinaryTreeSolution();
        BinaryTreeNode root = demo.new BinaryTreeNode(5);
        BinaryTreeNode a = demo.new BinaryTreeNode(3);
        BinaryTreeNode b = demo.new BinaryTreeNode(7);
        BinaryTreeNode c = demo.new BinaryTreeNode(2);
        BinaryTreeNode d = demo.new BinaryTreeNode(4);
        BinaryTreeNode e = demo.new BinaryTreeNode(6);
        BinaryTreeNode f = demo.new BinaryTreeNode(8);
        
        root.left = a;
        root.right = b;
        a.left = c;
        a.right = d;
        b.left = e;
        b.right = f;

        // 中序遍历
        BinaryTreeNode k = midOrderKthNode(root,3);
        System.out.println(k.val);
        // 前序遍历
        index =0;
        k = preOrder(root, 3);
        System.out.println(k.val);
        // 后序遍历
        index = 0;
        k = afterOrder(root, 3);
        System.out.println(k.val);
    }
    
    static int index=0;
    
    static BinaryTreeNode midOrderKthNode(BinaryTreeNode pRoot,int k) {
        if(pRoot==null || k<=0){
            return null;
        }
        return midOrder(pRoot,k);
    }
    
    private static BinaryTreeNode midOrder(BinaryTreeNode pRoot,int k) {
        BinaryTreeNode KthNode = null;
        //如果该节点有左孩子，就一直递归到左叶子节点
        if(pRoot.left != null) {
            // 5
            KthNode = midOrder(pRoot.left, k);
        }
        if(KthNode==null) {
            index++;  //中序遍历的计数
            if(k==index)
                KthNode = pRoot; 
        }
        
        if(KthNode==null && pRoot.right!=null)
            //如果该节点有右孩子，就递归到右孩子
            KthNode = midOrder(pRoot.right,k);
        
        return KthNode;
        
    }

    private static BinaryTreeNode preOrder(BinaryTreeNode root, int k){
        System.out.println(root.val);
        BinaryTreeNode KthNode = null;
        index++;  //中序遍历的计数
        if(k==index){
            KthNode = root;
        }
        if (Objects.nonNull(KthNode)){
            return KthNode;
        }
        if (Objects.nonNull(root.left)){
            KthNode = preOrder(root.left, k);
        }
        if (KthNode==null && Objects.nonNull(root.right)){
            KthNode = preOrder(root.right, k);
        }
        return KthNode;
    }

    private static BinaryTreeNode afterOrder(BinaryTreeNode root, int k){
        BinaryTreeNode KthNode = null;
        if (root == null){
            return null;
        }
        if (Objects.nonNull(root.left)){
            KthNode = afterOrder(root.left, k);
        }
        if (KthNode==null && Objects.nonNull(root.right)){
            KthNode = afterOrder(root.right, k);
        }
        System.out.println(root.val);
        index++;
        if(k==index){
            KthNode = root;
        }
        return KthNode;
    }
}