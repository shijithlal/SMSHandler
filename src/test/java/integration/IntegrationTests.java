package integration;

import com.shijith.sms.SmsHandlerApplication;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Collections;

import static com.shijith.sms.Constants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SmsHandlerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void inboundBasicSuccess() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNOIM");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("441224980093");
        request.setTo("4924195509198");
        request.setText("Hello");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/inbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("",response.getError());
        Assert.assertEquals(INBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());
    }

    @Test
    public void inboundAuthFailure() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNO");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("441224980093");
        request.setTo("4924195509198");
        request.setText("Hello");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/inbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void outboundAuthFailure() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNO");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("441224980093");
        request.setTo("4924195509198");
        request.setText("Hello");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/outbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }


    @Test
    public void outboundBasicSuccess() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNOIM");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("4924195509198");
        request.setTo("441224980093");
        request.setText("Hello");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/outbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("",response.getError());
        Assert.assertEquals(OUTBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());

    }

    @Test
    public void outboundMaxAPICallLimit() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("61871112940" + COUNTER_SUFFIX,"0");

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo5,Password=6DLH8A25XZ");
                    return execution.execute(request, body);
                }));

        for(int i=0;i<API_CALL_LIMIT;i++) {
            SMSRequest request = new SMSRequest();
            request.setFrom("61871112940");
            request.setTo("61881666914");
            request.setText("Hello");
            ResponseEntity<SMSResponse> responseEntity =
                    restTemplate.postForEntity("/outbound/sms/", request, SMSResponse.class);

            SMSResponse response = responseEntity.getBody();
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assert.assertEquals("", response.getError());
            Assert.assertEquals(OUTBOUND_END_POINT + SPACE + OK_MESSAGE, response.getMessage());
        }
        SMSRequest request = new SMSRequest();
        request.setFrom("61871112940");
        request.setTo("61881666914");
        request.setText("Hello");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/outbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(String.format(API_CALL_LIMIT_REACHED,"61871112940"), response.getError());
        Assert.assertEquals("", response.getMessage());

    }

    @Test
    public void inboundAddToCache() {

        Jedis jedis = new Jedis("localhost");
        jedis.del("441224980093");

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNOIM");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("441224980093");
        request.setTo("4924195509198");
        request.setText("STOP");
        ResponseEntity<SMSResponse> responseEntity =
                restTemplate.postForEntity("/inbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("",response.getError());
        Assert.assertEquals(INBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());

        String blockedTo = jedis.get("441224980093");
        Assert.assertEquals("4924195509198",blockedTo);
        jedis.del("441224980093");

    }
    @Test
    public void outboutBlockMessage() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("4924195509198","441224980093");
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "UserName=plivo1,Password=20S0KPNOIM");
                    return execution.execute(request, body);
                }));

        SMSRequest request = new SMSRequest();
        request.setFrom("4924195509198");
        request.setTo("441224980093");
        request.setText("Hello");
        ResponseEntity<SMSResponse>  responseEntity =
                restTemplate.postForEntity("/outbound/sms/", request, SMSResponse.class);

        SMSResponse response = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(String.format(BLOCKED_MESSAGE,"4924195509198","441224980093"),response.getError());
        Assert.assertEquals("",response.getMessage());

        jedis.del("4924195509198");

    }

}
