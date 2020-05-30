package com.coungard.client;

import org.apache.log4j.Logger;

import java.sql.*;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());

    /**
     * Имена параметров для таблицы PRMS
     */
    public enum PRMS {
        CSV_DOWNLOADED_COUNT;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            return DriverManager.getConnection("jdbc:derby:" + Settings.dbDir +
                    ";create=true;dataEncryption=true;bootPassword=coungard");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized static void createDB() {
        LOGGER.info("createDB begin");
        Connection conn = null;
        try {
            conn = getConnection();
            try {
                Statement st = conn.createStatement();
                st.setMaxRows(1);
                st.executeQuery("SELECT ID, \"Vendor\", \"Number\", \"Description\", \"Price\", \"Count\" FROM APP.PriceItems");
            } catch (Exception ex) {
                LOGGER.warn(ex.getMessage());
                try {
                    conn.createStatement().executeUpdate("DROP TABLE APP.PriceItems");
                } catch (Exception ignored) {
                }
                conn.createStatement().executeUpdate("CREATE TABLE APP.PriceItems("
                        + " ID BIGINT NOT NULL,"
                        + " \"Vendor\" VARCHAR(64),"
                        + " \"Number\" VARCHAR(64),"
                        + " \"Description\" VARCHAR(512),"
                        + " \"Price\" DECIMAL(18,2) NOT NULL,"
                        + " \"Count\" INTEGER NOT NULL,"
                        + " PRIMARY KEY (ID))");
            } finally {
                conn.close();
            }

            conn = getConnection();
            try {
                Statement st = conn.createStatement();
                st.setMaxRows(1);
                st.executeQuery("SELECT NAME, VAL FROM APP.PRMS");
            } catch (Exception ex) {
                LOGGER.warn(ex.getMessage());
                try {
                    conn.createStatement().executeUpdate("DROP TABLE APP.PRMS");
                } catch (Exception ignored) {
                }
                conn.createStatement().executeUpdate("CREATE TABLE APP.PRMS("
                        + " NAME VARCHAR(128) NOT NULL,"
                        + " VAL VARCHAR(128) NOT NULL,"
                        + " PRIMARY KEY (NAME))");
            } finally {
                conn.close();
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        LOGGER.info("createDB end");
    }

    /**
     * Обновить параметр name на val. Если параметр ранее не существует он создастся
     *
     * @param name наименование параметра
     * @param val  новое значение параметра
     */
    synchronized public static void updatePrm(String name, String val) {
        LOGGER.info("begin updatePrm(name=" + name + ", val=" + val + ")");
        Connection conn = null;
        try {
            conn = getConnection();
            if (val != null) {
                PreparedStatement pt = conn.prepareStatement("UPDATE APP.PRMS SET VAL=? WHERE NAME=?");
                pt.setString(1, val);
                pt.setString(2, name);
                if (pt.executeUpdate() == 0) {
                    pt.close();
                    pt = conn.prepareStatement("INSERT INTO APP.PRMS VALUES(?, ?)");
                    pt.setString(1, name);
                    pt.setString(2, val);
                    pt.executeUpdate();
                }
            } else {
                PreparedStatement pt = conn.prepareStatement("DELETE FROM APP.PRMS WHERE NAME=?");
                pt.setString(1, name);
                pt.executeUpdate();
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * @param name наименование параметра
     * @return значение параметра
     */
    synchronized public static String getPrm(String name) {
        LOGGER.info("begin getPrm(name=" + name + ")");
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement pt = conn.prepareStatement("SELECT VAL FROM APP.PRMS WHERE NAME=?");
            pt.setString(1, name);
            pt.executeQuery();
            ResultSet rs = pt.getResultSet();
            if (rs.next()) {
                String ret = rs.getString("VAL");
                LOGGER.info("return " + ret);
                return ret;
            }
            return null;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
