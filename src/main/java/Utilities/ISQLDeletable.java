package Utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISQLDeletable {
    void DeleteFromStatement (Connection conn) throws SQLException;
}
