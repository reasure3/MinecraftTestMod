package reasure.reasurecraft.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.item.crafting.recipe.PressingRecipe;

public class ModRecipes {
    private ModRecipes() {
    }

    static void register() {
    }

    public static class Types {
        public static final IRecipeType<PressingRecipe> PRESSING = IRecipeType.register(ReasureCraft.MOD_ID + ".pressing");

        private Types() {
        }
    }

    public static class Serializers {
        public static final RegistryObject<IRecipeSerializer<?>> PRESSING = Registration.RECIPE_SERIALIZERS.register(
                "pressing", PressingRecipe.Serializer::new);

        private Serializers() {
        }
    }
}
