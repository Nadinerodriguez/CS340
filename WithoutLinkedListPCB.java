public class WithoutLinkedListPCB {

    private int parent;
    private int first_child;
    private int younger_sibling;
    private int older_sibling;

    public WithoutLinkedListPCB() {
        this.parent = -1;
        this.first_child = -1;
        this.younger_sibling = -1;
        this.older_sibling = -1;
    }

    public WithoutLinkedListPCB(int p) {
        this.parent = p;
        this.first_child = -1;
        this.younger_sibling = -1;
        this.older_sibling = -1;
    }

    public int getParent() {
        return this.parent;
    }

    public void setParent(int p) {
        this.parent = p;
    }

    public int getFirst() {
        return this.first_child;
    }

    public void setFirst(int f) {
        this.first_child = f;
    }

    public int getYounger() {
        return this.younger_sibling;
    }

    public void setYounger(int y) {
        this.younger_sibling = y;
    }

    public int getOlder() {
        return this.older_sibling;
    }

    public void setOlder(int o) {
        this.older_sibling = o;
    }
}
