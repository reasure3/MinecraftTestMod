package reasure.reasurecraft.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.item.crafting.recipe.PressingRecipe;
import reasure.reasurecraft.item.crafting.recipe.RefillItemRecipe;
import reasure.reasurecraft.item.crafting.recipe.TossingRecipe;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ReasureCraft.MOD_ID);

    public static final IRecipeType<PressingRecipe> PRESSING_TYPE = register("pressing");
    public static final IRecipeType<TossingRecipe> TOSSING_TYPE = register("tossing");

    public static final RegistryObject<IRecipeSerializer<PressingRecipe>> PRESSING_SERIALIZER = register("pressing", PressingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<TossingRecipe>> TOSSING_SERIALIZER = register("tossing", TossingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<RefillItemRecipe>> REFILL_SERIALIZER = register("refill", RefillItemRecipe.Serializer::new);

    public static <T extends IRecipe<?>> IRecipeType<T> register(final String id) {
        return Registry.register(Registry.RECIPE_TYPE, ModResourceLocation.getId(id), new IRecipeType<T>() {
            public String toString() {
                return ReasureCraft.MOD_ID + ":" + id;
            }
        });
    }

    private static <T extends IRecipe<?>> RegistryObject<IRecipeSerializer<T>> register(String name, Supplier<IRecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(name, serializer);
    }
}
