package com.nextech.server.v1.domain.members.controller;


import com.nextech.server.v1.domain.members.dto.request.PasswordChangeRequest;
import com.nextech.server.v1.domain.members.dto.request.PhoneNumberChangeRequest;
import com.nextech.server.v1.domain.members.dto.request.RoleChangeRequest;
import com.nextech.server.v1.domain.members.dto.response.PatchProfilePictureResponse;
import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.service.*;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "계정", description = "계정관련 API")
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

    @Operation(summary = "AllMembersInquiry", description = "모든 회원 조회")
    @GetMapping("/all")
    public MembersInquiryListResponse getAllMembers() {
        return allMembersInquiryService.getAllMembers();
    }

    @Operation(summary = "ParticularMemberInquiry", description = "특정 회원 조회")
    @GetMapping("/{id}")
    public MembersInquiryResponse getParticularMembers(@PathVariable Long id) {
        return particularMemberInquiryService.getParticularMembers(id);
    }

    @Operation(summary = "CurrentMemberInquiry", description = "현재 로그인한 사용자 정보 조회")
    @GetMapping
    public MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request) {
        return currentMemberInquiryService.getCurrentMemberInfo(request);
    }

    @Operation(summary = "WardMembersInquiry", description = "피보호자 정보 조회")
    @GetMapping("/ward")
    public MembersInquiryListResponse getWardMembers(HttpServletRequest request) {
        return currentWardMembersInquiryService.getWardMembers(request);
    }

    @Operation(summary = "ProtectorMemberInquiry", description = "보호자 정보 조회")
    @GetMapping("/protector")
    public MembersInquiryResponse getProtectorMembers(HttpServletRequest request) {
        return currentProtectorMemberInquiryService.getCurrentProtectorMemberInfo(request);
    }

    @Operation(summary = "PhoneNumberChange", description = "전화번호 변경")
    @PatchMapping("/phone-number")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePhoneNumber(HttpServletRequest request, @RequestBody PhoneNumberChangeRequest phoneNumberChangeRequest) {
        phoneNumberChangeService.changePhoneNumber(
                request,
                phoneNumberChangeRequest.getCurrentPassword(),
                phoneNumberChangeRequest.getNewPhoneNumber()
        );
    }

    @Operation(summary = "PasswordChange", description = "비밀번호 변경")
    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(HttpServletRequest request, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        passwordChangeService.changePassword(
                request,
                passwordChangeRequest.getCurrentPassword(),
                passwordChangeRequest.getNewPassword()
        );
    }

    @Operation(summary = "RoleChange", description = "역할 변경")
    @PatchMapping("/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRole(HttpServletRequest request, @RequestBody RoleChangeRequest roleChangeRequest) {
        roleChangeService.changeRole(
                request,
                roleChangeRequest.getCurrentPassword(),
                roleChangeRequest.getRole()
        );
    }

    @Operation(summary = "ProfilePicturePatch", description = "프로필 사진 추가/변경")
    @PatchMapping(value = "/profile-picture",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatchProfilePictureResponse> patchProfilePicture(HttpServletRequest request, @RequestParam MultipartFile file) {
        return patchProfilePictureService.patchProfilePicture(request, file);
    }

    @Operation(summary = "ProfilePictureDelete", description = "프로필 사진 삭제")
    @DeleteMapping("/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfilePicture(HttpServletRequest request) {
        deleteProfilePictureService.deleteProfilePicture(request);
    }

    @Operation(summary = "Resign", description = "회원 탈퇴")
    @DeleteMapping("/resign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resign(HttpServletRequest request) {
        resignService.resign(request);
    }
}