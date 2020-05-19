package opcodes;

public class Opcode {
    private int gasCost;
    private String code, name, description;
    boolean extraData;

    /**
     *
     * @param ref the code associated with the opcode
     * @param name the full name of the opcode
     * @param desc A complete description of the opcode action
     * @param gasCost the gas cost of executing this opcode
     * @param extraData ture if data is added/removed from the stack, false otherwise
     */
    public Opcode(String ref, String name, String desc, int gasCost, boolean extraData) {
        this.code = ref;
        this.name = name;
        this.description = desc;
        this.gasCost = gasCost;
        this.extraData = extraData;
    }

    public Opcode() { };

    /**
     *
     * @return the code associated with the opcode
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @return the full name of the opcode
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the complete description of the opcodes action
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * NOT USED IN THIS IMPLEMENTATION
     * @return the cost of executing this operation
     */
    public int getGasCost() {
        return this.gasCost;
    }

    /**
     *
     * @return whether or not this operation modifies the stack
     */
    public boolean extraDataRequired() {
        return this.extraData;
    }

    /**
     *
     * @param code The code associated with the opcode
     * @return A complete reference to the opcode
     */
    public Opcode getOpcode(String code) {
        return new Opcode(code, this.name, this.description, this.gasCost, this.extraData);
    }

    /**
     *
     * @return A string representation of the opcode
     */
    @Override
    public String toString() {
        return ((this.code) + " " +  this.name + " " +
                this.description + " " + String.valueOf(this.gasCost));
    }
}