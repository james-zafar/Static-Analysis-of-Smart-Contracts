package stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack {

    List<String> theStack;

    public Stack() {
        theStack = new ArrayList<String>();
    }

    /**
     *
     * @param info information to add to the stack
     * @throws RuntimeException If the argument is null
     */
    public void push(String info) throws RuntimeException {
        if(info == null) {
            throw new RuntimeException("Tried to push null to the stack");
        }else {
            this.theStack.add(0, info);
        }
    }

    /**
     * Used to remove the top element from the stack
     */
    public void pop() {
        this.theStack.remove(0);
    }

    /**
     *
     * @param arg the index of the element to retrieve
     * @return the information at the given index on the stack
     */
    public String get(int arg) {
        try {
            if (arg >= theStack.size()) {
                throw new IndexOutOfBoundsException("The stack element requested does not exist");
            } else {
                return this.theStack.get(arg);
            }
        }catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("Could not retrieve element at index " + arg , e);
        }
    }

    /**
     *
     * @param toSwap swap the top element of the stack with that at the index provided
     */
    public void swapElements(int toSwap) {
        Collections.swap(this.theStack, 0, toSwap);
    }

    /**
     *
     * @return true if the next element (at index 1) is 0, false otherwise
     */
    public boolean nextZero() {
        return (Integer.parseInt(this.theStack.get(1)) == 0);
    }

    /**
     *
     * @param index the index of the element to amend
     * @param newData the data to be inserted in place of that at the index provided
     */
    public void replace(int index, String newData) {
        this.theStack.set(index, newData);
    }

    /**
     *
     * @return an instance of the stack
     */
    public List<String> getStack() {
        return this.theStack;
    }
}