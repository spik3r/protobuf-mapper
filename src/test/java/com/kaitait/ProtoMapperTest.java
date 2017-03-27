package com.kaitait;

import com.kaitait.testFixtures.domain.camelCase.DomainObject;
import com.kaitait.testFixtures.domain.camelCase.InnerObject;
import com.kaitait.testFixtures.domain.snakeCase.SnakeDomainObject;

import com.kaitait.testFixtures.domain.snakeCase.SnakeInnerObject;
import com.kaitait.testFixtures.proto.ProtoObject;
import javafx.util.Pair;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ProtoMapperTest {

    @Test
    public void createProtoMessageBuilder_camelCaseVariablesInPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException {
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
    public void createProtoMessageBuilder_snakeCaseVariablesInPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException {
        //Given
        SnakeInnerObject domainInnerObject = new SnakeInnerObject(555666333222L, "snakes_are_awesome");
        SnakeDomainObject domainDomainObject
                = new SnakeDomainObject("like python but not", domainInnerObject);

        //When
        ProtoObject.Builder builder = (ProtoObject.Builder) ProtoMapper.createProtoMessageBuilder
                (ProtoObject.class, domainDomainObject);

        ProtoObject requestMessage = builder.build();

        //Then
        assertThat(requestMessage.getLabel(), is("like python but not"));
        assertThat(requestMessage.getInnerObject().getObjectId(), is(555666333222L));
        assertThat(requestMessage.getInnerObject().getSerial(), is("snakes_are_awesome"));
    }

    @Test
    public void createProto_camelCaseVariablesInPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException {
        //Given
        InnerObject domainInnerObject = new InnerObject(123456789L, "someSerial");
        DomainObject domainDomainObject
                = new DomainObject("someDescriptiveLabel", domainInnerObject);

        //When
        ProtoObject protoObject = (ProtoObject) ProtoMapper.createProto
                (ProtoObject.class, domainDomainObject);

        //Then
        assertThat(protoObject.getLabel(), is("someDescriptiveLabel"));
        assertThat(protoObject.getInnerObject().getObjectId(), is(123456789L));
        assertThat(protoObject.getInnerObject().getSerial(), is("someSerial"));
    }

    @Test
    public void createProto_snakeCaseVariablesInPojo_ProtoSuccessfullCreated() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException {
        //Given
        SnakeInnerObject domainInnerObject = new SnakeInnerObject(987654321345L, "corn flakes");
        SnakeDomainObject domainDomainObject
                = new SnakeDomainObject("Awesome label", domainInnerObject);

        //When
        ProtoObject protoObject = (ProtoObject) ProtoMapper.createProto
                (ProtoObject.class, domainDomainObject);


        //Then
        assertThat(protoObject.getLabel(), is("Awesome label"));
        assertThat(protoObject.getInnerObject().getObjectId(), is(987654321345L));
        assertThat(protoObject.getInnerObject().getSerial(), is("corn flakes"));
    }

    @Test(expected = NullPointerException.class)
    public void pojoToProto_inCompatiblePojo_throwsNPE() throws
            NoSuchMethodException,
            IllegalAccessException,
            NoSuchFieldException,
            InstantiationException,
            InvocationTargetException,
            ClassNotFoundException {
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
