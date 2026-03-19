import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String rssUrl = "https://archiveofourown.org/tags/The%20Pitt%20(TV)/feed";
        String webhook = "https://discord.com/api/webhooks/1484217230509211700/KwZkbPyNkTzBEOfdlmTZbH26dWaXowYZp1chXy1xBZQ5zoyAADr-1rihgs-HT399e_Cs";

        RSSReader reader = new RSSReader();
        Storage storage = new Storage("seen.json");
        DiscordNotifier notifier = new DiscordNotifier(webhook);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            try {
                System.out.println("Checking for new fanfics...");

                var entries = reader.fetch(rssUrl);

                for (var entry : entries) {
                    String link = entry.getLink();

                    if (!storage.hasSeen(link)) {
                        storage.markSeen(link);

                        notifier.sendEmbed(
                                entry.getTitle(),
                                entry.getAuthor(),
                                link
                        );

                        System.out.println("New fic: " + entry.getTitle());
                    }
                }

                storage.save();

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 60, TimeUnit.MINUTES);
    }
}

        scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.MINUTES);
    }
}
