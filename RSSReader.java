import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.*;
import java.net.URL;
import java.util.*;

public class RSSReader {
    public static class Fic {
        private String title, link, author;
        public Fic(String t, String l, String a) { title=t; link=l; author=a; }
        public String getTitle(){return title;}
        public String getLink(){return link;}
        public String getAuthor(){return author;}
    }

    public List<Fic> fetch(String url) throws Exception {
        List<Fic> list = new ArrayList<>();
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        for (SyndEntry entry : feed.getEntries()) {
            list.add(new Fic(entry.getTitle(), entry.getLink(), entry.getAuthor()));
        }
        return list;
    }
}
