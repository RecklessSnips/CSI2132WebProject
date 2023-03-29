package Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISQLWritable {
    void WriteFromStatement(Connection conn) throws SQLException;
}
