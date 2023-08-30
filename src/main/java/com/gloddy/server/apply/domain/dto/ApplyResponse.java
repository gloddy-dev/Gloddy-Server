package com.gloddy.server.apply.domain.dto;

import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplyResponse {

    @Getter
    @AllArgsConstructor
    @Schema(name = "ApplyCreateResponse")
    public static class Create {
        private Long applyId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ApplyGetAllResponse")
    public static class GetAll {
        private int totalCount;
        private List<GetOne> applies;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ApplyGetOneResponse")
    public static class GetOne {
        private Long userId;
        private Boolean isCertifiedStudent;
        private String userNickname;
        private String userImageUrl;
        private ReliabilityLevel reliabilityLevel;
        private Long applyId;
        private String introduce;
        private String reason;
    }
}
