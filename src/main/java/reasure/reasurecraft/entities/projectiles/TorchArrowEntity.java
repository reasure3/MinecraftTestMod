package reasure.reasurecraft.entities.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class TorchArrowEntity extends ArrowEntity {
    private final ItemStack torch;

    public TorchArrowEntity(World world, LivingEntity shooter, ItemStack torch) {
        super(world, shooter);
        this.torch = torch.copy();
        this.torch.setCount(1);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);

        Direction direction = blockRayTraceResult.getDirection();
        BlockPos pos = blockRayTraceResult.getBlockPos().relative(direction);
        boolean isReplaceable = this.level.getBlockState(pos).getMaterial().isReplaceable();

        if (direction == Direction.DOWN || !isReplaceable) {
            InventoryHelper.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), this.torch.copy());
        } else if (direction == Direction.UP) {
            this.level.setBlockAndUpdate(pos, getTorchBlock());
        } else {
            this.level.setBlockAndUpdate(pos, getWallTorchBlock(direction));
        }

        this.remove();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);

        Entity entity = entityRayTraceResult.getEntity();

        if (isFireTorch()) {
            entity.setSecondsOnFire(this.isOnFire() ? 30 : 15);
        }

        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).addEffect(new EffectInstance(Effects.GLOWING, 15, 0));
        }
    }

    private boolean isFireTorch() {
        Item item = this.torch.getItem();

        return item == Items.TORCH || item == Items.SOUL_TORCH;
    }

    public BlockState getTorchBlock() {
        Item stack = this.torch.getItem();
        if (stack == Items.TORCH) {
            return Blocks.TORCH.defaultBlockState();
        } else if (stack == Items.SOUL_TORCH) {
            return Blocks.SOUL_TORCH.defaultBlockState();
        } else if (stack == Items.REDSTONE_TORCH) {
            return Blocks.REDSTONE_TORCH.defaultBlockState();
        }

        return Blocks.TORCH.defaultBlockState();
    }

    public BlockState getWallTorchBlock(Direction direction) {
        Item stack = this.torch.getItem();
        if (stack == Items.TORCH) {
            return Blocks.WALL_TORCH.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        } else if (stack == Items.SOUL_TORCH) {
            return Blocks.SOUL_WALL_TORCH.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        } else if (stack == Items.REDSTONE_TORCH) {
            return Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        }

        return Blocks.WALL_TORCH.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
    }
}
