package src.opcodes;

import java.util.ArrayList;
import java.util.List;
public class PushOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public PushOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        StringBuilder builder;
        String start = "Place ";
        String end = "-byte item on stack";
        for(int i = 0; i <= 30; i++) {
            builder = new StringBuilder();
            builder.append(start);
            builder.append(Integer.toString((i + 1)));
            builder.append(end);
            addOpcode(i, builder.toString());
        }
        String last = "Place 32-byte (full word) item on stack";
        allCodes.add(new Opcode("7f", "PUSH32", last, 3, true));
    }

    private void addOpcode(int number, String desc) {
        String name = "PUSH" + String.valueOf((number + 1));
        if(number < 10) {
            String code = "6" + String.valueOf(number);
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }else if((number >= 10) && (number < 16)) {
            String code = "6" + Integer.toHexString(number);
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }else if((number >= 16) && (number < 26)) {
            String code = "7" + String.valueOf((number - 16));
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }else {
            String code = "7" + Integer.toHexString((number - 16));
            allCodes.add(new Opcode(code, name, desc, 3, true));
        }
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }

}