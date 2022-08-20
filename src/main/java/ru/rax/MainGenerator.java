package ru.rax;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.PerlinNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGenerator extends ChunkGenerator {
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
        return populators;
    }

    int height, width;

    public MainGenerator(int height, int width) {
        this.height = height;
        this.width = width;
        Bukkit.getLogger().info(0.0002*width + " " + 0.2*height);
    }

    public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid grid) {
        ChunkData data = createChunkData(world);

        PerlinNoiseGenerator perlin = new PerlinNoiseGenerator(world.getSeed());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int nx = ChunkX*16+x;
                int nz = ChunkZ*16+z;

                double noise = 16;
                //double p_noise = noise + perlin.noise(nx*0.002, nz*0.002, 2, 4, 2)*64;
                double p_noise = noise + perlin.noise(nx*0.0002*width, nz*0.0002*width, 2, 4, 0.2*height)*64;
                noise = noise + p_noise;
                double level = 100;
                if (noise > 100) {
                    level = noise;
                }

                for(int y = 0; y < level; y++) {
                    if (y == 0) {
                        data.setBlock(x, 0, z, Material.BEDROCK);
                    } else if (y <= 75) {
                        data.setBlock(x, y, z, Material.STONE);
                    } else if (y <= noise) {
                        if (y >= noise - 1) {
                            if (y > 90 && y <= 100) {
                                data.setBlock(x, y, z, Material.SAND);
                            } else if (y > 100) {
                                data.setBlock(x, y, z, Material.GRASS_BLOCK);
                            } else {
                                data.setBlock(x, y, z, Material.STONE);
                            }
                        } else {
                            data.setBlock(x, y, z, Material.STONE);
                        }
                    } else {
                        data.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
        return data;
    }
}
