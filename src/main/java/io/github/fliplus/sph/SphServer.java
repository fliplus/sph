package io.github.fliplus.sph;

import io.github.fliplus.sph.commands.GiveHeadCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class SphServer implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> GiveHeadCommand.register(dispatcher));
    }

}