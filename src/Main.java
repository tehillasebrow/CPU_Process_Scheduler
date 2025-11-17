import java.util.*;
//Tehilla Sebrow T00521812
public class Main {
    public static void main(String args[]) {
        SimProcessor processor = new SimProcessor();//instantiate  the processor object and processes
        SimProcess process1 = new SimProcess(1, "read file", 100);
        ProcessControlBlock block1 = new ProcessControlBlock(process1);
        SimProcess process2 = new SimProcess(2, "write file", 200);
        ProcessControlBlock block2 = new ProcessControlBlock(process2);
        SimProcess process3 = new SimProcess(3, "save to memory", 300);
        ProcessControlBlock block3 = new ProcessControlBlock(process3);
        SimProcess process4 = new SimProcess(4, "run Google", 400);
        ProcessControlBlock block4 = new ProcessControlBlock(process4);
        SimProcess process5 = new SimProcess(5, "run Java program", 150);
        ProcessControlBlock block5 = new ProcessControlBlock(process5);
        SimProcess process6 = new SimProcess(6, "play music on Spotify", 250);
        ProcessControlBlock block6 = new ProcessControlBlock(process6);
        SimProcess process7 = new SimProcess(7, "send message on Google Voice", 350);
        ProcessControlBlock block7 = new ProcessControlBlock(process7);
        SimProcess process8 = new SimProcess(8, "download file", 225);
        ProcessControlBlock block8 = new ProcessControlBlock(process8);
        SimProcess process9 = new SimProcess(9, "print document", 225);
        ProcessControlBlock block9 = new ProcessControlBlock(process9);
        SimProcess process10 = new SimProcess(10, "save file", 125);
        ProcessControlBlock block10 = new ProcessControlBlock(process10);

        final int QUANTUM = 5;//quantum ticks
        Queue<ProcessControlBlock> readyList = new LinkedList<>();//the ready list
        ArrayList<ProcessControlBlock> blockedList = new ArrayList<>();// the blocked list

        readyList.add(block1);//added all the processes to the ready list
        readyList.add(block2);
        readyList.add(block3);
        readyList.add(block4);
        readyList.add(block5);
        readyList.add(block6);
        readyList.add(block7);
        readyList.add(block8);
        readyList.add(block9);
        readyList.add(block10);

        Random random = new Random();//makes random object for the blocked process
        ProcessControlBlock pcb = null;

        int qTicks = 0;//count of quantum ticks. it gets reset after 5 times.

        for (int i = 0; i < 3000; i++) {//iterates 3000 times
            System.out.println("Step: " + (i + 1));

            if (!blockedList.isEmpty()) { //if the blocked list has blocked processes
                for (int h = blockedList.size() - 1; h >= 0; h--) { //the program picks a random blocked process to unblock
                    if (random.nextInt(100) <= 30) {  // about a 30% chance
                        ProcessControlBlock blockedProcess = blockedList.remove(h);
                        readyList.add(blockedProcess);  // and place on the ready list
                        System.out.println("Unblocked process " + blockedProcess.getCurProcess().getPid());
                    }
                }
            }
            if (processor.getCurProcess() == null) {  //if the processor doesn't have any current processes
                pcb = readyList.poll(); //it gets the top process from the list
                if (pcb == null) {  //if its empty, processor stays idle
                    System.out.println("Processor is idle");
                    continue;//ends current step
                }
                restoreProcess(pcb, processor);
            }
            ProcessState state = processor.executeNextInstruction();
            qTicks++;

            if (state == ProcessState.FINISHED || ProcessState.BLOCKED == state || qTicks == QUANTUM) { //3 reasons why you would preform a context switch
                qTicks = 0;
                if (state == ProcessState.BLOCKED) { //if the process is blocked, you add it to the blocked list and it will continue later
                    System.out.println("Process blocked!");
                    saveProcess(pcb, processor);
                    blockedList.add(pcb); //  added to the blocked list
                } else if (state == ProcessState.FINISHED) {
                    System.out.println("Process finished!");
                    saveProcess(pcb, processor);
                } else { // the quantum expired

                    System.out.println("*** Quantum expired ***");
                    readyList.add(pcb);
                    // Saves the current instruction from the processor and stores it in the pcb before switching
                    saveProcess(pcb, processor);
                }
            }
        }
        System.out.println("All processes completed.");
    }

    public static void restoreProcess(ProcessControlBlock pcb, SimProcessor processor) {
        System.out.println("Context switch: Restoring process " + pcb.getCurProcess().getPid() +
                " Instruction: " + pcb.getCurrentInstruction() +
                " R1: " + pcb.getRegister1() + " R2: " + pcb.getRegister2() +
                " R3: " + pcb.getRegister3() + " R4: " + pcb.getRegister4());
        processor.setCurProcess(pcb.getCurProcess());
        processor.setCurrentInstruction(pcb.getCurrentInstruction());
        processor.setRegisters(pcb.getRegister1(), pcb.getRegister2(),
                pcb.getRegister3(), pcb.getRegister4());
    }

    public static void saveProcess(ProcessControlBlock pcb, SimProcessor processor) {
        System.out.println("Context switch: Saving process " + processor.getCurProcess().getPid() +
                " Instruction: " + processor.getCurrentInstruction() +
                " R1: " + processor.getRegister1() + " R2: " + processor.getRegister2() +
                " R3: " + processor.getRegister3() + " R4: " + processor.getRegister4());
        pcb.setCurrentInstruction(processor.getCurrentInstruction());
        pcb.setRegisters(processor.getRegister1(), processor.getRegister2(), processor.getRegister3(), processor.getRegister4());
        processor.setCurProcess(null);

    }
}
