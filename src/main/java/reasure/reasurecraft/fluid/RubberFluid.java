package reasure.reasurecraft.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModFluids;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.init.ModTags;
import reasure.reasurecraft.util.ModResourceLocation;

public abstract class RubberFluid extends FlowingFluid {
    public RubberFluid() {
        super();
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.RUBBER_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.RUBBER.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.RUBBER_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity(state) ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, tileentity);
    }

    @Override
    protected int getSlopeFindDistance(IWorldReader reader) {
        return 1;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.RUBBER.get().defaultBlockState().setValue(FlowingFluidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.RUBBER.get() || fluid == ModFluids.RUBBER_FLOWING.get();
    }

    @Override
    public int getDropOff(IWorldReader reader) {
        return 3;  // 블럭 한칸 지날때마다 LEVEL 이 얼마나 감소갛는가
    }

    @Override
    public int getTickDelay(IWorldReader p_205569_1_) {
        return 50;
    }

    public int getSpreadDelay(World world, BlockPos pos, FluidState state, FluidState state1) {
        int i = this.getTickDelay(world);
        if (!state.isEmpty() && !state1.isEmpty() && !state.getValue(FALLING) && !state1.getValue(FALLING)
                && state1.getHeight(world, pos) > state.getHeight(world, pos) && world.getRandom().nextInt(4) != 0) {
            i *= 4;
        }

        return i;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, IBlockReader reader, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(ModTags.Fluids.RUBBER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0f;
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(
                ModResourceLocation.getId("block/rubber_still"),
                ModResourceLocation.getId("block/rubber_flowing"))
                .translationKey("block.reasurecraft.rubber")
                .density(5000).luminosity(0).viscosity(10000).temperature(0)
                .rarity(Rarity.COMMON).color(0xFFFFFFFF)
                .sound(SoundEvents.HONEY_DRINK, SoundEvents.HONEY_BLOCK_HIT)
                .overlay(ModResourceLocation.getId("block/rubber_overlay"))
                .build(this);
    }

    public static class Flowing extends RubberFluid {
        public Flowing() {
            super();
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        @Override
        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends RubberFluid {
        public Source() {
            super();
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
