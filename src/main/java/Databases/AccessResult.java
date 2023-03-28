package Databases;

import java.sql.Connection;
import java.sql.SQLException;

class AccessResult {
    private boolean success;
    private Object result;

    public AccessResult (boolean success, Object result) {
        this.success = success;
        this.result = result;
    }

    public boolean didSucceed() {
        return success;
    }

    public Object getResult() {
        return result;
    }

    public static AccessResult failed () {
        return new AccessResult(false, null);
    }
}

interface IAccessRunFunction {
    boolean tryRun(Connection conn) throws SQLException;
}

interface IAccessReturnFunction {
    AccessResult tryReturn(Connection conn) throws SQLException;
}
