public class BinaryTree {
    public
    Character content;
    BinaryTree left;
    BinaryTree right;

    public BinaryTree(Character content) {
        this.content = content;
        left = null;
        right = null;
    }

    public BinaryTree() {
        left = null;
        right = null;
        content = null;
    }

    public BinaryTree(Character content, BinaryTree left, BinaryTree right) {
        this.content = content;
        this.left = left;
        this.right = right;
    }

    public Character getContent() {
        return content;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public BinaryTree getRight() {
        return right;
    }

    int depth() {
        if (left == null) {
            if (right == null) {
                return 1;
            } else {
                return 1 + right.depth();
            }
        } else {
            if (right == null) {
                return 1 + left.depth();
            } else {
                return 1 + Math.max(right.depth(), left.depth());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(content);
        sb.append(' ');
        sb.append(left == null ? "/" : left.toString());
        sb.append(' ');
        sb.append(right == null ? "/" : right.toString());
        sb.append(')');
        return sb.toString();
    }
}
