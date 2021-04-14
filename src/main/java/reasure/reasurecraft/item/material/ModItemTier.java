package reasure.reasurecraft.item.material;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import reasure.reasurecraft.init.ModItems;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {
    SILVER(2, 200, 5.3F, 2.4F, 17, () -> Ingredient.of(ModItems.SILVER_INGOT.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus; //데미지 공식 = ItemTier 데미지 보너스 + 무기 데미지 + 1
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    ModItemTier(int levelIn, int usesIn, float speedIn, float attackDamageBonusIn, int enchantmentValueIn, Supplier<Ingredient> repairIngredientIn) {
        this.level = levelIn;
        this.uses = usesIn;
        this.speed = speedIn;
        this.attackDamageBonus = attackDamageBonusIn;
        this.enchantmentValue = enchantmentValueIn;
        this.repairIngredient = new LazyValue<>(repairIngredientIn);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
