import java.util.*;
public class ComparisonOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public ComparisonOperations() {
        populateOpcodeList();
    }

    public byte lessThan(byte valueOne, byte valueTwo) {
        return((valueOne < valueTwo) ? (byte) 1 : (byte) 0);
    }

    public byte geaterThan(byte valueOne, byte valueTwo) {
        return((valueOne > valueTwo) ? (byte) 1 : (byte) 0);
    }

    public byte equality(byte valueOne, byte valueTwo) {
        return((valueOne == valueTwo) ? (byte) 1 : (byte) 0);
    }

    public byte isZero(byte valueOne) {
        return((valueOne == 0) ? (byte) 1 : (byte) 0);
    }

    public byte bitwiseAnd(byte valueOne, byte valueTwo) {
        return 0;
    }

    public byte bitwiseOr(byte valueOne, byte valueTwo) {
        return 0;
    }

    public byte bitwiseExclusiveOr(byte valueOne, byte valueTwo) {
        return 0;
    }

    public byte not(byte valueOne) {
        return 0;
    }

    public byte retrieveByte(byte valueOne, byte valueTwo) {
        return 0;
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }



    private void populateOpcodeList() {
        allCodes.add(new Opcode("10", "LT", "Less-than comparison", 0, true));
        allCodes.add(new Opcode("11", "GT", "Greater-than comparison", 0, true));
        allCodes.add(new Opcode("12", "SLT", "Signed less-than comparison", 0, true));
        allCodes.add(new Opcode("13", "SGT", "Signed greater-than comparison", 0, true));
        allCodes.add(new Opcode("14", "EQ", "Equality comparison", 0, true));
        allCodes.add(new Opcode("15", "ISZERO", "Simple not operator", 0, true));
        allCodes.add(new Opcode("16", "AND", "Bitwise AND operation", 0, true));
        allCodes.add(new Opcode("17", "OR", "Bitwise OR operation", 0, true));
        allCodes.add(new Opcode("18", "XOR", "Bitwise XOR operation", 0, true));
        allCodes.add(new Opcode("19", "NOT", "Bitwise NOT operation", 0, true));
        allCodes.add(new Opcode("1a", "BYTE", "Retrieve single byte from word", 0, true));
    }
}