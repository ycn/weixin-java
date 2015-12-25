package cc.ycn.common.util.base62;

import java.io.IOException;

/**
 * Created by andy on 12/25/15.
 */
public class Base62 {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private Base62() {
    }

    public static String encode(byte[] original) {

        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(original);             // Set up the BitStream
        byte[] read = new byte[1];                              // only read 6-bit at a time
        while (true) {
            read[0] = 0;
            int length = stream.read(read, 0, 6);               // try to read 6 bits
            if (length == 6) {                                  // Not reaching the end
                if ((read[0] >> 3) == 0x1f) {                   // First 5-bit is 11111
                    sb.append(ALPHABET.charAt(61));
                    stream.seek(-1, SeekOrigin.CURRENT);        // Leave the 6th bit to next group
                } else if ((read[0] >> 3) == 0x1e) {            // First 5-bit is 11110
                    sb.append(ALPHABET.charAt(60));
                    stream.seek(-1, SeekOrigin.CURRENT);
                } else {                                        // Encode 6-bit
                    sb.append(ALPHABET.charAt((read[0] >> 2)));
                }
            } else if (length == 0) {                           // Reached the end completely
                break;
            } else {                                            // Reached the end with some bits left
                // Padding 0s to make the last bits to 6 bit
                sb.append(ALPHABET.charAt((read[0] >> (8 - length))));
                break;
            }
        }

        return sb.toString();
    }

    public static byte[] decode(String base62) throws IOException {
        // Character count
        int count = 0;

        // Set up the BitStream
        BitStream stream = new BitStream(base62.length() * 6 / 8);

        for (char c : base62.toCharArray()) {

            // Look up coding table
            int index = ALPHABET.indexOf(c);

            // If end is reached
            if (count == base62.length() - 1) {

                // Check if the ending is good
                int mod = (int) (stream.getPosition() % 8);
                if (mod == 0)
                    throw new IOException("an extra character was found");

                if ((index >> (8 - mod)) > 0)
                    throw new IOException("invalid ending character was found");

                stream.write(new byte[]{(byte) (index << mod)}, 0, 8 - mod);
            } else {
                // If 60 or 61 then only write 5 bits to the stream, otherwise 6 bits.
                if (index == 60) {
                    stream.write(new byte[]{(byte) 0xf0}, 0, 5);
                } else if (index == 61) {
                    stream.write(new byte[]{(byte) 0xf8}, 0, 5);
                } else {
                    stream.write(new byte[]{(byte) index}, 2, 6);
                }
            }
            count++;
        }

        // Dump out the bytes
        byte[] result = new byte[(int) (stream.getPosition() / 8)];
        stream.seek(0, SeekOrigin.BEGIN);
        stream.read(result, 0, result.length * 8);
        return result;
    }
    
}
