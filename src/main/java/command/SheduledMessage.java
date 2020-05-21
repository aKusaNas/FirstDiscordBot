package command;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SheduledMessage {
	
	// VEIKIA BET REIKTU KOMANDOS ISJUNGIMUI. (NEPAJUNGTAS)
		public void scheduledMessage(GuildMessageReceivedEvent event) {
			TextChannel lchannel = event.getGuild().getTextChannelById("376792069324275715");

			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleAtFixedRate(new Runnable() {

				public void run() {
					if (lchannel != null)
						lchannel.sendMessage("sheduled message").queue();
				}
			}, 0, 30, TimeUnit.SECONDS);

		}

}
