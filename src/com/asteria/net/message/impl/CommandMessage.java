package com.asteria.net.message.impl;

import com.asteria.game.character.Flag;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.skill.Skill;
import com.asteria.game.character.player.skill.Skills;
import com.asteria.game.item.Item;
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

	@Override
	public void handleMessage(Player player, int opcode, int size, MessageBuilder payload) {
		if (player.isDisabled())
			return;

		String[] command = payload.getString().toLowerCase().split(" ");

		switch (command[0]) {
		case "master":
			for (int i = 0; i < 6; i++) {
				Skill skill = player.getSkills()[i];
				skill.setExperience(15000000);
				skill.setLevel(99, true);
				Skills.refresh(player, i);
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
		}
	}
}
