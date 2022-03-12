package me.ivan.ivancarpetaddition.commands.xpcounter;

import me.ivan.ivancarpetaddition.helpers.xpcounter.ExperienceCounter;
import me.ivan.ivancarpetaddition.translations.TranslationContext;
import net.minecraft.text.BaseText;

public class SpawnReason extends TranslationContext {
    public static final SpawnReason EXPERIENCE_BOTTLE = new SpawnReason("experience_bottle");
    public static final SpawnReason ENTITY_LOOT = new SpawnReason("entity_loot");
    public static final SpawnReason FURNACE = new SpawnReason("furnace");
    public static final SpawnReason BLOCK = new SpawnReason("block");
    public static final SpawnReason ENDER_DRAGON = new SpawnReason("ender_dragon");
    public static final SpawnReason BREEDING = new SpawnReason("breeding");
    public static final SpawnReason FISHING = new SpawnReason("fishing");
    public static final SpawnReason TRADING = new SpawnReason("trading");

    private final String translationKey;

    public SpawnReason(String translationKey) {
        super(ExperienceCounter.getStaticTranslator().getDerivedTranslator("spawn_reason"));
        this.translationKey = translationKey;
    }

    public BaseText toText() {
        return tr(this.translationKey);
    }
}
