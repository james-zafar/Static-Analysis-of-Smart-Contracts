import java.util.*;
public class CreateProgramFlow {
    public HashMap<Integer, List<String>> programFlow;
    private List<String> finished;
    private List<String> opcodes;
    private List<String> currentBranch;
    private List<Integer> index;
    int i, branchNumber = 1;
    public CreateProgramFlow(List<String> opcodes, List<String> finished) throws ProgramFlowException {
        this.finished = finished;
        this.opcodes = opcodes;
        currentBranch = new ArrayList<String>();
        programFlow = new HashMap<>();
        index = new ArrayList<Integer>();
        for(i = 0; i < opcodes.size(); i++) {
            index.add(i);
            manageOpcode(this.opcodes.get(i));
        }
        for (Map.Entry<Integer, List<String>> entry : programFlow.entrySet()) {
            String listString = String.join(", ", entry.getValue());
            System.out.println("Key = " + entry.getKey() + ", Value = " + listString);
        }
        //finished.forEach(current -> System.out.print(current + "\n"));
        //System.out.println(Arrays.asList(programFlow));
        //System.out.println(Arrays.deepToString(programFlow.entrySet().toArray()));

    }

    private void manageOpcode(String code) throws ProgramFlowException {
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

    private synchronized void addBranch() {
        //String listString = String.join(", ", currentBranch);
        //System.out.println("Current branch content: " + listString);
        programFlow.put(branchNumber, currentBranch);
        currentBranch.clear();
        branchNumber++;
    }

    private void doJump() throws ProgramFlowException {
        String temp = finished.get(i);
        List<String> split = new LinkedList<String>(Arrays.asList(temp.split("\\s+")));
        String jumpToLocation = split.get(4).replaceAll("\\D+", "");
        try {
            if (opcodes.get(Integer.parseInt(jumpToLocation)).matches("JUMPDEST")) {
                //This marks a valid jump
                i = Integer.parseInt(split.get(4), 16);
                split.set(3, "None");
                split.remove(4);
                currentBranch.add(split.toString());
            } else {
                addFailedJump(split, "Jump conditions not met");
            }
        }catch(IndexOutOfBoundsException e) {
            try {
                throw new ProgramFlowException("The jump location does not exist", e);
            }catch(ProgramFlowException ex) {
                ex.printStackTrace();
            }
        }finally {
            addFailedJump(split, "The jump location does not exist");
            i++;
            index.add(i);
            manageOpcode(this.opcodes.get(i));
        }
    }

    private void addFailedJump(List<String> split, String reason) {
        String setReason = "Failed jump: " + reason;
        split.set(3, setReason);
        split.remove(4);
        currentBranch.add(split.toString());
        addBranch();
    }

    private void doJumpI() throws ProgramFlowException {
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