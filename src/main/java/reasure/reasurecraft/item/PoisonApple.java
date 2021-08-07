package reasure.reasurecraft.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PoisonApple extends Item {
    public PoisonApple(Properties properties) {
        //noinspection deprecation
        super(properties.food(new Food.Builder()
                .nutrition(4)
                .saturationMod(1.2f)
                .effect(new EffectInstance(Effects.CONFUSION, 300, 1), 0.3f)
                .effect(new EffectInstance(Effects.POISON, 300, 1), 1)
                .alwaysEat()
                .build()));
    }
}
