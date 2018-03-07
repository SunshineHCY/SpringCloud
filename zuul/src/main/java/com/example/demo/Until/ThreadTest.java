package com.example.demo.Until;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    public static void main(String[] args) {
        TimerLock timerLock1 = new TimerLock(1);
        TimerLock timerLock2 = new TimerLock(2);

        try {
            Thread.sleep(1000);

            Thread thread1 = new Thread(timerLock1);
            Thread thread2 = new Thread(timerLock2);
            thread1.start();
            thread2.start();

            thread2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class TimerLock implements Runnable {

    public TimerLock() {
    }

    public TimerLock(int lockFlag) {
        this.lockFlag = lockFlag;
    }

    private int lockFlag;
    private static final ReentrantLock rLock1 = new ReentrantLock();
    private static final ReentrantLock rLock2 = new ReentrantLock();

    @SuppressWarnings("all")
    public void run() {

        try {
            if (lockFlag == 1) {
                try {
                    rLock1.lockInterruptibly();
                    Thread.sleep(1000);
                    rLock2.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (lockFlag == 2) {
                try {
                    rLock2.lockInterruptibly();
                    Thread.sleep(1000);
                    rLock1.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (rLock1.isHeldByCurrentThread()) {
                rLock1.unlock();
            }
            if (rLock2.isHeldByCurrentThread()) {
                rLock2.unlock();
            }
        }
    }
}
