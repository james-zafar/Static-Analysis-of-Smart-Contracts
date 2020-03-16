package decode.programFlow;

import exceptions.ProgramFlowException;
import json.CreateJson;
import ui.java.LaunchWebGUI;
import upload.AWSUpload;
import utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CreateProgramFlow {
    public HashMap<Integer, ArrayList<String>> programFlow;
    public List<Pair<Integer, Integer>> branchLinks;
    private List<String> finished;
    private List<String> opcodes;
    private List<String> index;
    int current, branchNumber = 1;
    private boolean addToBranch = false;
    public CreateProgramFlow(List<String> opcodes, List<String> finished, String output) {
        this.finished = finished;
        this.opcodes = opcodes;
        ArrayList<String> currentBranch = new ArrayList<>();
        branchLinks = new ArrayList<>();
        //Element 0 identifies the type of branch
        currentBranch.add(0, "ROOT");
        programFlow = new HashMap<Integer, ArrayList<String>>();
        index = new ArrayList<>();
        for(current = 0; current < opcodes.size(); current++) {
            if(addToBranch) {
                //"reset" the branch if it has already been added to tree
                currentBranch = new ArrayList<>();
                addToBranch = false;
            }
            //Add to branch after checking opcode type
            manageOpcode(this.opcodes.get(current), currentBranch);
            if(current == opcodes.size()) {
                addBranch(currentBranch);
            }
        }
        //If there is still data in the branch, add it to the tree
        if(!currentBranch.isEmpty() && !addToBranch) {
            addBranch(currentBranch);
        }
        //index is stored in pairs to identify any parts not yet analysed
        if(!index.isEmpty()) {
            missedOpcodes();
        }
        SimplifyFlow sp = new SimplifyFlow(programFlow, branchLinks);
        outputData(output, sp);
        branchLinks = sp.getSimplifiedLinks();
        if(output.matches("web")) {
            new CreateJson(programFlow, branchLinks);
            new AWSUpload();
            new LaunchWebGUI();
        }
        //finished.forEach(current -> System.out.print(current + "\n"));
        //System.out.println(Arrays.deepToString(programFlow.entrySet().toArray()));

    }

    private void outputData(String outputType, SimplifyFlow sp) {
        if(outputType.matches("simpleBranch") || outputType.matches("web")) {
            programFlow = sp.getSimplifiedFlow();
        }
        assert programFlow != null;
        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            String listString = String.join(", ", entry.getValue());
            System.out.println("Branch = " + entry.getKey() + ", Opcodes = " + listString);
        }
    }

    private void missedOpcodes() {
        int start, finish;
        String temp;
        List<String> tempArr;
        ArrayList<String> currentBranch =  new ArrayList<>();
        try {
            //Reserved branch is added to ensure missed codes are inserted in the correct place
            branchNumber = findReservedBranch();
            //Remove empty reserved branch to add the current to
            programFlow.remove(branchNumber);
            currentBranch.add("Not executed");
        }catch(ProgramFlowException e) {
            throw new RuntimeException("Unreachable statement - no reserved branch exists");
        }
        temp = index.get(0);
        //String contains two ints separated by space marking start and finish points
        tempArr = Arrays.asList(temp.split("\\s+"));
        start = Integer.parseInt(tempArr.get(0));
        finish = Integer.parseInt(tempArr.get(1));
        for(current = start; current < finish; current++) {
            if(addToBranch) {
                //Create new branch if current has already been added
                currentBranch = new ArrayList<>();
                addToBranch = false;
            }
        }
        index.remove(0);
        if(!currentBranch.isEmpty()) {
            addBranch(currentBranch);
        }
        if(!index.isEmpty()) {
            //recursively call if there are more instances of indexes to be analysed
            missedOpcodes();
        }
    }

    private int findReservedBranch() throws ProgramFlowException {
        /*
        Search for and return the first instance of a reserved branch
         */
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
                    branch.add(finished.get(current));
            }
        }catch(ProgramFlowException e) {
            //Exception thrown if invalid jumps are attempted
            e.printStackTrace();
        }
    }

    private synchronized void addBranch(ArrayList<String> currentBranch) {
        if(branchLinkPossible(programFlow.get((branchNumber - 1)))) {
            if(!(branchLinkExists((branchNumber - 1), branchNumber))) {
                branchLinks.add(new Pair<>((branchNumber - 1), branchNumber));
            }
        }
        //Synchronized method for adding branches to ensure branches are added in the correct order
        if(programFlow.containsKey(branchNumber)) {
            branchNumber++;
        }
        programFlow.put(branchNumber, currentBranch);
        branchNumber++;
        addToBranch = true;
    }

    private void addJump(ArrayList<String> currentBranch) throws ProgramFlowException {
        String temp = finished.get(current);
        //Split current into [instructionNo, opcodeNo, opcodeType, addedToStack, removedFromStack]
        List<String> split = new LinkedList<>(Arrays.asList(temp.split("\\s+")));
        //Index 4 will always contain an int denoting the "jump to" location
        String jumpToLocation = split.get(4).replaceAll("\\D+", "");
        try {
            if (opcodes.get(Integer.parseInt(jumpToLocation)).matches("JUMPDEST")) {
                //This marks a valid jump
                String checkLater = (current + 1) + " " + jumpToLocation;
                index.add(checkLater);
                current = Integer.parseInt(split.get(4), 16) - 1;
                split.set(3, "None");
                split.remove(4);
                currentBranch.add(split.toString());
                ArrayList<String> tempBranch = new ArrayList<>();
                tempBranch.add("Reserved");
                addBranch(tempBranch);
            }else {
                addFailedJump(split, "Jump conditions not met", currentBranch);
            }
        }catch(IndexOutOfBoundsException e) {
                addFailedJump(split, "The jump location does not exist", currentBranch);
                throw new ProgramFlowException("The jump location does not exist", e);
        }finally {
            //If an exception is thrown, deal with it in manageOpcode() and keep executing
            current++;
            manageOpcode(this.opcodes.get(current), currentBranch);
        }
    }

    private void addFailedJump(List<String> split, String reason, ArrayList<String> currentBranch) {
        branchLinks.add(new Pair<>((branchNumber - 1), branchNumber));
        String setReason = "Failed jump: " + reason;
        split.set(3, setReason);
        split.remove(4);
        currentBranch.add(split.toString());
        addBranch(currentBranch);
    }

    private void doJumpI(ArrayList<String> currentBranch) throws ProgramFlowException {
        //JumpI is essentially "Jump If", if the test is passed then addJump as normal
        String info = finished.get(current);
        if(info.contains("VALID")) {
            addJump(currentBranch);
        }else {
            List<String> split = Arrays.asList(info.split("\\s+"));
            split.set(2, "None");
            addBranch(currentBranch);
        }
    }

    private void addKill(ArrayList<String> currentBranch) {
        //Kill is any stopping/reverting function which alters the flow of execution
        currentBranch.add(finished.get(current));
        branchLinks.add(new Pair<>((branchNumber - 1), branchNumber));
        addBranch(currentBranch);
    }

    private boolean branchLinkPossible(List<String> branchFrom) {
        //If origin branch is empty, return false
        if(branchFrom == null) return false;
        for(String current : branchFrom) {
            //If branch contains stopping operations, return false
            if(current.contains("STOP") || current.contains("KILL")
                || current.contains("HALT") || current.contains("SELFDESTRUCT")) {
                return false;
            }
        }
        return true;
    }

    private boolean branchLinkExists(int branchFrom, int branchTo) {
        //Create a new instance of pair with the pair being searched for
        Pair<Integer, Integer> newPair = new Pair<>(branchFrom, branchTo);
        return(newPair.pairExists(branchLinks, newPair));
    }
}
