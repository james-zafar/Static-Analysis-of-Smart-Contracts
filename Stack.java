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

    public String get(int arg) {
        if(arg > theStack.size()) {
            throw new IllegalArgumentException();
        }
        return this.theStack.get(arg);
    }

    public List<String> getStack() {
        return this.theStack;
    }

    public void swapElements(int toSwap) {
        Collections.swap(this.theStack, 0, toSwap);
    }

    public boolean nextZero() {
        return (Integer.parseInt(this.theStack.get(1)) == 0);
    }
}