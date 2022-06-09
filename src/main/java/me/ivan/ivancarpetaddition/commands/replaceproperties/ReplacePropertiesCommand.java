package me.ivan.ivancarpetaddition.commands.replaceproperties;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.commands.AbstractCommand;
import me.ivan.ivancarpetaddition.utils.CarpetModUtil;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.command.argument.BlockPosArgumentType.*;
import static net.minecraft.command.argument.BlockPredicateArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class ReplacePropertiesCommand extends AbstractCommand {
    private static final String NAME = "replaceproperties";
    private static final ReplacePropertiesCommand INSTANCE = new ReplacePropertiesCommand();

    private ReplacePropertiesCommand() {
        super(NAME);
    }

    public static ReplacePropertiesCommand getInstance() {
        return INSTANCE;
    }

    public void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> root = literal(NAME).requires((player) -> CarpetModUtil.canUseCommand(player, IvanCarpetAdditionSettings.commandReplaceProperties));

        root.then(argument("from", blockPos())
                .then(argument("to", blockPos())
                        .then(argument("property_name", string())
                                .then(argument("value", string())
                                        .executes(this::execute)
                                        .then(argument("block_predicate", blockPredicate())
                                                .executes(this::execute)
                                        )
                                )
                        )
                )
        );

        dispatcher.register(root);
    }

    private int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Predicate<CachedBlockPosition> blockPredicate = null;
        try {
            blockPredicate = getBlockPredicate(context, "block_predicate");
        } catch (IllegalArgumentException ignored) {

        }
        return this.execute(
                context.getSource(),
                new BlockBox(getLoadedBlockPos(context, "from"), getLoadedBlockPos(context, "to")),
                getString(context, "property_name"),
                getString(context, "value"),
                blockPredicate
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

    private int execute(
            ServerCommandSource source,
            BlockBox range,
            String propertyName,
            String value,
            @Nullable Predicate<CachedBlockPosition> predicate
    ) {
        int count = 0;
        for (BlockPos pos : BlockPos.iterate(range.minX, range.minY, range.minZ, range.maxX, range.maxY, range.maxZ)) {
            if (predicate != null && !predicate.test(new CachedBlockPosition(source.getWorld(), pos, true))) continue;
            BlockState state = source.getWorld().getBlockState(pos);
            Optional<BlockState> optional = this.modifyBlockState(state, propertyName, value);
            if (optional.isPresent()) {
                if (!optional.get().equals(state)) {
                    source.getWorld().setBlockState(pos, optional.get());
                    count++;
                }
            }
        }
        Messenger.tell(source, tr("done", propertyName, value, count));
        return 1;
    }
}
