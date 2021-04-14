package reasure.reasurecraft.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.item.material.ModArmorMaterial;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;
import reasure.reasurecraft.item.material.ModItemTier;

public class ModItems {
    public static final RegistryObject<HorseArmorItem> SILVER_HORSE_ARMOR = Registration.ITEMS.register("silver_horse_armor", () ->
            new HorseArmorItem(5, ReasureCraft.getId("textures/entity/horse/armor/horse_armor_silver.png"),
                    new Item.Properties().stacksTo(1).tab(ModItemGroup.reasurecraft)));
    //공격력 계산 공식 = 1 + ItemTier 공격력 + 무기 공격력
    //공격 속도 계산 공식 = 4 + 무기 공격 속도
    //공격속도를 0.0F로 설정하면 쿨다운 없어짐
    public static final RegistryObject<SwordItem> SILVER_SWORD = Registration.ITEMS.register("silver_sword", () ->
            new SwordItem(ModItemTier.SILVER, 3, -2.4F, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = Registration.ITEMS.register("silver_shovel", () ->
            new ShovelItem(ModItemTier.SILVER, 1.5F, -3.0F, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = Registration.ITEMS.register("silver_pickaxe", () ->
            new PickaxeItem(ModItemTier.SILVER, 1, -2.8F, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<AxeItem> SILVER_AXE = Registration.ITEMS.register("silver_axe", () ->
            new AxeItem(ModItemTier.SILVER, 5.4F, -2.9F, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<HoeItem> SILVER_HOE = Registration.ITEMS.register("silver_hoe", () ->
            new HoeItem(ModItemTier.SILVER, -2, 0.3F, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<ArmorItem> SILVER_HELMET = Registration.ITEMS.register("silver_helmet", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.HEAD, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<ArmorItem> SILVER_CHESTPLATE = Registration.ITEMS.register("silver_chestplate", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.CHEST, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<ArmorItem> SILVER_LEGGINGS = Registration.ITEMS.register("silver_leggings", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.LEGS, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<ArmorItem> SILVER_BOOTS = Registration.ITEMS.register("silver_boots", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.FEET, new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<Item> SILVER_INGOT = Registration.ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<Item> SILVER_NUGGET = Registration.ITEMS.register("silver_nugget", () ->
            new Item(new Item.Properties().tab(ModItemGroup.reasurecraft)));
    public static final RegistryObject<Item> SILVER_DUST = Registration.ITEMS.register("silver_dust", () ->
            new Item(new Item.Properties().tab(ModItemGroup.reasurecraft)));

    private ModItems() {
    }

    static void register() {
    }
}
