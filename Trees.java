import java.util.Arrays;

class Node {
    int key;
    int height;
    Node left, right;

    public Node(int data) {
        key = data;
        left = right = null;
    }
}

public class Trees {

    Node root;

    public Trees() {
        root = null;
    }

    public void add_to_bst(int keys[]) {
        for (int key : keys) {
            root = insert(root, key);
        }
    }

    public Node insert(Node node, int val) {
        if (node == null) {
            node = new Node(val);
            return node;
        }
        if (node.key > val) {
            node.left = insert(node.left, val);
        } else {
            node.right = insert(node.right, val);
        }
        return node;
    }

    public void pre_order(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.key + " ");
        pre_order(node.left);
        pre_order(node.right);
    }

    public void pre_order_by_key(Node node, int key) {
        if (key < node.key) {
            pre_order_by_key(node.left, key);
        } else if (key > node.key) {
            pre_order_by_key(node.right, key);
        } else {
            pre_order(node);
        }
    }

    public void print_in_order(Node node) {
        if (node == null) {
            return;
        }
        print_in_order(node.left);
        System.out.print(node.key + " ");
        print_in_order(node.right);
    }

    int j = 0;

    public void in_order(Node node, int[] arr) {
        if (node == null) {
            return;
        }
        in_order(node.left, arr);
        arr[j++] = node.key;
        in_order(node.right, arr);
    }

    int i = 0;

    public void post_order(Node node, int[] arr) {
        if (node == null) {
            return;
        }
        post_order(node.left, arr);
        post_order(node.right, arr);
        arr[i++] = node.key;
    }

    public void maxBST(Node node) {
        if (node.right == null) {
            System.out.print(node.key);
        } else {
            System.out.print(node.key + "->");
            maxBST(node.right);
        }
    }

    public void minBST(Node node) {
        if (node.left == null) {
            System.out.print(node.key);
        } else {
            System.out.print(node.key + "->");
            minBST(node.left);
        }
    }

    private int minKeyBST(Node node) {
        int min = node.key;
        while (node.left != null) {
            min = node.left.key;
            node = node.left;
        }
        return min;
    }

    public void find_min(Node node) {
        int min = node.key;
        while (node.left != null) {
            min = node.left.key;
            node = node.left;
        }
        System.out.println(min);
    }

    public void delete(Node node, int keys[]) {
        for (int key : keys) {
            removeLeaf(node, key);
        }
    }

    public Node removeLeaf(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = removeLeaf(node.left, key);
        } else if (key > node.key) {
            node.right = removeLeaf(node.right, key);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                node.key = minKeyBST(node.right);
                node.right = removeLeaf(node.right, node.key);
            }
        }
        return node;
    }

    public Node deleteAll(Node node) {
        if (node == null) {
            return null;
        }
        node.left = deleteAll(node.left);
        node.right = deleteAll(node.right);
        System.out.print(node.key + " ");
        removeLeaf(node, node.key);
        return null;
    }

    public void road(Node node, int key) {
        if (node == null) {
            return;
        }
        if (node.key > key && node.left != null) {
            System.out.print(node.key + "->");
            road(node.left, key);
        } else if (node.key < key && node.right != null) {
            System.out.print(node.key + "->");
            road(node.right, key);
        }
        if (node.key == key) {
            System.out.print(node.key);
            return;
        }
        if (node.left == null && node.right == null && node.key != key) {
            System.out.println("No such key exists");
        }
    }

    int to_vine(Node node) {
        int count = 0;
        var temp = node.right;
        while (temp != null) {
            if (temp.left != null) {
                var prev_temp = temp;
                temp = temp.left;
                prev_temp.left = temp.right;
                temp.right = prev_temp;
                node.right = temp;
            } else {
                ++count;
                node = temp;
                temp = temp.right;
            }
        }
        return count;
    }

    void compress(Node node, int m) {
        var temp = node.right;
        while (m-- > 0) {
            var prev_temp = temp;
            temp = temp.right;
            node.right = temp;
            prev_temp.right = temp.left;
            temp.left = prev_temp;
            node = temp;
            temp = temp.right;
        }
    }

    public Node balanceBST(Node node) {
        Node temp = new Node(0);
        temp.right = node;
        int count = to_vine(temp);
        int m = (int) Math.pow(2, (int) (Math.log(count + 1) / Math.log(2))) - 1;
        compress(temp, count - m);
        for (m = m / 2; m > 0; m /= 2)
            compress(temp, m);
        return temp.right;
    }


    public void add_to_avl(int keys[]) {
        if (keys.length > 0) {
            // Arrays.sort(keys); dla testów
            root = insert_to_avl(null, keys, 0, keys.length - 1);
        }
    }

    private Node insert_to_avl(Node node, int keys[], int from, int to) {
        if (from > to) {
            return null;
        }
        int mid = (from + to) / 2;
        node = new Node(keys[mid]);
        node.left = insert_to_avl(node.left, keys, from, mid - 1);
        node.right = insert_to_avl(node.right, keys, mid + 1, to);
        return rebalance(node);
    }

    public void delete_from_avl(int key) {
        root = remove_leaf_from_avl(root, key);
    }

    private Node remove_leaf_from_avl(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = remove_leaf_from_avl(node.left, key);
        } else if (key > node.key) {
            node.right = remove_leaf_from_avl(node.right, key);
        } else {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                node.key = minKeyBST(node.right);
                node.right = remove_leaf_from_avl(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private void update_height(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.right) - height(node.left);
    }

    private Node rotateRight(Node a) {
        Node b = a.left;
        Node c = b.right;
        b.right = a;
        a.left = c;
        update_height(a);
        update_height(b);
        return b;
    }

    private Node rotateLeft(Node a) {
        Node b = a.right;
        Node c = b.left;
        b.left = a;
        a.right = c;
        update_height(a);
        update_height(b);
        return b;
    }

    private Node rebalance(Node node) {
        update_height(node);
        int balance = balance(node);
        if (balance > 1) {
            if (height(node.right.right) > height(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        } else if (balance < -1) {
            if (height(node.left.left) > height(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }
        return node;
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

}