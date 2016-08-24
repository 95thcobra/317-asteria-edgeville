package com.asteria.aquickaccess;

import java.util.Optional;
import java.util.function.Consumer;

import com.asteria.game.character.Animation;
import com.asteria.game.character.Locations;
import com.asteria.game.character.npc.Npc;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.content.Spellbook;
import com.asteria.game.character.player.dialogue.NpcDialogue;
import com.asteria.game.character.player.dialogue.OptionDialogue;
import com.asteria.game.character.player.dialogue.OptionType;
import com.asteria.game.character.player.skill.Skills;
import com.asteria.game.location.Position;

public class NpcFirstClickAction {

	public void handle(Player player, Npc npc, Position position) {
		int npcId = npc.getId();
		switch (npcId) {

		// edge teleport wizard
		case 4400:
			player.getDialogueChain().append(new NpcDialogue(npcId, "Where would you like to teleport to?"));
			player.getDialogueChain().append(new OptionDialogue("Godwars Dungeon", "Other") {
				@Override
				public Optional<Consumer<OptionType>> getOptionListener() {
					return Optional.of(new Consumer<OptionType>() {
						@Override
						public void accept(OptionType t) {
							switch (t) {
							case FIRST_OPTION:
								player.teleport(Locations.BANDOS_OUTSIDE.getPosition());
								break;
							case SECOND_OPTION:
								player.message("Other");
								break;
								
							default:
								player.message("Unhandled option");
								break;
							}
							player.getMessages().sendCloseWindows();
						}
					});
				}
			});
			player.getDialogueChain().advance();
			break;
		}
	}

}
