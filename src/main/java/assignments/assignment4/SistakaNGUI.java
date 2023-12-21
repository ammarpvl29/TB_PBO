package assignments.assignment4;

import javax.swing.JFrame;

import assignments.assignment4.backend.SistakaNG;
import assignments.assignment4.frontend.HomeGUI;
import assignments.assignment4.frontend.WelcomePanel;

// Kelas utama program berjalan
public class SistakaNGUI {
    public static void main(String[] args) {
        // Membuat Frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mini Tel - U Lib");
        SistakaNG.registerStaf();

        LibraryDatabase db = new LibraryDatabase();
        db.createNewTables();

        // Memanggil welcome panel dan memulai GUI
        HomeGUI homepage = new HomeGUI(frame);
        new WelcomePanel(homepage);

        frame.setVisible(true);
    }

}