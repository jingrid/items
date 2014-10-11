/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.dataobject;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.realpaas.platform.core.file.dataobject.ImageInfo;
import com.realpaas.platform.core.universal.dataobject.ProgramPartyRole;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 * 
 * </pre>
 * 
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	2012-7-28	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( User.OBJECT_TYPE_TREE_CODE )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class User extends ProgramPartyRole {

    private static final long serialVersionUID = 1759601614643428844L;

    public static final String OBJECT_TYPE_TREE_CODE = "USER";
    
    public static final String OBJECT_TYPE_NAME = "User";
    
    private Set<UserAccess> userAccesses;

    private String email;
    
    private Identifier<?> imageId;
    
    private ImageInfo image;
    
    private String imageUrl;
    

    @Column(name = "EMAIL", insertable = true, updatable = true, nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = UserTracking.PROP_USER, cascade = {CascadeType.REMOVE}, orphanRemoval=true)
    public Set<UserAccess> getUserAccesses() {
        return userAccesses;
    }

    public void setUserAccesses(Set<UserAccess> userAccesses) {
        this.userAccesses = userAccesses;
    }

    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = "IMAGE_ID", insertable = true, updatable = true, nullable = true)
    public Identifier<?> getImageId() {
        return imageId;
    }

    public void setImageId(Identifier<?> imageId) {
        this.imageId = imageId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID", insertable = false, updatable = false, nullable = true)
    public ImageInfo getImage() {
        return image;
    }

    public void setImage(ImageInfo image) {
        this.image = image;
    }

    @Column(name = "IMAGE_URL", insertable = true, updatable = true, nullable = true, length = 255)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}
