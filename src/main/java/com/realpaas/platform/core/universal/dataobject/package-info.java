/**
 * Copyright (c) 2012, RealPaaS Technologies Ltd. All rights reserved.
 */

@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(name = GenericIdentifierUserType.USER_TYPE_ID, typeClass = GenericIdentifierUserType.class),
    @org.hibernate.annotations.TypeDef(name = LifeFlagUserType.USER_TYPE_ID, typeClass = LifeFlagUserType.class)
})

package com.realpaas.platform.core.universal.dataobject;

import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;
import com.realpaas.platform.ssos.ext.dataobject.usertype.LifeFlagUserType;

