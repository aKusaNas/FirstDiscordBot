package command;

import java.awt.Color;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RegisterRegiment {
	private String avatar = "https://images-ext-1.discordapp.net/external/bwdl6cz65cP9iEH4VtSUQeROfyn9uyyAAYYN0cAx6zI/https/cdn.discordapp.com/avatars/122769236950122498/9597093aa42ceeddbcfa280782a24e49.png";
	private EmbedBuilder info = new EmbedBuilder();

	// REGISTRUOJA KLANUS, BETA, PRIKLAUSYS NUO NAUDOJIMO.
	public void regRegiment(GuildMessageReceivedEvent event, String[] args) {
		TextChannel lchannel = event.getGuild().getTextChannelById("693913260734414909");
		Message message = event.getMessage();

		message.delete().queueAfter(5, TimeUnit.SECONDS);
		if (message.getMentionedMembers().size() >= 2) {

			info.setColor(Color.CYAN).setAuthor("Nauja Registracijos Forma")
					.setTitle("Regiment Pavadinimas: \n" + args[1].toString())
					.addField("Regiment registruojantis asmuo: \n", message.getAuthor().getAsMention(), true)
					.addBlankField(true)
					.addField("Registruojami zaidejai:", message.getMentionedMembers().stream()
							.map(s -> s.getAsMention()).collect(Collectors.joining("\n")), true)
					.setFooter("creator aKu//", avatar);
			lchannel.sendMessage(info.build()).complete();
			info.clear();

			info.setColor(Color.MAGENTA).setAuthor("Registracija pavyko")
					.setTitle("Regiment Pavadinimas: \n" + args[1].toString())
					.addField("Registracijos peržiūra", "", false)
					.addField("Regiment registruojantis asmuo: \n", message.getAuthor().getAsMention(), true)
					.addField("Registruojami zaidejai:",
							message.getMentionedMembers().stream().map(s -> s.getAsMention())
									.collect(Collectors.joining("\n")),
							true)
					.addField("Ačiū, dabar laukite kol administracija sukurs jums role ir kanalus", "", false)
					.setFooter("Warzone LT administracija.");
			message.getChannel().sendMessage(info.build()).complete().delete().queueAfter(10, TimeUnit.MINUTES);
			info.clear();

		} else {
			info.setColor(Color.RED).setTitle("Privaloma užtaginti bent 2 žaidejus.");
			message.getChannel().sendMessage(info.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);
			info.clear();
		}

	}
}
