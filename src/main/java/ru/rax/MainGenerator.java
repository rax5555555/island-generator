package ru.rax;

import org.bukkit.Material;
import org.bukkit.World;
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

    public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid grid) {
        ChunkData data = createChunkData(world);

        PerlinNoiseGenerator perlin = new PerlinNoiseGenerator(world.getSeed());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int nx = ChunkX*16+x;
                int nz = ChunkZ*16+z;

                double noise = 64;
                double p_noise = noise + perlin.noise(nx*0.01, nz*0.01, 1, 4, 0.25)*32;
                noise = noise + p_noise;
                for(int y = 0; y < noise; y++) {
                    if (y==0) {
                        data.setBlock(x, 0, z, Material.BEDROCK);
                    } else {
                        data.setBlock(x, y, z, Material.STONE);
                    }
                }
            }
        }
        return data;
    }
}
