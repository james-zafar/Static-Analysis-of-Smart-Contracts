import java.util.*;
public class Decoder {

    public static fullOpcodeList list = new fullOpcodeList();

    public static String bytecode = "0x68466bad7e2343211320d5dcc03764c0ba522ad7aa22a9076d94f7a7519121dcaabbss1122ab";
    private static List<String> theOpcodes = new ArrayList<String>();
    public static HashMap<List<String>, String> decoded = new HashMap<List<String>, String>();
    public static final List<Opcode> allOpcodes = list.getList();
    private static Opcode myCode = new Opcode();
    public static Stack stack = new Stack();

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
        while(theOpcodes.size() > 0) {
            List<String> tempList = new ArrayList<String>();
            String opcodeName = findOpcodeName(theOpcodes.get(0));
            if(opcodeName != null) {
                tempList.add(theOpcodes.get(0));
                tempList.add(opcodeName);
                if(myCode.extraDataRequired()) {
                    String additionalData = getAdditionalInfo(theOpcodes.get(0));
                    decoded.put(tempList, additionalData);
                }else {
                    decoded.put(tempList, null);
                }
            }
            theOpcodes.remove(0);
            //Print out the stack
            //System.out.println(Arrays.toString(stack.getStack().toArray()));;
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

    private static String getAdditionalInfo(String theCode) {
        StringBuilder sb = new StringBuilder("");
        int counter = 0;
        int noBytes = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        if(theCode.startsWith("6")) {
            while(counter <= noBytes) {
                sb.append(theOpcodes.get(counter));
                stack.push(theOpcodes.get(counter));
                counter++;
            }
        }else if(theCode.startsWith("8")) {
            duplicateStackItem(theCode);
        }else if(theCode.startsWith("9")) {
            swapStackItems(theCode);
        }
        String additionalInfo = sb.toString();
        return additionalInfo;
    }

    private static void duplicateStackItem(String theCode) {
        int stackNumber = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.push(stack.get(stackNumber + 1));
    }

    private static void swapStackItems(String theCode) {
        int swapWith = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.swapElements(swapWith + 1);

    }
}