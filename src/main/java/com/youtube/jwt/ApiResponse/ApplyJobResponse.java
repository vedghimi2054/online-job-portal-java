package com.youtube.jwt.ApiResponse;

import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.payload.UploadFileResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyJobResponse {
    UploadFileResponse uploadFileResponse;
    ApplyJob applyJob;
}
