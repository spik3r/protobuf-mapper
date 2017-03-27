# protobuf-mapper

Maps from Pojo to Protobuf generated class by calling: `ProtoMapper.createProto(Class.class, object);`

The Ptotobuf generated class will have all the variables assigned to the values variables with the same name have in the domainObject.

It should also work for non protobuf generated classes as long as they
 have a builder and follow the same naming convention.
 - the proto class should have the same variable as the domain class be with an underscore at the end
 -- eg. if the domainObject has a variable: `long objectId` the proto class should have:` long objectId_`
 - the proto class must have a builder with a method called newBuilder and a setter for any objects
-- eg. `newBuilder()` and `setObjectId(long value)` methods.

See `package com.kaitait.testFixtures.proto` for an example.
