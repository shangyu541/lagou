package com.lagou;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.lagou.service.ResumeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 〈自动投递〉
 *
 * @author 商玉
 * @create 2022/1/10
 * @since 1.0.0
 */
@RestController
@RequestMapping("/autodeliver")
public class AutodeliverController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ResumeFeignClient resumeFeignClient;

    //@GetMapping("/checkState/{userId}")
    //public Integer findResumeOpenState(@PathVariable Long userId) {
    //    //Integer forObject =
    //    //        restTemplate.getForObject("http://localhost:8080/resume/openstate/"
    //    //                + userId, Integer.class);
    //    //System.out.println("======>>>调⽤简历微服务，获取到⽤户" +
    //    //        userId + "的默认简历当前状态为：" + forObject);
    //    List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-resume");
    //    ServiceInstance serviceInstance = instances.get(0);
    //    String host = serviceInstance.getHost();
    //    int port = serviceInstance.getPort();
    //    String url="http://"+host+":"+port+"/resume/openstate/"+userId;
    //    Integer forObject = restTemplate.getForObject(url, Integer.class);
    //    System.out.println("======>>>调⽤简历微服务，获取到⽤户" +
    //            userId + "的默认简历当前状态为：" + forObject);
    //    return forObject;
    //}

    /**
     * 使用Ribbon负载均衡
     * @param userId
     * @return
     */
    @GetMapping("/checkState/{userId}")
    public Integer findResumeOpenState(@PathVariable Long userId) {
        // 使用ribbon不需要我们自己获取服务实例然后选择一个那么去访问了（自己的负载均衡）
        String url = "http://lagou-service-resume/resume/openstate/" + userId;  // 指定服务名
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }

    /**
     * 使用Ribbon负载均衡
     * @param userId
     * @return
     */
    @GetMapping("/checkStateFeign/{userId}")
    public Integer checkStateFeign(@PathVariable Long userId) {
        // 使用ribbon不需要我们自己获取服务实例然后选择一个那么去访问了（自己的负载均衡）
        Integer resumeOpenState = resumeFeignClient.findResumeOpenState(userId);
        return resumeOpenState;
    }

    //@HystrixCommand(
    //        //线程池标识，要保持唯一，不唯一的话就共用了
    //        threadPoolKey = "findResumeOpenStateTimeout",
    //        //线程池细节属性配置
    //        threadPoolProperties = {
    //                //线程数
    //                @HystrixProperty(name = "coreSize", value = "1"),
    //                //线程等待队列长度
    //                @HystrixProperty(name = "maxQueueSize", value = "20")
    //        },
    //        //熔断的一些细节属性配置
    //        commandProperties = {
    //                //每一个属性都是一个HystrixProperty
    //                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2100")
    //        }
    //)
    //@GetMapping("/checkStateTimeout/{userId}")
    //public Integer findResumeOpenStateTimeout(@PathVariable Long
    //                                                  userId) {
    //    // 使⽤ribbon不需要我们⾃⼰获取服务实例然后选择⼀个那么去访问了（⾃⼰的负载均衡）
    //    String url = "http://lagou-service-resume/resume/openstate/" + userId;  // 指定服务名
    //    // 指定服务名
    //    Integer forObject = restTemplate.getForObject(url,
    //            Integer.class);
    //    return forObject;
    //}

    //@HystrixCommand(
    //        //线程池标识，要保持唯一，不唯一的话就共用了
    //        threadPoolKey = "findResumeOpenStateTimeoutFallback",
    //        //线程池细节属性配置
    //        threadPoolProperties = {
    //                //线程数
    //                @HystrixProperty(name = "coreSize", value = "2"),
    //                //等待队列长度
    //                @HystrixProperty(name = "maxQueueSize", value = "20")
    //        },
    //        //熔断的一些细节属性配置
    //        commandProperties = {
    //                //每一个属性都是一个HystrixProperty
    //                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2100"),
    //                //统计时间窗口定义
    //                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "8000"),
    //                //统计时间窗口内的最小请求数
    //                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
    //                //统计时间窗口内的错误数量百分比阈值
    //                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
    //                //自我修复时的活动窗口长度
    //                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")
    //        },
    //        //回退方法
    //        fallbackMethod = "myFallBack"
    //)
    @GetMapping("/checkStateTimeoutFallback/{userId}")
    public Integer findResumeOpenStateTimeoutFallback(@PathVariable Long userId) {
        // 使⽤ribbon不需要我们⾃⼰获取服务实例然后选择⼀个那么去访问了（⾃⼰的负载均衡）
        String url = "http://lagou-service-resume/resume/openstate/" + userId;  // 指定服务名
        // 指定服务名
        Integer forObject = restTemplate.getForObject(url,
                Integer.class);
        return forObject;
    }

    public Integer myFallBack(Long userId){
        return -123333;
    }


    /**
     * 1) 服务提供者处理超时，熔断，返回错误信息
     * 2) 有可能服务提供者出现异常直接抛出异常信息
     *
     * 以上信息，都会返回到消费者这里，很多时候消费者服务不希望把收到异常/错误信息再跑到它的上游去
     *
     * 用户微服务-注册微服务-优惠劵微服务
     *      1. 登记注册
     *      2.分发优惠劵（并不是核心步骤），这里如果调用优惠劵微服务返回了异常信息或者是熔断后的错误信息，
     *      这些信息如果抛给用户很不友好，此时，我们可以返回一个兜底数据，预设的默认值（服务降级）
     *
     *
     *
     */

}
