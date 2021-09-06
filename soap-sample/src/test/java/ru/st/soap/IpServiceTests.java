package ru.st.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IpServiceTests {

    @Test
    public void testMyIp() {
        String getIpLocation= new GeoIPService().getGeoIPServiceSoap12().getIpLocation("194.28.29.152");
        Assert.assertTrue(getIpLocation.contains("RU"));
    }

    @Test
    public void testMyIpWrong() {
        String getIpLocation= new GeoIPService().getGeoIPServiceSoap12().getIpLocation("qwerty");
        Assert.assertTrue(getIpLocation.contains("US"));
    }
}
