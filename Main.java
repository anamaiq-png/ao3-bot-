import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.*;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Map config = gson.fromJson(new FileReader("./config.json"), Map.class);

        String rssUrl = (String) config.get("rss_url");
        String webhook = (String) config.get("discord_webhook");
        int interval = ((Double) config.get("interval_minutes")).intValue();

        RSSReader reader = new RSSReader();
        Storage storage = new Storage("seen.json");
        DiscordNotifier notifier = new DiscordNotifier(webhook);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            try {
                var entries = reader.fetch(rssUrl);
                for (var entry : entries) {
                    String link = entry.getLink();
                    if (!storage.hasSeen(link)) {
                        storage.markSeen(link);
                        notifier.sendEmbed(entry.getTitle(), entry.getAuthor(), link);
                    }
                }
                storage.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.MINUTES);
    }
}
