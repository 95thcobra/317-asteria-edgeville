package com.asteria.aquickaccess;

import com.asteria.game.character.Animation;
import com.asteria.game.character.npc.Npc;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.content.Spellbook;
import com.asteria.game.character.player.skill.Skills;
import com.asteria.game.location.Position;

public class NpcSecondClickAction {

	public void handle(Player player, Npc npc, Position position) {
		switch (npc.getId()) {
		
		// edgeville ditch
		case 23271:
			Position pos = player.getPosition();
			boolean below = pos.getY() < 3521;
			player.move(new Position(pos.getX(), below ? 3523 : 3520));
			break;
		}
	}

}
