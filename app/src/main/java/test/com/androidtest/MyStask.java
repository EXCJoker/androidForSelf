package test.com.androidtest;

import java.util.Arrays;

/**
 * 创建日期：18/4/13 on 上午10:39.
 * 作者：liuxun
 * 描述：
 */

public class MyStask<T> {
    private T[] dataArry;//存数据的数组
    private int arrySize;//数据个数
    private static final int DEAFAUT_SIZE = 10;//默认栈的容量

    public MyStask() {
        init();
    }

    private void init() {
        arrySize = 0;
        ensureCapacity(DEAFAUT_SIZE);
    }

    /**
     * @return 栈的容量
     */
    private int stackSize() {
        if (null == dataArry) {
            return 0;
        } else {
            return dataArry.length;
        }
    }

    /**
     * 确保数据添加的过程中，不会越界
     *
     * @param newCapacity 新数组的长度
     */
    private void ensureCapacity(int newCapacity) {
        if (newCapacity < stackSize()) {
            return;
        }
        growArray(newCapacity);
    }

    private void growArray(int newCapacity) {
        //checkSize(); 考虑增长因子，最大数等
        dataArry = Arrays.copyOf(dataArry, newCapacity);
    }

    public synchronized void push(T t) {
        ensureCapacity(arrySize + 1);
        dataArry[arrySize++] = t;
    }

    //获取栈顶元素，并移除
    public synchronized T pop() {
        int index = arrySize - 1;
        T t = peak();
        removeElement(index);
        return t;
    }


    //移除指定元素
    private void removeElement(int index) {
        if (index > arrySize) {
            throw new ArrayIndexOutOfBoundsException(index + " > " + arrySize);
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        // 如何移除不是末尾的元素的处理方式
        int j = arrySize - index - 1;
        if (j > 0) {
            System.arraycopy(dataArry, index + 1, dataArry, index, j);
        }
        arrySize--;
        dataArry[arrySize] = null;
    }

    //获取栈顶元素
    public synchronized T peak() {
        if(arrySize == 0){
            return  null;
        }
        T t = dataArry[arrySize];
        return t;
    }
}
