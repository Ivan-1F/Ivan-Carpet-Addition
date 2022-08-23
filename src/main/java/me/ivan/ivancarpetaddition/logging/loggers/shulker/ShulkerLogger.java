package me.ivan.ivancarpetaddition.logging.loggers.shulker;

import me.ivan.ivancarpetaddition.logging.loggers.AbstractLogger;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;

public class ShulkerLogger extends AbstractLogger {
    private static final String NAME = "shulker";
    private static final ShulkerLogger INSTANCE = new ShulkerLogger();

    private ShulkerLogger() {
        super(NAME, false);
    }

    public static ShulkerLogger getInstance() {
        return INSTANCE;
    }

    public void onShulkerTeleport(ShulkerEntity shulker, BlockPos from, BlockPos to, boolean isDupe) {
        this.log(option -> new MutableText[]{
                Messenger.c(
                        Messenger.entity("b", shulker),
                        "  @ ",
                        Messenger.coord(from),
                        "g  -> ",
                        Messenger.coord(to),
                        isDupe ? Messenger.c(
                                "q  (",
                                Messenger.formatting(tr("dupe"), "q"),
                                "q )"
                        ) : ""
                )
        });
    }
}
