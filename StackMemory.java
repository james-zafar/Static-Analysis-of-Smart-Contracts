import java.util.*;
public class StackMemory {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StackMemory() {
        populateCodeList();
    }


    //Load word from memory
    public void mload() {

    }

    //Save word to memory
    public void mstore() {

    }

    //Save byte to memory
    public void mstores() {

    }

    //load word from storage
    public void sload() {

    }

    //Save word to storage
    public void sstore() {

    }

    //Jump to location
    public void jump() {

    }

    //Conditional Jump
    public void jumpi() {

    }

    //Current counter value prior to instruction call
    public void pc() {

    }

    //Get size of active memory in bytes
    public void msize() {

    }

    //Get amount of available gas
    public void gas() {

    }

    //Mark valid jump point
    public void jumpdest() {

    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }

    public String getOpcodeName(String code) {
        for(Opcode i : allCodes) {
            if(i.getCode().matches(code)) {
                return i.getName();
            }
        }
        return null;
    }


    private void populateCodeList() {
        allCodes.add(new Opcode("50", "POP", "Remove item from the stack", 2, false));
        allCodes.add(new Opcode("51", "MLOAD", "Load word from memory", 3, false));
        allCodes.add(new Opcode("52", "MSTORE", "Save word to memory", 3, true));
        allCodes.add(new Opcode("53", "MSTORES", "Save byte to memory", 3, true));
        allCodes.add(new Opcode("54", "SLOAD", "Load word from storage", 50, false));
        allCodes.add(new Opcode("55", "SSTORE", "Save word to storage", 20000, true));
        allCodes.add(new Opcode("56", "JUMP", "Alter the program counter", 8, true));
        allCodes.add(new Opcode("57", "JUMPI",  "Conditionally alter the program counter", 10, true));
        allCodes.add(new Opcode("58", "PC", "Get the value of the program counter prior to the increment corresponding to this instruction", 2, false));
        allCodes.add(new Opcode("59", "MSIZE", "Get the size of active memory in bytes", 2, false));
        allCodes.add(new Opcode("5a", "GAS", "Get the amount of available gas, including the corresponding reduction for the cost of this instruction", 2, false));
        allCodes.add(new Opcode("5b", "JUMPDEST", "Mark a valid destination for jumps.\n" +
                "This operation has no effect on machine state during execution", 1, false));
    }

}