package me.ivan.ivancarpetaddition.commands.xpcounter;

import me.ivan.ivancarpetaddition.translations.TranslatableBase;
import me.ivan.ivancarpetaddition.translations.Translator;

public class SpawnReason extends TranslatableBase {
    public static final SpawnReason EXPERIENCE_BOTTLE = new SpawnReason("experience_bottle");
    public static final SpawnReason ENTITY_LOOT = new SpawnReason("entity_loot");

    private final String translationKey;

    public SpawnReason(String translationKey) {
        super(new Translator("xpcounter.reason"));
        this.translationKey = translationKey;
    }

    public String toText() {
        return tr(this.translationKey);
    }
}
