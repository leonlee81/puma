package com.dianping.puma.biz.service;

import com.dianping.puma.alarm.service.ClientAlarmStrategyService;
import com.dianping.puma.biz.convert.Converter;
import com.dianping.puma.biz.dao.ClientAlarmStrategyDao;
import com.dianping.puma.biz.entity.ClientAlarmStrategyEntity;
import com.dianping.puma.alarm.model.strategy.AlarmStrategy;
import com.dianping.puma.alarm.model.strategy.ExponentialAlarmStrategy;
import com.dianping.puma.alarm.model.strategy.LinearAlarmStrategy;

/**
 * Created by xiaotian.li on 16/3/17.
 * Email: lixiaotian07@gmail.com
 */
public class ClientAlarmStrategyServiceImpl implements ClientAlarmStrategyService {

    private Converter converter;

    private ClientAlarmStrategyDao clientAlarmStrategyDao;

    @Override
    public AlarmStrategy find(String clientName) {
        ClientAlarmStrategyEntity entity = clientAlarmStrategyDao.find(clientName);

        if (entity.isLinearAlarm()) {
            return converter.convert(entity, LinearAlarmStrategy.class);
        } else if (entity.isExponentialAlarm()) {
            return converter.convert(entity, ExponentialAlarmStrategy.class);
        } else {
            return null;
        }
    }

    @Override
    public void create(AlarmStrategy strategy) {
        ClientAlarmStrategyEntity entity = converter.convert(strategy, ClientAlarmStrategyEntity.class);

        if (strategy instanceof LinearAlarmStrategy) {
            entity.setLinearAlarm(true);
        } else if (strategy instanceof ExponentialAlarmStrategy) {
            entity.setExponentialAlarm(true);
        } else {
            // do nothing.
        }

        clientAlarmStrategyDao.insert(entity);
    }

    @Override
    public int update(AlarmStrategy strategy) {
        ClientAlarmStrategyEntity entity = converter.convert(strategy, ClientAlarmStrategyEntity.class);

        if (strategy instanceof LinearAlarmStrategy) {
            entity.setLinearAlarm(true);
        } else if (strategy instanceof ExponentialAlarmStrategy) {
            entity.setExponentialAlarm(true);
        } else {
            // do nothing.
        }

        return clientAlarmStrategyDao.update(entity);
    }

    @Override
    public void remove(String clientName) {
        clientAlarmStrategyDao.delete(clientName);
    }
}