package com.iridium.iridiumcrates.commands;

import com.iridium.iridiumcrates.IridiumCrates;
import com.iridium.iridiumcrates.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload your configurations", "iridiumcrates.reload", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        IridiumCrates.getInstance().loadConfigs();
        sender.sendMessage(StringUtils.color(IridiumCrates.getInstance().getMessages().reloaded.replace("%prefix%", IridiumCrates.getInstance().getConfiguration().prefix)));
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
