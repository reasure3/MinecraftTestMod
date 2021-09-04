package reasure.reasurecraft.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import reasure.reasurecraft.util.KeyboardHelper;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipItem extends Item {
    public TooltipItem(Properties properties) {
        super(properties);
    }

    /**
     * Use When Define Other Key.
     * <p>
     * reference to {@link #addKeyTooltip(ItemStack, World, List, ITooltipFlag, KeyboardHelper.Key, KeyboardHelper.Side) addKeyTooltip(stack, world, tooltips, flag, key, side)}
     */
    protected void addKeyTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        addKeyTooltip(stack, world, tooltips, flag, KeyboardHelper.Key.SHIFT, KeyboardHelper.Side.BOTH_OR);
    }

    protected final void addKeyTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag, KeyboardHelper.Key key, KeyboardHelper.Side side) {
        if (KeyboardHelper.isHoldingKey(key, side)) {
            addInformationTooltip(stack, world, tooltips, flag);
        } else {
            tooltips.add(new StringTextComponent(I18n.get(TranslateHelper.getTooltipPrefix("more_information"),
                    TextFormatting.YELLOW, (side + " " + key).trim(), TextFormatting.RESET)));
        }
    }

    protected void addInformationTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix(this)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public final void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        addKeyTooltip(stack, world, tooltips, flag);
        super.appendHoverText(stack, world, tooltips, flag);
    }
}
