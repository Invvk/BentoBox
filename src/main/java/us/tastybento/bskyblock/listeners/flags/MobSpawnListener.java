/**
 * 
 */
package us.tastybento.bskyblock.listeners.flags;

import java.util.Optional;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import us.tastybento.bskyblock.BSkyBlock;
import us.tastybento.bskyblock.database.objects.Island;
import us.tastybento.bskyblock.lists.Flags;

/**
 * Handles natural mob spawning.
 * @author tastybento
 *
 */
public class MobSpawnListener extends AbstractFlagListener {

    public MobSpawnListener(BSkyBlock plugin) {
        super(plugin);
    }

    /**
     * Prevents mobs spawning naturally
     *
     * @param e
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onNaturalMobSpawn(final CreatureSpawnEvent e) {
        // If not in the right world, return
        if (!inWorld(e.getEntity())) {
            return;
        }
        // Deal with natural spawning
        if (e.getSpawnReason().equals(SpawnReason.NATURAL)
                || e.getSpawnReason().equals(SpawnReason.JOCKEY)
                || e.getSpawnReason().equals(SpawnReason.CHUNK_GEN)
                || e.getSpawnReason().equals(SpawnReason.DEFAULT)
                || e.getSpawnReason().equals(SpawnReason.MOUNT)
                || e.getSpawnReason().equals(SpawnReason.JOCKEY)
                || e.getSpawnReason().equals(SpawnReason.NETHER_PORTAL)) {
            Optional<Island> island = plugin.getIslands().getIslandAt(e.getLocation());
            if (island.isPresent()) {
                if (e.getEntity() instanceof Monster || e.getEntity() instanceof Slime) {             
                    if (!island.get().isAllowed(Flags.MOB_SPAWN)) {
                        // Mobs not allowed to spawn
                        e.setCancelled(true);
                        return;
                    }
                } else if (e.getEntity() instanceof Animals) {
                    if (!island.get().isAllowed(Flags.MONSTER_SPAWN)) {
                        // Mobs not allowed to spawn
                        e.setCancelled(true);
                        return;
                    }
                }
            } else {
                // Outside of the island
                if (e.getEntity() instanceof Monster || e.getEntity() instanceof Slime) {             
                    if (!isDefaultAllowed(Flags.MOB_SPAWN)) {
                        // Mobs not allowed to spawn
                        e.setCancelled(true);
                        return;
                    }
                } else if (e.getEntity() instanceof Animals) {
                    if (!isDefaultAllowed(Flags.MONSTER_SPAWN)) {
                        // Mobs not allowed to spawn
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

}
