package SQL;

import java.sql.CallableStatement;

public class CallResult {
    public CallResult(CallableStatement callableStatement)
    {
        this.statement = callableStatement;
    }
    CallableStatement statement;

    public CallableStatement getStatement() {
        return statement;
    }
}
