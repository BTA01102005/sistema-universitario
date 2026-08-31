import modelo.Universidad;
import ui.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Universidad universidad = Universidad.crearUniversidadInicial();

        SwingUtilities.invokeLater(() -> {
            MainFrame ventana = new MainFrame(universidad);
            ventana.setVisible(true);
        });
    }
}
