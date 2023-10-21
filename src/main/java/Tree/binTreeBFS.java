package Tree;

import Utils.TreeUtils;
import leetCode.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class binTreeBFS {
    public static List<Integer> list = new ArrayList<>();

    /* 层序遍历 */
    public static List<Integer> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode parent = queue.poll();
            list.add(parent.val);
            if (parent.left != null) {
                queue.offer(parent.left);
            }
            if (parent.right != null) {
                queue.offer(parent.right);
            }
        }
        return list;
    }

    /* 前序遍历 */
    static void preOrder(TreeNode root) {
        if(root==null) return;
        list.add(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    /* 中序遍历 */
    static void midOrder(TreeNode root) {
        if(root==null) return;
        midOrder(root.left);
        list.add(root.val);
        midOrder(root.right);
    }

    static void afterOrder(TreeNode root) {
        if(root==null) return;
        afterOrder(root.left);
        afterOrder(root.right);
        list.add(root.val);
    }

    public static void main(String[] args) {
        TreeNode treeNode = TreeUtils.intArrayToTreeNode(new Integer[]{1, 2, 3, 4, 7, 6, 8});
        afterOrder(treeNode);
        list.forEach(System.out::println);
    }
}
