package com.kaitait;

import com.kaitait.testFixtures.domain.DomainObject;
import com.kaitait.testFixtures.domain.InnerObject;
import com.kaitait.testFixtures.proto.ProtoObject;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import javafx.util.Pair;
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
        InnerObject domainInnerObject = new InnerObject(123456789L, "someSerial");
        DomainObject domainDomainObject
                = new DomainObject("someDescriptiveLabel", domainInnerObject);
        
        //When
        ProtoObject.Builder builder = (ProtoObject.Builder) ProtoMapper.createProtoMessageBuilder
                (ProtoObject.class, domainDomainObject);
        
        ProtoObject requestMessage = builder.build();
            
                //Then
        assertThat(requestMessage.getLabel(), is("someDescriptiveLabel"));
        assertThat(requestMessage.getInnerObject().getObjectId(), is(123456789L));
        assertThat(requestMessage.getInnerObject().getSerial(), is("someSerial"));
    }
    
    @Test
    public void createProtoMessageObject_validPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException
    {
        //Given
        InnerObject domainInnerObject = new InnerObject(123456789L, "someSerial");
        DomainObject domainDomainObject
                = new DomainObject("someDescriptiveLabel", domainInnerObject);
        
        //When
        ProtoObject protoObject = (ProtoObject) ProtoMapper.createProtoMessageObject
                (ProtoObject.class, domainDomainObject);
        
//        ProtoObject requestMessage = builder.build();
        
        //Then
        assertThat(protoObject.getLabel(), is("someDescriptiveLabel"));
        assertThat(protoObject.getInnerObject().getObjectId(), is(123456789L));
        assertThat(protoObject.getInnerObject().getSerial(), is("someSerial"));
    }
    
    @Test(expected=NullPointerException.class)
    public void pojoToProto_inCompatiblePojo_throwsNPE() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException
    {
        //Given
        Pair domainInnerObject = new Pair(123456789L, "someSerial");
        Pair domainDomainObject
                = new Pair("someDescriptiveLabel", domainInnerObject);
        
        //When
        ProtoObject.Builder builder = (ProtoObject.Builder) ProtoMapper.createProtoMessageBuilder
                (ProtoObject.class, domainDomainObject);
        
        ProtoObject requestMessage = builder.build();
    }
}
