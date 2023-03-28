package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISQLParsable {
    void ReadFromResultSet (ResultSet resultSet) throws SQLException;
}
