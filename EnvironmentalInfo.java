import java.util.*;
public class EnvironmentalInfo {
    List <Opcode> allCodes = new ArrayList<Opcode>();
    public EnvironmentalInfo() {
        populateCodeList();
    }

    //get addrress of currently executing account
    public String getAddress() {
        return "";
    }

    //get balance of a given account with address arg
    public String getBalance(String arg) {
        return "";
    }

    //Returns transaction sender
    public String getOrigin() {
        return "";
    }

    //Returns call sender
    public String getCaller() {
        return "";
    }

    public void callValue() {
        //Placeholder
    }

    //call data starting from given position
    public void callDataLoad(String position) {

    }

    //returns size of call data in bytes
    public byte callDataSize() {
        return 0;
    }

    //Copy bytes from calldata at given position to given memory position
    public void callDataCopy(byte toPosition, byte fromPosition, byte byteData) {

    }

    //size of the code of the current contract / execution context
    public void codeSize() {

    }

    //Copy bytes from code at given position to given memory position
    public void codeCopy(byte toPosition, byte fromPosition, byte byteData) {

    }

    //returns gas price of transaction
    public void gasPrice() {

    }

    //Returns size of code at position at address a
    public void extCodeSize(String address) {

    }

    //Same as code copy but takes code at address a
    public void extCodeCopy(String address, byte toPosition, byte fromPosition, byte byteData) {

    }


    public String returnDataSize() {
        return "";
    }

    //copy s bytes from returndata at position f to mem at position t
    public void returnDataCopy(byte s, byte f, byte t) {

    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }


    private void populateCodeList() {
        allCodes.add(new Opcode("30", "ADDRESS", "Get address of currently executing account", 0, false));
        allCodes.add(new Opcode("31", "BALANCE", "Get balance of the given account", 0, false));
        allCodes.add(new Opcode("32", "ORIGIN", "Get execution origination address", 0, false));
        allCodes.add(new Opcode("33", "CALLER", "Get caller address", 0, false));
        allCodes.add(new Opcode("34", "CALLVALUE", "Get deposited value by the instruction/transaction responsible for this execution", 0, false));
        allCodes.add(new Opcode("35", "CALLDATALOAD", "Get input data of current environment", 0, false));
        allCodes.add(new Opcode("36", "CALLDATASIZE", "Get size of input data in current environment", 0, false));
        allCodes.add(new Opcode("37", "CALLDATACOPY", "Copy input data in current environment to memory", 0, false));
        allCodes.add(new Opcode("38", "CODESIZE", "Get size of code running in current environment", 0, false));
        allCodes.add(new Opcode("39", "CODECOPY", "Copy code running in current environment to memory", 0, false));
        allCodes.add(new Opcode("3a", "GASPRICE", "Get price of gas in current environment", 0, false));
        allCodes.add(new Opcode("3b", "EXTCODESIZE", "Get size of an account's code", 0, false));
        allCodes.add(new Opcode("3c", "EXTCODECOPY", "Copy an account's code to memory", 0, false));
        allCodes.add(new Opcode("3d", "RETURNDATASIZE", "Get size of output data from the previous call from the current environment", 0, false));
        allCodes.add(new Opcode("3e", "RETURNDATACOPY", "Copy output data from the previous call to memory", 0, false));

    }
}