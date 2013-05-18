package com.mordrum.mlib.question;

import com.mordrum.mlib.mLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Vector;

public class Questioner {
	private static final Vector<Question> questions = new Vector<Question>();
	public static ChatColor messageColor = ChatColor.WHITE;

	public Questioner(mLib main) {
		mLib.server.getPluginManager().registerEvents(new QuestionerPlayerListener(questions), main);
		mLib.server.getScheduler().scheduleSyncRepeatingTask(main, new QuestionsReaper(questions), 15000, 15000);
	}

	public static String ask(Player respondent, String questionMessage, String... answers) {
		return ask(respondent, true, questionMessage, answers);
	}

	public static String ask(Player respondent, boolean isQuestion, String questionMessage, String... answers) {
		final Question question = new Question(respondent, questionMessage, answers);
		questions.add(question);
		return question.ask(isQuestion);
	}

	public static String askOpenQuestion(Player respondent, String questionMessage) {
		final Question question = new Question(respondent, questionMessage);
		questions.add(question);
		return question.response();
	}

	//TODO add waitForAnswer support
	/*public static boolean waitForAnswer(Player respondent, String answerToWaitFor) {
		Question question = new Question(respondent, null, answerToWaitFor);
		questions.add(question);
	}*/

	public static void setDefaultMessageColor(ChatColor defaultColor) {
		Questioner.messageColor = defaultColor;
	}
}
