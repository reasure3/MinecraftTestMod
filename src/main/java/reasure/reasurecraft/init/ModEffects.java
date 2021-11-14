package reasure.reasurecraft.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.effects.ExplosiveEffect;
import reasure.reasurecraft.effects.TeleportEffect;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ReasureCraft.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, ReasureCraft.MOD_ID);

    public static final RegistryObject<Effect> EXPLOSIVE_EFFECT = EFFECTS.register("explosive", ExplosiveEffect::new);
    public static final RegistryObject<Potion> EXPLOSIVE_POTION = POTIONS.register("explosive", () ->
            new Potion(new EffectInstance(EXPLOSIVE_EFFECT.get(), 1200, 0)));
    public static final RegistryObject<Potion> LONG_EXPLOSIVE_POTION = POTIONS.register("long_explosive", () ->
            new Potion(new EffectInstance(EXPLOSIVE_EFFECT.get(), 2400, 0)));
    public static final RegistryObject<Potion> STRONG_EXPLOSIVE_POTION = POTIONS.register("strong_explosive", () ->
            new Potion(new EffectInstance(EXPLOSIVE_EFFECT.get(), 600, 2)));
    public static final RegistryObject<Effect> TELEPORT_EFFECT = EFFECTS.register("teleport", TeleportEffect::new);
    public static final RegistryObject<Potion> TELEPORT_POTION = POTIONS.register("teleport", () ->
            new Potion(new EffectInstance(TELEPORT_EFFECT.get(), 900, 0)));
    public static final RegistryObject<Potion> LONG_TELEPORT_POTION = POTIONS.register("long_teleport", () ->
            new Potion(new EffectInstance(TELEPORT_EFFECT.get(), 1800, 0)));

    public static void addPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, Items.TNT, EXPLOSIVE_POTION.get()));
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(EXPLOSIVE_POTION.get(), Items.REDSTONE, LONG_EXPLOSIVE_POTION.get()));
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(EXPLOSIVE_POTION.get(), Items.GLOWSTONE_DUST, STRONG_EXPLOSIVE_POTION.get()));

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, Items.ENDER_PEARL, TELEPORT_POTION.get()));
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(TELEPORT_POTION.get(), Items.REDSTONE, LONG_TELEPORT_POTION.get()));
    }

    @SuppressWarnings("NullableProblems")
    private static class BetterBrewingRecipe implements IBrewingRecipe {
        private final Potion bottleInput;
        private final Item ingredient;
        private final ItemStack output;

        public BetterBrewingRecipe(Potion bottleInput, Item ingredient, Potion output) {
            this.bottleInput = bottleInput;
            this.ingredient = ingredient;
            this.output = PotionUtils.setPotion(new ItemStack(Items.POTION), output);
        }

        @Override
        public boolean isInput(ItemStack input) {
            return PotionUtils.getPotion(input).equals(this.bottleInput);
        }

        @Override
        public boolean isIngredient(ItemStack ingredient) {
            return ingredient.getItem().equals(this.ingredient);
        }

        @Override
        public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
            if (isInput(input) && isIngredient(ingredient)) {
                return this.output.copy();
            }

            return ItemStack.EMPTY;
        }
    }
}
