import java.util.*;
import java.util.logging.*;

/*

When a thread is about to access a member of a class, the thread checks
to see if the class has been initialized.

Ignoring serious errors, there are four possible cases:

1. The class is not yet initialized.
2. The class is being initialized by the current thread: a recursive
   request for initialization.
3. The class is being initialized by some thread other than the current
   thread.
4. The class is already initialized.

Main thread invokes Lazy.main -> case 1
  1. Starts initializing the class.
  2. Sets initialized = false.
  3. Creates and starts a background thread.
  4. Waits for the background thread to finish.

Background thread invokes its run method:
  1. Before attempting to set Lazy.initialized to true,
     checks whether Lazy has been initialized.
  2. It has not been initialized -> case 3.
  3. Waits on the Lazy to finish initialization.
  4. Main thread which is doing the initialization,
     is waiting for the background thread to complete.

*/


public class Lazy {
  private static boolean initialized = false;
  static Logger myLogger = Logger.getGlobal();
  static {
    myLogger.setLevel(Level.FINEST);
    Thread currentThread = Thread.currentThread();
    myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
      "; thread id: " + currentThread.getId() +
      "; inside static block of Lazy");

    Scanner scan = new Scanner(System.in);
    System.out.println("Press Enter to continue...");
    String varStr = scan.nextLine();

    Thread t = new Thread(new Runnable() {
      public void run() {
        Thread currentThread = Thread.currentThread();
        myLogger.entering("Anonymous Thread", "run", null);
        myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
          "; thread id: " + currentThread.getId() +
          "; initialized before assignment: " + initialized);
        initialized = true;
        myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
          "; thread id: " + currentThread.getId() +
          "; initialized before assignment: " + initialized);
      }
    });
    myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
      "; thread id: " + currentThread.getId() +
      "; before calling start() to launch a new thread");
    t.start();
    try {
      myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
        "; thread id: " + currentThread.getId() +
        "; before calling join()");
      t.join();
      myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
        "; thread id: " + currentThread.getId() +
        "; after calling join()");
    }
    catch (InterruptedException e) {
      throw new AssertionError(e);
    }
  }
  public static void main(String[] args) {
    Thread currentThread = Thread.currentThread();
    myLogger.entering("Lazy", "main", args);
    myLogger.log(Level.INFO, "Thread name: " + currentThread.getName() +
      "; thread id: " + currentThread.getId() +
      "; initialized: " +initialized);

  }
}