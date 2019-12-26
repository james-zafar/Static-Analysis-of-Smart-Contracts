import java.util.*;
public class EnvironmentalInfo {
    List <Opcode> allCodes = new ArrayList<Opcode>();
    public EnvironmentalInfo() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("30", "ADDRESS", 0, "Get address of currently executing account", 0));
        allCodes.add(new Opcode("31", "BALANCE", 1, "Get balance of the given account", 0));
        allCodes.add(new Opcode("32", "ORIGIN", 0, "Get execution origination address", 0));
        allCodes.add(new Opcode("33", "CALLER", 0, "Get caller address", 0));
        allCodes.add(new Opcode("34", "CALLVALUE", 0, "Get deposited value by the instruction/transaction responsible for this execution", 0));
        allCodes.add(new Opcode("35", "CALLDATALOAD", 1, "Get input data of current environment", 0));
        allCodes.add(new Opcode("36", "CALLDATASIZE", 0, "Get size of input data in current environment", 0));
        allCodes.add(new Opcode("37", "CALLDATACOPY", 3, "Copy input data in current environment to memory", 0));
        allCodes.add(new Opcode("38", "CODESIZE", 0, "Get size of code running in current environment", 0));
        allCodes.add(new Opcode("39", "CODECOPY", 3, "Copy code running in current environment to memory", 0));
        allCodes.add(new Opcode("3a", "GASPRICE", 0, "Get price of gas in current environment", 0));
        allCodes.add(new Opcode("3b", "EXTCODESIZE", 1, "Get size of an account's code", 0));
        allCodes.add(new Opcode("3c", "EXTCODECOPY", 4, "Copy an account's code to memory", 0));
        allCodes.add(new Opcode("3d", "RETURNDATASIZE", 0, "Get size of output data from the previous call from the current environment", 0));
        allCodes.add(new Opcode("3e", "RETURNDATACOPY", 3, "Copy output data from the previous call to memory", 0));

    }
}