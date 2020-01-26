import java.util.*;
public class Decoder {

    private FullOpcodeList list = new FullOpcodeList();

    private String bytecode = "0x68466bad7e2343211320d5dcc03764c0ba522ad7aa22a9076d94f7a7519121dcaabbss1122ab01";
    private List<String> theOpcodes = new ArrayList<String>();
    //HashMap contains [Code, Name], data added to stack (optional)
    private HashMap<List<String>, List<String>> decoded = new HashMap<List<String>, List<String>>();
    public final List<Opcode> allOpcodes = list.getList();
    private Opcode myCode = new Opcode();
    public Stack stack = new Stack();
    private StopAndArithmetic completeArithmeticOps = new StopAndArithmetic();

    public Decoder() {
        //Remove leading 0x in any input bytecode
        if(bytecode.startsWith("0x")) {
            bytecode = bytecode.substring(2, bytecode.length());
        }
        splitCode();
        //System.out.println(Arrays.toString(theOpcodes.toArray()));
        decodeBytecode();
        //System.out.println(Arrays.asList(decoded));
        FormatBytecode formatter = new FormatBytecode(decoded);
        List<String> finished = new ArrayList<String>();
        finished = formatter.getFormattedData();
        finished.forEach(current -> System.out.print(current + "\n"));
    }

    private void splitCode() {
        int j = 0;
        int last = (int) (bytecode.length() / 2) - 1;
        for (int i = 0; i < last; i++) {
            theOpcodes.add(bytecode.substring(j, j + 2));
            j += 2;
        }
        theOpcodes.add(bytecode.substring(j));

    }

    private void decodeBytecode() {
        List<String> additionalData = new ArrayList<String>();
        while(theOpcodes.size() > 0) {
            List<String> tempList = new ArrayList<String>();
            String opcodeName = findOpcodeName(theOpcodes.get(0));
            if(opcodeName != null) {
                tempList.add(theOpcodes.get(0));
                tempList.add(opcodeName);
                additionalData = getAdditionalInfo(theOpcodes.get(0));
                decoded.put(tempList, additionalData);
            }
            theOpcodes.remove(0);
            //Print out the stack
            //System.out.println(Arrays.toString(stack.getStack().toArray()));;
        }
    }

    private String findOpcodeName(String opcode) {
        try {
            int counter = 0;
            while (counter < allOpcodes.size()) {
                if (allOpcodes.get(counter).getCode().matches(opcode)) {
                    myCode = allOpcodes.get(counter);
                    return allOpcodes.get(counter).getName();
                }
                counter++;
            }
            throw new UnknownOpcodeException("Invalid opcode: " + opcode);
        }catch(UnknownOpcodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getAdditionalInfo(String theCode) throws RuntimeException {
        List<String> stackAmendments = new ArrayList<String>();
        int counter = 0;
        int noBytes = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        char swtichChar = theCode.charAt(0);
        switch(swtichChar) {
            case '0':
                stackAmendments = arithmeticOp(theCode);
                break;
            case '1':
                //Comparsion Operations
                stackAmendments = doComparison(theCode);
                break;
            case '2':
                stackAmendments.add("None");
                stackAmendments.add("None");
                //SHA3
                break;
            case '3':
                stackAmendments.add("None");
                stackAmendments.add("None");
                //Environmental Operations
                break;
            case '4':
                stackAmendments.add("None");
                stackAmendments.add("None");
                //Block Operations
                break;
            case '5':
                //Memory, Storage and Flow Operations
                if(theCode.matches("50")) {
                    stack.pop();
                    stackAmendments.add("None");
                    stackAmendments.add(stack.get(0));
                }else {
                    stackOperations(theCode);
                }
                break;
            case '6':
            case '7':
                //Push Operations
                StringBuilder sb = new StringBuilder("");
                while(counter <= noBytes) {
                    sb.append(theOpcodes.get(counter) + " ");
                    stack.push(theOpcodes.get(counter));
                    counter++;
                }
                stackAmendments.add(sb.toString());
                stackAmendments.add("None");
                break;
            case '8':
                //Duplication Operations
                stackAmendments = duplicateStackItem(theCode);
                break;
            case '9':
                //Exchange(Swap) Operations
                stackAmendments = swapStackItems(theCode);
                break;
            case 'a':
                stackAmendments.add("None");
                stackAmendments.add("None");
                //Logging Operations
                break;
            case 'f':
                stackAmendments.add("None");
                stackAmendments.add("None");
                //System Operations
                break;
            default:
                throw new RuntimeException("Unreachable");
        }
        return stackAmendments;
    }


    private List<String> doComparison(String theCode) {
        List<String> additionalInfo = new ArrayList<String>();
        ComparisonOperations classRef = new ComparisonOperations();
        String arg1 = stack.get(0);
        String arg2 = stack.get(1);
        String removed = arg1 + ", " + arg2;
        String res = "";
        stack.pop();
        stack.pop();
        String methodName = classRef.getOpcodeName(theCode).toLowerCase();
        try {
            Class<?> myClass = Class.forName("ComparisonOperations");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodName, String.class, String.class);
            Object result = method.invoke(classRef, arg1, arg2);
            res = (String) result;
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
        additionalInfo.add(res);
        additionalInfo.add(removed);
        return additionalInfo;
    }

    private List<String> duplicateStackItem(String theCode) {
        List<String> info = new ArrayList<String>();
        int stackNumber = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.push(stack.get(stackNumber + 1));
        info.add(stack.get(stackNumber + 1));
        info.add("None");
        return info;
    }

    private List<String> swapStackItems(String theCode) {
        List<String> changes = new ArrayList<String>();
        changes.add("None");
        changes.add("None");
        int swapWith = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.swapElements(swapWith + 1);
        return changes;
    }

    private List<String> arithmeticOp(String theCode) {
        List<String> additionalInfo = new ArrayList<String>();
        String methodToCall = completeArithmeticOps.getOpcodeName(theCode).toLowerCase();
        String arg1 = stack.get(0);
        String arg2 = stack.get(1);
        String removed = arg1 + ", " + arg2;
        try {
            Class<?> myClass = Class.forName("StopAndArithmetic");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodToCall, String.class, String.class);
            Object result = method.invoke(completeArithmeticOps, arg1, arg2);
            String res = (String) result;
            stack.pop();
            stack.pop();
            stack.push(res);
            additionalInfo.add(res);
            additionalInfo.add(removed);
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

    private void stackOperations(String theCode) {
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