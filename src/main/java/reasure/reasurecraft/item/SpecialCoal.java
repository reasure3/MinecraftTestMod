package reasure.reasurecraft.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import reasure.reasurecraft.util.KeyboardHelper;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;

public class SpecialCoal extends Item {
    public SpecialCoal(Properties properties) {
        super(properties.rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix(this)));
        } else {
            tooltip.add(new StringTextComponent(I18n.get(
                    TranslateHelper.getTooltipPrefix("more_information"),
                    TextFormatting.YELLOW,
                    KeyboardHelper.getShiftText(KeyboardHelper.Side.BOTH_OR),
                    TextFormatting.RESET))
            );
        }
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
        if (recipeType == IRecipeType.SMELTING) {
            return 1;
        } else if (recipeType == IRecipeType.BLASTING || recipeType == IRecipeType.SMOKING) {
            return 2;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return this.getDefaultInstance();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity playerEntity) {
        super.onCraftedBy(stack, world, playerEntity);
        stack.shrink(1); // not work
    }
}
