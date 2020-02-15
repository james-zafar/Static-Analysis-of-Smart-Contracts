package src.decode.programFlow;

import src.exceptions.ProgramFlowException;

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
    private List<String> duplicateBranches;
    int current, branchNumber = 1;
    private boolean addToBranch = false;
    public CreateProgramFlow(List<String> opcodes, List<String> finished) {
        this.finished = finished;
        this.opcodes = opcodes;
        ArrayList<String> currentBranch = new ArrayList<>();
        duplicateBranches = new ArrayList<>();
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

        simplifyFlow();

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
        ArrayList<String> currentBranch =  new ArrayList<String>();
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
                currentBranch = new ArrayList<String>();
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
                ArrayList<String> tempBranch = new ArrayList<String>();
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
        addBranch(currentBranch);
    }


    private void simplifyFlow() {
        //Can not amend a map whilst iterating over it, so use tempFlow to store the new flow
        HashMap<Integer, ArrayList<String>> tempFlow = new HashMap<Integer, ArrayList<String>>();
        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            ArrayList<String> values = entry.getValue();
            //If we are at the end of map then break from the loop
            if(entry.getKey() == (programFlow.size() - 1)) {
                break;
            }
            //Else, if values has some content attempt to find identical branches to collapse
            if(!values.isEmpty()) {
                collapseBranches(entry.getKey(), values, tempFlow);
            }
        }
        programFlow = tempFlow;
        removeDuplicateBranches();
    }

    private void removeDuplicateBranches() {
        //After collapsing all branches iterate over the new map and delete any excess branches
        String current = "";
        while(!duplicateBranches.isEmpty()) {
            current = duplicateBranches.get(0);
            List<String> startFinish = Arrays.asList(current.split("\\s+"));
            int start = Integer.parseInt(startFinish.get(0));
            int finish = Integer.parseInt(startFinish.get(1));
            for (int i = start; i <= finish; i++) {
                programFlow.remove(i);
            }
            duplicateBranches.remove(0);
        }
    }

    private void collapseBranches(int key, ArrayList<String> values, HashMap<Integer, ArrayList<String>> tempFlow) {
        StringBuilder sb = new StringBuilder();
        //Marks the start and finish point of branches that can be removed
        int branchStart = 0, branchFinish = 0;
        boolean matchFound = false;
        branchStart = (key + 1);
        for(int i = (key + 1); i < (programFlow.size() - 1); i++) {
            if(branchMatch(i, values)) {
                //Keep a list of duplicate branches that have been collapsed
                sb.append(i).append(" ");
                matchFound = true;
            }else {
                break;
            }
            branchFinish = i;
        }
        if(matchFound) {
            //Only need to add this data if identical branches have been found
            String branchesToRemove = Integer.toString(branchStart) + " " + Integer.toString(branchFinish);
            sb.insert(0, "Branches <");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(">");
            values.add(0, sb.toString());
            duplicateBranches.add(branchesToRemove);
        }
        tempFlow.put(key, values);
    }

    private boolean branchMatch(int key, ArrayList<String> values) {
        //Remove the instruction number from branches to compare the opcodes only
        List<String> temp = Arrays.asList(programFlow.get(key).get(0).split("\\s+"));
        temp = temp.subList(1, temp.size());
        List<String> toCompare = Arrays.asList(values.get(0).split("\\s+"));
        toCompare = toCompare.subList(1, toCompare.size());
        //If they are the same return true
        if(temp.equals(toCompare)) {
            return true;
        }
        //If not then compare then compare the sizes and check if all opcodes are present in both branches
        if(programFlow.get(key).size() == (values.subList(1, values.size()).size())) {
            if(programFlow.get(key).size() == 1 && (values.subList(1, values.size()).size() == 1)) {
                return temp.containsAll(toCompare);
            }
        }
        return false;
    }
}