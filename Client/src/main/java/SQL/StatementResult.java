package SQL;

import java.sql.ResultSet;
import java.sql.Statement;

public class StatementResult {
    Statement statement;
    ResultSet resultSet;

    public StatementResult(Statement statement, ResultSet resultSet)
    {
        this.statement = statement;
        this.resultSet = resultSet;
    }
}
