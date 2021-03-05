package com.iridium.iridiumcrates;

import com.heretere.hdl.dependency.maven.annotation.MavenDependency;
import com.heretere.hdl.relocation.annotation.Relocation;
import com.heretere.hdl.spigot.DependencyPlugin;
import com.iridium.iridiumcrates.commands.CommandManager;
import com.iridium.iridiumcrates.configs.Configuration;
import com.iridium.iridiumcrates.configs.Messages;
import com.iridium.iridiumcrates.configs.SQL;
import com.iridium.iridiumcrates.database.DatabaseManager;
import lombok.Getter;
import org.bstats.bukkit.Metrics;

import java.sql.SQLException;

@MavenDependency("com|fasterxml|jackson|core:jackson-databind:2.12.1")
@MavenDependency("com|fasterxml|jackson|core:jackson-core:2.12.1")
@MavenDependency("com|fasterxml|jackson|core:jackson-annotations:2.12.1")
@MavenDependency("com|fasterxml|jackson|dataformat:jackson-dataformat-yaml:2.12.1")
@MavenDependency("org|yaml:snakeyaml:1.27")
@Relocation(from = "org|yaml", to = "com|iridium|iridiumcrates")
@Getter
public class IridiumCrates extends DependencyPlugin {

    private static IridiumCrates instance;
    private Persist persist;
    private DatabaseManager databaseManager;

    private CommandManager commandManager;

    private Configuration configuration;
    private Messages messages;
    private SQL sql;

    @Override
    protected void enable() {
        getDataFolder().mkdir();
        instance = this;
        this.persist = new Persist(Persist.PersistType.YAML);
        this.commandManager = new CommandManager("crates");
        loadConfigs();
        saveConfigs();
        try {
            this.databaseManager = new DatabaseManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        registerListeners();
        new Metrics(this, 10563);
        getLogger().info("----------------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("----------------------------------------");
    }

    @Override
    protected void disable() {
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Disabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    @Override
    public void load() {
    }

    public void loadConfigs() {
        this.configuration = persist.load(Configuration.class);
        this.messages = persist.load(Messages.class);
        this.sql = persist.load(SQL.class);
    }

    public void saveConfigs() {
        this.persist.save(configuration);
        this.persist.save(messages);
        this.persist.save(sql);
    }

    public void registerListeners() {
    }

    public static IridiumCrates getInstance() {
        return instance;
    }
}
