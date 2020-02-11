package src.opcodes;

import src.opcodes.Opcode;

import java.util.*;
public class SystemOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public SystemOperations() {
        populateCodeList();
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }


    private void populateCodeList() {
        allCodes.add(new Opcode("f0", "CREATE", "Create a new account with associated code", 32000, true));
        allCodes.add(new Opcode("f1", "CALL", "Message-call into an account", 0, true));
        allCodes.add(new Opcode("f2", "CALLCODE", "Message-call into this account with an alternative account's code. Exactly equivalent to CALL ", 0, true));
        allCodes.add(new Opcode("f3", "RETURN", "Halt execution returning output data \n"
                + "This has the effect of halting the execution at this point with output defned.", 0, false));
        allCodes.add(new Opcode("f4", "DELEGATECALL", "Message-call into this account with an alternative account's code, but\n" +
                "persisting the current values for sender and value.\n" +
                "Compared with CALL, DELEGATECALL takes one fewer arguments", 0, true));
        allCodes.add(new Opcode("fa", "STATICCALL", "Static message-call into an account", 0, true));
        allCodes.add(new Opcode("fd", "REVERT", "Halt execution reverting state changes but returning data and remaining gas", 0, false));
        allCodes.add(new Opcode("fe", "INVALID", "Designated invalid instruction", 0, false));
        allCodes.add(new Opcode("ff", "SELFDESTRUCT", "Halt execution and register account for later deletion", 0, false));
    }
}