package com.dianping.puma.alarm.render;

import com.dianping.puma.alarm.exception.PumaAlarmRenderUnsupportedException;
import com.dianping.puma.alarm.model.AlarmMessage;
import com.dianping.puma.alarm.model.AlarmTemplate;
import com.dianping.puma.alarm.model.benchmark.PullTimeDelayAlarmBenchmark;
import com.dianping.puma.alarm.model.data.PullTimeDelayAlarmData;
import com.dianping.puma.alarm.model.data.PushTimeDelayAlarmData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xiaotian.li on 16/3/25.
 * Email: lixiaotian07@gmail.com
 */
public class PullTimeDelayAlarmRendererTest {

    PullTimeDelayAlarmRenderer renderer = new PullTimeDelayAlarmRenderer();

    @Before
    public void setUp() throws Exception {
        AlarmTemplate template = new AlarmTemplate();
        template.setTitleTemplate("%s");
        template.setContentTemplate("%s%s%s");
        renderer.setTemplate(template);
        renderer.start();
    }

    /**
     * 测试正常的标题和内容替换功能.
     *
     * @throws Exception
     */
    @Test
    public void test0() throws Exception {
        PullTimeDelayAlarmData data = new PullTimeDelayAlarmData();
        data.setPullTimeDelayInSecond(100);

        PullTimeDelayAlarmBenchmark benchmark = new PullTimeDelayAlarmBenchmark();
        benchmark.setPullTimeDelayAlarm(true);
        benchmark.setMinPullTimeDelayInSecond(1000);
        benchmark.setMaxPullTimeDelayInSecond(10000);

        String clientName = "test";
        AlarmMessage message = renderer.render(clientName, data, benchmark);
        assertEquals(clientName, message.getTitle());
        assertEquals("100100010000", message.getContent());
    }

    /**
     * 测试从本地properties文件中读到标题和内容的模板.
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        AlarmTemplate template = renderer.generateAlarmTemplate(renderer.propertiesFilePath);
        assertNotNull(template.getTitleTemplate());
        assertNotNull(template.getContentTemplate());
    }

    /**
     * 测试非拉取时间类型会抛出异常.
     *
     * @throws Exception
     */
    @Test(expected = PumaAlarmRenderUnsupportedException.class)
    public void testException0() throws Exception {
        PushTimeDelayAlarmData data = new PushTimeDelayAlarmData();
        data.setPushTimeDelayInSecond(100);

        PullTimeDelayAlarmBenchmark benchmark = new PullTimeDelayAlarmBenchmark();
        benchmark.setPullTimeDelayAlarm(true);
        benchmark.setMinPullTimeDelayInSecond(1000);
        benchmark.setMaxPullTimeDelayInSecond(10000);

        String clientName = "test";
        renderer.render(clientName, data, benchmark);
    }

    @After
    public void tearDown() throws Exception {
        renderer.stop();
    }
}