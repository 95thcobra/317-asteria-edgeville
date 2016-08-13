package com.asteria.net.message.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.asteria.game.World;
import com.asteria.game.character.Animation;
import com.asteria.game.character.Graphic;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.skill.Skills;
import com.asteria.game.item.Item;
import com.asteria.game.item.ItemDefinition;
import com.asteria.game.location.Position;
import com.asteria.net.message.InputMessageListener;
import com.asteria.net.message.MessageBuilder;

/**
 * The message that is sent from the client when the player chats anything
 * beginning with '::'.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class CommandMessage implements InputMessageListener {

	private static String glue(String... args) {
		return Arrays.stream(args).collect(Collectors.joining(" "));
	}

	@Override
	public void handleMessage(Player player, int opcode, int size, MessageBuilder payload) {

		String[] command = payload.getString().toLowerCase().split(" ");

		switch (player.getRights()) {
		case PLAYER:
		case DONATOR:
		case VETERAN:
			handlePlayerCommands(player, command);
			break;
		case MODERATOR:
			handlePlayerCommands(player, command);
			handleModeratorCommands(player, command);
			break;
		case ADMINISTRATOR:
			handlePlayerCommands(player, command);
			handleModeratorCommands(player, command);
			handleAdministratorCommands(player, command);
			break;
		case DEVELOPER:
			handlePlayerCommands(player, command);
			handleModeratorCommands(player, command);
			handleAdministratorCommands(player, command);
			handleDeveloperCommands(player, command);
			break;
		}
	}

	private void handlePlayerCommands(Player player, String[] command) {

	}

	private void handleModeratorCommands(Player player, String[] command) {

	}

	private void handleAdministratorCommands(Player player, String[] command) {
		switch (command[0].toLowerCase()) {
		case "anim":
			player.animation(new Animation(Integer.parseInt(command[1])));
			break;
		case "gfx":
			player.graphic(new Graphic(Integer.parseInt(command[1])));
			break;
		}
	}

	private void handleDeveloperCommands(Player player, String[] command) {
		switch (command[0].toLowerCase()) {
		case "update":
			int delay = Integer.parseInt(command[1]);
			World.update(delay);
			break;

		case "master":
			for (int i = 0; i < 6; i++) {
				//Skill skill = player.getSkills()[i];
				Skills.experience(player, 15000000, i);
			}
			player.determineCombatLevel();
			break;
		case "item":
			if (command.length > 2) {
				player.getInventory().add(new Item(Integer.parseInt(command[1]), Integer.parseInt(command[2])));
			} else {
				player.getInventory().add(new Item(Integer.parseInt(command[1]), 1));
			}
			break;
		case "tele":
			player.move(new Position(Integer.parseInt(command[1]), Integer.parseInt(command[2])));
			break;
		case "edge":
			player.move(new Position(3093, 3493));
			break;
		case "mypos":
			player.message("x: %d, y: %d", player.getPosition().getX(), player.getPosition().getY());
			break;
		case "local":
			int lx = player.getPosition().getLocalX();
			int ly = player.getPosition().getLocalY();
			int x = player.getPosition().getLocalX() + 8 * player.getPosition().getRegionX();
			int y = player.getPosition().getLocalY() + 8 * player.getPosition().getRegionY();
			String message = String.format("localx: %d (%d), localy:%d (%d)", lx, x, ly, y);
			player.getMessages().sendMessage(message);
			break;
		case "find":
			String item = command[1];
			if (command.length >= 3) {
				item += " " + command[2];
			}
			final String search = item.toLowerCase();

			new Thread(() -> {
				int found = 0;

				for (int i = 0; i < 7900; i++) {
					if (found >= 250) {
						player.message("Too many results, try again.");
						return;
					}
					ItemDefinition def = new Item(i).getDefinition();
					if (def != null && def.getName().toLowerCase().contains(search)) {
						player.message("Result: " + i + " - " + def.getName());
						found++;
					}
				}
				player.message("Found " + found + " items.");
			}).start();
		}
	}
}
