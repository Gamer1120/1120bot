package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
		setUpChannelList();
		setUpConnection();
	}

	private void setUpChannelList() {
		channelList.add("#cohhcarnage");
		channelList.add("#gamer1120");
		channelList.add("#mythred");
		channelList.add("#xxthebigbearxx");
		channelList.add("#wolfsgorawr");
		channelList.add("#inspekt0r");
		channelList.add("#sevadus");
		channelList.add("#wolsk");
		channelList.add("#tehjman1993");
		channelList.add("#zurairofl");
		channelList.add("#sarcaster");
		channelList.add("#kikichan94");

	}

	private void setUpConnection() {
		try {
			this.connect(SERVER, PORT, Passwords.OAUTH);
			joinChannels();
		} catch (NickAlreadyInUseException e) {
			System.out.println("Nickname already in use!");
			JOptionPane
					.showConfirmDialog(
							null,
							"Nickname already in use. I don't even know how this is possible../",
							"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException Error");
			JOptionPane.showConfirmDialog(null,
					"Cheesebot was unable to connect to the Twitch IRC server. Your internet is probably down.",
					"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		} catch (IrcException e) {
			System.out.println("IRCException Error");
			JOptionPane.showMessageDialog(null,
					"Cheesebot was unable to connect to the Twitch IRC server. Got an IRCException.",
					"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void joinChannels() {
		for (String channel : channelList) {
			this.joinChannel(channel);
			System.out.println("Joined channel: " + channel);
		}
		JOptionPane.showMessageDialog(null,
				"Successfully connected to IRC channels.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		new Bot();
	}
}
