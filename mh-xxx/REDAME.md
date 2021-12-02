``` /**
* 序列号生成配置
* @author: yujiaxin
* @createTime: 2020/8/25
* @description:
  */
  @Configuration
  public class XSequenceConfig {

  @Autowired
  private DataSource dataSource;


    /**
     * 业绩数据获取流水号后7位
     * 每天从 1000000 开始
     * 使用:
     *     @Autowired
     *     Sequence performanceSeq;
     * @return
     */
    @Bean
    public Sequence performanceSeq(){
        return getSequence(1,1000000,"performance_seq",1);
    }

    @NotNull
    private Sequence getSequence(int step,long stepStart,String bizName,int type){

        /**
         * 参数说明如下：
         * dataSource：数据库的数据源
         * bizName：具体某中业务的序列号
         * step：[可选] 默认1000，即每次取redis获取步长值，根据具体业务吞吐量来设置，越大性能越好，但是序列号断层的风险也就越大
         * type:序列号 是否每天初始化 [可选：默认：0] 0-不会每天初始化 1-每天都会初始化从头开始
         */
        return DbSeqBuilder.create().dataSource(dataSource).step(step).stepStart(stepStart).bizName(bizName).setType(type).build();
    }

}


