package reasure.reasurecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.stream.Stream;

public class FaucetBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final EnumMap<Direction, VoxelShape> SHAPES;

    static {
        SHAPES = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Stream.of(
                        Block.box(6, 6, 14, 10, 9, 16),
                        Block.box(6, 5, 12, 10, 8, 14),
                        Block.box(6, 4, 10, 10, 7, 12)
                ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(),

                Direction.SOUTH, Stream.of(
                        Block.box(6, 6, 0, 10, 9, 2),
                        Block.box(6, 5, 2, 10, 8, 4),
                        Block.box(6, 4, 4, 10, 7, 6)
                ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(),

                Direction.WEST, Stream.of(
                        Block.box(14, 6, 6, 16, 9, 10),
                        Block.box(12, 5, 6, 14, 8, 10),
                        Block.box(10, 4, 6, 12, 7, 10)
                ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(),

                Direction.EAST, Stream.of(
                        Block.box(0, 6, 6, 2, 9, 10),
                        Block.box(2, 5, 6, 4, 8, 10),
                        Block.box(4, 4, 6, 6, 7, 10)
                ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get()
        ));
    }

    public FaucetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction dir = context.getClickedFace();
        if (dir == Direction.UP || dir == Direction.DOWN) {
            dir = context.getHorizontalDirection().getOpposite();
        }
        return this.defaultBlockState().setValue(FACING, dir);
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
