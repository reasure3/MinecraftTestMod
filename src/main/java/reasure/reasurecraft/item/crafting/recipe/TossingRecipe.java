package reasure.reasurecraft.item.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.init.ModRecipes;

import javax.annotation.Nullable;
import java.util.Objects;

public class TossingRecipe implements IRecipe<IInventory> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final Block block;
    private final ResourceLocation id;

    public TossingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, Block block) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.block = block;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.TOSSING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.TOSSING_TYPE;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.ENDER_STICK.get());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        list.add(Ingredient.of(new ItemStack(this.block)));
        return list;
    }

    public boolean isValid(ItemStack input, Block block) {
        return this.ingredient.test(input) && block == this.block;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TossingRecipe> {
        @Override
        public TossingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final JsonElement inputElement = JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient")
                    : JSONUtils.getAsJsonObject(json, "ingredient");
            final Ingredient ingredient = Ingredient.fromJson(inputElement);

            final ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));

            final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "block")));

            Objects.requireNonNull(block, "Block does not exist.");

            return new TossingRecipe(recipeId, ingredient, result, block);
        }

        @Nullable
        @Override
        public TossingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient ingredient = Ingredient.fromNetwork(buffer);
            final ItemStack result = buffer.readItem();
            final Block block = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());

            Objects.requireNonNull(block, "Block does not exist.");

            return new TossingRecipe(recipeId, ingredient, result, block);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, TossingRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.block.getRegistryName()));
        }
    }
}
