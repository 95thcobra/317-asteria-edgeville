package com.asteria.aquickaccess;

import com.asteria.game.character.Animation;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.content.Spellbook;
import com.asteria.game.character.player.skill.Skills;
import com.asteria.game.location.Position;

public class ObjectSecondClickAction {

	public void handle(Player player, int objectId, Position position) {
		switch (objectId) {
			// bank
		case 11744:
			player.getBank().open();
			break;
		}
	}

}
