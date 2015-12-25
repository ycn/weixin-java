package cc.ycn.common.util.base62;

/**
 * Created by andy on 12/25/15.
 */
public class BitStream {
    private byte[] source;
    private long length;
    private long position;

    public BitStream(int capacity) {
        this.source = new byte[capacity];
        this.length = this.source.length * 8;
    }

    public BitStream(byte[] source) {
        this.source = source;
        this.length = this.source.length * 8;
    }

    public long getLength() {
        return length;
    }

    public long getPosition() {
        return position;
    }

    public int read(byte[] buffer, int offset, int count) {
        // Temporary position cursor
        long tempPos = this.position;
        tempPos += offset;

        // Buffer byte position and in-byte position
        int readPosCount = 0, readPosMod = 0;

        // Stream byte position and in-byte position
        int posCount = (int) tempPos >> 3;
        int posMod = (int) (tempPos - ((tempPos >> 3) << 3));

        while (tempPos < this.position + offset + count && tempPos < this.length) {
            // Copy the bit from the stream to buffer
            if ((this.source[posCount] & (0x1 << (7 - posMod))) != 0) {
                buffer[readPosCount] = (byte) ((int) (buffer[readPosCount]) | (0x1 << (7 - readPosMod)));
            } else {
                buffer[readPosCount] = (byte) ((int) (buffer[readPosCount]) & (0xffffffff - (0x1 << (7 - readPosMod))));
            }

            // Increment position cursors
            tempPos++;
            if (posMod == 7) {
                posMod = 0;
                posCount++;
            } else {
                posMod++;
            }
            if (readPosMod == 7) {
                readPosMod = 0;
                readPosCount++;
            } else {
                readPosMod++;
            }
        }

        int bits = (int) (tempPos - this.position - offset);
        this.position = tempPos;
        return bits;
    }

    public long seek(int offset, SeekOrigin origin) {
        switch (origin) {
            case BEGIN:
                this.position = offset;
                break;
            case CURRENT:
                this.position += offset;
                break;
            case END:
                this.position = this.length + offset;
                break;
        }
        return this.position;
    }

    public void write(byte[] buffer, int offset, int count) {
        // Temporary position cursor
        long tempPos = this.position;

        // Buffer byte position and in-byte position
        int readPosCount = offset >> 3, readPosMod = offset - ((offset >> 3) << 3);

        // Stream byte position and in-byte position
        int posCount = (int) tempPos >> 3;
        int posMod = (int) (tempPos - ((tempPos >> 3) << 3));

        while (tempPos < this.position + count && tempPos < this.length) {
            // Copy the bit from buffer to the stream
            if ((((int) buffer[readPosCount]) & (0x1 << (7 - readPosMod))) != 0) {
                this.source[posCount] = (byte) ((int) (this.source[posCount]) | (0x1 << (7 - posMod)));
            } else {
                this.source[posCount] = (byte) ((int) (this.source[posCount]) & (0xffffffff - (0x1 << (7 - posMod))));
            }

            // Increment position cursors
            tempPos++;
            if (posMod == 7) {
                posMod = 0;
                posCount++;
            } else {
                posMod++;
            }

            if (readPosMod == 7) {
                readPosMod = 0;
                readPosCount++;
            } else {
                readPosMod++;
            }
        }

        this.position = tempPos;
    }
}
