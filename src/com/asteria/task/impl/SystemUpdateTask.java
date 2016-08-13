package com.asteria.task.impl;

import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

import com.asteria.game.World;
import com.asteria.game.character.player.Player;
import com.asteria.task.Task;

public class SystemUpdateTask extends Task {

	public SystemUpdateTask(int delay) {
		super(delay, false);
	}

	@Override
	public void execute() {
		World.setServerUpdated(true);
		for (Player player : World.getPlayers()) {
			if (player == null) {
				continue;
			}
			//World.getPlayers().remove(player); // logs ppl out
			player.save();
			this.cancel();
		}

		try {
			if (SystemUtils.IS_OS_LINUX) {
				Runtime.getRuntime().exec("killall screen");
				Runtime.getRuntime().exec("screen -A -m -d -S rsps java -classpath bin:lib/* edgeville.GameServer");
			} else if (SystemUtils.IS_OS_WINDOWS) {
				Runtime.getRuntime().exec("cmd /c start run.bat");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Server has shut down.");
		System.exit(0);
	}

}
