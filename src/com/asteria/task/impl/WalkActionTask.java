package com.asteria.task.impl;

import com.asteria.game.character.player.Player;
import com.asteria.game.location.Position;
import com.asteria.task.Task;

public abstract class WalkActionTask extends Task {

	private Player player;
	private Position targetPosition;
	
	public WalkActionTask(Player player, Position targetPosition) {
		super(0, true);
		this.player = player;
		this.targetPosition = targetPosition;
		player.setCurrentCancellableTask(this);
	}

	@Override
	public void execute() {
		player.message("EXECUTING");
		if (player.getPosition().withinDistance(targetPosition, 2)) {
			doAction();
			player.message("DO ACTION");
			this.cancel();
		}
	}

	public abstract void doAction();

}
