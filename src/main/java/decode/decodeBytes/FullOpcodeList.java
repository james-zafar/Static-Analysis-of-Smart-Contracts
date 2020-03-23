package decode.decodeBytes;

import opcodes.*;

import java.util.ArrayList;
import java.util.List;

public class FullOpcodeList {

    private List<Opcode> allCodes = new ArrayList<Opcode>();

    public FullOpcodeList() {
        StopAndArithmetic stopOps = new StopAndArithmetic();
        allCodes.addAll(stopOps.getAllCodes());
        ComparisonOperations comparisonOps = new ComparisonOperations();
        allCodes.addAll(comparisonOps.getAllCodes());
        SHA3 sha3 = new SHA3();
        allCodes.addAll(sha3.getAllCodes());
        EnvironmentalInfo enviroOps = new EnvironmentalInfo();
        allCodes.addAll(enviroOps.getAllCodes());
        BlockOperations blockOps = new BlockOperations();
        allCodes.addAll(blockOps.getAllCodes());
        StackMemory stackMemOps = new StackMemory();
        allCodes.addAll(stackMemOps.getAllCodes());
        PushOperations pushOps = new PushOperations();
        allCodes.addAll(pushOps.getAllCodes());
        DuplicationOperations duplicationOps = new DuplicationOperations();
        allCodes.addAll(duplicationOps.getAllCodes());
        ExchangeOperations exchangeOps = new ExchangeOperations();
        allCodes.addAll(exchangeOps.getAllCodes());
        LoggingOperations loggingOps = new LoggingOperations();
        allCodes.addAll(loggingOps.getAllCodes());
        SystemOperations systemOps = new SystemOperations();
        allCodes.addAll(systemOps.getAllCodes());

    }

    public List<Opcode> getList() {
        return allCodes;
    }
}
