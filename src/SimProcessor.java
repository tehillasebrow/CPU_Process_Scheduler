import java.util.Random;

public class SimProcessor {
    SimProcess curProcess;

    public SimProcess getCurProcess() {
        return curProcess;
    }

    public void setCurProcess(SimProcess curProcess) {
        this.curProcess = curProcess;
    }

    int register1;
    int register2;
    int register3;
    int register4;

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

    int currentInstruction;
    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }
    public int getCurrentInstruction() {
        return currentInstruction;
    }
    public ProcessState executeNextInstruction() {
       ProcessState p = curProcess.execute(currentInstruction);
        currentInstruction++;
        Random rand = new Random();
        setRegisters(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100),rand.nextInt(100));
        return p;
    }
}
