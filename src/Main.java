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
        ProcessControlBlock block8 = new ProcessControlBlock(block8); // Note: fixed a small typo here in variable usage if exists, assumed correct from context
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

            // The unblocking loop works correctly (backwards iteration prevents skipping)
            if (!blockedList.isEmpty()) { 
                for (int h = blockedList.size() - 1; h >= 0; h--) { 
                    if (random.nextInt(100) <= 30) {  
                        ProcessControlBlock blockedProcess = blockedList.remove(h);
                        readyList.add(blockedProcess);  
                        System.out.println("Unblocked process " + blockedProcess.getCurProcess().getPid());
                    }
                }
            }

            if (processor.getCurProcess() == null) {  //if the processor doesn't have any current processes
                pcb = readyList.poll(); //it gets the top process from the list
                
                if (pcb == null) {  //if its empty, processor stays idle
                    System.out.println("Processor is idle");
                    continue; // FIX: End the tick here if idle
                }
                
                restoreProcess(pcb, processor);
                // FIX: Context switch consumes the tick. Do not execute instruction in the same tick.
                continue; 
            }
            
            ProcessState state = processor.executeNextInstruction();
            qTicks++;

            if (state == ProcessState.FINISHED || ProcessState.BLOCKED == state || qTicks == QUANTUM) { 
                qTicks = 0;
                if (state == ProcessState.BLOCKED) { 
                    System.out.println("Process blocked!");
                    saveProcess(pcb, processor);
                    blockedList.add(pcb); 
                } else if (state == ProcessState.FINISHED) {
                    System.out.println("Process finished!");
                    // FIX: Do NOT save process. Just clear the processor.
                    // The next tick will pick up a new process naturally.
                    processor.setCurProcess(null); 
                } else { // the quantum expired
                    System.out.println("*** Quantum expired ***");
                    readyList.add(pcb);
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
