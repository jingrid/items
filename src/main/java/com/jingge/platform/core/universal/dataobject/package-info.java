/**
 * Copyright (c) 2014, JingGe Technologies Ltd. All rights reserved.
 */

@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(name = GenericIdentifierUserType.USER_TYPE_ID, typeClass = GenericIdentifierUserType.class),
    @org.hibernate.annotations.TypeDef(name = LifeFlagUserType.USER_TYPE_ID, typeClass = LifeFlagUserType.class)
})

package com.jingge.platform.core.universal.dataobject;

import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;
import com.realpaas.platform.ssos.ext.dataobject.usertype.LifeFlagUserType;

