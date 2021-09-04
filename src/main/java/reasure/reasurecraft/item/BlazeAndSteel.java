package reasure.reasurecraft.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;

public class BlazeAndSteel extends FlintAndSteelItem {
    public BlazeAndSteel(Properties properties) {
        super(properties.fireResistant().stacksTo(1).durability(128));
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack item, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!target.fireImmune()) {
            int fireTick = random.nextInt(10 * 20 + 1) + 20;
            if (fireTick > target.getRemainingFireTicks()) {
                player.awardStat(Stats.ITEM_USED.get(this));
                player.playSound(SoundEvents.FLINTANDSTEEL_USE, 1.0f, random.nextFloat() * 0.4f + 0.8f);
                item.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                target.setRemainingFireTicks(fireTick);
                target.hurt(DamageSource.ON_FIRE, random.nextInt(4));
                return ActionResultType.sidedSuccess(player.level.isClientSide());
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(new TranslationTextComponent(TranslateHelper.getTooltipPrefix(this)));
        super.appendHoverText(stack, world, tooltips, flag);
    }
}
