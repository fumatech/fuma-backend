package com.backend.Service;

import java.util.List;

import com.backend.Entity.FaceEncoding;

public interface FaceEncodingService {

    FaceEncoding registerFace(FaceEncoding faceEncoding);

    FaceEncoding updateFace(Long employeeId, String faceDescriptor);

    FaceEncoding getByEmployeeId(Long employeeId);

    List<FaceEncoding> getAllFaceEncodings();

    void deleteFaceEncoding(Long id);

    Long matchFace(String faceDescriptor);

}
