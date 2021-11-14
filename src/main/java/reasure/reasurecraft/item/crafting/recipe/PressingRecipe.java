package reasure.reasurecraft.item.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import reasure.reasurecraft.init.ModRecipes;

import javax.annotation.Nullable;

public class PressingRecipe extends SingleItemRecipe {
    public PressingRecipe(ResourceLocation recipeId, Ingredient ingredient, ItemStack result) {
        super(ModRecipes.PRESSING_TYPE, ModRecipes.PRESSING_SERIALIZER.get(), recipeId, "", ingredient, result); // "" : group
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.ingredient.test(inv.getItem(0)); //getItem(slot)
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PressingRecipe> {
        @Override
        public PressingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(json, "result")); //result
            int count = JSONUtils.getAsInt(json, "count", 1); //p_151208: default count value

            ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);

            return new PressingRecipe(recipeId, ingredient, result);
        }

        @Nullable
        @Override
        public PressingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new PressingRecipe(recipeId, ingredient, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PressingRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
