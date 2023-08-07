package io.github.fliplus.sph.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class GiveHeadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("givehead")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("player", StringArgumentType.word())
                                .executes(context -> giveHead(
                                        context.getSource(),
                                        EntityArgument.getPlayers(context, "targets"),
                                        StringArgumentType.getString(context, "player"),
                                        1
                                ))
                                .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                        .executes(context -> giveHead(
                                                context.getSource(),
                                                EntityArgument.getPlayers(context, "targets"),
                                                StringArgumentType.getString(context, "player"),
                                                IntegerArgumentType.getInteger(context, "count")
                                        ))
                                )
                        )
                )
        );
    }

    private static int giveHead(CommandSourceStack source, Collection<ServerPlayer> targets, String player, int count) throws CommandSyntaxException {
        for (ServerPlayer serverPlayer : targets) {
            String giveCommand = String.format("/give %s minecraft:player_head{SkullOwner:\"%s\"} %d", serverPlayer.getName().getString(), player, count);
            source.getServer().getCommands().performPrefixedCommand(source.getServer().createCommandSourceStack().withSource(source.getPlayer()), giveCommand);
        }

        return targets.size();
    }

}