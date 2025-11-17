public class ProcessControlBlock {

    SimProcess curProcess;
    int currentInstruction=0;
    public ProcessControlBlock(SimProcess curProcess){
        this.curProcess = curProcess;
    }
    public SimProcess getCurProcess() {
        return curProcess;
    }

    public int getCurrentInstruction() {
        return currentInstruction;
    }
    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    int register1=0;
    int register2=0;
    int register3=0;
    int register4=0;

    public void setRegisters(int register1, int register2, int register3, int register4) {
        this.register1 = register1;
        this.register2 = register2;
        this.register3 = register3;
        this.register4 = register4;
    }

    public int getRegister1() {
        return register1;
    }

    public int getRegister2() {
        return register2;
    }

    public int getRegister3() {
        return register3;
    }

    public int getRegister4() {
        return register4;
    }

}
