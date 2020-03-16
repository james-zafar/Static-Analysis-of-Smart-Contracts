package decode.programFlow;

import utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class SimplifyFlow {

    private HashMap<Integer, ArrayList<String>> programFlow;
    private List<String> duplicateBranches;
    private List<Pair<Integer, Integer>> branchLinks;

    public SimplifyFlow(HashMap<Integer, ArrayList<String>> programFlow,
                        List<Pair<Integer, Integer>> branchLinks)
    {
        this.programFlow = programFlow;
        this.branchLinks = branchLinks;
        duplicateBranches = new ArrayList<>();
        simplify();
        //Check for invalid branch links after collapsing branches
        checkBranches();
        //Some links will be missing after collapsing branches
        missingBranchLinks();
    }

    public HashMap<Integer, ArrayList<String>> getSimplifiedFlow() {
        return this.programFlow;
    }

    public List<Pair<Integer, Integer>> getSimplifiedLinks() {
        return this.branchLinks;
    }

    private void simplify() {
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
        String current;
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
        int branchStart, branchFinish = 0;
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

    private void checkBranches() {
        //copy of branch links required to prevent concurrent modification exception
        List<Pair<Integer, Integer>> tempBranchLinks = new ArrayList<Pair<Integer, Integer>>();
        //All branch IDs
        List<Integer> allKeys = new ArrayList<>(programFlow.keySet());
        for(Pair<Integer, Integer> current : branchLinks) {
            //Check if source is valid
            Integer present = searchList(allKeys, current.getFirst());
            if(present != null) {
                //check if target is valid
                present = searchList(allKeys, current.getSecond());
                if(present != null) {
                    tempBranchLinks.add(current);
                }
            }
        }
        branchLinks.clear();
        branchLinks = tempBranchLinks;
    }

    private Integer searchList(List<Integer> toSearch, Integer searchFor) {
        return toSearch.stream().filter(current -> (current.equals(searchFor))).findAny().orElse(null);
    }

    private void missingBranchLinks() {
        for (Map.Entry<Integer, ArrayList<String>> entry : programFlow.entrySet()) {
            List<String> temp = entry.getValue();
            //Check to see if this branch contains collapsed branches
            if(temp.get(0).contains("Branches")) {
                //If so get all branches inside by removing all non-digits from first element
                String allNums = temp.get(0).replaceAll("\\D+", " ");
                allNums = allNums.trim().replaceAll(" +", ",");
                List<Integer> allBranches = Arrays.stream(allNums.split(","))
                                                    .map(Integer::parseInt)
                                                    .collect(Collectors.toList());
                Optional<Integer> minBranch = allBranches.stream().min(Integer::compare);
                Optional<Integer> maxBranch = allBranches.stream().max(Integer::compare);
                //If min and max are present (i.e. non empty list)
                if(minBranch.isPresent() && maxBranch.isPresent()) {
                    int source = minBranch.get() - 1;
                    int target = maxBranch.get() + 1;
                    if(!branchExists(source, target)) {
                        branchLinks.add(new Pair<>(source, target));
                    }
                }
            }
        }
    }

    private boolean branchExists(int source, int target) {
        if(source == target) return false;
        if(source <= 0 || target <= 0) return  false;
        //Create a new instance of pair with the pair being searched for
        Pair<Integer, Integer> newPair = new Pair<>(source, target);
        return(newPair.pairExists(branchLinks, newPair));
    }

}
