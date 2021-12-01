package com.mh.xsequence.sequence;

import com.mh.xsequence.exception.SeqException;
import com.mh.xsequence.range.SeqRangeMgr;

/**
 * 序列号区间生成器接口
 * Created by yjx on 2018/5/6.
 */
public interface RangeSequence extends Sequence {

    /**
     * 设置区间管理器
     *
     * @param seqRangeMgr 区间管理器
     */
    void setSeqRangeMgr(SeqRangeMgr seqRangeMgr);

    /**
     * 设置获取序列号名称
     *
     * @param name 名称
     */
    void setName(String name);


    /**
     *
     * 每天初始化 从头开始
     * 生成下一个序列号
     *
     * @return 序列号
     * @throws SeqException 序列号异常
     */
    long myNextValue() throws SeqException;
}
