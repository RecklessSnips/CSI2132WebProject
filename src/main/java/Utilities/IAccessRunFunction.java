package Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface IAccessRunFunction {
    boolean tryRun(Connection conn) throws SQLException;
}
