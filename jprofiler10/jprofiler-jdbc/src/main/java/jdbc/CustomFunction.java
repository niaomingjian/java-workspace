package jdbc;

public class CustomFunction {

    public static boolean sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
