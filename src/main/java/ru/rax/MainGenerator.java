package ru.rax;

import org.bukkit.Bukkit;
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

    int height, width;
    int max_height = 0;

    int NumberIsland = 0;
    int countIteration = 0;

    public int getCountIteration() {
        return countIteration;
    }

    ArrayList<IslandPeace> peaceMap = new ArrayList<>();

    ArrayList<Integer> island = new ArrayList<>();

    public String getMax_height() {
        return Integer.toString(max_height);
    }

    public MainGenerator(int height, int width) {
        this.height = height;
        this.width = width;
        Bukkit.getLogger().info(0.0002*width + " " + 0.2*height);
    }

    public ArrayList<IslandPeace> getPeaceMap() {
        return peaceMap;
    }

    public ArrayList<Integer> getIsland() {
        return island;
    }

    public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid grid) {
        ChunkData data = createChunkData(world);

        PerlinNoiseGenerator perlin = new PerlinNoiseGenerator(world.getSeed());

        int height_peace = 0;
        int water_line = 100;

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int nx = ChunkX*16+x;
                int nz = ChunkZ*16+z;

                double noise = 16;
                //double p_noise = noise + perlin.noise(nx*0.002, nz*0.002, 2, 4, 2)*64;
                double p_noise = noise + perlin.noise(nx*0.0002*width, nz*0.0002*width, 2, 4, 0.2*height)*64;
                noise = noise + p_noise;
                double level = water_line;
                if (noise > water_line) {
                    level = noise;
                }

                int max_height_count = 0;

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

                    max_height_count++;
                    //height_peace++;
                }

                if (max_height_count > water_line && max_height_count > max_height) {
                    max_height = max_height_count;
                }
                if (max_height_count > water_line && max_height_count > height_peace) {
                    height_peace = max_height_count;
                }

            }
        }

        if (height_peace > water_line) {
            int collision = 0;
            int numberPiece = 0;
            for (int i = 0; i < peaceMap.size(); i++) {
                if (ChunkX - peaceMap.get(i).getChunkX() >= -10 && ChunkX - peaceMap.get(i).getChunkX() <= 10 &&
                    ChunkZ - peaceMap.get(i).getChunkZ() >= -10 && ChunkZ - peaceMap.get(i).getChunkZ() <= 10) {

                    peaceMap.get(i).setStatus(peaceMap.get(i).getStatus()+1);
                    peaceMap.get(i).setNumberIsland(peaceMap.get(i).getStatus()+1);
                    collision++;
                    numberPiece = peaceMap.get(i).getNumberIsland();
                }
            }

            if (collision == 0) {
                peaceMap.add(new IslandPeace(ChunkX, ChunkZ, 0, NumberIsland));
            } else {
                peaceMap.add(new IslandPeace(ChunkX, ChunkZ, collision, numberPiece));
            }
        }

        for (int i = 0; i < NumberIsland; i++) {
            boolean islandDetect = false;
            for (int j = 0; j < peaceMap.size(); j++) {
                if (peaceMap.get(j).getStatus() > 2 && peaceMap.get(j).getNumberIsland() == i) {
                    islandDetect = true;
                } else {
                    islandDetect = false;
                }
            }
            if (islandDetect) {
                island.add(i);
                for (int j = 0; j < peaceMap.size(); j++) {
                    if (peaceMap.get(j).getNumberIsland() == i) {
                        peaceMap.remove(j);
                        j--;
                    }
                }
            }
        }

        countIteration++;

        return data;
    }
}
