package reasure.reasurecraft.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import reasure.reasurecraft.util.KeyboardHelper;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class BlazeAndSteel extends FlintAndSteelItem {
    public BlazeAndSteel(Properties properties) {
        super(properties.fireResistant().stacksTo(1).durability(128));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            return new ActionResult<>(fireEntity(item, player, player, hand), item);
        }
        return ActionResult.consume(item);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack item, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!target.level.isClientSide()) {
            return fireEntity(item, player, target, hand);
        }
        return ActionResultType.CONSUME;
    }

    protected ActionResultType fireEntity(ItemStack item, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!target.fireImmune()) {
            player.awardStat(Stats.ITEM_USED.get(this));
            player.playSound(SoundEvents.FLINTANDSTEEL_USE, 1.0f, random.nextFloat() * 0.4f + 0.8f);
            item.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            int fireTick = random.nextInt(10 * 20 + 1) + 20;
            if (fireTick > target.getRemainingFireTicks()) {
                target.setRemainingFireTicks(fireTick);
                target.hurt(DamageSource.ON_FIRE, random.nextInt(4));
            } else {
                target.hurt(DamageSource.ON_FIRE, 1);
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.FAIL;
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
}
