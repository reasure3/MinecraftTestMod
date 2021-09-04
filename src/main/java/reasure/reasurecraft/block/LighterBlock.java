package reasure.reasurecraft.block;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class LighterBlock extends Block {
    public final static BooleanProperty REDSTONE_REACTION = ModBlockStateProperties.REDSTONE_REACTION;
    public final static BooleanProperty ON = ModBlockStateProperties.ON;

    public LighterBlock(Properties properties) {
        super(properties.randomTicks().harvestTool(ToolType.PICKAXE).strength(5.0f, 6.0f)
                .lightLevel(state -> {
                    if (state.getValue(REDSTONE_REACTION) && !state.getValue(ON)) {
                        return 0;
                    } else {
                        return 4;
                    }
                }));
        this.registerDefaultState(this.defaultBlockState().setValue(REDSTONE_REACTION, true).setValue(ON, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(REDSTONE_REACTION, ON);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        LightOn(world, state, pos);
        super.randomTick(state, world, pos, random);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos other_pos, boolean bool) {
        world.setBlockAndUpdate(pos, state.setValue(ON, world.hasNeighborSignal(pos)));
        LightOn(world, state, pos);
        super.neighborChanged(state, world, pos, block, other_pos, bool);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        world.setBlockAndUpdate(pos, state.setValue(ON, world.hasNeighborSignal(pos)));
        LightOn(world, state, pos);
        super.setPlacedBy(world, pos, state, livingEntity, stack);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (world.isClientSide()) {
            return ActionResultType.SUCCESS;
        }
        BlockState after = state.cycle(REDSTONE_REACTION);
        world.setBlockAndUpdate(pos, after);
        LightOn(world, after, pos);
        return ActionResultType.CONSUME;
    }

    private boolean canLightOn(World world, BlockPos pos) {
        return AbstractFireBlock.canBePlacedAt(world, pos.above(), Direction.DOWN);
    }

    private void LightOn(World world, BlockState state, BlockPos pos) {
        if (!world.isClientSide()) {
            boolean on_redstone = state.getValue(REDSTONE_REACTION);
            if (on_redstone) {
                if (world.hasNeighborSignal(pos)) {
                    if (canLightOn(world, pos)) {
                        setFire(world, pos);
                    }
                } else {
                    if (world.getBlockState(pos.above()).is(BlockTags.FIRE)) {
                        world.removeBlock(pos.above(), true);
                    }
                }
            } else if (canLightOn(world, pos)) {
                setFire(world, pos);
            }
        }
    }

    private void setFire(World world, BlockPos pos) {
        BlockState fire = AbstractFireBlock.getState(world, pos.above());
        world.setBlockAndUpdate(pos.above(), fire);
    }

    @Override
    public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return super.getLightValue(state, world, pos);
    }
}
