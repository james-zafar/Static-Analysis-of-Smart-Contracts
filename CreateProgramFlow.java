import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CreateProgramFlow {
    public HashMap<Integer, ArrayList<String>> programFlow;
    private List<String> finished;
    private List<String> opcodes;
    private List<String> index;
    int i, branchNumber = 1;
    private boolean addToBranch = false;
    public CreateProgramFlow(List<String> opcodes, List<String> finished) {
        this.finished = finished;
        this.opcodes = opcodes;
        ArrayList<String> currentBranch = new ArrayList<String>();
        programFlow = new HashMap<Integer, ArrayList<String>>();
        index = new ArrayList<String>();
        for(i = 0; i < opcodes.size(); i++) {
            if(addToBranch) {
                currentBranch = new ArrayList<String>();
                addToBranch = false;
            }
            manageOpcode(this.opcodes.get(i), currentBranch);
            if(i == opcodes.size()) {
                addBranch(currentBranch);
            }
        }

        if(!currentBranch.isEmpty() && !addToBranch) {
            addBranch(currentBranch);
        }
        if(!index.isEmpty()) {
            missedOpcodes();
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            String listString = String.join(", ", entry.getValue());
            System.out.println("Key = " + entry.getKey() + ", Value = " + listString);
        }
        //finished.forEach(current -> System.out.print(current + "\n"));
        //System.out.println(Arrays.deepToString(programFlow.entrySet().toArray()));

    }

    private void missedOpcodes() {
        int start, finish;
        String temp;
        List<String> tempArr;
        ArrayList<String> currentBranch =  new ArrayList<String>();;
        System.out.println("I'm executing this...");
        try {
            branchNumber = findReservedBranch();
            programFlow.remove(branchNumber);
            System.out.println("With branch number..." + branchNumber);
            currentBranch.add("Not executed");
        }catch(ProgramFlowException e) {
            e.printStackTrace();
        }
        while(index.size() > 0) {
            temp = index.get(0);
            temp = index.get(0);
            tempArr = Arrays.asList(temp.split("\\s+"));
            start = Integer.parseInt(tempArr.get(0));
            finish = Integer.parseInt(tempArr.get(1));
            for(int j = start; j <= finish; j++) {
                if(addToBranch) {
                    currentBranch = new ArrayList<String>();
                    addToBranch = false;
                }
                manageOpcode(this.opcodes.get(j), currentBranch);
            }
            index.remove(0);
            if(!currentBranch.isEmpty()) {
                addBranch(currentBranch);
            }
            if(!index.isEmpty()) {
                missedOpcodes();
            }
        }
    }

    private int findReservedBranch() throws ProgramFlowException {
        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            ArrayList<String> values = entry.getValue();
            if((values.size() == 1) && (values.get(0).matches("Reserved"))) {
                return entry.getKey();
            }
        }
        throw new ProgramFlowException("No reserved branch exists");
    }

    private void manageOpcode(String code, ArrayList<String> branch) {
        try {
            switch (code) {
                case "JUMP":
                    addJump(branch);
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
        }catch(ProgramFlowException e) {
            e.printStackTrace();
        }
    }

    private synchronized void addBranch(ArrayList<String> currentBranch) {
        if(programFlow.containsKey(branchNumber)) {
            branchNumber++;
        }
        programFlow.put(branchNumber, currentBranch);
        branchNumber++;
        addToBranch = true;
        currentBranch = new ArrayList<String>();
    }

    private void addJump(ArrayList<String> currentBranch) throws ProgramFlowException {
        String temp = finished.get(i);
        List<String> split = new LinkedList<String>(Arrays.asList(temp.split("\\s+")));
        String jumpToLocation = split.get(4).replaceAll("\\D+", "");
        try {
            if (opcodes.get(Integer.parseInt(jumpToLocation)).matches("JUMPDEST")) {
                //This marks a valid jump
                String checkLater = Integer.toString(i) + " " + jumpToLocation;
                index.add(checkLater);
                i = Integer.parseInt(split.get(4), 16) - 1;
                split.set(3, "None");
                split.remove(4);
                currentBranch.add(split.toString());
                ArrayList<String> tempBranch = new ArrayList<String>();
                tempBranch.add("Reserved");
                addBranch(tempBranch);
            }
        }catch(IndexOutOfBoundsException e) {
                throw new ProgramFlowException("The jump location does not exist", e);
        }finally {
            addFailedJump(split, "The jump location does not exist", currentBranch);
            i++;
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
            addJump(currentBranch);
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