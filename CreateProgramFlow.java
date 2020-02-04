import java.util.*;
public class CreateProgramFlow {
    public HashMap<Integer, ArrayList<String>> programFlow;
    private List<String> finished;
    private List<String> opcodes;
    private List<Integer> index;
    int i, branchNumber = 1;
    public CreateProgramFlow(List<String> opcodes, List<String> finished) throws ProgramFlowException {
        this.finished = finished;
        this.opcodes = opcodes;
        ArrayList<String> currentBranch = new ArrayList<String>();
        programFlow = new HashMap<Integer, ArrayList<String>>();
        index = new ArrayList<Integer>();
        for(i = 0; i < opcodes.size(); i++) {
            currentBranch = new ArrayList<String>();
            index.add(i);
            manageOpcode(this.opcodes.get(i), currentBranch);
        }
        if(!currentBranch.isEmpty()) {
            addBranch(currentBranch);
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            ArrayList<String> temp1 = programFlow.get(1);
            String listString = String.join(", ", entry.getValue());
            System.out.println("Key = " + entry.getKey() + ", Value = " + listString);
        }
        //finished.forEach(current -> System.out.print(current + "\n"));
        //System.out.println(Arrays.deepToString(programFlow.entrySet().toArray()));

    }

    private void missedOpcodes(List<Integer> codes) {
        List<Integer> missedOpcodes = new ArrayList<Integer>();

    }

    private void manageOpcode(String code, ArrayList<String> branch) throws ProgramFlowException {
        switch(code) {
            case "JUMP":
                addBranch(branch);
                doJump(branch);
                break;
            case "JUMPI":
                doJumpI(branch);
                break;
            case "SELFDESTRUCT":
            case "STOP":
                addKill(branch);
                break;
            default:
                branch.add(finished.get(i));
        }
    }

    private synchronized void addBranch(ArrayList<String> currentBranch) {
        if(programFlow.containsKey(branchNumber)) {
            branchNumber++;
        }
        programFlow.put(branchNumber, currentBranch);
        branchNumber++;
    }

    private void doJump(ArrayList<String> currentBranch) throws ProgramFlowException {
        String temp = finished.get(i);
        List<String> split = new LinkedList<String>(Arrays.asList(temp.split("\\s+")));
        String jumpToLocation = split.get(4).replaceAll("\\D+", "");
        try {
            if (opcodes.get(Integer.parseInt(jumpToLocation)).matches("JUMPDEST")) {
                //This marks a valid jump
                i = Integer.parseInt(split.get(4), 16) - 1;
                split.set(3, "None");
                split.remove(4);
                currentBranch.add(split.toString());
            } else {
                addFailedJump(split, "Jump conditions not met", currentBranch);
            }
        }catch(IndexOutOfBoundsException e) {
            try {
                throw new ProgramFlowException("The jump location does not exist", e);
            }catch(ProgramFlowException ex) {
                ex.printStackTrace();
            }
        }finally {
            addFailedJump(split, "The jump location does not exist", currentBranch);
            i++;
            index.add(i);
            manageOpcode(this.opcodes.get(i), currentBranch);
        }
    }

    private void addFailedJump(List<String> split, String reason, ArrayList<String> currentBranch) {
        String setReason = "Failed jump: " + reason;
        split.set(3, setReason);
        split.remove(4);
        currentBranch.add(split.toString());
        addBranch(currentBranch);
    }

    private void doJumpI(ArrayList<String> currentBranch) throws ProgramFlowException {
        String info = finished.get(i);
        if(info.contains("VALID")) {
            doJump(currentBranch);
        }else {
            List<String> split = Arrays.asList(info.split("//s+"));
            split.set(2, "None");
            addBranch(currentBranch);
        }
    }

    private void addKill(ArrayList<String> currentBranch) {
        currentBranch.add(finished.get(i));
        addBranch(currentBranch);
    }

}