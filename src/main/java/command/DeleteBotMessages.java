package command;

import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DeleteBotMessages {

	// STILL IN PROGRESS
		public void deleteBotMessages(GuildMessageReceivedEvent event) {
			event.getGuild().getMemberById("690587489320042637");
			TextChannel channel = event.getGuild().getTextChannelById("376792069324275715");
			MessageHistory messages = new MessageHistory(channel);
			List<Message> msgs;

			msgs = messages.getRetrievedHistory();
			// messages.retrievePast(50).map(s->s.contains(event.getGuild().getMemberById("690587489320042637")));
			// msgs =
			// messages.getRetrievedHistory().stream().filter(s->s.getMember().equals(event.getGuild().getMemberById("690587489320042637"))).collect(Collectors.toList());

			System.out.println(msgs);

		}
	
}
