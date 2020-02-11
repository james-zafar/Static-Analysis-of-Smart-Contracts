package src.opcodes;

import java.util.ArrayList;
import java.util.List;
public class ExchangeOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public ExchangeOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("90", "SWAP1", "Exchange 1st and 2nd stack items", 3, true));
        allCodes.add(new Opcode("91", "SWAP2", "Exchange 1st and 3rd stack items", 3, true));

        StringBuilder builder;
        String start = "Exechance 1st and ";
        String end = "th stack items";
        for(int i = 2; i <= 15; i++) {
            builder = new StringBuilder();
            builder.append(start);
            builder.append(Integer.toString((i + 2)));
            builder.append(end);
            addOpcode(i, builder.toString());
        }
    }

    private void addOpcode(int number, String desc) {
        String name = "SWAP" + String.valueOf((number + 1));
        if(number < 10) {
            String code = "9" + String.valueOf(number);
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }else {
            String code = "9" + Integer.toHexString(number);
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }
}