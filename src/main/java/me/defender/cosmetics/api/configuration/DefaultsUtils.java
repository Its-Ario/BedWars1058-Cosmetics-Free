package me.defender.cosmetics.api.configuration;

import com.hakan.core.HCore;
import me.defender.cosmetics.api.enums.ConfigType;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultsUtils {

    /**
     * This method will save default values from the
     * plugin jar yml for example, from Glyph.yml to
     * the file located in the plugin folder, example
     * resources/Glyph.yml -> pluginFolder/Categories/Glyph.yml
     */
    public void saveAllDefaults(){
        for(ConfigType type : ConfigType.values()){
            ConfigManager config = ConfigUtils.get(type);
            InputStream defaultConfigStream = Utility.plugin().getResource(type.getFileName() + ".yml");
            if(defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
                config.addDefaults(defaultConfig);
                config.save();
            }
            config.reload();
            config.save();
            String extrasPath = "Extras.fill-empty.";
            if(config.isFirstTime() && !config.getBoolean(extrasPath + "enabled")){
                config.set(extrasPath + "enabled", true);
                config.set(extrasPath + "item", "BLACK_STAINED_GLASS_PANE:0");
                config.save();
                config.reload();
            }
        }
    }

}
