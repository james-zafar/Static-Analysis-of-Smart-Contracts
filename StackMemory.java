import java.util.*;
public class StackMemory {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StackMemory() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("50", "POP", "Remove item from the stack", 0));
        allCodes.add(new Opcode("51", "MLOAD", "Load word from memory", 0));
        allCodes.add(new Opcode("52", "MSTORE", "Save word to memory", 0));
        allCodes.add(new Opcode("53", "MSTORES", "Save byte to memory", 0));
        allCodes.add(new Opcode("54", "SLOAD", "Load word from storage", 0));
        allCodes.add(new Opcode("55", "SSTORE", "Save word to storage", 0));
        allCodes.add(new Opcode("56", "JUMP", "Alter the program counter", 0));
        allCodes.add(new Opcode("57", "JUMPI",  "Conditionally alter the program counter", 0));
        allCodes.add(new Opcode("58", "PC", "Get the value of the program counter prior to the increment corresponding to this instruction", 0));
        allCodes.add(new Opcode("59", "MSIZE", "Get the size of active memory in bytes", 0));
        allCodes.add(new Opcode("5a", "GAS", "Get the amount of available gas, including the corresponding reduction for the cost of this instruction", 0));
        allCodes.add(new Opcode("5b", "JUMPDEST", "Mark a valid destination for jumps.\n" +
                "This operation has no effect on machine state during execution", 0));
    }

}