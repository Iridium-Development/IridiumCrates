package com.iridium.iridiumcrates.database;

import com.iridium.iridiumcrates.IridiumCrates;
import com.iridium.iridiumcrates.configs.SQL;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DatabaseManager {

    private static final SQL SQL_CONFIG = IridiumCrates.getInstance().getSql();

    private final ConnectionSource connectionSource;

    public DatabaseManager() throws SQLException {
        String databaseURL = getDatabaseURL();

        connectionSource = new JdbcConnectionSource(
                databaseURL,
                SQL_CONFIG.username,
                SQL_CONFIG.password,
                DatabaseTypeUtils.createDatabaseType(databaseURL)
        );

    }

    private @NotNull String getDatabaseURL() {
        switch (SQL_CONFIG.driver) {
            case MYSQL:
            case MARIADB:
            case POSTGRESQL:
                return "jdbc:" + SQL_CONFIG.driver + "://" + SQL_CONFIG.host + ":" + SQL_CONFIG.port + "/" + SQL_CONFIG.database;
            case SQLSERVER:
                return "jdbc:sqlserver://" + SQL_CONFIG.host + ":" + SQL_CONFIG.port + ";databaseName=" + SQL_CONFIG.database;
            case H2:
                return "jdbc:h2:file:" + SQL_CONFIG.database;
            case SQLITE:
                return "jdbc:sqlite:" + new File(IridiumCrates.getInstance().getDataFolder(), SQL_CONFIG.database + ".db");
        }

        throw new RuntimeException("How did we get here?");
    }
}
