package parcofaunistico;
 
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.UIManager;

import parcofaunistico.model.ReadingModelImpl;
import parcofaunistico.model.WritingModelImpl;
import parcofaunistico.view.MainView;
import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.DAOUtils;
 
public final class App {
    
    public static void main(String[] args) throws Exception {
        setUpUI();
        final var connection = localMySQLConnection();
        final var readingModel = new ReadingModelImpl(connection);
        final var writingModel = new WritingModelImpl(connection);
        final var mainController = new MainController(readingModel, writingModel);
        var view = new MainView(mainController, () -> {
            try {
               connection.close();
            } catch (Exception ignored) {}
        });
        var readingController = new ReadingController(readingModel, view);
        view.setReadingController(readingController);
    }

    private static void setUpUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {

        }
        UIManager.put("Panel.background", new Color(18, 30, 49));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 20));
        UIManager.put("Button.background", new Color(255, 185, 0));
        UIManager.put("Button.foreground", new Color(18, 30, 49));
        UIManager.put("BigLabel.font", new Font("Segoe UI", Font.PLAIN, 36));
        UIManager.put("Field.font", new Font("Segoe UI", Font.PLAIN, 20));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 20));
        UIManager.put("Label.background", new Color(173, 216, 230));
        UIManager.put("Label.foreground", new Color(144, 238, 144));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 20));
    }    

    public static Connection localMySQLConnection() throws Exception {
        Properties p = new Properties();

        Path external = Path.of("db.properties");
        if (Files.exists(external)) {
            try (InputStream in = new FileInputStream(external.toFile())) {
                p.load(in);
            }
        } else {
            try (InputStream in = DAOUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in != null) p.load(in);
            }
        }

        String host = System.getenv().getOrDefault("DB_HOST", p.getProperty("db.host", "localhost"));
        String port = System.getenv().getOrDefault("DB_PORT", p.getProperty("db.port", "3306"));
        String db   = System.getenv().getOrDefault("DB_NAME", p.getProperty("db.name", "parco_faunistico"));
        String user = System.getenv().getOrDefault("DB_USER", p.getProperty("db.user", "root"));
        String pass = System.getenv().getOrDefault("DB_PASS", p.getProperty("db.pass", ""));

        String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false", host, port, db);
        return DriverManager.getConnection(url, user, pass);
    }
}
