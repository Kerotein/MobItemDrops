package kero.mobitemdrops;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
	}

	@Override
	public void onDisable() {
		getLogger().info("Mob Item Drops plugin disabled");
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