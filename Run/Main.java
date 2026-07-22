package Run;
import Multithread.MultiThread;

public class Main {
    public static void main(String[] args) {

        Thread thread = new Thread(new MultiThread());
        thread.start();
    }
}