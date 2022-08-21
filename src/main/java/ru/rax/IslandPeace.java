package ru.rax;

public class IslandPeace {
    int ChunkX;
    int ChunkZ;
    int Status;
    int NumberIsland;

    public IslandPeace(int chunkX, int chunkZ, int status, int numberIsland) {
        ChunkX = chunkX;
        ChunkZ = chunkZ;
        Status = status;
        NumberIsland = numberIsland;
    }

    public void setNumberIsland(int numberIsland) {
        NumberIsland = numberIsland;
    }

    public int getNumberIsland() {
        return NumberIsland;
    }

    public int getChunkX() {
        return ChunkX;
    }

    public int getChunkZ() {
        return ChunkZ;
    }

    public int getStatus() {
        return Status;
    }

    public void setChunkX(int chunkX) {
        ChunkX = chunkX;
    }

    public void setChunkZ(int chunkZ) {
        ChunkZ = chunkZ;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
