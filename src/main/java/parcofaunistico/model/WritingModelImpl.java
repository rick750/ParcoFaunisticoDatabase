package parcofaunistico.model;

import java.sql.Connection;
import java.util.Objects;

public class WritingModelImpl implements WritingModel{
    
    private final Connection connection;

    public WritingModelImpl(final Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }
}
