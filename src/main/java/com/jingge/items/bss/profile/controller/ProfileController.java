/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.items.bss.profile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jingge.platform.common.file.UploadedFile;
import com.jingge.platform.common.file.UploadedFileBackedBySpring;
import com.jingge.platform.common.web.AbstractController;
import com.jingge.platform.core.file.dataobject.AttachmentInfo;
import com.jingge.platform.core.file.dataobject.FileStorageStrategy;
import com.jingge.platform.core.file.dataobject.ImageInfo;
import com.jingge.platform.core.file.dataobject.type.ContentDisposition;
import com.jingge.platform.core.file.service.InboundAttachmentFile;
import com.jingge.platform.core.file.service.AttachmentFileService;
import com.jingge.platform.core.file.service.InboundImageFile;
import com.jingge.platform.core.file.service.ImageFileService;
import com.jingge.platform.core.identity.dataobject.User;
import com.jingge.platform.core.identity.service.UserService;
import com.jingge.platform.core.security.controller.UserHelper;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifierInstantiator;

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
 * 	Aug 8, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@RequestMapping(value = "/bss/profile")
@Controller( "/bss/profile" )
public class ProfileController extends AbstractController{
    
    public ProfileController() {
        super();
        addPath( "bss" ).addPath( "profile" ).buildContext();
    }

    @Autowired
    private UserService userService;
    
    @Autowired
    private GenericIdentifierInstantiator identifierInstantiator;

    @Autowired
    private ImageFileService imageFileService;
    
    @Autowired
    private AttachmentFileService attachmentFileService;
    
    @RequestMapping(value = "/user-detail")
    public String userDetail(Model model) {
        String userId = UserHelper.getUserId();
        if( userId==null ){
            throw new RuntimeException(); //TODO:
        }
        
        User user = userService.loadById( identifierInstantiator.identifier( userId ) );
        if( user==null ){
            throw new RuntimeException(); //TODO:
        }
        
        UserDetail userDetail = new UserDetail();
        userDetail.setDisplayName( user.getName() );
        userDetail.setEmail( user.getEmail() );
        String imageId = user.getImageId()==null ? "" : user.getImageId().toString();
        userDetail.setImageId( imageId );
        userDetail.setImageUrl( user.getImageUrl() );
        
        model.addAttribute( "userDetail", userDetail );
        return renderTo( "user-detail" );
    }

    @RequestMapping(value = "/user-edit", method=RequestMethod.GET)
    public String userEditView(Model model) {
        String userId = UserHelper.getUserId();
        if( userId==null ){
            throw new RuntimeException(); //TODO:
        }
        
        User user = userService.loadById( identifierInstantiator.identifier( userId ) );
        if( user==null ){
            throw new RuntimeException(); //TODO:
        }
        
        UserDetail userDetail = new UserDetail();
        userDetail.setDisplayName( user.getName() );
        userDetail.setEmail( user.getEmail() );
        String imageId = user.getImageId()==null ? "" : user.getImageId().toString();
        userDetail.setImageId( imageId );
        
        model.addAttribute( "userDetail", userDetail );
        return renderTo( "user-edit" );
    }
/*
    @RequestMapping(value = "/user-edit", method=RequestMethod.POST)
    public String userEditSave(
            NativeWebRequest nativeWebRequest, 
            @ModelAttribute("userDetail") UserDetail userDetail,
            BindingResult bindingResult,
            ModelMap modelMap) {
        //TODO: validate input
        String userId = UserHelper.getUserId();
        if( userId==null ){
            throw new RuntimeException(); //TODO:
        }

        UploadedFile uploadedFile = new UploadedFileBackedBySpring( userDetail.getImage() );
        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileSize( uploadedFile.getSize() );
        fileStorage.setOriginalFileName( uploadedFile.getOriginalFilename() );
        fileStorage.setOriginalFileExt( uploadedFile.getOriginalFileExtension() );
        fileInFileSystemService.storeFile( uploadedFile, fileStorage );
        
        User user = userService.loadById( identifierInstantiator.identifier( userId ) );
        if( user==null ){
            throw new RuntimeException(); //TODO:
        }
        user.setName( userDetail.getDisplayName() );
        user.setEmail( userDetail.getEmail() );
        user.setImageId( fileStorage.getId() );
        userService.update( user );
        
        return redirectTo( "user-detail" );
    }
*/

    @RequestMapping(value = "/user-edit", method=RequestMethod.POST)
    public String userEditSave(
            NativeWebRequest nativeWebRequest, 
            @ModelAttribute("userDetail") UserDetail userDetail,
            BindingResult bindingResult,
            ModelMap modelMap) {
        //TODO: validate input
        String userId = UserHelper.getUserId();
        if( userId==null ){
            throw new RuntimeException(); //TODO:
        }

        UploadedFile uploadedFile = new UploadedFileBackedBySpring( userDetail.getImage() );
        InboundImageFile imageFile = new InboundImageFile( uploadedFile );
        imageFile.setContentDisposition( ContentDisposition.Inline );
        imageFile.setStrategyCode( FileStorageStrategy.RDB_CODE );
        ImageInfo imageInfo = imageFileService.create( imageFile );
        
        User user = userService.loadById( identifierInstantiator.identifier( userId ) );
        if( user==null ){
            throw new RuntimeException(); //TODO:
        }
        user.setName( userDetail.getDisplayName() );
        user.setEmail( userDetail.getEmail() );
        user.setImageId( imageInfo.getId() );
        user.setImageUrl( imageInfo.getUrl() );
        userService.update( user );
        
        return redirectTo( "user-detail" );
    }

    @RequestMapping(value = "/file-upload")
    public String upload(HttpServletRequest request, Model model){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        MultipartFile file = multipartRequest.getFile("profile-image");
        UploadedFile uploadedFile = new UploadedFileBackedBySpring( file );
        InboundAttachmentFile attachmentFile = new InboundAttachmentFile( uploadedFile );
        AttachmentInfo attachmentInfo = attachmentFileService.create( attachmentFile );
        model.addAttribute( "fileId", attachmentInfo.getId().toString() );
        model.addAttribute( "fileUrl", attachmentInfo.getUrl() );
        
        return renderTo( "file-detail" );
    }
    
    @RequestMapping(value = "/{view}")
    public String dispath(@PathVariable("view") String view) {
        return renderTo( view );
    }
}