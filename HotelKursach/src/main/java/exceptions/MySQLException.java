package exceptions;

import java.sql.SQLException;

/**
 * Created by user on 11.12.2017.
 */
public class MySQLException extends SQLException {
    public MySQLException(String message){
        super(message);
    }
}
