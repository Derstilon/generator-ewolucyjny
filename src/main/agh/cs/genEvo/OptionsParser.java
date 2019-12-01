package agh.cs.genEvo;

import java.util.Arrays;

public class OptionsParser {
    public MoveDirection[] parse(String[] args){
        MoveDirection[] dirs = new MoveDirection[args.length];
        int i = 0;
        for (String arg : args) {
                switch (arg) {
                    case "f":
                    case "forward":
                        dirs[i] = MoveDirection.FORWARD;
                        i++;
                        break;
                    case "l":
                    case "left":
                        dirs[i] = MoveDirection.LEFT;
                        i++;
                        break;
                    case "r":
                    case "right":
                        dirs[i] = MoveDirection.RIGHT;
                        i++;
                        break;
                    case "b":
                    case "backward":
                        dirs[i] = MoveDirection.BACKWARD;
                        i++;
                        break;
                    default:
                        throw new IllegalArgumentException(arg + " jest niepoprawny");
                }
        }
        return Arrays.copyOfRange(dirs, 0, i++);
    }
}
