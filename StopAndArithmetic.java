import java.util.*;
public class StopAndArithmetic{
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public StopAndArithmetic() {
        populateOpcodeList();
    }

    public String add(String arg1, String arg2) {
        int ans = Integer.parseInt(arg1, 16) + Integer.parseInt(arg2, 16);
        return Integer.toHexString(ans);
    }

    public String sub(String arg1, String arg2) {
        int ans = Integer.parseInt(arg1, 16) - Integer.parseInt(arg2, 16);
        return Integer.toHexString(ans);
    }

    public String mul(String arg1, String arg2) {
        int ans = Integer.parseInt(arg1, 16) * Integer.parseInt(arg2, 16);
        return Integer.toHexString(ans);
    }

    public String div(String arg1, String arg2) {
        int ans = (int) (Integer.parseInt(arg1, 16) / Integer.parseInt(arg2, 16));
        return Integer.toHexString(ans);
    }

    public String mod(String arg1, String arg2) {
        int ans = Integer.parseInt(arg1, 16) % Integer.parseInt(arg2, 16);
        return Integer.toHexString(ans);
    }

    public String exp(String arg1, String arg2) {
        int ans = (int) Math.pow(Integer.parseInt(arg1, 16), Integer.parseInt(arg2, 16));
        return Integer.toHexString(ans);
    }

    public byte addmod(byte valueOne, byte valueTwo, byte valueThree) {
        return((byte) ((valueOne + valueTwo) % valueThree));
    }

    public byte mulmod(byte valueOne, byte valueTwo, byte valueThree) {
        return((byte) ((valueOne * valueTwo)% valueThree));
    }
    public void signextend(byte start, byte x) {
        //Need to understand what this does
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }

    public String getOpcodeName(String code) {
        for(Opcode i : allCodes) {
            if(i.getCode().matches(code)) {
                if(i.getName().matches("SMOD")) {
                    return "MOD";
                }else if(i.getName().matches("SDIV")) {
                    return "DIV";
                }
                return i.getName();
            }
        }
        return null;
    }

    private void populateOpcodeList() {
        allCodes.add(new Opcode("00", "STOP", "Halts Execution", 0, false));
        allCodes.add(new Opcode("01", "ADD", "Addition Operation", 0, true));
        allCodes.add(new Opcode("02", "MUL", "Multiplication Operation", 0, true));
        allCodes.add(new Opcode("03", "SUB", "Subtraction Operation", 0, true));
        allCodes.add(new Opcode("04", "DIV", "Integer Division Operation", 0, true));
        allCodes.add(new Opcode("05", "SDIV", "Signed Integer Division Operation (truncated)", 0, true));
        allCodes.add(new Opcode("06", "MOD", "Modulo Remainder Operation", 0, true));
        allCodes.add(new Opcode("07", "SMOD", "Signed Modulo Remainder Operation", 0, true));
        allCodes.add(new Opcode("08", "ADDMOD", "Modulo Addition Operation", 0, true));
        allCodes.add(new Opcode("09", "MULMOD", "Modulo Multiplication Operation", 0, true));
        allCodes.add(new Opcode("0a", "EXP", "Exponential Operation", 0, true));
        allCodes.add(new Opcode("0b", "SIGNEXTEND", "Extend length of two's complement signed integer", 0, true));
    }
}