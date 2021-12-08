package com.mh.sequence;

import com.mh.sequence.range.impl.db.DbSeqRangeMgr;
import com.mh.sequence.sequence.Sequence;
import com.mh.sequence.sequence.impl.DefaultRangeSequence;

import javax.sql.DataSource;

/**
 * 基于DB取步长，序列号生成器构建者
 * Created by yjx on 2018/5/30.
 */
public class DbSeqBuilder implements SeqBuilder {

    /**
     * 数据库数据源[必选]
     */
    private DataSource dataSource;

    /**
     * 业务名称[必选]
     */
    private String bizName;
    /**
     * 存放序列号步长的表[可选：默认：sequence]
     */
    private String tableName  = "sequence";
    /**
     * 并发是数据使用了乐观策略，这个是失败重试的次数[可选：默认：100]
     */
    private int    retryTimes = 100;
    /**
     * 获取range步长[可选：默认：1000]
     */
    private int    step       = 1000;

    /**
     * 序列号分配起始值[可选：默认：0]
     */
    private long stepStart = 0;

    /**
     * 序列号记录表生成规则
     *
     * 序列号 是否每天初始化 [可选：默认：0]
     * 0-不会每天初始化 1-每天都会初始化从头开始
     */
    private int type = 0;

    @Override
    public Sequence build() {
        //利用DB获取区间管理器
        DbSeqRangeMgr dbSeqRangeMgr = new DbSeqRangeMgr();
        dbSeqRangeMgr.setDataSource(this.dataSource);
        dbSeqRangeMgr.setTableName(this.tableName);
        dbSeqRangeMgr.setRetryTimes(this.retryTimes);
        dbSeqRangeMgr.setStep(this.step);
        dbSeqRangeMgr.setStepStart(stepStart);
        dbSeqRangeMgr.setType(this.type);
        dbSeqRangeMgr.init();
        //构建序列号生成器
        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setName(this.bizName);
        sequence.setSeqRangeMgr(dbSeqRangeMgr);
        return sequence;
    }

    public static DbSeqBuilder create() {
        DbSeqBuilder builder = new DbSeqBuilder();
        return builder;
    }

    public DbSeqBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public DbSeqBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DbSeqBuilder retryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public DbSeqBuilder step(int step) {
        this.step = step;
        return this;
    }

    public DbSeqBuilder bizName(String bizName) {
        this.bizName = bizName;
        return this;
    }

    public DbSeqBuilder stepStart(long stepStart) {
        this.stepStart = stepStart;
        return this;
    }

    public DbSeqBuilder setType(int type) {
        this.type = type;
        return this;
    }
}
