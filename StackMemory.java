import java.util.*;
public class StackMemory {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StackMemory() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("50", "POP", 1, "Remove item from the stack", 0));
        allCodes.add(new Opcode("51", "MLOAD", 1, "Load word from memory", 0));
        allCodes.add(new Opcode("52", "MSTORE", 2, "Save word to memory", 0));
        allCodes.add(new Opcode("53", "MSTORES", 2, "Save byte to memory", 0));
        allCodes.add(new Opcode("54", "SLOAD", 1, "Load word from storage", 0));
        allCodes.add(new Opcode("55", "SSTORE", 2, "Save word to storage", 0));
        allCodes.add(new Opcode("56", "JUMP", 1, "Alter the program counter", 0));
        allCodes.add(new Opcode("57", "JUMPI", 2, "Conditionally alter the program counter", 0));
        allCodes.add(new Opcode("58", "PC", 0, "Get the value of the program counter prior to the increment corresponding to this instruction", 0));
        allCodes.add(new Opcode("59", "MSIZE", 0, "Get the size of active memory in bytes", 0));
        allCodes.add(new Opcode("5a", "GAS", 0, "Get the amount of available gas, including the corresponding reduction for the cost of this instruction", 0));
        allCodes.add(new Opcode("5b", "JUMPDEST", 0, "Mark a valid destination for jumps.\n" +
                "This operation has no effect on machine state during execution", 0));
    }

}