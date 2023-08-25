package org.example;

import com.fasterxml.jackson.databind.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.*;

import java.sql.*;
import java.util.*;

public class UpdateOfferFunction {

    final static String DB_USER = "ksilveira";
    final static String DB_PASSWORD = "trocarSENHA##2023";

    final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";
    final static String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION= (RETRY_COUNT=2)(RETRY_DELAY=3)(ADDRESS=(protocol=tcps)(PORT=1522)(HOST=10.159.7.11))(CONNECT_DATA=(SERVICE_NAME=g913a3e4a2806b8_distemiss_high.adb.oraclecloud.com))(SECURITY=(ssl_server_dn_match=no)))";

    // private OracleDataSource oracleDataSource

    public List handleRequest() throws Exception {
        final String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION= (RETRY_COUNT=2)(RETRY_DELAY=3)(ADDRESS=(protocol=tcps)(PORT=1522)(HOST=10.159.7.11))(CONNECT_DATA=(SERVICE_NAME=g913a3e4a2806b8_distemiss_high.adb.oraclecloud.com))(SECURITY=(ssl_server_dn_match=no)))";
        OracleDataSource oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(DB_URL);
        oracleDataSource.setUser(DB_USER);
        oracleDataSource.setPassword(DB_PASSWORD);
        Properties connproperty = new Properties();
        connproperty.setProperty("oracle.jdbc.DRCPConnectionClass", "DRCP_connect_class");

        Map<String, Object> response = new HashMap<>();
        oracleDataSource.setConnectionProperties(connproperty);
        List<HashMap<String, Object>> recordList = null;
        System.out.println("Handle Request");
        try (OracleConnection conn = (OracleConnection) oracleDataSource.getConnection()) {
            System.out.println(conn.getCurrentSchema());
            conn.attachServerConnection();
            try (Statement statement = conn.createStatement()) {
                System.out.println("Starting to select in database");
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM DV01DISTEMISSO.TSDEINDEXADOR")) {
                    recordList = convertResultSetToList(resultSet);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                conn.detachServerConnection((String) null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(new ObjectMapper().writeValueAsString(recordList));
        return recordList;
    }

    private List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

}
