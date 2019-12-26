import java.util.*;
public class DuplicationOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public DuplicationOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("80", "DUP1", "Duplicate 1st stack item", 0));
        allCodes.add(new Opcode("81", "DUP2", "Duplicate 2nd stack item", 0));
        allCodes.add(new Opcode("82", "DUP3", "Duplicate 3rd stack item", 0));

        StringBuilder builder;
        String start = "Duplicate ";
        String end = "th stack item";
        for(int i = 3; i <= 15; i++) {
            builder = new StringBuilder();
            builder.append(start);
            builder.append(Integer.toString((i + 1)));
            builder.append(end);
            addOpcode(i, builder.toString());
        }
    }

    private void addOpcode(int number, String desc) {
        String name = "DUP" + String.valueOf((number + 1));
        if(number < 10) {
            String code = "8" + String.valueOf(number);
            allCodes.add(new Opcode(code, name, desc, 0));
        }else {
            String code = "8" + Integer.toHexString(number);
            allCodes.add(new Opcode(code, name, desc, 0));
        }
    }

    public List<Opcode> getList() {
        return allCodes;
    }

}