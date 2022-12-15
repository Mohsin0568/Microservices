package com.systa.licencing.controller;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systa.licencing.model.License;
import com.systa.licencing.service.LicenseService;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {

    @Autowired
	private LicenseService licenseService;
    
    @GetMapping
    public ResponseEntity<List<License>> getLicenseByOrganization(@PathVariable("organizationId") String organizationId) throws TimeoutException{
    	List<License> licenses = licenseService.getLicensesByOrganization(organizationId);
    	return ResponseEntity.ok(licenses);
    }

	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense( @PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		
		License license = licenseService.getLicense(licenseId, organizationId);		
		return ResponseEntity.ok(license);
	}

	@PutMapping
	public ResponseEntity<License> updateLicense(@PathVariable("organizationId") String organizationId,
        @RequestBody License request,
        @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
			return ResponseEntity.ok(licenseService.updateLicense(request));
	}

	@PostMapping
	public ResponseEntity<License> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request,
			@RequestHeader(value = "Accept-Language",required = false) Locale locale) {
				return ResponseEntity.ok(licenseService.createLicense(request));
	}

	@DeleteMapping(value="/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
        @PathVariable("licenseId") String licenseId,
        @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
			return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
	}
    
}
