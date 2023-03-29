package Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISQLWritable {
    int WriteFromStatement(Connection conn) throws SQLException;
}
