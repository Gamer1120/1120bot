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
	private static final int MAXTRIES = 2;

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
		channelList.add("#wolsk");
		channelList.add("#tehjman1993");
		channelList.add("#zurairofl");
		channelList.add("#sarcaster");
		channelList.add("#kikichan94");
		
	}

	private void setUpConnection(int tries) {
		if (tries < MAXTRIES) {
			try {
				this.connect(SERVER, PORT, Passwords.OAUTH);
				joinChannels();
			} catch (NickAlreadyInUseException e) {
				System.out.println("Nickname already in use!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException Error");
				JOptionPane.showConfirmDialog(null,"Couldn't connect to IRC channels (IOException) :(","ERROR",JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				setUpConnection(tries + 1);
			} catch (IrcException e) {
				System.out.println("IRCException Error");
				JOptionPane.showMessageDialog(null,"Couldn't connect to IRC channels (IRCException) :(","ERROR",JOptionPane.WARNING_MESSAGE);
				setUpConnection(tries + 1);
				e.printStackTrace();
			}
		} else {
			
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
