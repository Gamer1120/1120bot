package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {
	private static final String SERVER = "irc.twitch.tv";
	private static final int PORT = 6667;
	private static final String TWITCHNAME = "1120bot";
	private static final String CHANNEL = "#xxthebigbearxx";
	private CommandList cmdList;
	private AdminList adminList;

	public Bot() {
		this.setName(TWITCHNAME);
		this.cmdList = new CommandList();
		this.adminList = new AdminList();
		setUpConnection();
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
							"Nickname already in use. I don't even know how this is possible...",
							"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException Error");
			JOptionPane
					.showConfirmDialog(
							null,
							"Cheesebot was unable to connect to the Twitch IRC server. Your internet is probably down.",
							"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		} catch (IrcException e) {
			System.out.println("IRCException Error");
			JOptionPane
					.showMessageDialog(
							null,
							"Cheesebot was unable to connect to the Twitch IRC server. Got an IRCException.",
							"ERROR", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void sendMessage(String message) {
		sendMessage(CHANNEL, message);
	}

	private void joinChannels() {
		this.joinChannel(CHANNEL);
		System.out.println("Joined channel: " + CHANNEL);
	}

	public static void main(String[] args) {
		new Bot();
	}

	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		System.out.println(sender + ": " + message);
		// MASTER BYPASS
		if (message.equals("!picnic") && sender.equals("gamer1120")) {
			try {
				adminList.add("gamer1120");
				sendMessage("panicBasket The almighty Gamer1120 shall now be an admin of this bot! PraiseIt panicBasket");
			} catch (AdminAlreadyExistsException e) {
				sendMessage("No picnic necessary!");
			}
		}
		if (message.startsWith("!") && !sender.equals(TWITCHNAME)
				&& adminList.getList().contains(sender)) {
			String[] messageArray = message.split(" ");
			switch (messageArray[0]) {
			case "!command":
				if (messageArray.length < 2) {
					sendMessage("Use this syntax: !command <add/remove/edit> <!trigger> <message>");
				} else {
					switch (messageArray[1]) {
					case "add":
						if (messageArray[2].startsWith("!")) {
							if (messageArray.length >= 4) {
								String text = "";
								for (int i = 3; i < messageArray.length; i++) {
									text += messageArray[i] + " ";
								}
								try {
									cmdList.add(messageArray[2], text);
									sendMessage("Command successfully added.");
								} catch (CommandAlreadyExistsException e) {
									sendMessage("This command already exists. Use !command edit "
											+ messageArray[2]
											+ " <message> to edit this command.");
								}
							} else {
								sendMessage("Use this syntax: !command add <!trigger> <message>.");
							}
						} else {
							sendMessage("All commands must start with an !.");
						}
						break;
					case "edit":
						if (messageArray[2].startsWith("!")) {
							if (messageArray.length >= 4) {
								String text = "";
								for (int i = 3; i < messageArray.length; i++) {
									text += messageArray[i] + " ";
								}
								try {
									cmdList.edit(messageArray[2], text);
									sendMessage("Command successfully edited.");
								} catch (NoSuchCommandException e) {
									sendMessage("This command doesn't exist.");
								}
							} else {
								sendMessage("Use this syntax: !command edit <!trigger> <message>.");
							}
						} else {
							sendMessage("All commands must start with an !.");
						}
						break;

					case "remove":
						if (messageArray.length == 3) {
							try {
								cmdList.remove(messageArray[2]);
								sendMessage("Command successfully removed.");
							} catch (NoSuchCommandException e) {
								sendMessage("This command doesn't exist.");
							}
						} else {
							sendMessage("Use this syntax: !command remove <!trigger>.");
						}
						break;
					case "list":
						if (messageArray.length == 2) {
							sendMessage(cmdList.getList());
						}
						break;
					}

				}
				break;
			case "!admin":
				if (messageArray.length < 2) {
					sendMessage("Use this syntax: !admin <add/remove/list> <name>");
				} else {
					switch (messageArray[1]) {
					case "add":
						try {
							adminList.add(messageArray[2]);
							sendMessage("Admin successfully added.");
						} catch (AdminAlreadyExistsException e) {
							sendMessage("This person is already an admin!");
						}
						break;
					case "remove":
						try {
							adminList.remove(messageArray[2]);
							sendMessage("Admin successfully removed.");
						} catch (NoSuchAdminException e) {
							sendMessage("This person isn't an admin.");
						}
						break;
					case "list":
						if (messageArray.length == 2) {
							sendMessage(adminList.getList());
						}
						break;

					default:
						try {
							sendMessage(cmdList.getCommand(messageArray[0]));
						} catch (NoSuchCommandException e) {
						}
						break;
					}
				}
			}
		}
	}
}
