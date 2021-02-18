package org.sess.report.services.report.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * клиент для сервиса CORE
 */
@FeignClient(value = "CoreUserDataClient", url = "${org.sess.service.external.core.url}")
public interface CoreUserDataClient {
    @RequestMapping(method = RequestMethod.GET, value = "${org.sess.service.external.core.events.user.path}")
    String getUserEventsJson(@PathVariable("userId") String userId);
}
