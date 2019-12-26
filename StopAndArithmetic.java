import java.util.*;
public class StopAndArithmetic{
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StopAndArithmetic() {
        populateOpcodeList();
    }

    private void populateOpcodeList() {
        allCodes.add(new Opcode("00", "STOP", 0, "Halts Execution", 0));
        allCodes.add(new Opcode("01", "ADD", 2, "Addition Operation", 0));
        allCodes.add(new Opcode("02", "MUL", 2, "Multiplication Operation", 0));
        allCodes.add(new Opcode("03", "SUB", 2, "Subtraction Operation", 0));
        allCodes.add(new Opcode("04", "DIV", 2, "Integer Division Operation", 0));
        allCodes.add(new Opcode("05", "SDIV", 2, "Signed Integer Division Operation (truncated)", 0));
        allCodes.add(new Opcode("06", "MOD", 2, "Modulo Remainder Operation", 0));
        allCodes.add(new Opcode("07", "SMOD", 2, "Signed Modulo Remainder Operation", 0));
        allCodes.add(new Opcode("08", "ADDMOD", 3, "Modulo Addition Operation", 0));
        allCodes.add(new Opcode("09", "MULMOD", 3, "Modulo Multiplication Operation", 0));
        allCodes.add(new Opcode("0a", "EXP", 2, "Exponential Operation", 0));
        allCodes.add(new Opcode("0b", "SIGNEXTEND", 2, "Extend length of two's complement signed integer", 0));
    }
}