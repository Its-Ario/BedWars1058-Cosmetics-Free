

package me.defender.cosmetics.api.category.glyphs.util;

import com.hakan.core.HCore;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class glyphUtil
{
    public static BukkitTask task = null;
    
    public static void sendglyphs(Player p, File file, Location loc) {
        try {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            ImageParticles particles = new ImageParticles(image, 1);
            particles.setAnchor(50, 10);
            particles.setDisplayRatio(0.1);
            Map<Location, Color> particle = particles.getParticles(loc, loc.getPitch(), 180.0f);
            for (Location spot : particle.keySet()) {
                HCore.syncScheduler().run(() -> new ParticleBuilder(ParticleEffect.REDSTONE, spot)
                        .setParticleData(new RegularColor(particle.get(spot).getRed(), particle.get(spot).getGreen(), particle.get(spot).getBlue()))
                        .display());
            }
        }catch (Exception exception){

        }
    }
       

}
