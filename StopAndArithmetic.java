import java.util.*;
public class StopAndArithmetic{
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StopAndArithmetic() {
        populateOpcodeList();
    }

    public byte add(byte valueOne, byte valueTwo) {
        return((byte) (valueOne + valueTwo));
    }

    public byte multiply(byte valueOne, byte valueTwo) {
        return ((byte) (valueOne * valueTwo));
    }

    public byte subtract(byte valueOne, byte valueTwo) {
        return ((byte) (valueOne - valueTwo));
    }

    public byte divide(byte valueOne, byte valueTwo) {
        return ((byte) (valueOne / valueTwo));
    }

    public byte modulo(byte valueOne, byte valueTwo) {
        return((byte) (valueOne % valueTwo));
    }

    public byte addModulo(byte valueOne, byte valueTwo, byte valueThree) {
        return((byte) ((valueOne + valueTwo) % valueThree));
    }

    public byte multiplyModulo(byte valueOne, byte valueTwo, byte valueThree) {
        return((byte) ((valueOne * valueTwo)% valueThree));
    }

    public byte exponent(byte valueOne, byte valueTwo) {
        return((byte) (Math.pow(valueOne, valueTwo)));
    }

    public void signExtend(byte start, byte x) {
        //Need to understand what this does
    }

    private void populateOpcodeList() {
        allCodes.add(new Opcode("00", "STOP", "Halts Execution", 0));
        allCodes.add(new Opcode("01", "ADD", "Addition Operation", 0));
        allCodes.add(new Opcode("02", "MUL", "Multiplication Operation", 0));
        allCodes.add(new Opcode("03", "SUB", "Subtraction Operation", 0));
        allCodes.add(new Opcode("04", "DIV", "Integer Division Operation", 0));
        allCodes.add(new Opcode("05", "SDIV", "Signed Integer Division Operation (truncated)", 0));
        allCodes.add(new Opcode("06", "MOD", "Modulo Remainder Operation", 0));
        allCodes.add(new Opcode("07", "SMOD", "Signed Modulo Remainder Operation", 0));
        allCodes.add(new Opcode("08", "ADDMOD", "Modulo Addition Operation", 0));
        allCodes.add(new Opcode("09", "MULMOD", "Modulo Multiplication Operation", 0));
        allCodes.add(new Opcode("0a", "EXP", "Exponential Operation", 0));
        allCodes.add(new Opcode("0b", "SIGNEXTEND", "Extend length of two's complement signed integer", 0));
    }
}