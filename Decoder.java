import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Decoder {

    private FullOpcodeList list = new FullOpcodeList();
    private String address = "0x20E0e9794A17aa5166c164b80fA0b126c72E5412B0";
    //private String bytecode = "0x6103010150";
    private String bytecode = "0x600161a029602b100160006008603360105658595a9092600250512020126238492950";
    //private String bytecode = "0x608060405234801561001057600080fd5b506040516020806103ee833981018060405281019080805190602001909291905050508060008190555080600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550506103608061008e6000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806318160ddd1461005c57806370a0823114610087578063a9059cbb146100de575b600080fd5b34801561006857600080fd5b50610071610143565b6040518082815260200191505060405180910390f35b34801561009357600080fd5b506100c8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061014c565b6040518082815260200191505060405180910390f35b3480156100ea57600080fd5b50610129600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610195565b604051808215151515815260200191505060405180910390f35b60008054905090565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60008073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141515156101d257600080fd5b600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054821115151561022057600080fd5b81600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205403600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555081600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060019050929150505600a165627a7a7230582025608b5c372888ca7316c16f1748775824bc03cb64852867e119a10a03402ef500290000000000000000000000000000000000000000000000000000000000000000";
    //HashMap contains [Code, Name], [added to stack, removed from stack]
    private Map<List<String>, List<String>> decoded;
    public final List<Opcode> allOpcodes;
    private List<String> opcodesFound;
    public Stack stack, memory;
    private StopAndArithmetic completeArithmeticOps;

    public Decoder() {
        decoded = new LinkedHashMap<List<String>, List<String>>();
        List<String> finished = new ArrayList<String>();
        stack = new Stack();
        memory = new Stack();
        allOpcodes = list.getList();
        opcodesFound = new ArrayList<String>();
        completeArithmeticOps = new StopAndArithmetic();

        //Remove leading 0x in any input bytecode
        if(bytecode.startsWith("0x")) {
            bytecode = bytecode.substring(2);
        }
        runDecoder();
        /*Use to print out Has Map */
        //System.out.println(Arrays.deepToString(decoded.entrySet().toArray()));
        FormatBytecode formatter = new FormatBytecode(decoded);
        finished = formatter.getFormattedData();
        //Output the finished reverse engineered data
        //finished.forEach(current -> System.out.print(current + "\n"));
        //opcodesFound.forEach(x -> System.out.print(x + ", "));
        new CreateProgramFlow(opcodesFound, finished);
    }

    private void runDecoder() {
        List<String> additionalData;
        String current, opcodeName;
        //System.out.println(bytecode);
        int instructionNo = 0;
        while(bytecode.length() > 0) {
            current = bytecode.substring(0, 2);
            List<String> tempList = new ArrayList<String>();
            if(isOpcode(current)) {
                tempList.add(Integer.toString(instructionNo));
                tempList.add(current);
                opcodeName = findOpcodeName(current);
                tempList.add(opcodeName);
                opcodesFound.add(opcodeName);
                bytecode = bytecode.substring(2);
                additionalData = getAdditionalInfo(current);
                decoded.put(tempList, additionalData);
            }else {
                /*If not opcode assumed to be function hash*/
                findFunctionHash();
                bytecode = bytecode.substring(2);
            }
            instructionNo++;
            /*Use to print out the Stack */
            //System.out.println(Arrays.toString(stack.getStack().toArray()));;
        }
    }

    private void findFunctionHash() {

    }

    private boolean isOpcode(String check) {
        return (findOpcodeName(check) != null);
    }

    private String findOpcodeName(String opcode) {
        try {
            int counter = 0;
            while (counter < allOpcodes.size()) {
                if (allOpcodes.get(counter).getCode().matches(opcode)) {
                    return allOpcodes.get(counter).getName();
                }
                counter++;
            }
            throw new UnknownOpcodeException("Error, invalid opcode: " + opcode);
        }catch(UnknownOpcodeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private List<String> getAdditionalInfo(String theCode) throws RuntimeException {
        List<String> stackAmendments = new ArrayList<String>();
        char switchChar = theCode.charAt(0);
        switch(switchChar) {
            case '0':
                stackAmendments = arithmeticOp(theCode);
                break;
            case '1':
                //Comparison Operations
                stackAmendments = doComparison(theCode);
                break;
            case '2':
                stackAmendments.add("None");
                stackAmendments.add("None");
                try {
                    throw new NoImplementationException("No SHA3 Implementation exists");
                } catch (NoImplementationException e) {
                    System.out.println(e.getMessage());
                }
                //SHA3
                break;
            case '3':
                doEnvironmentalOps(theCode);
                stackAmendments.add("None");
                stackAmendments.add("None");
                //Environmental Operations
                break;
            case '5':
                //Memory, Storage and Flow Operations
                if(theCode.matches("50")) {
                    stackAmendments.add("None");
                    stackAmendments.add(stack.get(0));
                    stack.pop();
                }else {
                    stackAmendments = stackOperations(theCode);
                }
                break;
            case '6':
            case '7':
                //Push Operations
                StringBuilder sb = new StringBuilder();
                int noBytes = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
                int counter = 0;
                if(noBytes == 0) {
                    sb.append(bytecode.substring(0, 2)).append(" ");
                    stack.push(bytecode.substring(0, 2));
                    bytecode = bytecode.substring(2);
                }else {
                    while (counter <= noBytes) {
                        sb.append(bytecode.substring(0, 2));
                        bytecode = bytecode.substring(2);
                        counter++;
                    }
                    stack.push(sb.toString());
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
            case '4':
            case 'a':
            case 'f':
                /*4 is for Block Operations
                //a is for Logging Operations
                f is for System operations */
                stackAmendments.add("None");
                stackAmendments.add("None");
                if(theCode.matches("f3")) {
                    stackAmendments.add("None");
                    stackAmendments.add("None");
                }else {
                    try {
                        if (!theCode.equals("ff")) {
                            String errorMessage = "No suitable implementation for (" + theCode + " "
                                    + findOpcodeName(theCode) + ") found";
                            throw new NoImplementationException(errorMessage);
                        }
                    } catch (NoImplementationException e) {
                        System.out.println(e.getMessage());
                    }
                }
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
            Class <?> myClass = Class.forName("ComparisonOperations");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodName, String.class, String.class);
            Object result = method.invoke(classRef, arg1, arg2);
            res = (String) result;
            stack.push(res);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        additionalInfo.add(res);
        additionalInfo.add(removed);
        return additionalInfo;
    }

    private List<String> duplicateStackItem(String theCode) {
        List<String> info = new ArrayList<String>();
        int stackNumber = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        info.add(stack.get(stackNumber));
        stack.push(stack.get(stackNumber));
        info.add("None");
        return info;
    }

    private List<String> swapStackItems(String theCode) {
        List<String> changes = new ArrayList<String>();
        changes.add("None");
        changes.add("None");
        int swapWith = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        stack.swapElements(swapWith);
        return changes;
    }

    private List<String> arithmeticOp(String theCode) {
        List<String> additionalInfo = new ArrayList<String>();
        if(theCode.matches("00")) {
            additionalInfo.add("None");
            additionalInfo.add("None");
            return additionalInfo;
        }
        String methodToCall = completeArithmeticOps.getOpcodeName(theCode).toLowerCase();
        String arg1 = stack.get(0);
        String arg2 = stack.get(1);
        String removed = arg1 + ", " + arg2;
        try {
            Class <?> myClass = Class.forName("StopAndArithmetic");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodToCall, String.class, String.class);
            Object result = method.invoke(completeArithmeticOps, arg1, arg2);
            String res = (String) result;
            stack.pop();
            stack.pop();
            stack.push(res);
            additionalInfo.add(res);
            additionalInfo.add(removed);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return additionalInfo;
    }

    private List<String> stackOperations(String theCode) {
        List<String> additionalInfo = new ArrayList<String>();
        String stackPos;
        if(theCode.matches("56")) {
            stackPos = "JUMP " + stack.get(0);
            additionalInfo.add(stackPos);
            additionalInfo.add("None");
        }else if(theCode.matches("57")) {
            if(stack.get(1).matches("[0]+")) {
                stackPos = "VALID " + stack.get(0);
            }else {
                stackPos = "INVAL";
            }
            additionalInfo.add(stackPos);
            additionalInfo.add("None");
        }else {
            additionalInfo.add("None");
            additionalInfo.add("None");
        }
        return additionalInfo;
    }

    private void invokeMemoryOps(String methodName) {
        try {
            Class <?> myClass = Class.forName("StackMemory");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodName);
            method.invoke(completeArithmeticOps);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doEnvironmentalOps(String theCode) {
        if(theCode.matches("33")) {
            stack.pop();
            stack.push(address);
        }
    }

}