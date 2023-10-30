package systems.kscott.randomspawnplus.spawn;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import systems.kscott.randomspawnplus.RandomSpawnPlus;
import systems.kscott.randomspawnplus.util.Locations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnCacher {

    public static SpawnCacher INSTANCE;
    private final RandomSpawnPlus plugin;
    @Getter
    private boolean spawnsRequireSaving;
    @Getter
    private List<String> cachedSpawns;

    public SpawnCacher(RandomSpawnPlus plugin) {
        this.plugin = plugin;
        this.spawnsRequireSaving = false;
        this.cachedSpawns = new ArrayList<>();
        cacheSpawns();
    }

    public static void initialize(RandomSpawnPlus plugin) {
        INSTANCE = new SpawnCacher(plugin);
    }

    public static SpawnCacher getInstance() {
        return INSTANCE;
    }

    private void cacheSpawns() {

        FileConfiguration spawns = plugin.getSpawns();
        FileConfiguration config = plugin.getConfig();

        SpawnFinder finder = SpawnFinder.getInstance();

        List<String> locationStrings = spawns.getStringList("spawns");

        cachedSpawns.addAll(locationStrings);

        int missingLocations = config.getInt("spawn-cache-target") - locationStrings.size();

        if (missingLocations <= 0) {
            return;
        }

        List<String> newLocations = new ArrayList<>();

        Bukkit.getLogger().info("Caching " + missingLocations + " spawns.");
        for (int i = 0; i <= missingLocations; i++) {
            plugin.foliaLib.getImpl().runLater(() -> {
                Location location = null;
                boolean valid = false;

                while (!valid) {
                    location = finder.getCandidateLocation();
                    valid = finder.checkSpawn(location);
                }

                newLocations.add(Locations.serializeString(location));
            }, 1);
        }

        plugin.foliaLib.getImpl().runTimer(() -> {
            /* Wait for all spawns to be cached */
            if (newLocations.size() <= missingLocations) {
                if (plugin.getConfig().getBoolean("debug-mode")) {
                    System.out.println(newLocations.size() + ", " + missingLocations);
                }
            } else {
                cachedSpawns.addAll(newLocations);
                /* Save spawns to file */
                save();
                plugin.foliaLib.getImpl().cancelAllTasks();
            }
        }, 10, 10);
    }

    public Location getRandomSpawn() {
        int element = ThreadLocalRandom.current().nextInt(cachedSpawns.size());
        return Locations.deserializeLocationString(cachedSpawns.get(element));
    }

    public void deleteSpawn(Location location) {
        cachedSpawns.removeIf(locationString -> Locations.serializeString(location).equals(locationString));
        spawnsRequireSaving = true;
    }

    public void save() {
        plugin.getSpawnsManager().getConfig().set("spawns", cachedSpawns);
        plugin.getSpawnsManager().save();
    }
}
