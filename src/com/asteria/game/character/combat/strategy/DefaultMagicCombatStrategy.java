package com.asteria.game.character.combat.strategy;

import com.asteria.game.NodeType;
import com.asteria.game.character.Animation;
import com.asteria.game.character.AnimationPriority;
import com.asteria.game.character.CharacterNode;
import com.asteria.game.character.combat.Combat;
import com.asteria.game.character.combat.CombatSessionData;
import com.asteria.game.character.combat.CombatStrategy;
import com.asteria.game.character.combat.CombatType;
import com.asteria.game.character.combat.magic.CombatSpell;
import com.asteria.game.character.combat.weapon.FightType;
import com.asteria.game.character.npc.Npc;
import com.asteria.game.character.player.Player;
import com.asteria.game.character.player.content.WeaponInterface;
import com.asteria.game.item.Item;
import com.asteria.game.item.container.Equipment;

public class DefaultMagicCombatStrategy implements CombatStrategy {

	@Override
	public boolean canAttack(CharacterNode character, CharacterNode victim) {
		if (character.getType() == NodeType.NPC) {
			return true;
		}
		Player player = (Player) character;
		return getSpell(player).canCast(player);
	}

	private CombatSpell getSpell(Player player) {
		if (player.isAutocast() && player.getCastSpell() != null && player.getAutocastSpell() != null || !player.isAutocast() && player.getAutocastSpell() == null) {
			return player.getCastSpell();
		}
		return player.getAutocastSpell();
	}

	@Override
	public CombatSessionData attack(CharacterNode character, CharacterNode victim) {
		if (character.getType() == NodeType.PLAYER) {
			Player player = (Player) character;
			player.prepareSpell(getSpell(player), victim);
		} else if (character.getType() == NodeType.NPC) {
			Npc npc = (Npc) character;
			npc.prepareSpell(Combat.prepareSpellCast(npc).getSpell(), victim);
		}

		if (character.getCurrentlyCasting().maximumHit() == -1) {
			return new CombatSessionData(character, victim, CombatType.MAGIC, true);
		}
		return new CombatSessionData(character, victim, 1, CombatType.MAGIC, true);
	}

	@Override
	public int attackDelay(CharacterNode character) {
		return 10;
	}

	@Override
	public int attackDistance(CharacterNode character) {
		return 8;
	}

	@Override
	public int[] getNpcs() {
		return new int[] { 13, 172, 174 };
	}
}
