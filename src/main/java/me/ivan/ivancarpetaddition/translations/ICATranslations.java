package me.ivan.ivancarpetaddition.translations;

//#if MC >= 11500
import carpet.CarpetSettings;
//#else
//$$ import me.ivan.ivancarpetaddition.utils.compat.carpet.CarpetSettings;
//#endif
import com.google.common.collect.Maps;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Reference: Carpet TIS Addition
 */
public class ICATranslations {
    // language -> (key -> content)
    public static final Map<String, Map<String, String>> translations = Maps.newLinkedHashMap();

    public static Collection<String> getLanguages() {
        return Collections.unmodifiableSet(translations.keySet());
    }

    public static void loadTranslations() {
        TranslationLoader.loadTranslations(translations);
    }

    public static String getServerLanguage() {
        return CarpetSettings.language.equalsIgnoreCase("none") ? TranslationConstants.DEFAULT_LANGUAGE : CarpetSettings.language;
    }

    @NotNull
    public static Map<String, String> getTranslation(String lang) {
        return translations.getOrDefault(lang, Collections.emptyMap());
    }

    @Nullable
    public static String translateKeyToFormattedString(String lang, String key) {
        return getTranslation(lang.toLowerCase()).get(key);
    }

    public static BaseText translate(BaseText text, ServerPlayerEntity player) {
        return translate(text, ((ServerPlayerEntityWithClientLanguage) player).getClientLanguage$ICA().toLowerCase());
    }

    public static BaseText translate(BaseText text) {
        return translate(text, getServerLanguage());
    }

    public static BaseText translate(BaseText text, String lang) {
        // quick scan to check if any required translation exists
        boolean[] translationRequired = new boolean[]{false};
        translate(text, lang, (txt, msgKeyString) -> {
            translationRequired[0] = true;
            return txt;
        });
        if (!translationRequired[0]) {
            return text;
        }

        return translate(Messenger.copy(text), lang, (txt, msgKeyString) -> {
            //#if MC >= 11900
            //$$ TranslatableTextContent content = (TranslatableTextContent) txt.getContent();
            //$$ String txtKey = content.getKey();
            //$$ Object[] txtArgs = content.getArgs();
            //#else
            String txtKey = txt.getKey();
            Object[] txtArgs = txt.getArgs();
            //#endif

            if (msgKeyString == null) {
                IvanCarpetAdditionServer.LOGGER.warn("ICA: Unknown translation key {}", txtKey);
                return txt;
            }

            BaseText newText;
            try {
                newText = Messenger.format(msgKeyString, txtArgs);
            }
            catch (IllegalArgumentException e) {
                newText = Messenger.s(msgKeyString);
            }

            // migrating text data
            newText.getSiblings().addAll(txt.getSiblings());
            newText.setStyle(txt.getStyle());

            return newText;
        });
    }

    private static BaseText translate(BaseText text, String lang, TextModifier modifier) {
        if (
                //#if MC >= 11900
                //$$ text.getContent() instanceof TranslatableTextContent
                //#else
                text instanceof TranslatableText
                //#endif
        ) {
            TranslatableText translatableText =
                    //#if MC >= 11900
                    //$$ (TranslatableTextContent) text.getContent();
                    //#else
                    (TranslatableText) text;
                    //#endif

            // translate arguments
            Object[] args = translatableText.getArgs();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof BaseText) {
                    BaseText newText = translate((BaseText) arg, lang, modifier);
                    if (newText != arg) {
                        args[i] = newText;
                    }
                }
            }

            // do translation logic
            if (translatableText.getKey().startsWith(TranslationConstants.TRANSLATION_KEY_PREFIX)) {
                String msgKeyString = translateKeyToFormattedString(lang, translatableText.getKey());
                if (msgKeyString == null && !lang.equals(TranslationConstants.DEFAULT_LANGUAGE)) {
                    // not supported language
                    msgKeyString = translateKeyToFormattedString(TranslationConstants.DEFAULT_LANGUAGE, translatableText.getKey());
                }
                text = modifier.apply(
                        //#if MC >= 11900
                        //$$ text,
                        //#else
                        translatableText,
                        //#endif
                        msgKeyString
                );
            }

        }

        // translate hover text
        HoverEvent hoverEvent = ((StyleAccessor) text.getStyle()).getHoverEventField();
        if (hoverEvent != null) {
            //#if MC >= 11600
            //$$ Object hoverText = hoverEvent.getValue(hoverEvent.getAction());
            //$$ if (hoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT && hoverText instanceof BaseText) {
            //$$ 	 BaseText newText = translate((BaseText) hoverText, lang, modifier);
            //$$ 	 if (newText != hoverText) {
            //$$ 		 text.setStyle(text.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, newText)));
            //$$ 	 }
            //$$ }
            //#else
            Text hoverText = hoverEvent.getValue();
            BaseText newText = translate((BaseText) hoverText, lang, modifier);
            if (newText != hoverText) {
                text.getStyle().setHoverEvent(new HoverEvent(hoverEvent.getAction(), newText));
            }
            //#endif
        }

        // translate sibling texts
        List<Text> siblings = text.getSiblings();
        for (int i = 0; i < siblings.size(); i++) {
            Text sibling = siblings.get(i);
            BaseText newText = translate((BaseText) sibling, lang, modifier);
            if (newText != sibling) {
                siblings.set(i, newText);
            }
        }
        return text;
    }

    @FunctionalInterface
    private interface TextModifier {
        BaseText apply(
                //#if MC >= 11900
                //$$ MutableText translatableText,
                //#else
                TranslatableText translatableText,
                //#endif
                @Nullable String msgKeyString
        );
    }
}
