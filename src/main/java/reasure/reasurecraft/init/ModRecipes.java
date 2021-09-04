package reasure.reasurecraft.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.item.crafting.recipe.PressingRecipe;
import reasure.reasurecraft.item.crafting.recipe.RefillItemRecipe;
import reasure.reasurecraft.item.crafting.recipe.TossingRecipe;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.function.Supplier;

public class ModRecipes {
    private ModRecipes() {
    }

    static void register() {
        Types.register();
        Serializers.register();
    }

    public static class Types {
        public static final IRecipeType<PressingRecipe> PRESSING = register("pressing");
        public static final IRecipeType<TossingRecipe> TOSSING = register("tossing");

        private Types() {
        }

        public static <T extends IRecipe<?>> IRecipeType<T> register(final String id) {
            return Registry.register(Registry.RECIPE_TYPE, ModResourceLocation.getId(id), new IRecipeType<T>() {
                public String toString() {
                    return ReasureCraft.MOD_ID + ":" + id;
                }
            });
        }

        static void register() {
        }
    }

    public static class Serializers {
        public static final RegistryObject<IRecipeSerializer<PressingRecipe>> PRESSING = register("pressing", PressingRecipe.Serializer::new);
        public static final RegistryObject<IRecipeSerializer<TossingRecipe>> TOSSING = register("tossing", TossingRecipe.Serializer::new);
        public static final RegistryObject<IRecipeSerializer<RefillItemRecipe>> REFILL = register("refill", RefillItemRecipe.Serializer::new);
        private Serializers() {
        }

        private static <T extends IRecipe<?>> RegistryObject<IRecipeSerializer<T>> register(String name, Supplier<IRecipeSerializer<T>> serializer) {
            return Registration.RECIPE_SERIALIZERS.register(name, serializer);
        }

        static void register() {
        }
    }
}
