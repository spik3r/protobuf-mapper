package com.kaitait;

import com.kaitait.testClasses.domain.Controller;
import com.kaitait.testClasses.domain.ControllerId;
import com.kaitait.testClasses.proto.ControllerIdRequestMessage;
import com.kaitait.testClasses.proto.ControllerRequestMessage;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ProtoMapperTest {
    
    @Test
    public void pojoToProto_validPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException
    {
        //Given
        ControllerId domainControllerId = new ControllerId(123456789, "someSerial");
        Controller domainController = new Controller("someDescriptiveLabel", domainControllerId);
        
        //When
//        ControllerRequestMessage requestMessage = (ControllerRequestMessage) ProtoMapper.createProtoMessageObject
//                (ControllerRequestMessage.class, domainController);
        ControllerRequestMessage.Builder builder = (ControllerRequestMessage.Builder) ProtoMapper
                .createProtoMessageObject2
                (ControllerRequestMessage.class, domainController);
        
        ControllerRequestMessage requestMessage = builder.build();
            
                //Then
        assertThat(requestMessage.getLabel(), is("someDescriptiveLabel"));
//        assertThat(requestMessage.getLabel(), is("someDescriptiveLabel"));
    }
}
