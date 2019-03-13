
public class ProgressDemo {

    static void updateProgress(double progressPercentage) {
        final int width = 50; // progress bar width in chars

    }

    public static void main(String[] args) {
        System.out.print("percent completed:   0 %");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.printf("pro\b\b\b\b\b%3d %%", (i + 1));
        }

    }
}
