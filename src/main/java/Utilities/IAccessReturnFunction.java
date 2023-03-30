package Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface IAccessReturnFunction {
    AccessResult tryReturn(Connection conn) throws SQLException;
}
