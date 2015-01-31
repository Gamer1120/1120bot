package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {
	private static final String SERVER = "irc.twitch.tv";
	private static final int PORT = 6667;
	private static final String TWITCHNAME = "gamer1120";
	private List<String> channelList;

	public Bot() {
		this.setName(TWITCHNAME);
		this.channelList = new ArrayList<String>();
		init();
	}

	private void init() {
		channelList.add("#cohhcarnage");
		channelList.add("#gamer1120");
		channelList.add("#mythred");
		channelList.add("#xxthebigbearxx");
		try {
			this.connect(SERVER, PORT, Passwords.OAUTH);
			for (String channel : channelList) {
				this.joinChannel(channel);
			}
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
	}

	public static void main(String[] args) {
		new Bot();
	}
}
