package reasure.reasurecraft.block.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import reasure.reasurecraft.init.ModTileEntityTypes;

import java.util.Objects;

public class QuarryTileEntity extends TileEntity implements ITickableTileEntity {
    public QuarryTileEntity() {
        super(ModTileEntityTypes.QUARRY.get());
    }

    @Override
    public void tick() {
        Objects.requireNonNull(this.getLevel()).destroyBlock(this.getBlockPos().below(), true);
    }
}
