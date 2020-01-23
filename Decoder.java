import java.util.*;
public class Decoder {

    public static fullOpcodeList list = new fullOpcodeList();

    public static String bytecode = "0x68466bad7e2343211320d5dcc03764c0ba522ad7aa22a9076d94f7a7519121dc";
    private static List<String> theOpcodes = new ArrayList<String>();
    public static HashMap<List<String>, String> decoded = new HashMap<List<String>, String>();
    public static final List<Opcode> allOpcodes = list.getList();
    private static Opcode myCode = new Opcode();

    public static void main(String [] args) {
        //Remove leading 0x in any input bytecode
        if(bytecode.startsWith("0x")) {
            bytecode = bytecode.substring(2, bytecode.length());
        }
        splitCode();
        //System.out.println(Arrays.toString(theOpcodes.toArray()));
        decodeBytecode();
        System.out.println(Arrays.asList(decoded));
    }

    private static void splitCode() {
        int j = 0;
        int last = (int) (bytecode.length() / 2) - 1;
        for (int i = 0; i < last; i++) {
            theOpcodes.add(bytecode.substring(j, j + 2));
            j += 2;
        }
        theOpcodes.add(bytecode.substring(j));

    }

    private static void decodeBytecode() {
        for(int i = 0; i < theOpcodes.size(); i++) {
            List<String> tempList = new ArrayList<String>();
            String opcodeName = findOpcodeName(theOpcodes.get(i));
            if(opcodeName != null) {
                tempList.add(theOpcodes.get(i));
                tempList.add(opcodeName);
                if(myCode.extraDataRequired()) {
                    String additionalData = getAdditionalInfo(theOpcodes.get(i));
                    decoded.put(tempList, additionalData);
                }else {
                    decoded.put(tempList, null);
                }
            }
        }
        System.out.println();
    }

    private static String findOpcodeName(String opcode) {
        int counter = 0;
        while(counter < allOpcodes.size()) {
            if(allOpcodes.get(counter).getCode().matches(opcode)) {
                myCode = allOpcodes.get(counter);
                return allOpcodes.get(counter).getName();
            }
            counter++;
        }
        return null;
    }

    private static String getAdditionalInfo(String opcode) {
        return "tbc";
    }
}