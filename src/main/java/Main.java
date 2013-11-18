public class Main {

    public static void main(String[] args) throws InterruptedException {
        String className = "Sinatra";
        String packageName = "com.io.test";
        JsonPojoConverter converter = new JsonPojoConverter(packageName, className);
        BlockingCallThread thread1 = new BlockingCallThread(7000, converter);
        BlockingCallThread thread2 = new BlockingCallThread(2000, converter);
        thread1.start();

        thread2.start();
    }

}
