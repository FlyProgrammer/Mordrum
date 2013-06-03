package com.mordrum.mcommon.question;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Question {
	private String answer = null;
	private HashMap<Integer, String> answers;
	private final String questionMessage;
	private final Player respondent;
	private final int respondentHash;
	private final long start;
	private boolean openQuestion = false;

	Question(Player respondent, String questionMessage, String[] answers) {
		start = System.currentTimeMillis();
		this.respondent = respondent;
		respondentHash = respondent.getName().hashCode();
		this.questionMessage = questionMessage;
		this.answers = new HashMap<Integer, String>(answers.length);
		for (final String ans : answers) {
			String strippedAns = ChatColor.stripColor(ans);
			this.answers.put(strippedAns.toLowerCase().hashCode(), ans);
		}
	}

	Question(Player respondent, String questionMessage) {
		start = System.currentTimeMillis();
		this.respondent = respondent;
		respondentHash = respondent.getName().hashCode();
		this.questionMessage = questionMessage;
	}

	synchronized String ask(boolean isQuestion) {
		final StringBuilder options = new StringBuilder();
		for (final String ans : answers.values())
			options.append(ans + ", ");
		options.delete(options.length() - 2, options.length());
		respondent.sendMessage(Questioner.messageColor + questionMessage);
		if (isQuestion) {
			respondent.sendMessage(Questioner.messageColor + "- " + options + "?");
		} else {
			respondent.sendMessage(Questioner.messageColor + "- " + options);
		}
		try {
			this.wait();
		} catch (final InterruptedException ex) {
			answer = "interrupted";
		}
		return ChatColor.stripColor(answer);
	} //Ask the player a question in traditional format

	synchronized String response() {
		openQuestion = true;
		respondent.sendMessage(Questioner.messageColor + questionMessage);
		try {
			this.wait();
		} catch (final InterruptedException ex) {
			answer = "interrupted";
		}
		return ChatColor.stripColor(answer);
	} //Ask the player an open ended question


	synchronized boolean isExpired() {
		if (System.currentTimeMillis() - start > 300000) {
			answer = "timed out";
			notify();
			return true;
		}
		return false;
	}

	boolean isPlayerQuestioned(int playerNameHash) {
		return playerNameHash == respondentHash;
	}

	boolean isRightAnswer(int answerHash) {
		return answers.containsKey(answerHash);
	}

	boolean isOpenQuestion() {
		return openQuestion;
	}

	synchronized void returnAnswer(int answerHash) {
		answer = answers.get(answerHash);
		notify();
	}

	synchronized void returnResponse(String Answer) {
		answer = Answer;
		notify();
	}
}
