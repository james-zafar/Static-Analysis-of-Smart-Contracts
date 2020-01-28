import java.util.*;
public class Stack {
    List<String> theStack;
    public Stack() {
        theStack = new ArrayList<String>();
    }

    public void push(String info) throws RuntimeException {
        if(info == null) {
            throw new RuntimeException("Tried to push null to the stack");
        }else {
            this.theStack.add(0, info);
        }
    }

    public void pop() {
        this.theStack.remove(0);
    }

    public String get(int arg) {
        try {
            if (arg >= theStack.size()) {
                throw new IllegalArgumentException("The stack element requested does not exist");
            } else {
                return this.theStack.get(arg);
            }
        }catch(IllegalArgumentException e) {
            throw new RuntimeException("Could not retrieve element at index " + arg , e);
        }
    }

    public void swapElements(int toSwap) {
        Collections.swap(this.theStack, 0, toSwap);
    }

    public boolean nextZero() {
        return (Integer.parseInt(this.theStack.get(1)) == 0);
    }

    public void replace(int index, String newData) {
        this.theStack.set(index, newData);
    }

    public List<String> getStack() {
        return this.theStack;
    }
}