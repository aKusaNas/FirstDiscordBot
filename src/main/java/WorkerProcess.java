import javax.security.auth.login.LoginException;

import org.apache.log4j.BasicConfigurator;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class WorkerProcess {

    public static JDA jda;
    public static String HKEY = System.getenv("DISCORD_KEY");

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

	BasicConfigurator.configure();
	
	

	try {
	    jda = new JDABuilder(AccountType.BOT).setToken(HKEY).build();
	    jda.getPresence().setStatus(OnlineStatus.ONLINE);
	    jda.getPresence().setActivity(Activity.playing("?info"));
	    
	    jda.awaitReady(); // Blocking guarantees that JDA will be completely loaded.
	    System.out.println("      //////////////////////////////");
	    System.out.println("     ////                      ////");
	    System.out.println("    ////Finished Building JDA!////");
	    System.out.println("   ////                      ////");
	    System.out.println("  ////   Creator aKu// :D   ////");
	    System.out.println(" ////                      ////");
	    System.out.println("//////////////////////////////");
	    jda.addEventListener(new Commands());
	} catch (LoginException e) {

	    e.printStackTrace();
	} catch (InterruptedException e) {

	    e.printStackTrace();
	}

    }
}
