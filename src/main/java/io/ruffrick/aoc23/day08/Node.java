package io.ruffrick.aoc23.day08;

public class Node {
    private final String label;
    private Node left;
    private Node right;
    private boolean end;

    public Node(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
