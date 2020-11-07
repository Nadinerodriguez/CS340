import static java.lang.Math.abs;
import java.util.Random;

public class PCBAssignment {
    private static LinkedListPCB[] PCB1 = new LinkedListPCB[20];
    private static WithoutLinkedListPCB[] PCB2 = new WithoutLinkedListPCB[20];
    private static int full1 = 0;
    private static int full2 = 0;
    private static int call = -1;

    public PCBAssignment() {
        System.out.println("Process Creation using a LinkedList: ");
        Random randNum = new Random();
        for (int i = 0 ; i < 15; i++) {
            int callType = abs(randNum.nextInt()) % 50;
            int randIndex = abs(randNum.nextInt()) % 20;
            if(callType >= 15) {
                System.out.println("Calling create1 method (" + randIndex + ")...");
                try {
                    create1(randIndex);
                    print(PCB1[randIndex], randIndex);
                } catch (Exception ex) {
                    System.out.println("PCB [" + randIndex + "] no more free allocated memory in PCB1[].");
                }
            }
            else {
                System.out.println("Calling destroy1 method (" + randIndex + ")...");
                destroy1(randIndex);
            }
        }
        System.out.println("\nMethod 1 LinkedList's Information: ");
        for (int i = 0; i < PCB1.length; i++) {
            print(PCB1[i], i);
        }
        System.out.println("\nProcess Creation without using a LinkedList: ");
        for (int i = 0 ; i < 15; i++) {
            int callType = abs(randNum.nextInt()) % 50;
            int randIndex = abs(randNum.nextInt()) % 20;
            if(callType >= 15) {
                System.out.println("Calling create2 method (" + randIndex + ")...");
                try {
                    create2(randIndex);
                    print(PCB2[randIndex], randIndex);
                } catch (Exception ex) {
                    System.out.println("PCB [" + randIndex + "] no more free allocated memory in PCB2[].");
                }
            }
            else {
                System.out.println("Calling destroy2 method (" + randIndex + ")...");
                destroy2(randIndex);
            }
        }
        System.out.println("\nMethod 2 WithoutLinkedList's Information: ");
        for (int i = 0; i < PCB2.length; i++) {
            print(PCB2[i], i);
        }
    }

    public static void create1(int p) throws Exception {
        if (PCB1[p] == null) {
            PCB1[p] = new LinkedListPCB();
            full1++;
        }
        if (full1 == PCB1.length) {
            throw new Exception();
        }
        int capacity = PCB1.length;
        int q = (p + 1) % capacity;

        while(PCB1[q] != null) {
            q = (q + 1) % capacity;
        }
        PCB1[q] = new LinkedListPCB(p);
        full1++;
        PCB1[p].setChild(PCB1[p].getChildrenSize(), q);
    }

    public static void create2(int p) throws Exception {
        if (PCB2[p] == null) {
            PCB2[p] = new WithoutLinkedListPCB();
            full2++;
        }
        if (full2 == PCB2.length) {
            throw new Exception();
        }
        int capacity = PCB2.length;
        int q = (p + 1) % capacity;
        while(PCB2[q] != null) {
            q = (q + 1) % capacity;
        }
        PCB2[q] = new WithoutLinkedListPCB(p);
        full2++;
        if (PCB2[p].getFirst() == -1)
            PCB2[p].setFirst(q);
        else {
            int currentChild = PCB2[p].getFirst();
            while (true) {
                if (PCB2[currentChild].getYounger() == -1) {
                    PCB2[currentChild].setYounger(q);
                    PCB2[q].setOlder(currentChild);
                    break;
                }
                currentChild = PCB2[currentChild].getYounger();
            }
        }
    }

    public static void destroy1(int p) {
        call++;
        if (PCB1[p] == null) return;
        while (PCB1[p].getChildrenSize() != 0) {
            int indexOfChild = PCB1[p].getYoungestChild();
            destroy1(indexOfChild);
            PCB1[p].removeYoungest();
        }
        if (call > 0) {
            PCB1[p] = null;
            full1--;
            call--;
        }
        else
            call--;
    }

    public static void destroy2(int p) {
        call++;
        if (PCB2[p] == null) return;
        while (PCB2[p].getFirst() != -1) {
            int childrenCount = 0;
            int currentChild = PCB2[p].getFirst();
            int olderChild = currentChild;
            while (true) {
                childrenCount++;
                if (PCB2[currentChild].getYounger() == -1) {
                    destroy2(currentChild);
                    childrenCount--;
                    break;
                }
                olderChild = currentChild;
                currentChild = PCB2[currentChild].getYounger();
            }
            while (childrenCount > 0) {
                currentChild = olderChild;
                olderChild = PCB2[olderChild].getOlder();
                destroy2(currentChild);
                childrenCount--;
            }
            PCB2[p].setFirst(-1);
        }
        if (call > 0) {
            PCB2[p] = null;
            full2--;
            call--;
        }
        else
            call--;
    }
    public static void print(LinkedListPCB PCB, int index) {
        if (PCB == null) {
            System.out.println("PCB [" + index + "] --> NULL");
            return;
        }
        StringBuilder sb = new StringBuilder();
        String s = (PCB.getParent() == -1)?
                ("PCB [" + index + "] --> Parent: NULL, Children:") :
                ("PCB [" + index + "] --> Parent: PCB [" + PCB.getParent() + "], Children:");
        sb.append(s);
        if (PCB.getChildrenSize() > 0) {
            for (int i = 0; i < PCB.getChildrenSize(); i++) {
                String p = " PCB [" + PCB.getChild(i) + "]";
                sb.append(p);
            }
        }
        else
            sb.append(" NULL");

        System.out.println(sb.toString());
    }

    public static void print(WithoutLinkedListPCB PCB, int index) {

        if (PCB == null) {
            System.out.println("PCB [" + index + "] --> NULL");
            return;
        }

        StringBuilder sb = new StringBuilder();
        String s = (PCB.getParent() == -1)?
                ("PCB [" + index + "] --> Parent: NULL, Children:") :
                ("PCB [" + index + "] --> Parent: PCB [" + PCB.getParent() + "], Children:");
        sb.append(s);

        if (PCB.getFirst() != -1) {
            int currentChild = PCB.getFirst();
            while (currentChild != -1) {
                String p = " PCB [" + currentChild + "]";
                sb.append(p);
                currentChild = PCB.getYounger();
            }
        }
        else
            sb.append(" NULL");

        System.out.println(sb.toString());
    }
}
