package dev.bennett.utiltests;

import dev.bennett.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Map;

public class ConnectionTest {

    @Test
    void generates_connection(){
        Connection conn = ConnectionUtil.createConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }

    @Test
    void get_environment_variable(){
        String env = System.getenv("CONN_DETAILS");
        System.out.println(env);
    }
}
