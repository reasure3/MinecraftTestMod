package reasure.reasurecraft.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import reasure.reasurecraft.config.ItemConfig;
import reasure.reasurecraft.util.KeyboardHelper;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;

public class
TorchPlacer extends TooltipItem {
    public TorchPlacer(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static CompoundNBT createNBT(ItemStack item) {
        CompoundNBT nbt = item.getOrCreateTag();
        if (!nbt.contains("auto_place")) {
            nbt.putBoolean("auto_place", true);
        }
        return nbt;
    }

    @Override
    protected void addKeyTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        CompoundNBT nbt = createNBT(stack);
        if (!nbt.getBoolean("auto_place")) {
            tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix("activate_item")));
            return;
        }
        super.addKeyTooltip(stack, world, tooltips, flag);
    }

    @Override
    protected void addInformationTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix("when_in_inventory")).withStyle(TextFormatting.DARK_PURPLE));
        tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix(this), ItemConfig.torch_replacer_brightness.get()));
        tooltips.add(StringTextComponent.EMPTY);
        tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix("deactivate_item")));
    }

    @Override
    public ActionResult<ItemStack> use(World world, @Nullable PlayerEntity player, Hand hand) {
        ItemStack item = player != null ? player.getItemInHand(hand) : ItemStack.EMPTY;

        if (KeyboardHelper.isHoldingShift(KeyboardHelper.Side.LEFT)) {
            CompoundNBT nbt = createNBT(item);
            boolean on = nbt.getBoolean("auto_place");
            nbt.putBoolean("auto_place", !on);
            return ActionResult.sidedSuccess(item, world.isClientSide());
        }

        return ActionResult.pass(item);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, world, entity, slotId, isSelected);

        if (!world.isClientSide()) {
            CompoundNBT nbt = createNBT(stack);
            if (entity instanceof PlayerEntity && nbt.getBoolean("auto_place")) {
                PlayerEntity player = (PlayerEntity)entity;

                if (player.isSpectator()) {
                    return;
                }

                int brightness = world.getRawBrightness(entity.blockPosition(), world.getSkyDarken());

                if (brightness < ItemConfig.torch_replacer_brightness.get() + 1) {

                    if (player.isCreative()) {
                        setTorchAndUpdate(player.blockPosition(), world);
                        return;
                    }


                    final List<NonNullList<ItemStack>> compartments = ImmutableList.of(player.inventory.items, player.inventory.offhand);
                    for (NonNullList<ItemStack> slot : compartments) {
                        for (ItemStack itemStack : slot) {
                            if (itemStack.sameItem(new ItemStack(Items.TORCH))) {
                                if (setTorchAndUpdate(player.blockPosition(), world)) {
                                    itemStack.shrink(1);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean setTorchAndUpdate(BlockPos entityPos, World world) {
        // 아래에 설치
        if (((TorchBlock)Blocks.TORCH).canSurvive(Blocks.TORCH.defaultBlockState(), world, entityPos)
                && world.getFluidState(entityPos).isEmpty()
                && world.getBlockState(entityPos).getMaterial().isReplaceable()) {

            world.setBlockAndUpdate(entityPos, Blocks.TORCH.defaultBlockState());
            return true;
        }
        return false;
    }

    public ActionResultType useOn(ItemUseContext itemUseContext) {
        if (!ItemConfig.torch_placer_install_directly.get()) {
            return super.useOn(itemUseContext);
        }

        PlayerEntity player = itemUseContext.getPlayer();
        if (player != null && !player.abilities.instabuild) {
            final List<NonNullList<ItemStack>> compartments = ImmutableList.of(player.inventory.items, player.inventory.offhand);
            for (NonNullList<ItemStack> slot : compartments) {
                for (ItemStack itemStack : slot) {
                    if (itemStack.sameItem(new ItemStack(Items.TORCH))) {
                        ActionResultType actionResultType = this.place(new BlockItemUseContext(itemUseContext), itemStack);
                        return actionResultType.consumesAction() ? actionResultType : this.use(itemUseContext.getLevel(), player, itemUseContext.getHand()).getResult();
                    }
                }
            }
            return this.use(itemUseContext.getLevel(), player, itemUseContext.getHand()).getResult();
        }
        ActionResultType actionResultType = this.place(new BlockItemUseContext(itemUseContext), ItemStack.EMPTY);
        return actionResultType.consumesAction() ? actionResultType : this.use(itemUseContext.getLevel(), player, itemUseContext.getHand()).getResult();
    }

    public ActionResultType place(BlockItemUseContext blockItemUseContext, ItemStack itemStack) {
        if (!blockItemUseContext.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockState blockstate = this.getPlacementState(blockItemUseContext);
            if (blockstate == null) {
                return ActionResultType.FAIL;
            } else if (!this.placeBlock(blockItemUseContext, blockstate)) {
                return ActionResultType.FAIL;
            } else {
                BlockPos blockpos = blockItemUseContext.getClickedPos();
                World world = blockItemUseContext.getLevel();
                PlayerEntity playerentity = blockItemUseContext.getPlayer();
                BlockState blockstate1 = world.getBlockState(blockpos);

                SoundType soundtype = blockstate1.getSoundType(world, blockpos, blockItemUseContext.getPlayer());
                world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, blockItemUseContext.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                if (!itemStack.isEmpty() && itemStack.getItem() == Items.TORCH) {
                    if (playerentity == null || !playerentity.abilities.instabuild) {
                        itemStack.shrink(1);
                    }
                }

                return ActionResultType.sidedSuccess(world.isClientSide());
            }
        }
    }

    @Nullable
    protected BlockState getPlacementState(BlockItemUseContext blockItemUseContext) {
        BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(blockItemUseContext);
        BlockState blockstate1 = null;
        IWorldReader worldReader = blockItemUseContext.getLevel();
        BlockPos blockpos = blockItemUseContext.getClickedPos();

        for (Direction direction : blockItemUseContext.getNearestLookingDirections()) {
            if (direction != Direction.UP) {
                BlockState blockstate2 = direction == Direction.DOWN ? Blocks.TORCH.getStateForPlacement(blockItemUseContext) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(worldReader, blockpos)) {
                    blockstate1 = blockstate2;
                    break;
                }
            }
        }

        return blockstate1 != null && worldReader.isUnobstructed(blockstate1, blockpos, ISelectionContext.empty()) ? blockstate1 : null;
    }

    protected boolean placeBlock(BlockItemUseContext blockItemUseContext, BlockState state) {
        return blockItemUseContext.getLevel().setBlock(blockItemUseContext.getClickedPos(), state, 11);
    }

    protected SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }
}