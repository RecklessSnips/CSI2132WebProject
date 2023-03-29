package Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISQLUpdatable {
    void UpdateFromStatement(Connection conn) throws SQLException;
}
