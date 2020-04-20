package com.darkender.plugins.feathergolem;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class FeatherGolem extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                checkChunks();
            }
        }, 20L, 20L);
    }
    
    private void checkChunks()
    {
        for(World world : getServer().getWorlds())
        {
            for(Chunk chunk : world.getLoadedChunks())
            {
                checkChunk(chunk);
            }
        }
    }
    
    private void checkChunk(Chunk c)
    {
        Set<IronGolem> golems = new HashSet<>();
        for(Entity e : c.getEntities())
        {
            if(e.getType() != EntityType.IRON_GOLEM)
            {
                continue;
            }
            golems.add((IronGolem) e);
        }
        
        if(golems.size() > 3)
        {
            for(IronGolem golem : golems)
            {
                if(!golem.hasPotionEffect(PotionEffectType.REGENERATION))
                {
                    golem.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 1));
                    golem.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));
                    golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 5));
                    golem.setMetadata("last-floating", new FixedMetadataValue(this, System.nanoTime()));
                    golem.setFireTicks(20);
                }
            }
        }
    }
}
