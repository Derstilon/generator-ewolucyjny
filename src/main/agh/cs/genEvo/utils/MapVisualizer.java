package agh.cs.genEvo.utils;


import agh.cs.genEvo.WorldInterface;
import agh.cs.genEvo.mapElements.animalElements.GenderlessAnimal;

public class MapVisualizer {

    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private WorldInterface map;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     * @param map
     */
    public MapVisualizer(WorldInterface map) {
        this.map = map;
    }

    /**
     * Convert selected region of the map into a string. It is assumed that the
     * indices of the map will have no more than two characters (including the
     * sign).
     *
     * @param lowerLeft  The lower left corner of the region that is drawn.
     * @param upperRight The upper right corner of the region that is drawn.
     * @return String representation of the selected region of the map.
     */
    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.y + 1; i >= lowerLeft.y - 1; i--) {
            if (i == upperRight.y + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.x; j <= upperRight.x + 1; j++) {
                if (i < lowerLeft.y || i > upperRight.y) {
                    builder.append(drawFrame(j <= upperRight.x));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.x) {
                        builder.append(drawObject(new Vector2d(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(Vector2d currentPosition) {
        String result = EMPTY_CELL;
        Object object = this.map.animalAt(currentPosition);
        //if (object != null)
        //    System.out.println(object.toString() + "  " + object.hashCode());
        if (object instanceof GenderlessAnimal) {
            result = ((GenderlessAnimal) object).toString();
        }else{
            //System.out.println("INU INU");
            object = this.map.plantAt(currentPosition);
            if (object != null) {
                result = object.toString();
            }else{
                Object biome = this.map.biomeAt(currentPosition);
                if (biome != null)
                    result = "" + biome.toString();
            }
        }
        return result;
    }
}
