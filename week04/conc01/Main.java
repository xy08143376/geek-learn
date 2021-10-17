package conc01;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author itguoy
 * @date 2021-10-14 15:56
 */
public class Main {


    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {

        System.out.println("main start...");
        String s1 = method1();
        System.out.println(s1);
        System.out.println("main end ...");

        System.out.println("main start...");
        FutureTask<String> futureTask = method2();
        Thread thread = new Thread(futureTask);
        thread.start();
        String s2 = futureTask.get();
        System.out.println(s2);
        System.out.println("main end ...");

        System.out.println("main start...");
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        method3(completableFuture, executorService);
        String s3 = completableFuture.get();
        System.out.println(s3);
        executorService.shutdown();
        System.out.println("main end ...");

        System.out.println("main start...");
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        Future<String> future = method4(executorService2);
        String s4 = future.get();
        System.out.println(s4);
        executorService.shutdown();
        System.out.println("main end ...");

        System.out.println("main start...");
        final Object obj = new Object();
        String s5 = method5(obj);
        System.out.println(s5);
        System.out.println("main end ...");

        System.out.println("main start...");
        final Object obj2 = new Object();
        String s6 = method6(obj2);
        System.out.println(s6);
        System.out.println("main end ...");

        System.out.println("main start...");
        String s7 = method7();
        System.out.println(s7);
        System.out.println("main end ...");

        System.out.println("main start...");
        Lock lock = new ReentrantLock();
        String s8 = method8(lock);
        System.out.println(s8);
        System.out.println("main end ...");

        System.out.println("main start...");
        Lock lock2 = new ReentrantLock();
        String s9 = method9(lock2);
        System.out.println(s9);
        System.out.println("main end ...");

        System.out.println("main start...");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String s10 = method10(countDownLatch);
        System.out.println(s10);
        System.out.println("main end ...");

        System.out.println("main start...");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        String s11 = method11(cyclicBarrier);
        System.out.println(s11);
        System.out.println("main end ...");

        System.out.println("main start...");
        Semaphore semaphore = new Semaphore(1);
        String s12 = method12(semaphore);
        System.out.println(s12);
        System.out.println("main end ...");

    }

    private static String method12(Semaphore semaphore) {
        Thread thread = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("method11 start...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        });
        thread.start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
        return "method11 end...";
    }

    private static String method11(CyclicBarrier barrier) throws InterruptedException, BrokenBarrierException {
        Thread thread = new Thread(() -> {
            System.out.println("method11 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        barrier.await();
        return "method11 end...";
    }

    private static String method10(CountDownLatch latch) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("method10 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        latch.await();
        return "method10 end...";
    }

    private static String method9(Lock lock) {
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            lock.lock();
            System.out.println("method9 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "method9 end...";
    }

    private static String method8(Lock lock) throws InterruptedException {
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            System.out.println("method8 start...");
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(2);
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        thread.start();
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
        return "method8 end...";
    }

    private static String method7() {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                System.out.println("method7 start...");
                TimeUnit.SECONDS.sleep(2);
                LockSupport.unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        LockSupport.park();
        return "method7 end...";
    }


    private static Future<String> method4(ExecutorService executorService) {
        Future<String> future = executorService.submit(() -> {
            System.out.println("method4 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "method4 end...";
        });
        return future;
    }

    private static String method6(final Object obj) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("method6 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                synchronized (obj) {
                    obj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        synchronized (obj) {
            obj.wait();
            return "method6 end...";
        }
    }

    private static String method5(Object obj) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("method5 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                synchronized (obj) {
                    obj.notify();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        synchronized (obj) {
            obj.wait();
            return "method5 end...";
        }
    }

    private static FutureTask<String> method2() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("method2 start...");
            TimeUnit.SECONDS.sleep(2);
            return "method2 end";
        });
        return futureTask;
    }

    private static void method3(CompletableFuture<String> completableFuture, ExecutorService executorService) {
        executorService.submit(() -> {
            System.out.println("method3 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete("method3 end...");
        });
        completableFuture.thenApply((s) -> {
            return s;
        });
    }

    private static String method1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("method1 start ...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        return "method1 end";
    }
}
