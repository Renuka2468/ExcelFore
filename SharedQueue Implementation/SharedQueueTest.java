import java.util.LinkedList;
import java.util.Queue;

class SharedQueue {

    //Defining Queue and lock object 
    private final Queue<String> queue = new LinkedList<>();
    private final Object lock = new Object();

    //Add method for adding string value or massage by writer
    public void add(String str) {

         //synchronized block to acquire lock from thread and adding wait for thread
        synchronized(lock) {
            queue.add(str);
            lock.notifyAll();
        }
    }

    //Remove method for removing string value by consumer
    public String remove() throws InterruptedException {
        
        //synchronized block to released lock from thread and adding wait for thread 
        synchronized(lock) {         
            while(queue.isEmpty()) {
                lock.wait();
            }
            return queue.remove();
        }
    }
}

//Consumer class for reads and remove data
class Consumer implements Runnable {
    private final SharedQueue queue;

    public Consumer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String message = queue.remove();
                System.out.println("Consumer " + Thread.currentThread().getId() + " consumed " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//Writer class writes data
class Writer implements Runnable {
    private final SharedQueue queue;

    public Writer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int count = 1;
        while(count <= 5) {
            String message = "Message " + count;
            queue.add(message);
            System.out.println("Writer added " + message);
            count++;
        }
    }
}

public class SharedQueueTest {
    public static void main(String[] args) {
        //Creating queue object
        SharedQueue queue = new SharedQueue();

        Thread writerThread = new Thread(new Writer(queue));
        //calls writer run method
        writerThread.start();

        for(int i = 0; i < 5; i++) {
            Thread consumerThread = new Thread(new Consumer(queue));
            //calls consumer run method
            consumerThread.start();
        }
    }
}
