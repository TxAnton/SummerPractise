import java.io.IOException;

public class App {

    public static BinaryTree readExpression() {
        BinaryTree tree = new BinaryTree();
        int c = 0;
        try {
            //Read root
            while ((c = System.in.read()) == ' ') ;
            if (c == ')') {
                return null;
            }
            tree.content = (char) c;
            //read left
            while ((c = System.in.read()) == ' ') ;
            if (c == ')') {
                return tree;
            } else if (c == '(') {
                tree.left = readExpression();
            } else if (c == '/') {
                tree.left = null;
            } else {
                tree.left = new BinaryTree((char) c);
            }

            //read right
            while ((c = System.in.read()) == ' ') ;
            if (c == ')') {
                return tree;
            } else if (c == '(') {
                tree.right = readExpression();
            } else if (c == '/') {
                tree.right = null;
            } else {
                tree.right = new BinaryTree((char) c);
            }

            while ((c = System.in.read()) == ' ') ;
            if (c != ')') System.out.println("Expected ')' symbol");

        } catch (IOException e) {
            System.out.println("Ошибка или Конец");
        }
        return tree;
    }

    public static BinaryTree readTree() {
        int c = 0;
        try {
            while ((c = System.in.read()) == ' ') ;
            if (c == '(') {
                return readExpression();
            } else {
                System.out.println("Ошибка или Конец");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        BinaryTree tree = readTree();
        System.out.println(tree.toString());
        System.out.println(tree.depth());
        return;

    }
}
