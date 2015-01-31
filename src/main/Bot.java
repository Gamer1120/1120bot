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
		setUpConnection(0);
	}

	private void setUpChannelList() {
		channelList.add("#cohhcarnage");
		channelList.add("#gamer1120");
		channelList.add("#mythred");
		channelList.add("#xxthebigbearxx");
		channelList.add("#wolfsgorawr");
		channelList.add("#inspekt0r");
		channelList.add("#sevadus");
	}

	private void setUpConnection(int tries) {
		if (tries < 10) {
			try {
				this.connect(SERVER, PORT, Passwords.OAUTH);
				joinChannels();
			} catch (NickAlreadyInUseException e) {
				System.out.println("Nickname already in use!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException Error");
				e.printStackTrace();
				setUpConnection(tries + 1);
			} catch (IrcException e) {
				System.out.println("IRCException Error");
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null,"Couldn't connect to IRC channels :(","ERROR",JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	private void joinChannels() {
		for (String channel : channelList) {
			this.joinChannel(channel);
			System.out.println("Joined channel: " + channel);
		}
	}

	public static void main(String[] args) {
		new Bot();
	}
}
