package Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISQLReadable {
    void ReadFromResultSet(ResultSet resultSet) throws SQLException;
}
