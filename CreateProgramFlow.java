import java.lang.reflect.Array;
import java.util.*;
public class CreateProgramFlow {
    public HashMap<Integer, List<String>> programFlow;
    private List<String> finished;
    private List<String> opcodes;
    private List<String> currentBranch;
    int i, branchNumber = 1;
    public CreateProgramFlow(List<String> opcodes, List<String> finished) {
        this.finished = finished;
        this.opcodes = opcodes;
        currentBranch = new ArrayList<String>();
        programFlow = new HashMap<>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for(i = 0; i < opcodes.size(); i++) {
            indexes.add(i);
            manageOpcode(this.opcodes.get(i));
        }
        //finished.forEach(current -> System.out.print(current + "\n"));
        //System.out.println(Arrays.asList(programFlow));
    }

    private void manageOpcode(String code) {
        switch(code) {
            case "JUMP":
                addBranch();
                doJump();
                break;
            case "JUMPI":
                doJumpI();
                break;
            case "SELFDESTRUCT":
            case "STOP":
                addKill();
                break;
            default:
                currentBranch.add(finished.get(i));
        }
    }

    private void addBranch() {
        programFlow.put(branchNumber, currentBranch);
        currentBranch.clear();
        branchNumber++;
    }

    private void doJump() {
        String temp = finished.get(i);
        List<String> split = new LinkedList<String>(Arrays.asList(temp.split("\\s+")));
        String jumpToLocation = split.get(4).replaceAll("\\D+", "");
        if(opcodes.get(Integer.parseInt(jumpToLocation)).matches("JUMPDEST")) {
            //This marks a valid jump
            i = Integer.parseInt(split.get(4), 16);
            split.set(3, "None");
            split.remove(4);
            currentBranch.add(split.toString());
        }else {
            split.set(3, "Failed Jump");
            split.remove(4);
            currentBranch.add(split.toString());
            addBranch();
        }
    }

    private void doJumpI() {
        String info = finished.get(i);
        if(info.contains("VALID")) {
            doJump();
        }else {
            List<String> split = Arrays.asList(info.split("//s+"));
            split.set(2, "None");
            addBranch();
        }
    }

    private void addKill() {
        currentBranch.add(finished.get(i));
        addBranch();
    }

}
