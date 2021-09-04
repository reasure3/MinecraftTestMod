package reasure.reasurecraft.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import reasure.reasurecraft.util.KeyboardHelper;
import reasure.reasurecraft.util.TranslateHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class EffectFoodItem extends TooltipItem {

    public EffectFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void addKeyTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        Food food = getFoodProperties();
        if (food == null) {
            tooltips.add((new TranslationTextComponent(TranslateHelper.getTooltipPrefix("food.none"))).withStyle(TextFormatting.GRAY));
            return;
        }

        List<Pair<EffectInstance, Float>> effects = food.getEffects(); // effect, chance

        if (effects.isEmpty()) {
            tooltips.add((new TranslationTextComponent("effect.none")).withStyle(TextFormatting.GRAY));
            return;
        }

        addKeyTooltip(stack, world, tooltips, flag, KeyboardHelper.Key.CTRL, KeyboardHelper.Side.BOTH_OR);
    }


    @Override
    protected void addInformationTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        Food food = getFoodProperties();
        if (food == null) {
            tooltips.add((new TranslationTextComponent(TranslateHelper.getTooltipPrefix("food.none"))).withStyle(TextFormatting.GRAY));
            return;
        }

        List<Pair<EffectInstance, Float>> effects = food.getEffects(); // effect, chance

        if (effects.isEmpty()) {
            tooltips.add((new TranslationTextComponent("effect.none")).withStyle(TextFormatting.GRAY));
            return;
        }

        List<Pair<Attribute, AttributeModifier>> attributeModifiers = Lists.newArrayList();

        for (Pair<EffectInstance, Float> effectPair : effects) {
            Float chance = effectPair.getSecond();
            EffectInstance effectInstance = effectPair.getFirst();
            TranslationTextComponent effectText = new TranslationTextComponent(effectInstance.getDescriptionId());

            Effect effect = effectInstance.getEffect();
            Map<Attribute, AttributeModifier> attributes = effect.getAttributeModifiers();

            if (!attributes.isEmpty()) {
                attributes.forEach((key, value) -> {
                    AttributeModifier b = new AttributeModifier(value.getName(),
                            effect.getAttributeModifierValue(effectInstance.getAmplifier(), value), value.getOperation());
                    attributeModifiers.add(new Pair<>(key, b));
                });
            }

            if (effectInstance.getAmplifier() > 0) {
                effectText = new TranslationTextComponent("potion.withAmplifier", effectText,
                        new TranslationTextComponent("potion.potency." + effectInstance.getAmplifier()));
            }

            if (effectInstance.getDuration() > 20) {
                effectText = new TranslationTextComponent("potion.withDuration", effectText, EffectUtils.formatDuration(effectInstance, 1.0f));
            }

            if (chance < 1) {
                effectText = new TranslationTextComponent(TranslateHelper.getTooltipPrefix("potion.with_percent"), effectText, chance * 100);
            }

            tooltips.add(effectText.withStyle(effect.getCategory().getTooltipFormatting()));
        }

        if (!attributeModifiers.isEmpty()) {
            tooltips.add(StringTextComponent.EMPTY);
            tooltips.add((new TranslationTextComponent(TranslateHelper.getTooltipPrefix("when_eat"))).withStyle(TextFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> attributeModifier : attributeModifiers) {
                AttributeModifier modifier = attributeModifier.getSecond();
                double amount = modifier.getAmount();
                double amount_multiply = amount;
                if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE || modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    amount_multiply *= 100.0D;
                }

                if (amount > 0.0D) {
                    tooltips.add((new TranslationTextComponent("attribute.modifier.plus." + modifier.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(amount_multiply),
                            new TranslationTextComponent(attributeModifier.getFirst().getDescriptionId()))).withStyle(TextFormatting.BLUE));
                } else if (amount < 0.0D) {
                    amount_multiply *= -1.0D;
                    tooltips.add((new TranslationTextComponent("attribute.modifier.take." + modifier.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(amount_multiply),
                            new TranslationTextComponent(attributeModifier.getFirst().getDescriptionId()))).withStyle(TextFormatting.RED));
                }
            }
        }
    }
}
