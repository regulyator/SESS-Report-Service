package org.sess.report.services.report.service.external.impl;

import org.sess.report.services.report.service.external.CoreUserDataClient;
import org.sess.report.services.report.service.external.CoreUserDataExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CoreUserDataExchangerImpl implements CoreUserDataExchanger {
    private final CoreUserDataClient coreUserDataClient;

    @Autowired
    public CoreUserDataExchangerImpl(CoreUserDataClient coreUserDataClient) {
        this.coreUserDataClient = coreUserDataClient;
    }

    /**
     * получение данных по активности пользователя из CORE сервиса
     * @param userId id пользователя
     * @return json
     */
    @Override
    public String getUserEventsJson(String userId) {
        return coreUserDataClient.getUserEventsJson(userId);
    }
}
