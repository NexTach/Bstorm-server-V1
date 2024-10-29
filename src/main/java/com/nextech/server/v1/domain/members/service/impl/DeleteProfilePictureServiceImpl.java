package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.DeleteProfilePictureService;
import com.nextech.server.v1.global.aws.service.FileDeleteService;
import com.nextech.server.v1.global.exception.FileDeletionFailedException;
import com.nextech.server.v1.global.exception.ProfilePictureNotFoundException;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProfilePictureServiceImpl implements DeleteProfilePictureService {

    private final MemberAuthService memberAuthService;
    private final FileDeleteService fileDeleteService;
    private final MemberRepository memberRepository;
    @Value("${AWS_S3_BUCKET}")
    private String BUCKET_NAME;

    @Override
    @Transactional
    public void deleteProfilePicture(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        if (member.getProfilePictureURI() == null && member.getProfilePictureName() == null) {
            throw new ProfilePictureNotFoundException("Profile picture not found");
        }
        try {
            fileDeleteService.deleteFile(member.getProfilePictureName(), BUCKET_NAME);
        } catch (Exception e) {
            throw new FileDeletionFailedException("Failed to delete profile picture");
        }
        member.setProfilePictureURI(null);
        member.setProfilePictureName(null);
        memberRepository.save(member);
    }
}