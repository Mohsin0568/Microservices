package com.systa.licencing.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.systa.licencing.model.Organization;
import com.systa.licencing.utils.FeignInterceptorUtils;

@FeignClient(name="organization-service", configuration = {FeignInterceptorUtils.class})
public interface OrganizationFeignClient {
	
	@RequestMapping(
            method= RequestMethod.GET,
            value="/v1/organization/{organizationId}",
            consumes="application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);

}
