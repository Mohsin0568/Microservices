package com.systa.licencing.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.systa.licencing.config.ServiceConfig;
import com.systa.licencing.model.License;
import com.systa.licencing.model.Organization;
import com.systa.licencing.repository.LicenceRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class LicenseService {

    @Autowired
	MessageSource messages;

	@Autowired
	private LicenceRepository licenseRepository;

	@Autowired
	ServiceConfig config;
	
	@Autowired
	OrganizationFeignClient organizationClient;
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

	public License getLicense(String licenseId, String organizationId){
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		if (null == license) {
			throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null),licenseId, organizationId));	
		}
		
		Organization organization = fetchOrganization(organizationId);
		if (null != organization) {
			license.setOrganizationName(organization.getName());
			license.setContactName(organization.getContactName());
			license.setContactEmail(organization.getContactEmail());
			license.setContactPhone(organization.getContactPhone());
		}
		
		return license.withComment(config.getProperty());
	}

	public License createLicense(License license){
		license.setLicenseId(UUID.randomUUID().toString());
		licenseRepository.save(license);

		return license.withComment(config.getProperty());
	}

	public License updateLicense(License license){
		licenseRepository.save(license);

		return license.withComment(config.getProperty());
	}

	public String deleteLicense(String licenseId){
		String responseMessage = null;
		License license = new License();
		license.setLicenseId(licenseId);
		licenseRepository.delete(license);
		responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId);
		return responseMessage;

	}
	
	@CircuitBreaker(name="licensingService", fallbackMethod = "buildFallbackLicenseList")	
	public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException{
		randomlyRunLong();
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	public List<License> buildFallbackLicenseList(String organizationId, Throwable t){
		License license = new License();
		license.setProductName("No License information available at this moment");
		return Arrays.asList(license);
	}
	
	private void randomlyRunLong() throws TimeoutException{
		Random rand = new Random();
		int randomNum = rand.nextInt(2) + 1;
		if (randomNum==2) sleep();
	}
	private void sleep() throws TimeoutException{
		try {
			System.out.println("Sleep");
			Thread.sleep(5000);
			throw new java.util.concurrent.TimeoutException();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}
	
	private Organization fetchOrganization(String organzationId) {
		
		return organizationClient.getOrganization(organzationId);
		
	}
    
}
