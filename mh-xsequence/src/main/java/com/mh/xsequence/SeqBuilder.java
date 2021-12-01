package com.mh.xsequence;

import com.mh.xsequence.sequence.Sequence;

/**
 * 序列号生成器构建者
 * Created by yjx on 2018/5/30.
 */
public interface SeqBuilder {

    /**
     * 构建一个序列号生成器
     *
     * @return 序列号生成器
     */
    Sequence build();
}
