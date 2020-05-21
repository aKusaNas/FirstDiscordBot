package command;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LookingForGroup extends ListenerAdapter {

    String avatar = "https://images-ext-1.discordapp.net/external/bwdl6cz65cP9iEH4VtSUQeROfyn9uyyAAYYN0cAx6zI/https/cdn.discordapp.com/avatars/122769236950122498/9597093aa42ceeddbcfa280782a24e49.png";
    EmbedBuilder info = new EmbedBuilder();

    // SUGENERUOJA VIZUALIA ZINUTE SU INFORMACIJA, KOKIE ZAIDEJAI IR KOKIA ROLE
    // TURI.
    public void lookingForGroup(GuildMessageReceivedEvent event, String[] args) {

	ArrayList<String> prints = new ArrayList<String>();
	MessageChannel channel = event.getChannel();
	Message message = event.getMessage();

	message.delete().queueAfter(5, TimeUnit.SECONDS);

	Role role1 = event.getGuild().getRoleById("693911489689682029"); // <1.0
	Role role2 = event.getGuild().getRoleById("693911334047186956"); // >1.0
	Role role3 = event.getGuild().getRoleById("693911644400779298"); // >1.5
	Role role4 = event.getGuild().getRoleById("694167348403503134"); // >2.0
	Role role5 = event.getGuild().getRoleById("698132521203794031"); // >3.0
	Role role6 = event.getGuild().getRoleById("703287298443182191"); // >4.0

	if (!event.getMember().getVoiceState().inVoiceChannel()) {
	    info.setColor(Color.RED).setTitle("Prisijunk į voice kanala tada ieškok žaidėjų!");
	    channel.sendMessage(info.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
	    info.clear();

	} else {

	    List<Member> member = event.getMember().getVoiceState().getChannel().getMembers();

	    for (Member member2 : member) {

		if (member2.getRoles().contains(role6)) {
		    prints.add(member2.getAsMention() + " -> " + role6.getAsMention());
		} else if (member2.getRoles().contains(role5)) {
		    prints.add(member2.getAsMention() + " -> " + role5.getAsMention());
		} else if (member2.getRoles().contains(role4)) {
		    prints.add(member2.getAsMention() + " -> " + role4.getAsMention());
		} else if (member2.getRoles().contains(role3)) {
		    prints.add(member2.getAsMention() + " -> " + role3.getAsMention());
		} else if (member2.getRoles().contains(role2)) {
		    prints.add(member2.getAsMention() + " -> " + role2.getAsMention());
		} else if (member2.getRoles().contains(role1)) {
		    prints.add(member2.getAsMention() + " -> " + role1.getAsMention());
		} else {
		    prints.add(member2.getAsMention() + " -> " + "N E R A");
		}

	    }

	    String channelName = event.getMember().getVoiceState().getChannel().getName();
	    List<String> userDesiredText = new ArrayList<String>();

	    for (int i = 1; i < args.length; i++) {
		userDesiredText.add(args[i] + " ");
	    }

	    String citiesCommaSeparated = String.join(" ", userDesiredText);
	    Invite inviteLink = event.getMember().getVoiceState().getChannel().createInvite().setTemporary(true)
		    .complete();

	    EmbedBuilder info = new EmbedBuilder();
	    info.setColor(Color.green).setAuthor("Ieškomi žaidėjai").setTitle(" " + citiesCommaSeparated)
		    .addField("Žaidėjai:", prints.stream().map(Object::toString).collect(Collectors.joining("\n")),
			    true)
		    .addBlankField(true)
		    .addField("Kanalas: " + channelName, "Prisijungimas į kanalą:\n" + inviteLink.getUrl(), true)
		    .setFooter("creator aKu//", avatar);
	    channel.sendTyping().queue();
	    channel.sendMessage("<@&688463333007032345>").complete().delete().queueAfter(30, TimeUnit.MINUTES); // warzone
														// role
	    channel.sendMessage(info.build()).complete().delete().queueAfter(30, TimeUnit.MINUTES);

	    info.clear();

	}
    }

    // EMBED ZINUTE, KAIP NAUDOTI LFG KOMANDA
    public void infoLFG(GuildMessageReceivedEvent event) {

	Message message = event.getMessage();
	message.delete().queueAfter(5, TimeUnit.SECONDS);

	info.setColor(Color.CYAN).setAuthor("Warzone LT LFG info").setTitle("Nori susirasti žaidėjų?")
		.setDescription("Botas kuris sukuria info aiškiai patraukiančią akį!"
			+ " Sukuria automatiškai pakvietimą į Voice kanalą!" + " Parašo esamus žaidėjus kanale." + "")

		.addField("Komanda: ", "``?lfg``", true)
		.addField("Jos naudojimas",
			"Veikia Tik <#688477412727718013> kanale!\n``?lfg Ieškomas +1``\n``?lfg +1``\n``?lfg +1 ne batonas``",
			true)
		.setFooter("creator aKu//", avatar);

	event.getChannel().sendTyping().queue();
	event.getChannel().sendMessage(info.build()).complete().delete().queueAfter(4, TimeUnit.HOURS);
	info.clear();

    }

}
