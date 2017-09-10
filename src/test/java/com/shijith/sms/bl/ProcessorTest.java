package com.shijith.sms.bl;

import com.shijith.sms.bean.AuthenticatedUser;
import com.shijith.sms.bean.PhoneNumber;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import com.shijith.sms.cache.ICacheService;
import com.shijith.sms.cache.JedisService;
import com.shijith.sms.db.PhoneNumberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.shijith.sms.Constants.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ProcessorTest {
    @Test
    public void processInboundRequest() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setId(1);
        phoneNumber.setNumber("1234567");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getTo()).thenReturn("1234567");

        Mockito.doNothing().when(cacheService).add(any(String.class),any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processInboundRequest(spyRequest);

        Assert.assertEquals(INBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());
        Assert.assertEquals("",response.getError());

    }

    @Test
    public void processInboundBlockRequest() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        Mockito.doReturn("STOP").when(spyRequest).getText();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setId(1);
        phoneNumber.setNumber("1234567");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getTo()).thenReturn("1234567");
        when(spyRequest.getFrom()).thenReturn("12345678");

        Mockito.doNothing().when(cacheService).add(any(String.class),any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processInboundRequest(spyRequest);

        verify(cacheService,times(1)).add(spyRequest.getFrom(),spyRequest.getTo());

        Assert.assertEquals(INBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());
        Assert.assertEquals("",response.getError());

    }


    @Test
    public void processInboundRequestInvalidRequest() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doThrow(new ValidationException("Error Message")).when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setId(1);
        phoneNumber.setNumber("1234567");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getTo()).thenReturn("1234567");

        Mockito.doNothing().when(cacheService).add(any(String.class),any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processInboundRequest(spyRequest);

        Assert.assertEquals("",response.getMessage());
        Assert.assertEquals("Error Message",response.getError());

    }

    @Test
    public void processInboundRequestFromNotFound() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setId(1);
        phoneNumber.setNumber("12345678");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getTo()).thenReturn("1234567");

        Mockito.doNothing().when(cacheService).add(any(String.class),any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processInboundRequest(spyRequest);

        Assert.assertEquals("",response.getMessage());
        Assert.assertEquals(TO_NOTFOUND,response.getError());

    }

    @Test
    public void processOutboundRequest() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);
        phoneNumber.setNumber("12345678");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getFrom()).thenReturn("12345678");
        when(spyRequest.getTo()).thenReturn("1234568");

        Mockito.doReturn("1230987").when(cacheService).get(any(String.class));

        Mockito.doReturn(10).when(cacheService).increment(any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processOutboundRequest(spyRequest);

        Assert.assertEquals(OUTBOUND_END_POINT + SPACE + OK_MESSAGE,response.getMessage());
        Assert.assertEquals("",response.getError());
    }

    @Test
    public void processOutboundRequestInvlaidFrom() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);
        phoneNumber.setNumber("12345678");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getFrom()).thenReturn("1234567");
        when(spyRequest.getTo()).thenReturn("1234568");

        Mockito.doReturn("1230987").when(cacheService).get(any(String.class));

        Mockito.doReturn(10).when(cacheService).increment(any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processOutboundRequest(spyRequest);

        Assert.assertEquals("",response.getMessage());
        Assert.assertEquals(FROM_NOTFOUND,response.getError());
    }

    @Test
    public void processOutboundRequestBlockedTo() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);
        phoneNumber.setNumber("12345678");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getFrom()).thenReturn("12345678");
        when(spyRequest.getTo()).thenReturn("1234568");

        Mockito.doReturn("1234568").when(cacheService).get(any(String.class));

        Mockito.doReturn(10).when(cacheService).increment(any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processOutboundRequest(spyRequest);

        Assert.assertEquals("",response.getMessage());
        Assert.assertEquals(String.format(BLOCKED_MESSAGE,spyRequest.getFrom(),spyRequest.getTo()),response.getError());
    }

    @Test
    public void processOutboundRequestApiCallLimit() throws Exception {
        SMSRequest request = new SMSRequest();
        SMSRequest spyRequest =  Mockito.spy(request);
        Mockito.doNothing().when(spyRequest).validate();
        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
        authenticatedUser.setUserId(1);

        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        ICacheService jedisService = new JedisService("");
        ICacheService cacheService = spy(jedisService);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);
        phoneNumber.setNumber("12345678");
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>() {{
            add(phoneNumber);
        }};

        when(phoneNumberRepository.findPhoneNumberByAccount_id(1)).thenReturn(phoneNumbers);

        when(spyRequest.getFrom()).thenReturn("12345678");
        when(spyRequest.getTo()).thenReturn("1234568");

        Mockito.doReturn("123456568").when(cacheService).get(any(String.class));

        Mockito.doReturn(API_CALL_LIMIT + 1).when(cacheService).increment(any(String.class));

        Processor processor = new Processor(phoneNumberRepository,cacheService);
        SMSResponse response = processor.processOutboundRequest(spyRequest);

        Assert.assertEquals("",response.getMessage());
        Assert.assertEquals(String.format(API_CALL_LIMIT_REACHED,spyRequest.getFrom()),response.getError());
    }
}