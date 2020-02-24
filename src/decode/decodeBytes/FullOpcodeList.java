package src.decode.decodeBytes;

import src.opcodes.*;

import java.util.ArrayList;
import java.util.List;

public class FullOpcodeList {

    private List<Opcode> allCodes = new ArrayList<Opcode>();

    public FullOpcodeList() {
        ComparisonOperations comparisonOps = new ComparisonOperations();
        allCodes.addAll(comparisonOps.getAllCodes());
        BlockOperations blockOps = new BlockOperations();
        allCodes.addAll(blockOps.getAllCodes());
        DuplicationOperations duplicationOps = new DuplicationOperations();
        allCodes.addAll(duplicationOps.getAllCodes());
        EnvironmentalInfo enviroOps = new EnvironmentalInfo();
        allCodes.addAll(enviroOps.getAllCodes());
        ExchangeOperations exchangeOps = new ExchangeOperations();
        allCodes.addAll(exchangeOps.getAllCodes());
        LoggingOperations loggingOps = new LoggingOperations();
        allCodes.addAll(loggingOps.getAllCodes());
        PushOperations pushOps = new PushOperations();
        allCodes.addAll(pushOps.getAllCodes());
        SHA3 sha3 = new SHA3();
        allCodes.addAll(sha3.getAllCodes());
        StackMemory stackMemOps = new StackMemory();
        allCodes.addAll(stackMemOps.getAllCodes());
        StopAndArithmetic stopOps = new StopAndArithmetic();
        allCodes.addAll(stopOps.getAllCodes());
        SystemOperations systemOps = new SystemOperations();
        allCodes.addAll(systemOps.getAllCodes());

    }

    public List<Opcode> getList() {
        return allCodes;
    }
}
