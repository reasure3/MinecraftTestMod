package reasure.reasurecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class FrameBlock extends Block implements IWaterLoggable {
    private static final VoxelShape shape;
    private static final BooleanProperty WATER_LOGGED = BlockStateProperties.WATERLOGGED;

    static {
        VoxelShape base = Block.box(0, 0, 0, 16, 1, 16);
        VoxelShape column1 = Block.box(0, 1, 0, 1, 15, 1);
        VoxelShape column2 = Block.box(15, 1, 0, 16, 15, 1);
        VoxelShape column3 = Block.box(0, 1, 15, 1, 15, 16);
        VoxelShape column4 = Block.box(15, 1, 15, 16, 15, 16);
        VoxelShape top = Block.box(0, 15, 0, 16, 16, 16);
        shape = VoxelShapes.or(base, column1, column2, column3, column4, top);
    }

    public FrameBlock(Properties properties) {
        super(properties.noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(WATER_LOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);

        if (state.is(this)) {
            return state.setValue(WATER_LOGGED, Boolean.FALSE);
        }

        FluidState fluidState = context.getLevel().getFluidState(pos);
        return this.defaultBlockState().setValue(WATER_LOGGED, fluidState.getType() == Fluids.WATER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState directionState, IWorld world, BlockPos curPos, BlockPos facingPos) {
        if (state.getValue(WATER_LOGGED)) {
            world.getLiquidTicks().scheduleTick(curPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, directionState, world, curPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATER_LOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATER_LOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}
