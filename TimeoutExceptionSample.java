import java.util.concurrent.*;

public class TimeoutExceptionSample {
  public static void main(String[] args) {
    executeTimeout();
  }

  public static void executeTimeout() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<?> future = executor.submit(TimeoutExceptionSample::wait100);
    System.out.println("Futureに設定");

    try {


      // Wait for 10 seconds for the task to complete
      future.get(10000,TimeUnit.MILLISECONDS);
      System.out.println("完了");
    } catch (TimeoutException e) {
      future.cancel(true);
      System.out.println("時間切れ");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      executor.shutdown();
    }
  }

  //待機用のメソッド
  static void wait100(){
    try {
      Thread.sleep(100000);
      System.out.println("待機完了");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
