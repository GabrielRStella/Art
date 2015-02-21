package com.ralitski.art.core.script;

import java.util.Arrays;

public class StringOutputStream {

    private char[] buf;

    private int count;

    public StringOutputStream() {
        this(64);
    }

    public StringOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + size);
        }
        buf = new char[size];
    }
    
    //writing

    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - buf.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = buf.length;
        int newCapacity = oldCapacity << 1;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity < 0) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        buf = Arrays.copyOf(buf, newCapacity);
    }

    public synchronized void write(char b) {
    	//System.out.println(b);
        ensureCapacity(count + 1);
        buf[count] = b;
        count++;
    }

    public synchronized void write(int b) {
    	write((char)b);
    }

    public synchronized void write(char b[], int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) - b.length > 0)) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(count + len);
        java.lang.System.arraycopy(b, off, buf, count, len);
        count += len;
    }

    public synchronized void write(String s) {
    	write(s.toCharArray(), 0, s.length());
    }

    public synchronized char[] toCharArray() {
        return Arrays.copyOf(buf, count);
    }
    
    public synchronized int capacity() {
    	return buf.length - count;
    }
    
    public String toString() {
    	return new String(buf, 0, count);
    }
}
