package Bean;

import java.util.Arrays;

/**
 * Created by xu on 2016/10/31.
 */
public class Bzjx {
    private byte[] img;

    public Bzjx(byte[] img) {
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Bzjx{" +
                "img=" + Arrays.toString(img) +
                '}';
    }
}
