package org.example.DataBaseManager;

import org.example.Enums.MessageType;
import org.example.connection.NotificationManager;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseVerifier {

    public static void verifySchema(DBManager db) {
        try (Statement stmt = db.getConnection().createStatement()) {
            // Tabla users
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                privilegios TEXT NOT NULL CHECK (privilegios IN ('admin', 'client'))
            );
        """);

            // Tabla address
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS address (
                addressid SERIAL PRIMARY KEY,
                zipcode VARCHAR(9)
            );
        """);

            // Tabla organization
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS organization (
                organizationid SERIAL PRIMARY KEY,
                fullname TEXT NOT NULL,
                annualturnover DOUBLE PRECISION NOT NULL CHECK (annualturnover > 0),
                employeescount INTEGER NOT NULL CHECK (employeescount > 0),
                addressid INTEGER NOT NULL REFERENCES address(addressid) ON DELETE CASCADE
            );
        """);

            // Tabla coordinates
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS coordinates (
                coordinatesid SERIAL PRIMARY KEY,
                x INTEGER NOT NULL CHECK (x >= 0 AND x <= 395),
                y DOUBLE PRECISION NOT NULL
            );
        """);

            // Tabla worker
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS worker (
                workerid SERIAL PRIMARY KEY,
                name TEXT NOT NULL CHECK (length(name) > 0),
                coordinatesid INTEGER NOT NULL REFERENCES coordinates(coordinatesid) ON DELETE CASCADE,
                creationdate TEXT NOT NULL DEFAULT now(),
                salary INTEGER NOT NULL CHECK (salary > 0),
                enddate TEXT,
                position TEXT CHECK (
                    position IS NULL OR position IN (
                        'HEAD_OF_DEPARTMENT', 'DEVELOPER', 'COOK', 'MANAGER_OF_CLEANING'
                    )
                ),
                status TEXT CHECK (
                    status IS NULL OR status IN (
                        'FIRED', 'HIRED', 'RECOMMENDED_FOR_PROMOTION', 'REGULAR', 'PROBATION'
                    )
                ),
                organizationid INTEGER REFERENCES organization(organizationid) ON DELETE CASCADE,
                whocreate TEXT NOT NULL
            );
        """);
        } catch (SQLException e) {
            NotificationManager.getInstance().pushMessage(
                    "Schema initialization failed:\n" + e.getMessage(), MessageType.ERROR);
            System.exit(1);
        }

    }
}
