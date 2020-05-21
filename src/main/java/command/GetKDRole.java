package command;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GetKDRole {

	//private String avatar = "https://images-ext-1.discordapp.net/external/bwdl6cz65cP9iEH4VtSUQeROfyn9uyyAAYYN0cAx6zI/https/cdn.discordapp.com/avatars/122769236950122498/9597093aa42ceeddbcfa280782a24e49.png";
	private EmbedBuilder info = new EmbedBuilder();

	// (IJUNGTAS) SPAUSDINA INFO KAIP NAUDOTI ROLES KOMANDA
	public void infoEmbed(GuildMessageReceivedEvent event) {

		Message message = event.getMessage();
		message.delete().queueAfter(5, TimeUnit.SECONDS);
		info.setColor(Color.magenta).setAuthor("Warzone LT K/D Ratio roles")
				.setTitle("Nori žaisti kartu su savės vertu žaidėju?")
				.setDescription("Botas kuris realiu laiku patikrina jūsų K/D ratio ir uždeda tam atitikusią rolę.\n"
						+ " Su kuria galite eiti į Rolių Voice kanalus!")
				.addField("Esamos roles:",
						"<@&693911489689682029>\n"
						+ "<@&693911334047186956>\n"
						+ "<@&693911644400779298>\n"
						+ "<@&694167348403503134>\n"
						+ "<@&698132521203794031>\n"
						+ "<@&703287298443182191>",
						false)
				.addField("Jos naudojimas", "Veikia Tik <#691741134959149106> kanale!\n ``!mw roles``", false)
				.setFooter("Warzone LT Administracija");

		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage(info.build()).complete().delete().queueAfter(4, TimeUnit.HOURS);
		info.clear();

	}

	// (ISJUNGTAS) SIUNCIA PLAYER TAG KURI ZMOGUS PATEIKIA, IR GAUNA JO KD ROLE IS
	// ACTIVISIONO IR AUTOMATISKAI UZDEDA ROLE
	public void getKDRole(GuildMessageReceivedEvent event, String[] args) {
		MessageChannel channel = event.getChannel();
		Message message = event.getMessage();
		message.delete().queueAfter(5, TimeUnit.SECONDS);
		User author = event.getAuthor();

		String[] battleTag = args[1].split("#", 2);
		
		if (battleTag.length != 2) {
			channel.sendTyping().queue();
			channel.sendMessage(
					"Reikalingas Activision TAG(nickname#tag), lengvai randamas �aidime de�in�j� vir� j�s� Rank")
					.complete().delete().queueAfter(1, TimeUnit.MINUTES);
		}
		if (battleTag.length == 2) {

			try {
				double kdratio = WarzoneGetKDR.requestActivisionKDR(battleTag[0], battleTag[1]);

				if (kdratio != 0.0) {
					kdratio = Math.round(kdratio * 100.0) / 100.0;
					event.getGuild().removeRoleFromMember(author.getIdLong(),
							event.getGuild().getRoleById("693911489689682029")).complete();
					event.getGuild().removeRoleFromMember(author.getIdLong(),
							event.getGuild().getRoleById("693911334047186956")).complete();
					event.getGuild().removeRoleFromMember(author.getIdLong(),
							event.getGuild().getRoleById("693911644400779298")).complete();
					event.getGuild().removeRoleFromMember(author.getIdLong(),
							event.getGuild().getRoleById("694167348403503134")).complete();
					event.getGuild().removeRoleFromMember(author.getIdLong(),
							event.getGuild().getRoleById("698132521203794031")).complete();

					if (kdratio < 1) {
						channel.sendTyping().queue();
						event.getGuild()
								.addRoleToMember(author.getIdLong(), event.getGuild().getRoleById("693911489689682029"))
								.queue();
						channel.sendMessage(author.getAsMention() + " gavai Role <@&693911489689682029>").complete()
								.delete().queueAfter(1, TimeUnit.DAYS);
					} else if (kdratio > 1 && kdratio < 1.5) {
						channel.sendTyping().queue();
						event.getGuild()
								.addRoleToMember(author.getIdLong(), event.getGuild().getRoleById("693911334047186956"))
								.queue();
						channel.sendMessage(author.getAsMention() + " gavai Role <@&693911334047186956>").complete()
								.delete().queueAfter(1, TimeUnit.DAYS);
					} else if (kdratio > 1.5 && kdratio < 2) {
						channel.sendTyping().queue();
						event.getGuild()
								.addRoleToMember(author.getIdLong(), event.getGuild().getRoleById("693911644400779298"))
								.queue();
						channel.sendMessage(author.getAsMention() + " gavai Role <@&693911644400779298>").complete()
								.delete().queueAfter(1, TimeUnit.DAYS);
					} else if (kdratio > 2 && kdratio < 3) {
						channel.sendTyping().queue();
						event.getGuild()
								.addRoleToMember(author.getIdLong(), event.getGuild().getRoleById("694167348403503134"))
								.queue();
						channel.sendMessage(author.getAsMention() + " gavai Role <@&694167348403503134>").complete()
								.delete().queueAfter(1, TimeUnit.DAYS);
					} else if (kdratio > 3) {
						channel.sendTyping().queue();
						event.getGuild()
								.addRoleToMember(author.getIdLong(), event.getGuild().getRoleById("698132521203794031"))
								.queue();
						channel.sendMessage(author.getAsMention() + " gavai Role <@&698132521203794031>").complete()
								.delete().queueAfter(1, TimeUnit.DAYS);
					}

				} else if (kdratio == 0.0) {
					channel.sendTyping().queue();
					channel.sendMessage(
							"Reikalingas Activision TAG(nickname#tag), lengvai randamas �aidime de�in�j� vir� j�s� Rank.")
							.complete().delete().queueAfter(1, TimeUnit.MINUTES);
				}

			} catch (IOException e) {
				e.printStackTrace();
				channel.sendTyping().queue();
				channel.sendMessage(
						"Reikalingas Activision TAG(nickname#tag), lengvai randamas �aidime de�in�j� vir� j�s� Rank.")
						.complete().delete().queueAfter(1, TimeUnit.MINUTES);
			}
		}
	}
}
