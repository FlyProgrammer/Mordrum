package com.mordrum.mcommon.api.question;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Vector;

public class QuestionerPlayerListener implements Listener {
	private final Vector<Question> questions;

	public QuestionerPlayerListener(Vector<Question> questions) {
		this.questions = questions;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!questions.isEmpty()) {
			final int playerHash = event.getPlayer().getName().hashCode();
			final int answerHash = event.getMessage().toLowerCase().hashCode();
			for (final Question question : questions) {

				if (question.isPlayerQuestioned(playerHash) && question.isOpenQuestion()) {
					question.returnResponse(event.getMessage());
					questions.remove(question);
					event.setCancelled(true);
					break;
				} else if (question.isPlayerQuestioned(playerHash) && question.isRightAnswer(answerHash)) {
					question.returnAnswer(answerHash);
					questions.remove(question);
					event.setCancelled(true);
					break;
				}
			}
		}
	}
}
