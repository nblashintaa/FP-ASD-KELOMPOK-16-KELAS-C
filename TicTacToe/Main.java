//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package TicTacToe;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        System.out.println("Playing Tic Tac Toe");
        SoundEffect soundEffect = new SoundEffect("SoundEffect.wav");
        soundEffect.play();

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException var3) {
            InterruptedException e = var3;
            e.printStackTrace();
        }

        soundEffect.stop();
    }
}