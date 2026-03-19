import okhttp3.*;
import com.google.gson.JsonObject;

public class DiscordNotifier {
    private final String webhook;
    private final OkHttpClient client = new OkHttpClient();

    public DiscordNotifier(String webhook) {
        this.webhook = webhook;
    }

    public void sendEmbed(String title, String author, String link) throws Exception {
        JsonObject embed = new JsonObject();
        embed.addProperty("title", title);
        embed.addProperty("url", link);
        embed.addProperty("description", "Autor: " + author);

        JsonObject payload = new JsonObject();
        payload.add("embeds", new com.google.gson.JsonArray());
        payload.getAsJsonArray("embeds").add(embed);

        RequestBody body = RequestBody.create(
            payload.toString(),
            MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
            .url(webhook)
            .post(body)
            .build();

        client.newCall(request).execute();
    }
}
