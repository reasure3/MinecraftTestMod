package reasure.reasurecraft.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {
    public final static Food POISON_APPLE;

    public final static Food PEANUT = new Food.Builder().nutrition(1).saturationMod(0.3F).build();

    static {
        POISON_APPLE = new Food.Builder().nutrition(4).saturationMod(0.3f).alwaysEat()
                .effect(() -> new EffectInstance(Effects.POISON, 45 * 20, 1), 1)
                .effect(() -> new EffectInstance(Effects.CONFUSION, 10 * 20, 0), 0.1f).build();
    }
}
