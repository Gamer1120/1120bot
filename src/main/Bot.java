package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {

	// Instance variables

	// Server and port the bot connects to
	private static final String SERVER = "irc.twitch.tv";
	private static final int PORT = 6667;

	// Channel the bot connects to
	private static final String CHANNEL = "#gamer1120";

	// Name of the bot
	private static final String TWITCHNAME = "1120bot";
	private List<String> ignorelist;

	public Bot() {
		this.setName(TWITCHNAME);
		this.ignorelist = new ArrayList<String>();
		init();
	}

	private void init() {
		// Set up ignore list, we don't want to say the messages of any twitch
		// bots.
		try {
			this.connect(SERVER, PORT, Passwords.OAUTH);
			this.joinChannel(CHANNEL);
			System.out.println("Joined channel: " + CHANNEL);
			//TODO: Decide whether to keep this or not.
			sendMessage(CHANNEL, "Joined channel: " + CHANNEL);
		} catch (NickAlreadyInUseException e) {
			System.out.println("Nickname already in use!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException Error");
			e.printStackTrace();
		} catch (IrcException e) {
			System.out.println("IRCException Error");
			e.printStackTrace();
		}
		
		ignorelist.add("1120bot");
		ignorelist.add("xanbot");
		ignorelist.add("nightbot");
		ignorelist.add("moobot");
		ignorelist.add("mikuia");
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		
	}

	public static void main(String[] args) {
		// Easy method to startup the bot.
		new Bot();
	}
}
