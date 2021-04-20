import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
//
public class Main {
    public static void main(String[] args) throws Exception {
        com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "");
        UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        Interface frame = new Interface();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
