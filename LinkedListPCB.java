import java.util.LinkedList;

public class LinkedListPCB {

    private int parent;
    private LinkedList<Integer> children;

    public LinkedListPCB() {
        this.parent = -1;
        this.children = new LinkedList<>();
    }

    public LinkedListPCB(int p) {
        this.parent = p;
        this.children = new LinkedList<>();
    }

    public int getParent() {
        return this.parent;
    }

    public void setParent(int p) {
        this.parent = p;
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public int getChild(int i) throws IndexOutOfBoundsException {
        if (children.isEmpty()) return -1;

        return this.children.get(i);
    }

    public int getYoungestChild() {
        if (children.isEmpty()) return -1;

        return this.children.getLast();
    }

    public void setChild(int listIndex, int indexOfChild) {
        this.children.add(listIndex, indexOfChild);
    }

    public int removeYoungest() {
        return this.children.removeLast();
    }
}
