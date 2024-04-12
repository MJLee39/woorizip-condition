package waf.fisa.condition.service;

import com.google.protobuf.Empty;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import waf.fisa.condition.dto.ConditionDto;
import waf.fisa.condition.dto.ConditionReqDto;
import waf.fisa.condition.entity.Condition;
import waf.fisa.condition.entity.QCondition;
import waf.fisa.condition.repository.ConditionRepository;
import waf.fisa.condition.repository.ConditionRepositoryCustom;
import waf.fisa.grpc.condition.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@GrpcService
@Slf4j
public class GrpcConditionService extends ConditionServiceGrpc.ConditionServiceImplBase {

    private final ConditionRepository conditionRepository;
    private final ConditionRepositoryCustom conditionRepositoryCustom;
    private final JPAQueryFactory jpaQueryFactory;

    /*
    조건 등록
     */
    @Override
    public void saveCondition(ConditionReq request, StreamObserver<ConditionResp> responseObserver) {
        log.info("** in log, SAVE request.toString: {}", request.toString());

        Condition entity = Condition.toEntity(request);

        Condition condition = conditionRepository.save(entity);

        responseObserver.onNext(ConditionResp.newBuilder()
                .setId(condition.getId())
                .setAccountId(condition.getAccountId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        );

        responseObserver.onCompleted();
    }

    /*
    단일 조건 조회
     */
    @Override
    public void readCondition(ConditionIdReq request, StreamObserver<ConditionResp> responseObserver) {
        log.info("** in log, READ request.toString: {}", request.toString());

        Condition condition = conditionRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);

        responseObserver.onNext(ConditionResp.newBuilder()
                .setId(condition.getId())
                .setAccountId(condition.getAccountId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        );

        responseObserver.onCompleted();
    }

    /*
    전체 조건 조회
     */
    @Override
    public void readAllCondition(Empty request, StreamObserver<ConditionRespList> responseObserver) {
        log.info("** in log, READ ALL request.tpString: no input");

        List<Condition> list = conditionRepository.findAll(); // entity
        log.info("** {}, {}, {}, {}, {}, {}, {}", list.get(0).getId(), list.get(0).getAccountId(), list.get(0).getLocation(),
                 list.get(0).getBuildingType(), list.get(0).getFee(), list.get(0).getMoveInDate(), list.get(0).getHashtag());

        responseObserver.onNext(ConditionRespList.newBuilder()
                .addAllConditions(convertToEntity(list))
                .build()
        );

        responseObserver.onCompleted();
    }

    private static List<waf.fisa.grpc.condition.Condition> convertToEntity(List<Condition> list) {
        return list.stream().map(condition -> waf.fisa.grpc.condition.Condition
                .newBuilder()
                .setId(condition.getId())
                .setAccountId(condition.getAccountId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        ).toList();
    }

    /*
    조건부 조건 조회
     */
    @Override
    public void readByWhereCondition(ConditionReq request, StreamObserver<ConditionRespList> responseObserver) {

        log.info("** in log, READ BY WHERE request.toString: {}", request.toString());

//        LocalDate time  = !request.getMoveInDate().isBlank() ? LocalDate.parse(request.getMoveInDate(), DateTimeFormatter.ISO_DATE): null;
        LocalDate time = !request.getMoveInDate().isBlank() ? LocalDate.parse(request.getMoveInDate(), DateTimeFormatter.ISO_DATE) : null;

        ConditionReqDto conditionReqDto = ConditionReqDto.builder()
                .accountId(request.getAccountId())
                .location(request.getLocation())
                .buildingType(request.getBuildingType())
                .fee(request.getFee())
                .moveInDate(time)
                .hashtag((request.getHashtag()))
                .build();

        Condition condition = conditionReqDto.toEntity();

        List<ConditionDto> list = conditionRepositoryCustom.readByBuilder(condition);

        if (list.size() != 0) {
            for (ConditionDto ele : list) {
                log.info(ele.toString());
            }
        } else {
            log.info("** list is empty.");
        }

        responseObserver.onNext(ConditionRespList.newBuilder()
                .addAllConditions(convertToEntityForReadByWhere(list))
                .build()
        );

        responseObserver.onCompleted();

    }

    private static List<waf.fisa.grpc.condition.Condition> convertToEntityForReadByWhere(List<ConditionDto> list) {
        return list.stream().map(conditionDto -> waf.fisa.grpc.condition.Condition.newBuilder()
                .setId(conditionDto.getId())
                .setAccountId(conditionDto.getAccountId())
                .setLocation(conditionDto.getLocation())
                .setBuildingType(conditionDto.getBuildingType())
                .setFee(conditionDto.getFee())
                .setMoveInDate(conditionDto.getMoveInDate().toString())
                .setHashtag(conditionDto.getHashtag())
                .build()
        ).toList();
    }


    /*
    조건 수정
     */
    @Override
    public void updateCondition(ConditionReqWithId request, StreamObserver<ConditionResp> responseObserver) {
        log.info("** in log, UPDATE request.toString: {}", request.toString());

        Condition condition = conditionRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);

        if (!condition.getLocation().equals(request.getLocation())) {
            condition.updateLocation(request.getLocation());
        }

        if (!condition.getBuildingType().equals(request.getBuildingType())) {
            condition.updateBuildingType(request.getBuildingType());
        }

        if (condition.getFee() != request.getFee()) {
            condition.updateFee(request.getFee());
        }

        LocalDate input = LocalDate.parse(request.getMoveInDate(), DateTimeFormatter.ISO_DATE);
        if (!condition.getMoveInDate().equals(input)) {
            condition.updateMoveInDate(input);
        }

        if (!condition.getHashtag().equals(request.getHashtag())) {
            condition.updateHashtag(request.getHashtag());
        }

        responseObserver.onNext(ConditionResp.newBuilder()
                .setId(condition.getId())
                .setLocation(condition.getLocation())
                .setBuildingType(condition.getBuildingType())
                .setAccountId(condition.getAccountId())
                .setFee(condition.getFee())
                .setMoveInDate(condition.getMoveInDate().toString())
                .setHashtag(condition.getHashtag())
                .build()
        );

        responseObserver.onCompleted();
    }

    /*
    조건 삭제
     */
    @Override
    public void deleteCondition(ConditionIdReq request, StreamObserver<ConditionDeleteResp> responseObserver) {
        log.info("** in log, DELETE request.toString: {}", request.toString());

        if (!conditionRepository.existsById(request.getId())) {
            responseObserver.onNext(ConditionDeleteResp.newBuilder()
                    .setMsg("It doesnt exist.")
                    .build());
            responseObserver.onCompleted();
        } else {
            conditionRepository.deleteById(request.getId());
            responseObserver.onNext(ConditionDeleteResp.newBuilder()
                    .setMsg("It was deleted.")
                    .build());
            responseObserver.onCompleted();
        }
    }
}
