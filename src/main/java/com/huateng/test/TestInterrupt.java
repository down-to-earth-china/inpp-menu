package com.huateng.test;

/**
 * @author shuaion 2018/7/17
 **/
public class TestInterrupt {

    public static class Worker implements Runnable {

        public void run() {
            System.out.println("Worker started.");

            for (int i = 0 ; i < 30 ;i++){
                if (i==20){
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("------------");
                }
                try {
                    Thread.sleep(10);
                    //Thread.currentThread().join();
                } catch (Exception e) {
                    System.out.println("====InterruptedException=====");
                    e.printStackTrace();
                    return;
                }
                System.out.println("=====i====="+i+",状态="+Thread.currentThread().isInterrupted());
            }

            System.out.println("Worker stopped.");
        }
    }

    public static void main(String[] args){
        Thread t = new Thread(new Worker());
        t.start();
        Thread.currentThread().interrupt();

        System.out.println("Main thread stopped.");
    }
}
