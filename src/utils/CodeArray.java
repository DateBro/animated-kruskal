package utils;

import edu.princeton.cs.algs4.In;
import graphics.model.Code;

import java.util.NoSuchElementException;

/**
 * @author DateBro
 * @date 2019/2/23
 */
public class CodeArray {

    private int size;
    private Code[] codes;

    public CodeArray(In in) {
        try {
            this.size = in.readInt();
            if (size < 0) {
                throw new IllegalArgumentException("Number of vertices must be nonNegative");
            }
            codes = new Code[size];
            for (int i = 0; i < size; i++) {
                int x = in.readInt();
                int y = in.readInt();
                int width = in.readInt();
                int height = in.readInt();
                Code code = new Code(x, y, width, height, " ");
                codes[i] = code;
            }
            // 为了防止下一次读的第一行是换行符
            in.readLine();
            for (int i = 0; i < size; i++) {
                String codeStr = in.readLine();
                codes[i].setCodeStr(codeStr);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    public Code[] codes() {
        return codes;
    }
}
