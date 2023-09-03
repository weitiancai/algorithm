package Utils;

import leetCode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class TreeUtils {
    public static TreeNode intArrayToTreeNode(Integer[] num){
        if (num.length == 0) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(num[0]);
        queue.add(root);
        boolean isLeft = true;
        for (int i = 1; i < num.length; i++) {
            TreeNode tree = queue.peek();
            if (isLeft) {
                if (num[i] != null) {
                    tree.left = new TreeNode(num[i]);
                    queue.offer(tree.left);
                }
                isLeft = false;
            }
            else {
                if (num[i] != null) {
                    tree.right = new TreeNode(num[i]);
                    queue.offer(tree.right);
                }
                isLeft = true;
                queue.poll();
            }
        }
        return root;
    }
}
