import java.util.*;
public class Decoder {

    public static fullOpcodeList list = new fullOpcodeList();

    private static String bytecode = "0x68466bad7e2343211320d5dcc03764c0ba522ad7aa22a9076d94f7a7519121dcaabbss1122ab01";
    private static List<String> theOpcodes = new ArrayList<String>();
    private static HashMap<List<String>, String> decoded = new HashMap<List<String>, String>();
    public static final List<Opcode> allOpcodes = list.getList();
    private static Opcode myCode = new Opcode();
    public static Stack stack = new Stack();
    private static StopAndArithmetic completeArithmeticOps = new StopAndArithmetic();

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
                    //throw new IllegalArgumentException("Unknown Opcode");
                }
            }
            theOpcodes.remove(0);
            //Print out the stack
            //System.out.println(Arrays.toString(stack.getStack().toArray()));;
        }
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
        if(theCode.startsWith("0")) {
            sb.append(arithmeticOp(theCode));
        }else if(theCode.startsWith("1")) {

        }else if(theCode.startsWith("3")) {

        }else if(theCode.startsWith("4")) {

        }else if(theCode.startsWith("5")) {
            if(theCode.matches("50")) {
                stack.pop();
            }else {
                stackOperations(theCode);
            }
        } else if(theCode.startsWith("6")) {
            while(counter <= noBytes) {
                sb.append(theOpcodes.get(counter));
                stack.push(theOpcodes.get(counter));
                counter++;
            }
        }else if(theCode.startsWith("8")) {
            sb.append(duplicateStackItem(theCode));
        }else if(theCode.startsWith("9")) {
            sb.append(swapStackItems(theCode));
        }
        String additionalInfo = sb.toString();
        return additionalInfo;
    }


    private static String duplicateStackItem(String theCode) {
        int stackNumber = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.push(stack.get(stackNumber + 1));
        return(stack.get(stackNumber + 1));
    }

    private static String swapStackItems(String theCode) {
        int swapWith = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.swapElements(swapWith + 1);
        String res = stack.get(swapWith + 1) + ", " + stack.get(0);
        return res;
    }

    private static String arithmeticOp(String theCode) {
        String methodToCall = completeArithmeticOps.getOpcodeName(theCode).toLowerCase();
        String arg1 = stack.get(0);
        String arg2 = stack.get(1);
        String additionalInfo = arg1 + ", " + arg2;
        try {
            Class<?> myClass = Class.forName("StopAndArithmetic");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodToCall, String.class, String.class);
            Object result = method.invoke(completeArithmeticOps, arg1, arg2);
            String res = (String) result;
            stack.pop();
            stack.pop();
            stack.push(res);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return additionalInfo;
    }

    private static void stackOperations(String theCode) {
        StackMemory temp = new StackMemory();
        String methodToCall = temp.getOpcodeName(theCode).toLowerCase();
        try {
            Class<?> myClass = Class.forName("StackMemory");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodToCall);
            method.invoke(temp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}