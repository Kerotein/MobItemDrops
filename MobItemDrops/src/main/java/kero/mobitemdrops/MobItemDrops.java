package kero.mobitemdrops;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class MobItemDrops extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		reloadConfig();
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		{
		}
		getLogger().info("Mob Item Drops plugin enabled");
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the statistics :-(
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("Mob Item Drops plugin disabled");
	}

	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("mobitemdrops")) {
			if (args.length > 0) {

				if (!sender.hasPermission("mobitemdrops.reload")) {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to use this command!");
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("mobitemdrops.reload")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command!");
						return true;
					}
					reloadConfig();
					sender.sendMessage(ChatColor.GREEN
							+ "TnTLevelLimit config has been reloaded.");
					return true;
				}
				sender.sendMessage("---------- Mob Item Drops ----------");
				sender.sendMessage("/mobitemdrops reload");
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public final void on(EntityDeathEvent e) {
		if (getConfig().getBoolean("debug", false))
			System.out.println("EntityDeathEvent | "
					+ (e.getEntity().getType().name().toLowerCase())
					+ " | "
					+ getConfig().getBoolean(
							(e.getEntity().getType().name().toLowerCase()),
							true));

		if (getConfig().getString("override-value", "none").equals("none")) {
			if (getConfig().getBoolean(
					(e.getEntity().getType().name().toLowerCase()), true)) {
				return;
			}
		} else if (getConfig().getBoolean("override-value", true)) {
			return;
		}

		e.getDrops().clear();

		if (!getConfig().getBoolean("drop-xp", false))
			e.setDroppedExp(0);
	}
}
