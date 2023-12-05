package systems.kscott.randomspawnplus.util;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import systems.kscott.randomspawnplus.RandomSpawnPlus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ConfigFile {

    @Getter
    private FileConfiguration config;
    private final String fileName;

    public ConfigFile(String fileName) {
        this.fileName = fileName;
        reload();
    }

    private File createFile() {
        File customConfigFile = new File(RandomSpawnPlus.getInstance().getDataFolder(), fileName);
        if (!customConfigFile.exists() || customConfigFile.getParentFile().mkdirs()) {
            RandomSpawnPlus.getInstance().saveResource(fileName, false);
        }
        return customConfigFile;
    }

    public void reload() {
        File customConfigFile = createFile();
        config = new YamlConfiguration();
        try {
            config.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(Paths.get(RandomSpawnPlus.getInstance().getDataFolder().getAbsolutePath(), fileName).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
