import java.util.*;
public class BlockOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public BlockOperations() {
        populateCodeList();
    }


    public List<Opcode> getAllCodes() {
        return allCodes;
    }


    private void populateCodeList() {
        allCodes.add(new Opcode("40", "BLOCKHASH", "Get the hash of one of the 256 most recent complete blocks", 20, false));
        allCodes.add(new Opcode("41", "COINBASE", "Get the block's benefciary address", 3, false));
        allCodes.add(new Opcode("42", "TIMESTAMP", "Get the block's timestamp", 2, false));
        allCodes.add(new Opcode("43", "NUMBER", "Get the block's nunber", 2, false));
        allCodes.add(new Opcode("44", "DIFFICULTY", "Get the block's difficulty", 2, false));
        allCodes.add(new Opcode("45", "GASLIMIT", "Get the block's limit", 2, false));
    }

}