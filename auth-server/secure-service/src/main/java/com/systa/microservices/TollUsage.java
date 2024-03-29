package com.systa.microservices;

public class TollUsage {
	
	public String Id;
	public String stationId;
	public String licensePlate;
	public String timestamp;
	
	public TollUsage() {}
	
	public TollUsage(String id, String stationid, String licenseplate, String timestamp){
		this.Id = id;
		this.stationId = stationid;
		this.licensePlate = licenseplate;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "TollUsage [Id=" + Id + ", stationId=" + stationId + ", licensePlate=" + licensePlate
				+ ", timestamp=" + timestamp + "]";
	}	
	
}
