import java.util.*;
public class Stack {
    List<String> theStack;
    public Stack() {
        theStack = new ArrayList<String>();
    }

    public void push(String info) {
        this.theStack.add(info);
    }

    public void pop() {
        this.theStack.remove(0);
    }

    public String inspectElement(int arg) {
        return this.theStack.get(arg);
    }

    public boolean nextZero() {
        return (Integer.parseInt(this.theStack.get(1)) == 0);
    }
}