package me.ivan.ivancarpetaddition.commands.replaceproperties;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.TranslationContext;
import me.ivan.ivancarpetaddition.utils.CarpetModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.command.arguments.BlockPosArgumentType.*;
import static net.minecraft.server.command.CommandManager.argument;

public class ReplacePropertiesCommand extends TranslationContext {
    private static final ReplacePropertiesCommand INSTANCE = new ReplacePropertiesCommand();

    protected ReplacePropertiesCommand() {
        super("command.replaceproperties");
    }

    public static ReplacePropertiesCommand getInstance() {
        return INSTANCE;
    }

    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> root = CommandManager.literal("replaceproperties").requires((player) -> CarpetModUtil.canUseCommand(player, IvanCarpetAdditionSettings.commandReplaceProperties));

        root.then(argument("from", blockPos())
                .then(argument("to", blockPos())
                        .then(argument("property_name", string())
                                .then(argument("value", string())
                                        .executes(this::execute)
                                )
                        )
                )
        );

        dispatcher.register(root);
    }

    private int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return this.execute(
                context.getSource(),
                new BlockBox(getLoadedBlockPos(context, "from"), getLoadedBlockPos(context, "to")),
                getString(context, "property_name"),
                getString(context, "value")
        );
    }

    private <T extends Comparable<T>> Optional<BlockState> setProperty(BlockState state, Property<T> property, String value) {
        Optional<T> optional = property.parse(value);
        return optional.map(t -> state.with(property, t));
    }

    private Optional<BlockState> modifyBlockState(BlockState state, String propertyName, String stringValue) throws IllegalArgumentException {
        Optional<Property<?>> propertyOptional = state
                .getProperties()
                .stream()
                .filter(property -> property.getName().equals(propertyName))
                .findFirst();
        if (propertyOptional.isPresent()) {
            return setProperty(state, propertyOptional.get(), stringValue);
        }
        return Optional.empty();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private int execute(ServerCommandSource source, BlockBox range, String propertyName, String value) {
        int count = 0;
        for (BlockPos pos : BlockPos.iterate(range.minX, range.minY, range.minZ, range.maxX, range.maxY, range.maxZ)) {
            BlockState state = source.getWorld().getBlockState(pos);
            Optional<BlockState> optional = this.modifyBlockState(state, propertyName, value);
            if (optional.isPresent()) {
                if (!optional.get().equals(state)) {
                    source.getWorld().setBlockState(pos, optional.get());
                    count++;
                }
            }
        }
        source.sendFeedback(tr("done", propertyName, value, count), false);
        return 1;
    }
}
