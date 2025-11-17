import java.util.Random;

public class SimProcess {
    int pid;
    String procName;
    int totalInstructions;

    public SimProcess(int pid, String procName, int totalInstructions) {
        this.pid = pid;
        this.procName = procName;
        this.totalInstructions = totalInstructions;
    }

    public int getPid() {
        return pid;
    }

    public int getTotalInstructions() {
        return totalInstructions;
    }

    public String getProcName() {
        return procName;
    }

    public ProcessState execute(int i) {
        System.out.println("PID: " + pid + " Process: " + procName + " Instruction Number: " + i);
        if (i >= totalInstructions) {
            return ProcessState.FINISHED;
        } else {
            Random rand = new Random();
            int num = rand.nextInt(100) + 1;
            if (num <= 15) {
                return ProcessState.BLOCKED;
            }
            return ProcessState.READY;
        }


    }
}


