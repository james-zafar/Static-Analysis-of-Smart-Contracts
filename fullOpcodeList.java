import java.util.*;
public class FullOpcodeList {
    private ComparisonOperations ComparisonOps = new ComparisonOperations();
    private BlockOperations blockOps = new BlockOperations();
    private DuplicationOperations duplicationOps = new DuplicationOperations();
    private EnvironmentalInfo enviroOps = new EnvironmentalInfo();
    private ExchangeOperations exchangeOps = new ExchangeOperations();
    private LoggingOperations loggingOps = new LoggingOperations();
    private PushOperations pushOps = new PushOperations();
    private SHA3 sha3 = new SHA3();
    private StackMemory stackMemOps = new StackMemory();
    private StopAndArithmetic stopOps = new StopAndArithmetic();
    private SystemOperations systemOps = new SystemOperations();

    private List<Opcode> allCodes = new ArrayList<Opcode>();

    public FullOpcodeList() {
        allCodes.addAll(ComparisonOps.getAllCodes());
        allCodes.addAll(blockOps.getAllCodes());
        allCodes.addAll(duplicationOps.getAllCodes());
        allCodes.addAll(enviroOps.getAllCodes());
        allCodes.addAll(exchangeOps.getAllCodes());
        allCodes.addAll(loggingOps.getAllCodes());
        allCodes.addAll(pushOps.getAllCodes());
        allCodes.addAll(sha3.getAllCodes());
        allCodes.addAll(stackMemOps.getAllCodes());
        allCodes.addAll(stopOps.getAllCodes());
        allCodes.addAll(systemOps.getAllCodes());

    }

    public List<Opcode> getList() {
        return allCodes;
    }
}