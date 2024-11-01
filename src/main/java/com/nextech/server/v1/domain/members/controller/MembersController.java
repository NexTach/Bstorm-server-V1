package com.nextech.server.v1.domain.members.controller;


import com.nextech.server.v1.domain.members.dto.request.PasswordChangeRequest;
import com.nextech.server.v1.domain.members.dto.request.PhoneNumberChangeRequest;
import com.nextech.server.v1.domain.members.dto.request.RoleChangeRequest;
import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.dto.response.PatchProfilePictureResponse;
import com.nextech.server.v1.domain.members.service.*;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MembersController {

    private final AllMembersInquiryService allMembersInquiryService;
    private final ParticularMemberInquiryService particularMemberInquiryService;
    private final CurrentMemberInquiryService currentMemberInquiryService;
    private final CurrentWardMembersInquiryService currentWardMembersInquiryService;
    private final CurrentProtectorMemberInquiryService currentProtectorMemberInquiryService;
    private final PhoneNumberChangeService phoneNumberChangeService;
    private final PasswordChangeService passwordChangeService;
    private final RoleChangeService roleChangeService;
    private final PatchProfilePictureService patchProfilePictureService;
    private final DeleteProfilePictureService deleteProfilePictureService;
    private final ResignService resignService;

    @GetMapping("/all")
    public MembersInquiryListResponse getAllMembers() {
        return allMembersInquiryService.getAllMembers();
    }

    @GetMapping("/{id}")
    public MembersInquiryResponse getParticularMembers(@PathVariable Long id) {
        return particularMemberInquiryService.getParticularMembers(id);
    }

    @GetMapping
    public MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request) {
        return currentMemberInquiryService.getCurrentMemberInfo(request);
    }

    @GetMapping("/ward")
    public MembersInquiryListResponse getWardMembers(HttpServletRequest request) {
        return currentWardMembersInquiryService.getWardMembers(request);
    }

    @GetMapping("/protector")
    public MembersInquiryResponse getProtectorMembers(HttpServletRequest request) {
        return currentProtectorMemberInquiryService.getCurrentProtectorMemberInfo(request);
    }

    @PatchMapping("/phone-number")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePhoneNumber(HttpServletRequest request, @RequestBody PhoneNumberChangeRequest phoneNumberChangeRequest) {
        phoneNumberChangeService.changePhoneNumber(
                request,
                phoneNumberChangeRequest.getCurrentPassword(),
                phoneNumberChangeRequest.getNewPhoneNumber()
        );
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(HttpServletRequest request, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        passwordChangeService.changePassword(
                request,
                passwordChangeRequest.getCurrentPassword(),
                passwordChangeRequest.getNewPassword()
        );
    }

    @PatchMapping("/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRole(HttpServletRequest request, @RequestBody RoleChangeRequest roleChangeRequest) {
        roleChangeService.changeRole(
                request,
                roleChangeRequest.getCurrentPassword(),
                roleChangeRequest.getRole()
        );
    }

    @PatchMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatchProfilePictureResponse> patchProfilePicture(HttpServletRequest request, @RequestParam MultipartFile file) {
        return patchProfilePictureService.patchProfilePicture(request, file);
    }

    @DeleteMapping("/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfilePicture(HttpServletRequest request) {
        deleteProfilePictureService.deleteProfilePicture(request);
    }

    @DeleteMapping("/resign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resign(HttpServletRequest request) {
        resignService.resign(request);
    }
}