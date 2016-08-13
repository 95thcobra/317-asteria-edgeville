package com.asteria.net.message.impl;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.asteria.game.World;
import com.asteria.game.character.Locations;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.Rights;
import com.asteria.game.character.player.minigame.Minigame;
import com.asteria.game.character.player.minigame.MinigameHandler;
import com.asteria.game.plugin.context.ButtonClickPlugin;
import com.asteria.net.message.InputMessageListener;
import com.asteria.net.message.MessageBuilder;
import com.asteria.utility.BufferUtils;

/**
 * The message sent from the client when the player clicks some sort of button
 * or module.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class ClickButtonMessage implements InputMessageListener {

	// TODO: Convert all buttons to the proper identifications.

	/**
	 * The flag that determines if this message should be read properly.
	 */
	private static final boolean PROPER_READ = false;

	@Override
	public void handleMessage(Player player, int opcode, int size, MessageBuilder payload) {
		int button = PROPER_READ ? payload.getShort() : BufferUtils.hexToInt(payload.getBytes(2));
		//World.getPlugins().execute(player, ButtonClickPlugin.class, new ButtonClickPlugin(button));

		switch (button) {

		// home tele regular magic
		case 4171:
			player.teleport(Locations.EDGEVILLE.getPosition());
			break;

		// logout 
		case 9154:
			if (!player.getLastCombat().elapsed(10, TimeUnit.SECONDS)) {
				player.message("You must wait 10 seconds after combat before logging out.");
				break;
			}
			World.getPlayers().remove(player);
			break;

		default:
			if (player.getRights() == Rights.DEVELOPER) {
				player.message("Unhandled button: %d", button);
				break;
			}
		}
	}
}
