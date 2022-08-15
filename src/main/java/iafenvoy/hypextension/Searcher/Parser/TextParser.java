package iafenvoy.hypextension.Searcher.Parser;

public class TextParser extends StringParser {
    @Override
    public String parse(String value) {
        StringBuilder builder = new StringBuilder();
        String[] words = value.toLowerCase().split("_");
        for (String s : words)
            builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
        return builder.toString();
    }
}
