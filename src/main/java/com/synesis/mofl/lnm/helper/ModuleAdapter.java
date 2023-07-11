package com.synesis.mofl.lnm.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.synesis.mofl.lnm.payload.ApiResponse;
/**
 * @author Md. Emran Hossain
 * @since 25 Jan, 2022
 * @version 1.1
 */
@Component
public class ModuleAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleAdapter.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method connect another service to get data
     * 
     * @author Md. Emran Hossain
     * @param request - HttpServletRequest
     * @param apiEndPoint - String
     * @return responseData - Map
     * @since 21 Mar, 2022
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getData(HttpServletRequest request, String apiEndPoint) {
        try{
            String jwt = HibernateUtil.getJwtFromRequest(request);

//            String client_url = apiEndPoint;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt);
            LOG.info("Authorization : {}", headers.get("Authorization"));
            HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
            ResponseEntity<?> result = restTemplate.exchange(apiEndPoint, HttpMethod.GET, jwtEntity, ApiResponse.class);

            if (!ObjectUtils.isEmpty(result.getBody())) {
                ApiResponse apiResponse = (ApiResponse) result.getBody();
                Map<String, Object> responseData = (Map<String, Object>) apiResponse.getObj();

                return responseData;
            }
            return null;
        } catch(Exception ex){
            ex.printStackTrace();
            LOG.error("could not data from another module", ex);
            return null;
        }
    }
}
