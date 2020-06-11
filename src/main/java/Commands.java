import java.awt.Color;
import java.util.concurrent.TimeUnit;

import command.GetKDRole;
import command.LookingForGroup;
import command.RegisterRegiment;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    // private String avatar =
    // "https://images-ext-1.discordapp.net/external/bwdl6cz65cP9iEH4VtSUQeROfyn9uyyAAYYN0cAx6zI/https/cdn.discordapp.com/avatars/122769236950122498/9597093aa42ceeddbcfa280782a24e49.png";
    private EmbedBuilder info = new EmbedBuilder();
    private LookingForGroup lfg = new LookingForGroup();
    private RegisterRegiment regr = new RegisterRegiment();
    private GetKDRole kdr = new GetKDRole();
    private WorkerProcess Bot = new WorkerProcess();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
	Message message = event.getMessage(); // The message that was received.
	TextChannel regimchannel = event.getGuild().getTextChannelById("691741134959149106");

	String[] args = event.getMessage().getContentRaw().split("\\s+");

	if (!event.getAuthor().isBot()) { // TIKRINA AR BOTAS KVIECIA KOMANDA

	    if (args[0].equalsIgnoreCase(Bot.prefix + "info")) { // INFO IF BLOCK
		lfg.infoLFG(event);

	    } else if (args[0].equalsIgnoreCase(Bot.prefix + "lfg")) { // LOOKING FOR PARTY IF BLOCK
		if (args.length == 1) {
		    lfg.lookingForGroup(event, args);
		} else if (args[1].contentEquals("info")) {
		    lfg.infoLFG(event);
		} else {
		    lfg.lookingForGroup(event, args);
		}
	    } else if (args[0].equalsIgnoreCase(Bot.prefix + "regiment") // REGISTRACIJA REGIMENTU
		    && message.getChannel().equals(regimchannel)) {
		if (args.length <= 1) {
		    message.delete().queueAfter(15, TimeUnit.SECONDS);
		    info.setColor(Color.RED).setTitle(
			    "Komandai truksta informacijos.\nMegink dar karta.\npvz: ``?regiment KomandosPavadinimas @aKu @Dole @Falanji``");
		    message.getChannel().sendMessage(info.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);
		    info.clear();
		} else {
		    regr.regRegiment(event, args);
		}

	    }

	    // K/D ROLE IF BLOCK BROKEN KOL ACTIVISION NENUIMS AUTH KEYS
	    if (args[0].equals(Bot.prefix + "role")) {
		int check = args.length;
		if (check == 1) {
		    kdr.infoEmbed(event);
		}
//				if (check == 2) {
//					kdr.getKDRole(event, args);
//				}

	    }

	} else {
	    System.out.println(message);
	}

    }

    // SPAUSDINA ZINUTES CONSOLEJE, KURIUOS MATO BOTAS.
    public void onMessageReceived(MessageReceivedEvent event) {

	User author = event.getAuthor(); // The user that sent the message
	Message message = event.getMessage(); // The message that was received.

	String msg = message.getContentDisplay(); // This returns a human readable version of the Message. Similar to

	if (event.isFromType(ChannelType.TEXT)) // If this message was sent to a Guild TextChannel
	{

	    Guild guild = event.getGuild();
	    TextChannel textChannel = event.getTextChannel(); // The TextChannel that this message was sent to.
	    Member member = event.getMember();

	    String name;
	    if (message.isWebhookMessage()) {
		name = author.getName();
	    } else {
		name = member.getEffectiveName();
	    }

	    System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);

	} else if (event.isFromType(ChannelType.PRIVATE)) // If this message was sent to a PrivateChannel
	{

	    System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
	}

    }

}