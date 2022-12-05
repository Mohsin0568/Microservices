package com.systa.licencing.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.systa.licencing.config.ServiceConfig;
import com.systa.licencing.model.License;
import com.systa.licencing.repository.LicenceRepository;

@Service
public class LicenseService {

    @Autowired
	MessageSource messages;

	@Autowired
	private LicenceRepository licenseRepository;

	@Autowired
	ServiceConfig config;

	public License getLicense(String licenseId, String organizationId){
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		if (null == license) {
			throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null),licenseId, organizationId));	
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
    
}
