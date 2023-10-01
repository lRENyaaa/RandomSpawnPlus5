package systems.kscott.randomspawnplus.spawn;

import lombok.Getter;

@Getter
public class SpawnRegion {
    int minX;
    int maxX;
    int minZ;
    int maxZ;

    public SpawnRegion(int minX, int maxX, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }
}
