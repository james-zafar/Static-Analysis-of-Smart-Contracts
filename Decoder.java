import java.util.*;
public class Decoder {

    public static String bytecode = "0x68466bad7e2343211320d5dcc03764c0ba522ad7aa22a9076d94f7a7519121dc";
    private static List<String> theOpcodes = new ArrayList<String>();
    public HashMap<String, String> decoded = new HashMap<String, String>();

    public ComparisonOperations ComparisonOps = new ComparisonOperations();
    public BlockOperations blockOps = new BlockOperations();
    public DuplicationOperations duplicationOps = new DuplicationOperations();
    public EnvironmentalInfo enviroOps = new EnvironmentalInfo();
    public ExchangeOperations exchangeOps = new ExchangeOperations();
    public LoggingOperations loggingOps = new LoggingOperations();
    public PushOperations pushOps = new PushOperations();
    public SHA3 sha3 = new SHA3();
    public StackMemory stackMemOps = new StackMemory();
    public StopAndArithmetic stopOps = new StopAndArithmetic();
    public SystemOperations systemOps = new SystemOperations();

    public static void main(String [] args) {
        //Remove leading 0x in any input bytecode
        if(bytecode.startsWith("0x")) {
            bytecode = bytecode.substring(2, bytecode.length());
        }
        splitCode();
        System.out.println(Arrays.toString(theOpcodes.toArray()));
        decodeBytecode();
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

        }
    }
}