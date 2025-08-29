package parcofaunistico.model;

import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.data.Visitatore;

public class WritingModelImpl implements WritingModel{
    
    private final Connection connection;

    public WritingModelImpl(final Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public boolean insertVisitatore(final Map<Parametri, JTextField> textfields) {
        return Visitatore.DAO.insert(connection, textfields);
    }
}
