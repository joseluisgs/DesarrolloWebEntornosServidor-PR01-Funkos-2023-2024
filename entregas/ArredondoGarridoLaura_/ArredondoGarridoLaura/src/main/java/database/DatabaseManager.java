package database;

import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.UUID;
import com.opencsv.CSVReader;

public class DatabaseManager {

    public static void main(String[] args) {
        String csvFile = "src/main/csv/funkos.csv";
        String propertiesFile = "database.properties";
        try {
            DataBaseConfig config = loadDatabaseConfig(propertiesFile);
            Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());

            String insertQuery = "INSERT INTO FUNKOS (COD, NOMBRE, MODELO, PRECIO, FECHA_LANZAMIENTO) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] nextLine;

            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                UUID cod = UUID.fromString(nextLine[0]);
                String nombre = nextLine[1];
                String modelo = nextLine[2];
                double precio = Double.parseDouble(nextLine[3]);
                String fechaLanzamiento = nextLine[4];

                preparedStatement.setObject(1, cod);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, modelo);
                preparedStatement.setDouble(4, precio);
                preparedStatement.setString(5, fechaLanzamiento);

                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
            connection.close();
            reader.close();

            System.out.println("Los datos se han cargado correctamente en la base de datos.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataBaseConfig loadDatabaseConfig(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            return new DataBaseConfig(url, username, password);
        }
    }
}