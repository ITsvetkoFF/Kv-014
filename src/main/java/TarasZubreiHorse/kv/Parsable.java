package TarasZubreiHorse.kv;


import java.util.List;

/**
 * Created by Taras on 07.04.2016.
 */
public interface Parsable  {
    public BlockType[][] parse(String path);
    public void write(List<Point> points, String fromPath, String toPath, long scriptTime, long startTime);
    public Point getStart();
}
