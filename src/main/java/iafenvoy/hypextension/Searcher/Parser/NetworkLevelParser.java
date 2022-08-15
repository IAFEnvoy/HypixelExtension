package iafenvoy.hypextension.Searcher.Parser;

public class NetworkLevelParser extends DoubleParser {
    @Override
    public double parse(double value) {
        return value < 0 ? 1 : (1 - 3.5 + Math.sqrt(12.25 + 0.0008 * value));
    }
}
