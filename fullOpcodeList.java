import java.util.*;
public class fullOpcodeList {
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

    public List<Opcode> allCodes = new ArrayList<Opcode>();

    public fullOpcodeList() {
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