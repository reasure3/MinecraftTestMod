package reasure.reasurecraft.dategen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;
import reasure.reasurecraft.util.TranslateHelper;

import java.util.Objects;

public class ModLanguageProvider extends LanguageProvider {
    private final String locale;

    public ModLanguageProvider(DataGenerator gen, String locale) {
        super(gen, ReasureCraft.MOD_ID, locale);
        this.locale = locale;
    }

    private void blocks(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModBlocks.SILVER_ORE.get(), "Silver Ore");
                helper.add(ModBlocks.SILVER_BLOCK.get(), "Block of Silver");
                helper.add(ModBlocks.METAL_PRESS.get(), "Metal Press");
                helper.add(ModBlocks.DISPLAY_CASE.get(), "Display Case");
                helper.add(ModBlocks.OBSIDIAN_FRAME.get(), "Obsidian Frame");
                helper.add(ModBlocks.PEANUTS.get(), "Peanuts");
                break;
            case "ko_kr":
                helper.add(ModBlocks.SILVER_ORE.get(), "은광석");
                helper.add(ModBlocks.SILVER_BLOCK.get(), "은 블록");
                helper.add(ModBlocks.METAL_PRESS.get(), "금속 유압기");
                helper.add(ModBlocks.DISPLAY_CASE.get(), "전시장");
                helper.add(ModBlocks.OBSIDIAN_FRAME.get(), "흑요석 프레임");
                helper.add(ModBlocks.PEANUTS.get(), "Peanuts");
                break;
        }
    }

    private void items(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModItems.SILVER_INGOT.get(), "Silver Ingot");
                helper.add(ModItems.SILVER_NUGGET.get(), "Silver Nugget");
                helper.add(ModItems.SILVER_DUST.get(), "Silver Dust");
                helper.add(ModItems.SILVER_SWORD.get(), "Silver Sword");
                helper.add(ModItems.SILVER_SHOVEL.get(), "Silver Shovel");
                helper.add(ModItems.SILVER_PICKAXE.get(), "Silver Pickaxe");
                helper.add(ModItems.SILVER_AXE.get(), "Silver Axe");
                helper.add(ModItems.SILVER_HOE.get(), "Silver Hoe");
                helper.add(ModItems.SILVER_HELMET.get(), "Silver Helmet");
                helper.add(ModItems.SILVER_CHESTPLATE.get(), "Silver Chestplate");
                helper.add(ModItems.SILVER_LEGGINGS.get(), "Silver Leggings");
                helper.add(ModItems.SILVER_BOOTS.get(), "Silver Boots");
                helper.add(ModItems.SILVER_HORSE_ARMOR.get(), "Silver Horse Armor");
                helper.add(ModItems.POISON_APPLE.get(), "Poisonous Apple");
                helper.add(ModItems.SPECIAL_COAL.get(), "Special Coal");
                helper.add(ModItems.BLAZE_INGOT.get(), "Blaze Ingot");
                helper.add(ModItems.BLAZE_AND_STEEL.get(), "Blaze And Steel");
                helper.add(ModItems.PEANUT.get(), "Peanut");
                break;
            case "ko_kr":
                helper.add(ModItems.SILVER_INGOT.get(), "은괴");
                helper.add(ModItems.SILVER_NUGGET.get(), "은 조각");
                helper.add(ModItems.SILVER_DUST.get(), "은 가루");
                helper.add(ModItems.SILVER_SWORD.get(), "은 검");
                helper.add(ModItems.SILVER_SHOVEL.get(), "은 삽");
                helper.add(ModItems.SILVER_PICKAXE.get(), "은 곡괭이");
                helper.add(ModItems.SILVER_AXE.get(), "은 도끼");
                helper.add(ModItems.SILVER_HOE.get(), "은 괭이");
                helper.add(ModItems.SILVER_HELMET.get(), "은 투구");
                helper.add(ModItems.SILVER_CHESTPLATE.get(), "은 흉갑");
                helper.add(ModItems.SILVER_LEGGINGS.get(), "은 각반");
                helper.add(ModItems.SILVER_BOOTS.get(), "은 부츠");
                helper.add(ModItems.SILVER_HORSE_ARMOR.get(), "은 말 갑옷");
                helper.add(ModItems.POISON_APPLE.get(), "독사과");
                helper.add(ModItems.SPECIAL_COAL.get(), "특별한 석탄");
                helper.add(ModItems.BLAZE_INGOT.get(), "블레이즈 주괴");
                helper.add(ModItems.BLAZE_AND_STEEL.get(), "블레이즈 라이터");
                helper.add(ModItems.PEANUT.get(), "땅콩");
                break;
        }
    }

    private void itemGroups(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModItemGroup.reasurecraft, "Reasure Craft");
                break;
            case "ko_kr":
                helper.add(ModItemGroup.reasurecraft, "래져 크래프트");
        }
    }

    private void containers(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.addContainer(ModBlocks.METAL_PRESS.get(), "Metal Press");
                helper.addContainer(ModBlocks.DISPLAY_CASE.get(), "Display Case");
                break;
            case "ko_kr":
                helper.addContainer(ModBlocks.METAL_PRESS.get(), "금속 유압기");
                helper.addContainer(ModBlocks.DISPLAY_CASE.get(), "전시장");
        }
    }

    private void tooltips(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.addTooltip("more_information", "Hold %1$s%2$s%3$s for more information!");
                helper.addTooltip(ModItems.SPECIAL_COAL.get(),
                        "This is an infinite coal.\nIts burning time is only 1tick, but not consumed after used as a fuel.");
                helper.addTooltip(ModItems.BLAZE_AND_STEEL.get(),
                        "When use on block: light the block fire.\nWhen use on air: light me on fire.\nWhen use on mob: light mob on fire.");
                break;
            case "ko_kr":
                helper.addTooltip("more_information", "%1$s%2$s%3$s를 눌러 더 많은 정보 확인");
                helper.addTooltip(ModItems.SPECIAL_COAL.get(),
                        "무한한 석탄\n타는 시간은 고작 1틱 뿐이지만, 연료로 사용되고 난 후에 사라지지 않음.");
                helper.addTooltip(ModItems.BLAZE_AND_STEEL.get(),
                        "블럭에 사용: 블럭에 불을 붙임.\n허공에 사용: 나에게 불을 붙임.\n몹에게 사용: 몹에게 불을 붙임.");
        }
    }

    @Override
    protected void addTranslations() {
        PrefixHelper helper = new PrefixHelper(this);
        blocks(helper, locale);
        items(helper, locale);
        itemGroups(helper, locale);
        containers(helper, locale);
        tooltips(helper, locale);
    }

    @SuppressWarnings("unused")
    public String getLocale() {
        return locale;
    }

    private static class PrefixHelper {
        private final ModLanguageProvider provider;
        private String prefix;

        public PrefixHelper(ModLanguageProvider provider) {
            this.provider = provider;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix + ".";
        }

        @SuppressWarnings("unused")
        public void add(String translationKey, String translation) {
            provider.add(prefix + translationKey, translation);
        }

        public void add(Block key, String name) {
            provider.add(key, name);
        }

        public void add(Item key, String name) {
            provider.add(key, name);
        }

        public void add(ModItemGroup key, String name) {
            provider.add("itemGroup." + key.getLabel(), name);
        }

        public void addContainer(Block key, String name) {
            setPrefix("container.reasurecraft");
            provider.add(prefix + Objects.requireNonNull(key.getRegistryName()).getPath(), name);
        }

        public void addTooltip(String key, String name) {
            provider.add(TranslateHelper.getTooltipPrefix(key), name);
        }

        public void addTooltip(Item key, String name) {
            provider.add(TranslateHelper.getTooltipPrefix(key), name);
        }
    }
}
