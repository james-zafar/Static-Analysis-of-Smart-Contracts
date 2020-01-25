import java.util.*;
public class ComparisonOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public ComparisonOperations() {
        populateOpcodeList();
    }

    //Less Than Operator
    public String lt(String arg1, String arg2) {
        if(Integer.parseInt(arg1, 16) < Integer.parseInt(arg2, 16)) {
            return "01";
        }else {
            return "00";
        }
    }

    //Greater than Operator
    public String gt(String arg1, String arg2) {
        if(Integer.parseInt(arg1, 16) > Integer.parseInt(arg2, 16)) {
            return "01";
        }else {
            return "00";
        }
    }

    public String slt(String arg1, String arg2) {
        if(Integer.parseInt(arg1, 16) < Integer.parseInt(arg2, 16)) {
            return "01";
        }else {
            return "00";
        }    }

    public String sgt(String arg1, String arg2) {
        if(Integer.parseInt(arg1, 16) < Integer.parseInt(arg2, 16)) {
            return "01";
        }else {
            return "00";
        }    }

    //Equality check
    public String eq(String arg1, String arg2) {
        if(arg1.matches(arg2)) {
            return "01";
        }else {
            return "00";
        }    }

    public String iszero(String arg1, String arg2) {
        if(!arg1.matches(arg2)) {
            return "01";
        }else {
            return "00";
        }    }

    public String and(String arg1, String arg2) {
        return null;
    }

    public String or(String arg1, String arg2) {
        return null;
    }

    public String xor(String arg1, String arg2) {
        return null;
    }

    public String not(String arg1, String arg2) {
        return null;
    }

    public String byte1(String arg1, String arg2) {
        return null;
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

    private void populateOpcodeList() {
        allCodes.add(new Opcode("10", "LT", "Less-than comparison", 3, true));
        allCodes.add(new Opcode("11", "GT", "Greater-than comparison", 3, true));
        allCodes.add(new Opcode("12", "SLT", "Signed less-than comparison", 3, true));
        allCodes.add(new Opcode("13", "SGT", "Signed greater-than comparison", 3, true));
        allCodes.add(new Opcode("14", "EQ", "Equality comparison", 3, true));
        allCodes.add(new Opcode("15", "ISZERO", "Simple not operator", 3, true));
        allCodes.add(new Opcode("16", "AND", "Bitwise AND operation", 3, true));
        allCodes.add(new Opcode("17", "OR", "Bitwise OR operation", 3, true));
        allCodes.add(new Opcode("18", "XOR", "Bitwise XOR operation", 3, true));
        allCodes.add(new Opcode("19", "NOT", "Bitwise NOT operation", 3, true));
        allCodes.add(new Opcode("1a", "BYTE", "Retrieve single byte from word", 3, true));
    }
}