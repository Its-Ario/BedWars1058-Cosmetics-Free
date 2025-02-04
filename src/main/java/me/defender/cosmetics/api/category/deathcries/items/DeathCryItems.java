package me.defender.cosmetics.api.category.deathcries.items;

import com.cryptomorin.xseries.XSound;
import me.defender.cosmetics.api.category.deathcries.DeathCry;
import me.defender.cosmetics.api.enums.RarityType;
import me.defender.cosmetics.api.util.StringUtils;
import me.defender.cosmetics.api.configuration.ConfigManager;
import me.defender.cosmetics.api.configuration.ConfigUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DeathCryItems {

    public void registerConfigItems(){
        FileConfiguration config = ConfigUtils.getDeathCries().getYml();
        ConfigManager configManager = ConfigUtils.getDeathCries();
        ConfigurationSection deathCrySection = config.getConfigurationSection("death-cry");

        for (String identifier : deathCrySection.getKeys(false)) {
            String path = "death-cry." + identifier + ".";
            DeathCry deathCry = new DeathCry() {
                @Override
                public ItemStack getItem() {
                    return configManager.getItemStack(path + "item");
                }

                @Override
                public String base64() {
                    String[] base64 = configManager.getString(path + "item").split(":", 3);
                    return base64[2];
                }

                @Override
                public String getIdentifier() {
                    return identifier;
                }

                @Override
                public String getDisplayName() {
                    return StringUtils.replaceHyphensAndCaptalizeFirstLetter(identifier);
                }

                @Override
                public List<String> getLore() {
                    if(getRarity() == RarityType.NONE){
                        return List.of("&7Selecting this option disables your", "&7Death Cry.");
                    }
                    return Arrays.asList("&7Select " + getDisplayName() + " as your Death cry");
                }

                @Override
                public int getPrice() {
                    return config.getInt(path + "price");
                }

                @Override
                public RarityType getRarity() {
                    return RarityType.valueOf(config.getString(path + "rarity").toUpperCase());
                }

                @Override
                public XSound getSound() {
                    return XSound.matchXSound(config.getString(path + "sound")).get();
                }

                @Override
                public float getPitch() {
                    return Float.parseFloat(config.getString(path + "pitch"));
                }

                @Override
                public float getVolume() {
                    return Float.parseFloat(config.getString(path + "volume"));
                }
            };
            deathCry.register();
        }
    }
}
