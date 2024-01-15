package com.gloddy.server.service.apply;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group_member.exception.AlreadyExistGroupMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionDefinition;

import java.time.LocalDate;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

public class ApplyConcurrencyTest extends GroupServiceTest {

    @Nested
    class DuplicationRequest {
        private Long captainId;
        private Long applyId;

        @BeforeEach
        void given() {

            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

            transactionTemplate.execute(status -> {
                captainId = createUser();
                Long applierId = createUser();

                GroupRequest.Create groupCreateCommand = createGroupCreateCommand(LocalDate.now().plusDays(1), "11:00");
                Long groupId = createGroup(captainId, groupCreateCommand);

                applyId = createApply(applierId, groupId, new ApplyRequest.Create(
                        "introduce",
                        "reason"));
                return null;
            });
        }

        @Test
        @DisplayName("방장의 지원서 승인 더블 클릭으로 인한 중복 요청이 발생하였을 때 동시성을 제어한다.")
        void success() throws InterruptedException {

            int threadCount = 2;
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            Future<?> future1 = executorService.submit(() -> applyService.updateStatusApply(captainId, applyId, Status.APPROVE));
            Future<?> future2 = executorService.submit(() -> applyService.updateStatusApply(captainId, applyId, Status.APPROVE));

            Exception cause = new Exception();

            try {
                future1.get();
                future2.get();
            } catch (ExecutionException e) {
                cause = (Exception) e.getCause();
            }


            Group group = groupJpaRepository.findFirstByOrderByIdDesc();
            assertThat(group.getMemberCount()).isEqualTo(2);
            assertThat(cause).isInstanceOf(AlreadyExistGroupMemberException.class);

            clear();
        }
    }
}
