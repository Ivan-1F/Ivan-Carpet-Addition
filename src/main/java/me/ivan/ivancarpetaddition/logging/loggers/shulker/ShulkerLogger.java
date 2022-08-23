package me.ivan.ivancarpetaddition.logging.loggers.shulker;

import me.ivan.ivancarpetaddition.logging.loggers.AbstractLogger;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.text.BaseText;
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

    public void onShulkerTeleport(ShulkerEntity shulker, BlockPos from, BlockPos to) {
        this.log(option -> new BaseText[]{
                Messenger.c(
                        Messenger.entity("b", shulker),
                        "  @ ",
                        Messenger.coord(from),
                        "g  -> ",
                        Messenger.coord(to)
                )
        });
    }
}
