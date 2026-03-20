package org.example;

import java.sql.*;
public class Rollback {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try{ conn = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword());
             Statement stmt = conn.createStatement();

             conn.setAutoCommit(false);
             //quitamos el autocomit para poder hacerlo manualmente

            String sqlDelete ="DROP TABLE EMPLEADO CASCADE CONSTRAINTS";
            String sql = "CREATE TABLE empleado (" + "id NUMBER PRIMARY KEY, " + "nombre VARCHAR(100), " + "salario NUMBER (10, 2))";
            stmt.executeUpdate(sqlDelete);
            stmt.executeUpdate(sql);

            String insertar = "INSERT INTO empleado VALUES (1, 'Jose', 2000)";
            String insertar1 = "INSERT INTO empleado VALUES (2, 'Juanma', 1000)";
            String insertar2 = "INSERT INTO empleado VALUES (3, 'Pepe', 2050)";
            String insertar3 = "INSERT INTO empleado VALUES (4, 'Rosa', 234.32)";
            String insertar4 = "INSERT INTO empleado VALUES (5, 5000)";
            stmt.executeUpdate(insertar);
            stmt.executeUpdate(insertar1);
            stmt.executeUpdate(insertar2);
            //hacemos el commit aqui para observar qyue no se guardan todos los inserts
            conn.commit();
            stmt.executeUpdate(insertar3);
            stmt.executeUpdate(insertar4);
            System.out.println("Insertdo");
            stmt.close();
            conn.close();

        }catch(SQLException e){
            //si salta excepcion, hacemos el rollback
            conn.rollback();
            System.out.println("Error, ejecutando rollback");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}