package decode.decodeBytes;

import decode.formatBytecode.FormatBytecode;
import decode.programFlow.CreateProgramFlow;
import exceptions.NoImplementationException;
import exceptions.UnknownOpcodeException;
import opcodes.ComparisonOperations;
import opcodes.Opcode;
import opcodes.StopAndArithmetic;
import stack.Stack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Decoder {

    //address is the caller address for the contract
    private String address = "0x20E0e9794A17aa5166c164b80fA0b126c72E5412B0";
    //Bytecode should be the runtime bytecode of the contract to be decoded
    //private String bytecode = "0x6103010150";
    //private String bytecode = "0x600161a029602b100160006008603360125658595b9092600250512020126238492950";
    private String bytecode = "608060405234801561001057600080fd5b506102f3806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630c55699c146100bc578063412a5a6d146100da578063bbf5f657146100e4575b600060015414156100ba576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f5743275320434f4646454520495320544845204245535400000000000000000081525060200191505060405180910390fd5b005b6100c461012e565b6040518082815260200191505060405180910390f35b6100e2610134565b005b6100ec6101af565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60015481565b600043604051610143906101d4565b80828152602001915050604051809103906000f080158015610169573d6000803e3d6000fd5b509050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60de806101e18339019056fe608060405234801561001057600080fd5b506040516100de3803806100de8339818101604052602081101561003357600080fd5b8101908080519060200190929190505050806000819055505060848061005a6000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c80638381f58a14602d575b600080fd5b60336049565b6040518082815260200191505060405180910390f35b6000548156fea265627a7a7231582072297843a879b6a0f8c17813747db854f7509568d61abde3fe11ab84cba5eb8864736f6c63430005100032a265627a7a72315820517ba7ae1ca97c58544e661a5a2154d513535fe0e968bee6b30f73a1e437494964736f6c63430005100032";
    //HashMap contains [Code, Name], [added to stack, removed from stack]
    private Map<List<String>, List<String>> decoded;
    public final List<Opcode> allOpcodes;
    private List<String> opcodesFound;
    public Stack stack, memory;
    private StopAndArithmetic completeArithmeticOps;

    public Decoder(String output) {
        decoded = new LinkedHashMap<List<String>, List<String>>();
        List<String> finished = new ArrayList<String>();
        stack = new Stack();
        memory = new Stack();
        allOpcodes = new FullOpcodeList().getList();
        opcodesFound = new ArrayList<String>();
        completeArithmeticOps = new StopAndArithmetic();

        //Remove leading 0x in any input bytecode
        if(bytecode.startsWith("0x")) {
            bytecode = bytecode.substring(2);
        }
        runDecoder();

        FormatBytecode formatter = new FormatBytecode(decoded);
        finished = formatter.getFormattedData();

        /*Use to print out Has Map */
        //System.out.println(Arrays.deepToString(decoded.entrySet().toArray()));
        chooseOutput(output, finished);
    }

    private void chooseOutput(String output, List<String> data) {
        if(output.matches("list") || output.matches("default")) {
            //Output a list of opcodes found
            data.forEach(current -> System.out.print(current + "\n"));
        }else {
            new CreateProgramFlow(opcodesFound, data, output);
        }
    }

    private void runDecoder() {
        List<String> additionalData;
        String current, opcodeName;
        int instructionNo = 0;
        while(bytecode.length() > 0) {
            current = bytecode.substring(0, 2);
            List<String> tempList = new ArrayList<String>();
            if(isOpcode(current)) {
                /*Decoded instructions are stored in the form
                    [InstructionNo, opcodeNo, opcodeType], [addedToStack, removedFromStack]
                 */
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
        //Simple check if opcode number can be matched to a name
        return (findOpcodeName(check) != null);
    }

    private String findOpcodeName(String opcode) {
        try {
            int counter = 0;
            while (counter < allOpcodes.size()) {
                //allOpcodes is a final ArrayList of all valid opcodes
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
        //After locating the class of opcodes, can the do operation on specific opcode
        try {
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
                    //SHA3
                    stackAmendments.add("None");
                    stackAmendments.add("None");
                    throw new NoImplementationException("No SHA3 Implementation exists");
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
                    stackAmendments.add(pushOperations(theCode));
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
                    //Return value to be used in future releases when these codes are implemented
                    String returned = unimplementedOpcode(theCode);
                    break;
                default:
                    throw new RuntimeException("Unreachable");
            }
        } catch(NoImplementationException e) {
            System.out.println(e.getMessage());
        }
        return stackAmendments;
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
            Class <?> myClass = Class.forName("opcodes.StopAndArithmetic");
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
            Class <?> myClass = Class.forName("opcodes.ComparisonOperations");
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

    private void doEnvironmentalOps(String theCode) {
        //Most opcodes for this class are unimplemented in this release
        if(theCode.matches("33")) {
            stack.pop();
            stack.push(address);
        }
    }

    private List<String> stackOperations(String theCode) {
        //Methods precedes invokeMemoryOps() which is not implemented in this release
        //This method is to deal with jump operations, everything else to be dealt with in invokeMemortyOps()
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

    private String pushOperations(String theCode) {
        StringBuilder sb = new StringBuilder();
        int noBytes = Character.digit((theCode.charAt(theCode.length() - 1)), 16);
        int counter = 0;
        if(noBytes == 0) {
            //0 is opcode 60 meaning push 1 byte to the stack
            sb.append(bytecode.substring(0, 2)).append(" ");
            stack.push(bytecode.substring(0, 2));
            bytecode = bytecode.substring(2);
        }else {
            while (counter <= noBytes) {
                //Collect the correct number of bytes to push to the stack together
                sb.append(bytecode.substring(0, 2));
                bytecode = bytecode.substring(2);
                counter++;
            }
            stack.push(sb.toString());
        }
        return sb.toString();
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

    private void invokeMemoryOps(String methodName) {
        //Method not used in this release but should be used in addition to StackOperations() when implemented
        try {
            Class <?> myClass = Class.forName("opcodes.StackMemory");
            java.lang.reflect.Method method = myClass.getDeclaredMethod(methodName);
            method.invoke(completeArithmeticOps);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private String unimplementedOpcode(String theCode) throws NoImplementationException {
        if(theCode.matches("f3")) {
            return "None";
        }else {
            if (!theCode.equals("ff")) {
                String errorMessage = "No suitable implementation for (" + theCode + " "
                        + findOpcodeName(theCode) + ") found";
                throw new NoImplementationException(errorMessage);
            }
        }
        return null;
    }

}