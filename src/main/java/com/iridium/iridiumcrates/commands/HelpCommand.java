package com.iridium.iridiumcrates.commands;

import com.iridium.iridiumcrates.IridiumCrates;
import net.md_5.bungee.api.chat.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super(Collections.singletonList("help"), "Displays the plugin commands", "", true);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        int page = 1;
        if (args.length == 2) {
            if (!StringUtils.isNumeric(args[1])) {
                cs.sendMessage(com.iridium.iridiumcrates.utils.StringUtils.color(IridiumCrates.getInstance().getMessages().mustBeANumber.replace("%prefix%", IridiumCrates.getInstance().getConfiguration().prefix)));
                return;
            }
            page = Integer.parseInt(args[1]);
        }
        int maxpage = (int) Math.ceil(IridiumCrates.getInstance().getCommandManager().commands.size() / 18.00);
        int current = 0;
        p.sendMessage(com.iridium.iridiumcrates.utils.StringUtils.color(IridiumCrates.getInstance().getMessages().helpHeader));
        for (Command command : IridiumCrates.getInstance().getCommandManager().commands) {
            if ((p.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("iridiumcrates.")) && command.isEnabled()) {
                if (current >= (page - 1) * 18 && current < page * 18)
                    p.sendMessage(com.iridium.iridiumcrates.utils.StringUtils.color(IridiumCrates.getInstance().getMessages().helpMessage.replace("%command%", command.getAliases().get(0)).replace("%description%", command.getDescription())));
                current++;
            }
        }
        BaseComponent[] components = TextComponent.fromLegacyText(com.iridium.iridiumcrates.utils.StringUtils.color(IridiumCrates.getInstance().getMessages().helpfooter.replace("%maxpage%", maxpage + "").replace("%page%", page + "")));

        for (BaseComponent component : components) {
            if (ChatColor.stripColor(component.toLegacyText()).contains(IridiumCrates.getInstance().getMessages().nextPage)) {
                if (page < maxpage) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is help " + (page + 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(IridiumCrates.getInstance().getMessages().helpPageHoverMessage.replace("%page%", "" + (page + 1))).create()));
                }
            } else if (ChatColor.stripColor(component.toLegacyText()).contains(IridiumCrates.getInstance().getMessages().previousPage)) {
                if (page > 1) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is help " + (page - 1)));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(IridiumCrates.getInstance().getMessages().helpPageHoverMessage.replace("%page%", "" + (page - 1))).create()));
                }
            }
        }
        p.getPlayer().spigot().sendMessage(components);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
