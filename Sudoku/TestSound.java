package Sudoku;

public class TestSound {
    public static void main(String[] args) {
        // Inisialisasi suara
        SoundEffect.initGame();

        // Tes memutar suara
        System.out.println("Memutar suara EAT_FOOD...");
        SoundEffect.EAT_FOOD.play();

        // Tunggu sebentar agar suara selesai diputar
        try {
            Thread.sleep(2000); // 2 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Selesai memutar suara.");
    }
}
