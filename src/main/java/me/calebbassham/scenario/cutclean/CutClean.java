package me.calebbassham.scenario.cutclean;

import me.calebbassham.scenariomanager.Scenario;
import me.calebbassham.scenariomanager.ScenarioManager;
import me.calebbassham.scenariomanager.ScenarioStartEvent;
import me.calebbassham.scenariomanager.ScenarioStopEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static org.bukkit.Material.*;

public class CutClean extends Scenario implements Listener {

    private HashMap<Material, Material> convertToSmelted;

    @EventHandler
    public void onScenStart(ScenarioStartEvent e) {
        if (e.getScenario() != this) return;
        convertToSmelted = new HashMap<Material, Material>();
        convertToSmelted.put(GOLD_ORE, GOLD_INGOT);
        convertToSmelted.put(IRON_ORE, IRON_INGOT);
        convertToSmelted.put(POTATO, BAKED_POTATO);
        convertToSmelted.put(BEEF, COOKED_BEEF);
        convertToSmelted.put(CHICKEN, COOKED_CHICKEN);
        convertToSmelted.put(SALMON, COOKED_SALMON);
        convertToSmelted.put(COD, COOKED_COD);
        convertToSmelted.put(PORKCHOP, COOKED_PORKCHOP);
        convertToSmelted.put(MUTTON, COOKED_MUTTON);
        convertToSmelted.put(RABBIT, COOKED_RABBIT);
    }

    @EventHandler
    public void onScenStop(ScenarioStopEvent e) {
        if (e.getScenario() != this) return;
        convertToSmelted = null;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        if(!ScenarioManager.isWorld(e.getLocation().getWorld())) return;
        ItemStack item = e.getEntity().getItemStack();

        if (item.getType() == GOLD_ORE || item.getType() == IRON_ORE) {
            ExperienceOrb orb = (ExperienceOrb) e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.EXPERIENCE_ORB);
            orb.setExperience(item.getAmount());
        }

        Material smelted = convertToSmelted.get(item.getType());
        if (smelted != null) item.setType(smelted);
    }

}
